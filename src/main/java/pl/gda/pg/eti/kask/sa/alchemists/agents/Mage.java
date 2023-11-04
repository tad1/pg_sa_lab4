package pl.gda.pg.eti.kask.sa.alchemists.agents;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.swing.SwingUtilities;

import jade.content.AgentAction;
import jade.content.Concept;
import jade.content.Predicate;
import jade.content.onto.basic.Result;
import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.ParallelBehaviour;
import jade.core.behaviours.SequentialBehaviour;
import jade.core.behaviours.WakerBehaviour;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import lombok.Getter;
import pl.gda.pg.eti.kask.sa.alchemists.OffertProposition;
import pl.gda.pg.eti.kask.sa.alchemists.behaviours.FindServiceBehaviour;
import pl.gda.pg.eti.kask.sa.alchemists.behaviours.ReceiveResultBehaviour;
import pl.gda.pg.eti.kask.sa.alchemists.behaviours.RequestActionBehaviour;
import pl.gda.pg.eti.kask.sa.alchemists.behaviours.mage.CheckOffertsBehaviour;
import pl.gda.pg.eti.kask.sa.alchemists.behaviours.mage.MageBehaviour;
import pl.gda.pg.eti.kask.sa.alchemists.behaviours.mage.RequestHerbBehaviour;
import pl.gda.pg.eti.kask.sa.alchemists.behaviours.mage.RequestPotionBehaviour;
import pl.gda.pg.eti.kask.sa.alchemists.gui.MageGUI;
import pl.gda.pg.eti.kask.sa.alchemists.ontology.Essence;
import pl.gda.pg.eti.kask.sa.alchemists.ontology.GiveEssenceOffert;
import pl.gda.pg.eti.kask.sa.alchemists.ontology.GiveHerbOffert;
import pl.gda.pg.eti.kask.sa.alchemists.ontology.GivePotionOffert;
import pl.gda.pg.eti.kask.sa.alchemists.ontology.Herb;
import pl.gda.pg.eti.kask.sa.alchemists.ontology.Offert;
import pl.gda.pg.eti.kask.sa.alchemists.ontology.Potion;
import pl.gda.pg.eti.kask.sa.alchemists.ontology.SellHerb;
import pl.gda.pg.eti.kask.sa.alchemists.ontology.SellPotion;

/**
 *
 * @author psysiu
 */
public class Mage extends BaseAgent {

    public Mage() {
    }

    private transient MageGUI myGui;

    @Getter
    private int money;
    @Getter
    private HashMap<Potion, Integer> requiredPotions;
    @Getter
    private HashMap<Herb, Integer> requiredHerbs;
    @Getter
    private HashMap<Essence, Integer> requiredEssences;

    @Getter
    private HashMap<Potion, ArrayList<OffertProposition>> potionOfferts;
    @Getter
    private HashMap<Herb, ArrayList<OffertProposition>> herbOfferts;
    @Getter
    private HashMap<Essence, ArrayList<OffertProposition>> essenceOfferts;

    @Override
    protected void setup() {
        super.setup();
        
        // money, potions, herbs, elements
        Object[] args = getArguments();
        String potions_s = args[1].toString();
        String herbs_s = args[2].toString();
        String essences_s = args[3].toString();
        
        
        this.money = Integer.parseInt(args[0].toString());
        String[] potions = potions_s.split("\\|");
        String[] herbs = herbs_s.split("\\|");
        String[] essences = essences_s.split("\\|");
        
        requiredEssences = new HashMap<>();
        requiredPotions = new HashMap<>();
        requiredHerbs = new HashMap<>();
        
        potionOfferts = new HashMap<>();
        essenceOfferts = new HashMap<>();
        herbOfferts = new HashMap<>();
        
        countPopulateHashMap(requiredPotions, potions, s -> new Potion(s));
        countPopulateHashMap(requiredHerbs, herbs, s -> new Herb(s));
        countPopulateHashMap(requiredEssences, essences, s -> new Essence(s));
        
        myGui = new MageGUI(this);
        myGui.showCorrect();
        
        ParallelBehaviour get_all_behaviour = new ParallelBehaviour(this, ParallelBehaviour.WHEN_ALL);
        ParallelBehaviour request_all = new ParallelBehaviour(this, ParallelBehaviour.WHEN_ALL);
        ParallelBehaviour recieve_all = new ParallelBehaviour(this, ParallelBehaviour.WHEN_ALL);
        get_all_behaviour.addSubBehaviour(getFetchOffertBehaviour("alchemist", requiredPotions, potionOfferts, i -> new GivePotionOffert(i)));
        get_all_behaviour.addSubBehaviour(getFetchOffertBehaviour("herbalist", requiredHerbs, herbOfferts, i -> new GiveHerbOffert(i)));
        get_all_behaviour.addSubBehaviour(getFetchOffertBehaviour("elementarist", requiredEssences, essenceOfferts, i -> new GiveEssenceOffert(i)));

        SequentialBehaviour behaviour = new SequentialBehaviour(this);
        behaviour.addSubBehaviour(get_all_behaviour);
        behaviour.addSubBehaviour(request_all);
        behaviour.addSubBehaviour(recieve_all);
        behaviour.addSubBehaviour(new CheckOffertsBehaviour(this));

        // on finish, 

        // addBehaviour(new MageBehaviour(get_all_behaviour, this));
        addBehaviour(new WakerBehaviour(this, 1000) {
            protected void onWake() {
                addBehaviour(behaviour);
            };
        });
    }


    public <T extends Concept> void payForItems(T item, OffertProposition offert, int count, HashMap<T, Integer> required){
        assert count <= offert.getOffert().getQuantity();
        if(required.get(item) == null){
            return;
        }
        
        int left_required = required.get(item);
        count = Math.min(count, left_required);

        int price = offert.getOffert().getPrice() * count;
        
        left_required -= count;
        if(left_required == 0){
            required.remove(item);            
        } else {
            required.put(item, left_required - count);
        }
        money -= price;



        this.reloadGUI();

        System.out.println("Payed "+price+" for "+count+ "x"+item);
    }

    private <T extends Concept, R extends AgentAction> Behaviour getFetchOffertBehaviour(String service_name, HashMap<T,Integer> required, HashMap<T,ArrayList<OffertProposition>> offerts, Function<T,R> itemToAction){
        
        
        return new FindServiceBehaviour(this, service_name) {
  
            @Override
            protected void onResult(DFAgentDescription[] services) {
                if (services != null && services.length > 0) {
  
                    for (DFAgentDescription service : services) {
                        AID name = service.getName();
                        for (T item : required.keySet()) {
                            System.out.print("requesting "+item);
                            R action = itemToAction.apply(item);
                            RequestActionBehaviour<R, Mage> request = new RequestActionBehaviour<R,Mage>(Mage.this, name, action) {
  
                                @Override
                                protected ReceiveResultBehaviour<Mage> createResultBehaviour(String conversationId) {
                                    return new ReceiveResultBehaviour<Mage>(myAgent, conversationId) {
  

                                        @Override
                                        protected void handleResult(Predicate predicate, AID participant) {
                                            if (predicate instanceof Result) {
                                                Offert offert = (Offert)((Result) predicate).getValue();
                                                myAgent.addOffert(offerts, item, offert, name);
                                                myAgent.addBehaviour(new CheckOffertsBehaviour(myAgent));
  
                                            } else {
                                                System.out.println("No result");
                                            }
                                        }
                                        
                                    };
                                }
                                
                            };
                            myAgent.addBehaviour(request);
                        }
                    }
                }
            }
        };
    }

    private <T> void countPopulateHashMap(HashMap<T, Integer> map, String[] list, Function<String, T> convert){
        for (String string : list) {
            if(string == "")
                continue;
            T item = convert.apply(string);
            int count = map.getOrDefault(item, 0);
            map.put(item, count+1);
        }
    }

    private <T extends Concept>void addOffert(HashMap<T, ArrayList<OffertProposition>> map, T item, Offert offert, AID seller){
        OffertProposition proposition = new OffertProposition(offert, seller);

        ArrayList<OffertProposition> list = map.getOrDefault(item, new ArrayList<>());
        list.add(proposition);
        list.sort(new Comparator<OffertProposition>() {

            @Override
            public int compare(OffertProposition arg0, OffertProposition arg1) {
                if(arg0.getOffert().getPrice() < arg1.getOffert().getPrice()){
                    return -1;
                }
                if(arg0.getOffert().getQuantity() < arg1.getOffert().getQuantity()){
                    return -1;
                }
                if(arg0.getOffert().getQuantity() == arg1.getOffert().getQuantity() && arg0.getOffert().getPrice() == arg1.getOffert().getPrice()){
                    return 0;
                }
                return 1;
            }
            
        });
        map.put(item, list);
    }

    protected void takeDown() {
        this.disposeGUI();
     }
  
     public MageGUI getGui() {
        return this.myGui;
     }
  
     protected void beforeMove() {
        this.disposeGUI();
     }
  
     protected void afterMove() {
        this.restoreGUI();
     }
  
     protected void afterClone() {
        this.restoreGUI();
     }
  
     public void afterLoad() {
        this.restoreGUI();
     }
  
     public void beforeFreeze() {
        this.disposeGUI();
     }
  
     public void afterThaw() {
        this.restoreGUI();
     }
  
     public void beforeReload() {
        this.disposeGUI();
     }
  
     public void afterReload() {
        this.restoreGUI();
     }

    public void reloadGUI(){
        disposeGUI();
        restoreGUI();
    }

    private void restoreGUI() {
        this.myGui = new MageGUI(this);
        this.myGui.showCorrect();
     }
  
    private void disposeGUI() {
		if(myGui != null) {
			final MageGUI gui = myGui;
			myGui = null;
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					gui.setVisible(false);
					gui.dispose();
				}
			});
		}
	}

}
