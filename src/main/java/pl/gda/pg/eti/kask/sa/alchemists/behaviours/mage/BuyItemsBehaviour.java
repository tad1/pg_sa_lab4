package pl.gda.pg.eti.kask.sa.alchemists.behaviours.mage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Function;

import jade.content.AgentAction;
import jade.content.Concept;
import jade.content.Predicate;
import jade.content.onto.basic.Result;
import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.ParallelBehaviour;
import jade.core.behaviours.SequentialBehaviour;
import pl.gda.pg.eti.kask.sa.alchemists.OffertProposition;
import pl.gda.pg.eti.kask.sa.alchemists.agents.BaseAgent;
import pl.gda.pg.eti.kask.sa.alchemists.agents.Mage;
import pl.gda.pg.eti.kask.sa.alchemists.behaviours.ReceiveResultBehaviour;
import pl.gda.pg.eti.kask.sa.alchemists.behaviours.RequestActionBehaviour;
import pl.gda.pg.eti.kask.sa.alchemists.ontology.Item;
import pl.gda.pg.eti.kask.sa.alchemists.ontology.SellEssence;
import pl.gda.pg.eti.kask.sa.alchemists.ontology.SellHerb;
import pl.gda.pg.eti.kask.sa.alchemists.ontology.SellPotion;

public class BuyItemsBehaviour extends OneShotBehaviour {

    Mage myAgent;
    ParallelBehaviour requestAll;

    BuyItemsBehaviour(Mage agent){
        super(agent);
        myAgent = agent;
        requestAll = new ParallelBehaviour(myAgent, ParallelBehaviour.WHEN_ALL);
    }

    @Override
    public void action() {

        buyAllRequired(myAgent.getRequiredEssences(), myAgent.getEssenceOfferts(), (i,c) -> new SellEssence(i, c), requestAll);
        buyAllRequired(myAgent.getRequiredPotions(), myAgent.getPotionOfferts(), (i,c) -> new SellPotion(i, c), requestAll);
        buyAllRequired(myAgent.getRequiredHerbs(), myAgent.getHerbOfferts(), (i,c) -> new SellHerb(i, c), requestAll);

        if(getParent() != null && getParent() instanceof SequentialBehaviour){
            ((SequentialBehaviour)getParent()).addSubBehaviour(requestAll);
        } else{
            myAgent.addBehaviour(requestAll);
        }
    }

    @FunctionalInterface
    public interface Function2<T,U,R>{
        public R apply(T t, U u);
    }

    private <T extends Item, R extends AgentAction> void buyAllRequired(HashMap<T, Integer> required, HashMap<T, ArrayList<OffertProposition>> offerts, Function2<T,Integer,R> itemCountToAction, ParallelBehaviour parent){
        for (T item : required.keySet()) {
            if(offerts.get(item) == null){
                return;
            }

            for (OffertProposition offert : offerts.get(item)) {
                int count = Math.min(required.get(item), offert.getOffert().getQuantity());
                R action = itemCountToAction.apply(item, count);
                parent.addSubBehaviour(new RequestActionBehaviour<R,Mage>(myAgent, offert.getSeller(), action) {

                    @Override
                    protected ReceiveResultBehaviour createResultBehaviour(String conversationId) {
                        return new ReceiveResultBehaviour<Mage>(myAgent, conversationId) {

                            @Override
                            protected void handleResult(Predicate predicate, AID participant) {
                                if(predicate instanceof Result){
                                    myAgent.payForItems(item, offert, count, required);
                                }
                            }
                        };
                    }
                    
                });
                
            }
        }
    }
}
