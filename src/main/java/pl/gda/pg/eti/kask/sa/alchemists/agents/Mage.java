package pl.gda.pg.eti.kask.sa.alchemists.agents;

import jade.core.AID;
import jade.core.behaviours.SequentialBehaviour;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import pl.gda.pg.eti.kask.sa.alchemists.behaviours.FindServiceBehaviour;
import pl.gda.pg.eti.kask.sa.alchemists.behaviours.mage.MageBehaviour;
import pl.gda.pg.eti.kask.sa.alchemists.behaviours.mage.RequestHerbBehaviour;
import pl.gda.pg.eti.kask.sa.alchemists.behaviours.mage.RequestPotionBehaviour;
import pl.gda.pg.eti.kask.sa.alchemists.ontology.Herb;
import pl.gda.pg.eti.kask.sa.alchemists.ontology.Potion;
import pl.gda.pg.eti.kask.sa.alchemists.ontology.SellHerb;
import pl.gda.pg.eti.kask.sa.alchemists.ontology.SellPotion;

/**
 *
 * @author psysiu
 */
public class Mage extends BaseAgent {

    public Mage() {
    }

    @Override
    protected void setup() {
        super.setup();
        SequentialBehaviour behaviour = new SequentialBehaviour(this);

        Object[] args = getArguments();
        String potions_s = args[0].toString();
        String herbs_s = args[1].toString();

        String[] potions = potions_s.split(";");
        String[] herbs = herbs_s.split(";");

        behaviour.addSubBehaviour(new FindServiceBehaviour(this, "alchemist") {

            @Override
            protected void onResult(DFAgentDescription[] services) {
                if (services != null && services.length > 0) {
                    AID alchemist = services[0].getName();

                    SequentialBehaviour parent = (SequentialBehaviour) getParent();
                    for (String potion : potions) {
                        SellPotion action = new SellPotion(new Potion(potion));
                        RequestPotionBehaviour request = new RequestPotionBehaviour(Mage.this, alchemist, action);
                        
                        parent.addSubBehaviour(request);
                    }

                }
            }
        });

        behaviour.addSubBehaviour(new FindServiceBehaviour(this, "herbalist") {

            @Override
            protected void onResult(DFAgentDescription[] services) {
                if (services != null && services.length > 0) {
                    AID herbalist = services[0].getName();
                    SellHerb action = new SellHerb(new Herb("Peacebloom"));
                    RequestHerbBehaviour request = new RequestHerbBehaviour(Mage.this, herbalist, action);
                    ((SequentialBehaviour) getParent()).addSubBehaviour(request);

                }
            }
        });

        addBehaviour(behaviour);
        addBehaviour(new MageBehaviour(behaviour, this));
    }

}
