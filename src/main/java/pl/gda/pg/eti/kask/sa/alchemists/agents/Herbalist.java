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
public class Herbalist extends SingleConceptMerchant<Herb> {

    
    public Herbalist() {
    }

    @Override
    protected void setup() {
        super.setup();

        addBehaviour(new RegisterServiceBehaviour(this, "herbalist"));
        addBehaviour(new HerbalistBehaviour(this));
    }

    @Override
    protected Herb retriveConcept(Object arg) {
        return new Herb(arg.toString());
    }

}
