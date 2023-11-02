package pl.gda.pg.eti.kask.sa.alchemists.agents;

import java.util.HashMap;
import java.util.Map;

import jade.core.AID;
import jade.core.behaviours.ParallelBehaviour;
import jade.core.behaviours.SequentialBehaviour;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import pl.gda.pg.eti.kask.sa.alchemists.behaviours.FindServiceBehaviour;
import pl.gda.pg.eti.kask.sa.alchemists.behaviours.mage.MageBehaviour;
import pl.gda.pg.eti.kask.sa.alchemists.behaviours.mage.RequestHerbBehaviour;
import pl.gda.pg.eti.kask.sa.alchemists.behaviours.mage.RequestPotionBehaviour;
import pl.gda.pg.eti.kask.sa.alchemists.ontology.Herb;
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

    private int money;
    private HashMap<String, Integer> requiredPotions;
    private HashMap<String, Integer> requiredHerbs;
    private HashMap<String, Integer> requiredElements;

    private HashMap<String, Integer> potionsOfferts;
    private HashMap<String, Integer> herbsOfferts;
    private HashMap<String, Integer> elementsOfferts;

    @Override
    protected void setup() {
        super.setup();
        ParallelBehaviour get_all_behaviour = new ParallelBehaviour(this, ParallelBehaviour.WHEN_ALL);

        // money, potions, herbs, elements
        Object[] args = getArguments();
        String potions_s = args[1].toString();
        String herbs_s = args[2].toString();
        String elements_s = args[3].toString();
        

        this.money = Integer.parseInt(args[0].toString());
        String[] potions = potions_s.split(";");
        String[] herbs = herbs_s.split(";");
        String[] elments = elements_s.split(";");

        

        get_all_behaviour.addSubBehaviour(new FindServiceBehaviour(this, "alchemist") {

            @Override
            protected void onResult(DFAgentDescription[] services) {
                if (services != null && services.length > 0) {
                    AID alchemist = services[0].getName();

                    SequentialBehaviour parent = (SequentialBehaviour) getParent();
                    for (String potion : potions) {
                        // SellPotion action = new SellPotion(new Potion(potion));
                        // RequestPotionBehaviour request = new RequestPotionBehaviour(Mage.this, alchemist, action);
                        
                        // parent.addSubBehaviour(request);
                    }

                }
            }
        });

        get_all_behaviour.addSubBehaviour(new FindServiceBehaviour(this, "herbalist") {

            @Override
            protected void onResult(DFAgentDescription[] services) {
                if (services != null && services.length > 0) {
                    // AID herbalist = services[0].getName();
                    // SellHerb action = new SellHerb(new Herb("Peacebloom"));
                    // RequestHerbBehaviour request = new RequestHerbBehaviour(Mage.this, herbalist, action);
                    // ((SequentialBehaviour) getParent()).addSubBehaviour(request);

                }
            }
        });

        addBehaviour(get_all_behaviour);
        addBehaviour(new MageBehaviour(get_all_behaviour, this));
    }

}
