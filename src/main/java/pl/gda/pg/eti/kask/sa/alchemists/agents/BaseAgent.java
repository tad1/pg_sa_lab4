package pl.gda.pg.eti.kask.sa.alchemists.agents;

import jade.content.lang.sl.SLCodec;
import jade.core.Agent;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import pl.gda.pg.eti.kask.sa.alchemists.ontology.AlchemyOntology;

/**
 *
 * @author psysiu
 */
public class BaseAgent extends GuiAgent {

    @Getter
    protected final List<String> activeConversationIds = new ArrayList<>();

    public BaseAgent() {
    }

    @Override
    protected void setup() {
        super.setup();
        getContentManager().registerLanguage(new SLCodec());
        getContentManager().registerOntology(AlchemyOntology.getInstance());
    }

    @Override
    protected void onGuiEvent(GuiEvent arg0) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'onGuiEvent'");
    }

}
