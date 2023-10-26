package pl.gda.pg.eti.kask.sa.alchemists.behaviours.alchemist;

import jade.content.Predicate;
import jade.content.onto.basic.Result;
import jade.core.AID;
import pl.gda.pg.eti.kask.sa.alchemists.agents.Alchemist;
import pl.gda.pg.eti.kask.sa.alchemists.behaviours.ActionBehaviour;
import pl.gda.pg.eti.kask.sa.alchemists.ontology.SellPotion;

/**
 *
 * @author psysiu
 */
public class SellPotionBehaviour extends ActionBehaviour<SellPotion, Alchemist> {

    public SellPotionBehaviour(Alchemist agent, SellPotion action, String conversationId, AID participant) {
        super(agent, action, conversationId, participant);
    }

    @Override
    protected Predicate performAction() {
        if (myAgent.getPotions().contains(action.getPotion())) {
            return new Result(action, action.getPotion());
        } else {
            return null;    
        }
    }
    
}
