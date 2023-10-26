package pl.gda.pg.eti.kask.sa.alchemists.behaviours.mage;

import jade.core.AID;
import pl.gda.pg.eti.kask.sa.alchemists.agents.Mage;
import pl.gda.pg.eti.kask.sa.alchemists.behaviours.ReceiveResultBehaviour;
import pl.gda.pg.eti.kask.sa.alchemists.behaviours.RequestActionBehaviour;
import pl.gda.pg.eti.kask.sa.alchemists.ontology.SellPotion;

/**
 *
 * @author psysiu
 */
public class RequestPotionBehaviour extends RequestActionBehaviour<SellPotion, Mage> {

    public RequestPotionBehaviour(Mage agent, AID participant, SellPotion action) {
        super(agent, participant, action);
    }

    @Override
    protected ReceiveResultBehaviour createResultBehaviour(String conversationId) {
        return new ReceivePotionBehaviour(myAgent, conversationId);
    }
    
}
