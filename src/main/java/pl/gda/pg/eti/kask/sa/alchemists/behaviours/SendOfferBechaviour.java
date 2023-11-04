package pl.gda.pg.eti.kask.sa.alchemists.behaviours;

import java.util.logging.Level;

import jade.content.AgentAction;
import jade.content.Concept;
import jade.content.Predicate;
import jade.content.lang.Codec;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.OntologyException;
import jade.content.onto.basic.Action;
import jade.content.onto.basic.Result;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import pl.gda.pg.eti.kask.sa.alchemists.agents.Alchemist;
import pl.gda.pg.eti.kask.sa.alchemists.agents.SingleConceptMerchant;
import pl.gda.pg.eti.kask.sa.alchemists.ontology.AlchemyOntology;
import pl.gda.pg.eti.kask.sa.alchemists.ontology.Offert;
import pl.gda.pg.eti.kask.sa.alchemists.ontology.SellPotion;

public class SendOfferBechaviour<T extends Concept, R extends AgentAction> extends OneShotBehaviour{

    protected final SingleConceptMerchant<T> myAgent;
    protected final String conversationId;
    protected final AID participant;
    protected final T item;
    protected final R action;

    public SendOfferBechaviour(SingleConceptMerchant<T> agent, R action, T item, String conversationId, AID participant) {
        super(agent);
        this.myAgent = agent;
        this.item = item;
        this.action = action;
        
        this.conversationId = conversationId;
        this.participant = participant;
    }

    @Override
    public void action() {
        ACLMessage msg;
        
        Offert offert = null;
        Predicate result = null;

        if(myAgent.getItemNumber().containsKey(item) && myAgent.getItemPrice().containsKey(item)){
            offert = new Offert(myAgent.getItemPrice().get(item), myAgent.getItemNumber().get(item));
        }

        if(offert != null){
            result = new Result(action, offert);
        }

        if (result != null) {
            msg = new ACLMessage(ACLMessage.INFORM);
        } else {
            msg = new ACLMessage(ACLMessage.REFUSE);
        }
        msg.setLanguage(new SLCodec().getName());
        msg.setOntology(AlchemyOntology.getInstance().getName());
        msg.setConversationId(conversationId);
        msg.addReceiver(participant);
        try {
            if (result != null) {
                myAgent.getContentManager().fillContent(msg, result);
            }
            myAgent.send(msg);
        } catch (Codec.CodecException | OntologyException ex) {
        }
    }
    
}
