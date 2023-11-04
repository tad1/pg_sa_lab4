package pl.gda.pg.eti.kask.sa.alchemists.agents;

import java.util.List;

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

        addBehaviour(new RegisterServiceBehaviour(this, "elementarist"));
        addBehaviour(new ElementaristBehaviour(this));
        

    }

    @Override
    protected Essence retriveConcept(Object arg) {
        return new Essence(arg.toString());
    }
}
