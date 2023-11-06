package pl.gda.pg.eti.kask.sa.alchemists.behaviours.buyers.member;

import jade.content.onto.basic.Action;
import jade.core.AID;
import pl.gda.pg.eti.kask.sa.alchemists.agents.BaseAgent;
import pl.gda.pg.eti.kask.sa.alchemists.agents.Mage;
import pl.gda.pg.eti.kask.sa.alchemists.behaviours.ReceiveResultBehaviour;
import pl.gda.pg.eti.kask.sa.alchemists.behaviours.RequestActionBehaviour;
import pl.gda.pg.eti.kask.sa.alchemists.behaviours.WaitingBehaviour;
import pl.gda.pg.eti.kask.sa.alchemists.ontology.ApplyPlan;
import pl.gda.pg.eti.kask.sa.alchemists.ontology.CheckPlan;
import pl.gda.pg.eti.kask.sa.alchemists.ontology.Plan;

public class MemberBehaviour extends WaitingBehaviour<Mage> {

    public MemberBehaviour(Mage agent) {
        super(agent);

        // send base plan
        Plan myPlan = new Plan();
        if(myAgent.SignToPlan(myPlan)){
            myAgent.addBehaviour(new RequestActionBehaviour<CheckPlan,Mage>(agent, myAgent.getLeader(), new CheckPlan(myPlan)) {
                
                @Override
                protected ReceiveResultBehaviour<Mage> createResultBehaviour(String conversationId) {
                    return null;
                }
                
            });
        }
    }

    @Override
    protected void action(Action action, String conversationId, AID participant) {

        if(action.getAction() instanceof CheckPlan){
            // TODO: try add self to the plan, once a while if there are no more plans, sign to the last largest one
        } else if(action.getAction() instanceof ApplyPlan){
            //TODO: apply plan, remove behaviour
        }

    }
    
}
