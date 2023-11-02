package pl.gda.pg.eti.kask.sa.alchemists.behaviours.alchemist;

import jade.content.onto.basic.Action;
import jade.core.AID;
import pl.gda.pg.eti.kask.sa.alchemists.agents.Alchemist;
import pl.gda.pg.eti.kask.sa.alchemists.behaviours.SendOfferBechaviour;
import pl.gda.pg.eti.kask.sa.alchemists.behaviours.WaitingBehaviour;
import pl.gda.pg.eti.kask.sa.alchemists.ontology.GivePotionOffert;
import pl.gda.pg.eti.kask.sa.alchemists.ontology.Potion;
import pl.gda.pg.eti.kask.sa.alchemists.ontology.SellPotion;

/**
 *
 * @author psysiu
 */
public class AlchemistBehaviour extends WaitingBehaviour<Alchemist>{

    public AlchemistBehaviour(Alchemist agent) {
        super(agent);
    }

    @Override
    protected void action(Action action, String conversationId, AID participant) {
        if (action.getAction() instanceof SellPotion) {
            myAgent.addBehaviour(new SellPotionBehaviour(myAgent, (SellPotion) action.getAction(), conversationId, participant));
        } else if (action.getAction() instanceof GivePotionOffert){
            myAgent.addBehaviour(new SendOfferBechaviour<Potion>(myAgent, ((GivePotionOffert) action.getAction()).getItem(), conversationId, participant));
        }
    }
    
}
