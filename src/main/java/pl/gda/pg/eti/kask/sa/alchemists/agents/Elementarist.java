package pl.gda.pg.eti.kask.sa.alchemists.agents;

import java.util.List;

import lombok.Getter;
import pl.gda.pg.eti.kask.sa.alchemists.ontology.Essence;

public class Elementarist extends SingleConceptMerchant<Essence>{
    
    @Getter
    private final List<Essence> essences = new java.util.ArrayList<>();

    public Elementarist(){

    }

    @Override
    protected void setup() {
        super.setup();

        // fetch argument -- the file, select the agent we want
        

    }

    @Override
    protected Essence retriveConcept(Object arg) {
        return new Essence(arg.toString());
    }
}
