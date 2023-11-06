package pl.gda.pg.eti.kask.sa.alchemists.behaviours.buyers;

import jade.core.Agent;
import jade.content.Predicate;
import jade.content.onto.basic.Result;
import jade.content.onto.basic.TrueProposition;
import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import pl.gda.pg.eti.kask.sa.alchemists.agents.Mage;
import pl.gda.pg.eti.kask.sa.alchemists.behaviours.FindServiceBehaviour;
import pl.gda.pg.eti.kask.sa.alchemists.behaviours.ReceiveResultBehaviour;
import pl.gda.pg.eti.kask.sa.alchemists.behaviours.RegisterServiceBehaviour;
import pl.gda.pg.eti.kask.sa.alchemists.behaviours.RequestActionBehaviour;
import pl.gda.pg.eti.kask.sa.alchemists.behaviours.buyers.leader.LeaderBehaviour;
import pl.gda.pg.eti.kask.sa.alchemists.behaviours.buyers.member.MemberBehaviour;
import pl.gda.pg.eti.kask.sa.alchemists.ontology.AcceptNewMember;

public class RequestCommunityMembership extends TickerBehaviour {

    private Mage myAgent;

    public RequestCommunityMembership(Mage a, long period) {
        super(a, period);
        myAgent = a;
    }

    @Override
    public void onTick() {
        myAgent.addBehaviour(new FindServiceBehaviour(myAgent, "buyers_leader") {

            @Override
            protected void onResult(DFAgentDescription[] services) {
                if (services.length == 0){
                    // register yourself
                    myAgent.addBehaviour(new RegisterServiceBehaviour(myAgent, "buyers_leader"));;
                    return;
                }

                AID leader = services[0].getName();
                if(leader.equals(myAgent.getAID())){
                    // you are the leader, so act as a leader
                    System.out.println("It appears that I am the leader, therefore I will act as a leader");
                    myAgent.addBehaviour(new LeaderBehaviour(RequestCommunityMembership.this.myAgent));
                    RequestCommunityMembership.this.myAgent.setLeader(null);
                    RequestCommunityMembership.this.stop();
                } else {
                    myAgent.addBehaviour(new RequestActionBehaviour<AcceptNewMember,Mage>(RequestCommunityMembership.this.myAgent, leader, new AcceptNewMember(myAgent.getAID())) {

                        // TODO: add max await time
                        @Override
                        protected ReceiveResultBehaviour<Mage> createResultBehaviour(String conversationId) {
                            return new ReceiveResultBehaviour<Mage>(myAgent, conversationId) {

                                @Override
                                protected void handleResult(Predicate predicate, AID participant) {
                                    if(predicate instanceof TrueProposition){
                                        System.out.println("I got accepted to the "+ participant.getLocalName() +"'s community!");
                                        myAgent.setLeader(participant);
                                        RequestCommunityMembership.this.stop();
                                        myAgent.addBehaviour(new MemberBehaviour(myAgent));
                                        
                                    }
                                }
                                
                            };
                        }
                    });
                }
            }
            
        });
    }

    
    
}
