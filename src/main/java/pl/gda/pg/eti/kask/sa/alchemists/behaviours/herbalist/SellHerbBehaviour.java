package pl.gda.pg.eti.kask.sa.alchemists.behaviours.herbalist;

import jade.content.Predicate;
import jade.content.onto.basic.Result;
import jade.core.AID;
import pl.gda.pg.eti.kask.sa.alchemists.agents.Herbalist;
import pl.gda.pg.eti.kask.sa.alchemists.behaviours.ActionBehaviour;
import pl.gda.pg.eti.kask.sa.alchemists.ontology.SellHerb;

/**
 *
 * @author psysiu
 */
public class SellHerbBehaviour extends ActionBehaviour<SellHerb, Herbalist> {

    public SellHerbBehaviour(Herbalist agent, SellHerb action, String conversationId, AID participant) {
        super(agent, action, conversationId, participant);
    }

    @Override
    protected Predicate performAction() {
        if (myAgent.getHerbs().contains(action.getHerb())) {
            return new Result(action, action.getHerb());
        } else {
            return null;    
        }
    }
    
}
