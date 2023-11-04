package pl.gda.pg.eti.kask.sa.alchemists.behaviours.elementarist;

import jade.content.onto.basic.Action;
import jade.core.AID;
import pl.gda.pg.eti.kask.sa.alchemists.agents.Elementarist;
import pl.gda.pg.eti.kask.sa.alchemists.behaviours.SendOfferBechaviour;
import pl.gda.pg.eti.kask.sa.alchemists.behaviours.WaitingBehaviour;
import pl.gda.pg.eti.kask.sa.alchemists.ontology.Essence;
import pl.gda.pg.eti.kask.sa.alchemists.ontology.GiveEssenceOffert;
import pl.gda.pg.eti.kask.sa.alchemists.ontology.GivePotionOffert;
import pl.gda.pg.eti.kask.sa.alchemists.ontology.SellEssence;
import pl.gda.pg.eti.kask.sa.alchemists.ontology.SellPotion;

/**
 *
 * @author psysiu
 */
public class ElementaristBehaviour extends WaitingBehaviour<Elementarist>{

    public ElementaristBehaviour(Elementarist agent) {
        super(agent);
    }

    @Override
    protected void action(Action action, String conversationId, AID participant) {
        if (action.getAction() instanceof SellEssence) {
            myAgent.addBehaviour(new SellElementBehaviour(myAgent, (SellEssence) action.getAction(), conversationId, participant));
        } else if (action.getAction() instanceof GiveEssenceOffert){
            myAgent.addBehaviour(new SendOfferBechaviour<Essence, GiveEssenceOffert>(myAgent, (GiveEssenceOffert)action.getAction(),((GiveEssenceOffert) action.getAction()).getItem(), conversationId, participant));
        }
    }
    
}
