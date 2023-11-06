package pl.gda.pg.eti.kask.sa.alchemists.behaviours.buyers.leader;

import java.util.ArrayList;
import java.util.Set;

import jade.content.onto.basic.Action;
import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.WakerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import pl.gda.pg.eti.kask.sa.alchemists.agents.Mage;
import pl.gda.pg.eti.kask.sa.alchemists.behaviours.WaitingBehaviour;
import pl.gda.pg.eti.kask.sa.alchemists.behaviours.buyers.SendPlan;
import pl.gda.pg.eti.kask.sa.alchemists.ontology.AcceptNewMember;
import pl.gda.pg.eti.kask.sa.alchemists.ontology.ApplyPlan;
import pl.gda.pg.eti.kask.sa.alchemists.ontology.CheckPlan;
import pl.gda.pg.eti.kask.sa.alchemists.ontology.Plan;

public class LeaderBehaviour extends WaitingBehaviour<Mage> {

    public LeaderBehaviour(Mage agent) {
        super(agent);
        members = new ArrayList<>();
        done = false;

        myAgent.addBehaviour(new WakerBehaviour(agent, 500) {
            @Override
            protected void onWake() {
                // 1. check if there are any members, if not; then send your plan
                // myAgent.addBehaviour(SendPlan);
            }
        });
        // add delay on sending your own plan
    }

    private ArrayList<AID> members;
    private boolean done;

    @Override
    protected void action(Action action, String conversationId, AID participant) {
        if(action.getAction() instanceof AcceptNewMember){
            // accept new member, but only if we don't got the final plan (data race? possible)
            boolean accepted = !done && !members.contains(participant);
            if(accepted){
                members.add(participant);
            }
            myAgent.addBehaviour(new SendMemberAcknowledge(myAgent, (AcceptNewMember)action.getAction(), conversationId, participant, accepted));
        } else if(action.getAction() instanceof CheckPlan){
            CheckPlan checkPlan = (CheckPlan)action.getAction();
            Set<AID> participantList = checkPlan.getPlan().getEntries().keySet();
            
            if(isFinal(checkPlan.getPlan())){
                // send all members request to execute plan
                for (AID member : members) {
                    myAgent.addBehaviour(new SendPlan<ApplyPlan>(myAgent, member, new ApplyPlan(checkPlan.getPlan())));
                }

                // lock self
                done = false;
                try{
                    DFService.deregister(myAgent);
                } catch (FIPAException fe){
                    fe.printStackTrace();
                }

            } else{
                // resend plan to all not involved members
                for (AID member : members) {
                    if(participantList.contains(member) || member.equals(myAgent.getAID()))
                        continue;
                    
                    myAgent.addBehaviour(new SendPlan<CheckPlan>(myAgent, participant, checkPlan));
                }

                if(! participantList.contains(myAgent.getAID())){
                    //TODO: if not involved, check plan; if you are the last member, then try apply
                }
            }         
        } else if(action.getAction() instanceof ApplyPlan){
            // TODO
            myAgent.removeBehaviour(this);
        }
    }

    protected boolean isFinal(Plan plan){
        for (AID participant : members) {
            if(!plan.getEntries().containsKey(participant))
                return false;
        }
        return true;
    }
    
}
