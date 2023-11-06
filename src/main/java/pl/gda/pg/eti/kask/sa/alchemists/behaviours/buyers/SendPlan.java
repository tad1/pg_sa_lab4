package pl.gda.pg.eti.kask.sa.alchemists.behaviours.buyers;

import jade.content.AgentAction;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import pl.gda.pg.eti.kask.sa.alchemists.agents.Mage;
import pl.gda.pg.eti.kask.sa.alchemists.behaviours.ReceiveResultBehaviour;
import pl.gda.pg.eti.kask.sa.alchemists.behaviours.RequestActionBehaviour;
import pl.gda.pg.eti.kask.sa.alchemists.ontology.CheckPlan;
import pl.gda.pg.eti.kask.sa.alchemists.ontology.Plan;

public class SendPlan<R extends AgentAction> extends RequestActionBehaviour<R,Mage> {

    public SendPlan(Mage agent, AID participant, R action) {
        super(agent, participant, action);
    }

    @Override
    protected ReceiveResultBehaviour createResultBehaviour(String conversationId) {
        return null;
    }

}
