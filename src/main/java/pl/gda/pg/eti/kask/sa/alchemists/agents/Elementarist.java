package pl.gda.pg.eti.kask.sa.alchemists.agents;

import java.util.List;

import jade.core.behaviours.WakerBehaviour;
import lombok.Getter;
import pl.gda.pg.eti.kask.sa.alchemists.behaviours.RegisterServiceBehaviour;
import pl.gda.pg.eti.kask.sa.alchemists.behaviours.elementarist.ElementaristBehaviour;
import pl.gda.pg.eti.kask.sa.alchemists.ontology.Essence;

public class Elementarist extends SingleConceptMerchant<Essence>{
    
    @Getter
    private final List<Essence> essences = new java.util.ArrayList<>();

    public Elementarist(){

    }

    @Override
    protected void setup() {
        super.setup();

        
        addBehaviour(new WakerBehaviour(this, this.delay) {
            @Override
            protected void onWake() {
                super.onWake();
                addBehaviour(new ElementaristBehaviour(Elementarist.this));
                addBehaviour(new RegisterServiceBehaviour(Elementarist.this, "elementarist"));
            }
        });

    }

    @Override
    protected Essence retriveConcept(Object arg) {
        return new Essence(arg.toString());
    }
}
