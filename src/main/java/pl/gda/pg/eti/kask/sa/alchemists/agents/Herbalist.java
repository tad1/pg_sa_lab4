package pl.gda.pg.eti.kask.sa.alchemists.agents;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import pl.gda.pg.eti.kask.sa.alchemists.behaviours.RegisterServiceBehaviour;
import pl.gda.pg.eti.kask.sa.alchemists.behaviours.herbalist.HerbalistBehaviour;
import pl.gda.pg.eti.kask.sa.alchemists.ontology.Herb;

/**
 *
 * @author psysiu
 */
public class Herbalist extends BaseAgent {

    @Getter
    private final List<Herb> herbs = new ArrayList<>();
    
    public Herbalist() {
    }

    @Override
    protected void setup() {
        super.setup();
        herbs.add(new Herb("Peacebloom"));
        herbs.add(new Herb("Swiftthistle"));
        herbs.add(new Herb("Sungrass"));
        addBehaviour(new RegisterServiceBehaviour(this, "herbalist"));
        addBehaviour(new HerbalistBehaviour(this));
    }

}
