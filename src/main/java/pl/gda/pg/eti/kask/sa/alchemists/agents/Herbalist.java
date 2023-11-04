package pl.gda.pg.eti.kask.sa.alchemists.agents;

import java.util.ArrayList;
import java.util.List;

import jade.core.behaviours.WakerBehaviour;
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

        addBehaviour(new WakerBehaviour(this, this.delay) {
            @Override
            protected void onWake() {
                super.onWake();
                addBehaviour(new HerbalistBehaviour(Herbalist.this));
                addBehaviour(new RegisterServiceBehaviour(Herbalist.this, "herbalist"));
            }
        });
    }

    @Override
    protected Herb retriveConcept(Object arg) {
        return new Herb(arg.toString());
    }

}
