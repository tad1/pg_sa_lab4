package pl.gda.pg.eti.kask.sa.alchemists.behaviours.buyers.leader;

import jade.content.Predicate;
import jade.content.onto.basic.FalseProposition;
import jade.content.onto.basic.Result;
import jade.content.onto.basic.TrueProposition;
import jade.core.AID;
import pl.gda.pg.eti.kask.sa.alchemists.agents.Mage;
import pl.gda.pg.eti.kask.sa.alchemists.behaviours.ActionBehaviour;
import pl.gda.pg.eti.kask.sa.alchemists.ontology.AcceptNewMember;

public class SendMemberAcknowledge extends ActionBehaviour<AcceptNewMember, Mage> {

    boolean accepted;

    public SendMemberAcknowledge(Mage agent, AcceptNewMember action, String conversationId, AID participant, boolean accepted) {
        super(agent, action, conversationId, participant);
        this.accepted = accepted;
    }

    @Override
    protected Predicate performAction() {
        if(accepted)
            return new TrueProposition();

        return new FalseProposition();
    }

}
