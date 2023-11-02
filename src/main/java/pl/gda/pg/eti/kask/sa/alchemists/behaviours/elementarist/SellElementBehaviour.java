package pl.gda.pg.eti.kask.sa.alchemists.behaviours.elementarist;



import jade.content.Predicate;
import jade.content.onto.basic.Result;
import jade.core.AID;
import pl.gda.pg.eti.kask.sa.alchemists.agents.Elementarist;
import pl.gda.pg.eti.kask.sa.alchemists.behaviours.ActionBehaviour;
import pl.gda.pg.eti.kask.sa.alchemists.ontology.SellEssence;

/**
 *
 * @author psysiu
 */
public class SellElementBehaviour extends ActionBehaviour<SellEssence, Elementarist> {

    public SellElementBehaviour(Elementarist agent, SellEssence action, String conversationId, AID participant) {
        super(agent, action, conversationId, participant);
    }

    @Override
    protected Predicate performAction() {
        if (myAgent.haveEnought(action.getEssence(), action.getNumber())) {
            myAgent.sell(action.getEssence(), action.getNumber());
            return new Result(action, action.getEssence());
        } else {
            return null;    
        }
    }
    
}

