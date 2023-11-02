package pl.gda.pg.eti.kask.sa.alchemists.agents;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import pl.gda.pg.eti.kask.sa.alchemists.behaviours.RegisterServiceBehaviour;
import pl.gda.pg.eti.kask.sa.alchemists.behaviours.alchemist.AlchemistBehaviour;
import pl.gda.pg.eti.kask.sa.alchemists.ontology.Potion;

/**
 *
 * @author psysiu
 */
public class Alchemist extends SingleConceptMerchant<Potion> {

    @Getter
    private final List<Potion> potions = new ArrayList<>();
    
    public Alchemist() {
    }

    @Override
    protected void setup() {
        super.setup();
        Object[] args = getArguments();
        for (Object arg : args) {
            potions.add(new Potion(arg.toString()));
        } 
        addBehaviour(new RegisterServiceBehaviour(this, "alchemist"));
        addBehaviour(new AlchemistBehaviour(this));
    }

    @Override
    protected Potion retriveConcept(Object arg) {
        return new Potion(arg.toString());
    }

}
