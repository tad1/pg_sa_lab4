package pl.gda.pg.eti.kask.sa.alchemists.behaviours.herbalist;

import jade.content.onto.basic.Action;
import jade.core.AID;
import pl.gda.pg.eti.kask.sa.alchemists.agents.Herbalist;
import pl.gda.pg.eti.kask.sa.alchemists.behaviours.SendOfferBechaviour;
import pl.gda.pg.eti.kask.sa.alchemists.behaviours.WaitingBehaviour;
import pl.gda.pg.eti.kask.sa.alchemists.ontology.GiveHerbOffert;
import pl.gda.pg.eti.kask.sa.alchemists.ontology.Herb;
import pl.gda.pg.eti.kask.sa.alchemists.ontology.SellHerb;

/**
 *
 * @author psysiu
 */
public class HerbalistBehaviour extends WaitingBehaviour<Herbalist>{

    public HerbalistBehaviour(Herbalist agent) {
        super(agent);
    }

    @Override
    protected void action(Action action, String conversationId, AID participant) {
        if (action.getAction() instanceof SellHerb) {
            myAgent.addBehaviour(new SellHerbBehaviour(myAgent, (SellHerb) action.getAction(), conversationId, participant));
        }  else if (action.getAction() instanceof GiveHerbOffert){
            myAgent.addBehaviour(new SendOfferBechaviour<Herb, GiveHerbOffert>(myAgent, (GiveHerbOffert)action.getAction(), ((GiveHerbOffert) action.getAction()).getItem(), conversationId, participant));
        }
    }
    
}
