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
public class Alchemist extends BaseAgent {

    @Getter
    private final List<Potion> potions = new ArrayList<>();
    
    public Alchemist() {
    }

    @Override
    protected void setup() {
        super.setup();
        potions.add(new Potion("Shrouding Potion"));
        potions.add(new Potion("Heroic Potion"));
        addBehaviour(new RegisterServiceBehaviour(this, "alchemist"));
        addBehaviour(new AlchemistBehaviour(this));
    }

}
