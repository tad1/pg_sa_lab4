package pl.gda.pg.eti.kask.sa.alchemists.ontology;

import jade.content.onto.BasicOntology;
import jade.content.onto.BeanOntology;
import jade.content.onto.BeanOntologyException;
import jade.content.onto.Ontology;
import jade.content.onto.OntologyException;
import jade.content.onto.SerializableOntology;
import jade.content.schema.ObjectSchema;

import java.util.logging.Level;
import lombok.Getter;
import lombok.extern.java.Log;

/**
 *
 * @author psysiu
 */
@Log
public class AlchemyOntology extends BeanOntology {

    public static final String NAME = "alchemy-ontology";

    @Getter
    private static final AlchemyOntology instance = new AlchemyOntology(NAME);

    private AlchemyOntology(String name) {
        super(name, new Ontology[]{BasicOntology.getInstance(), SerializableOntology.getInstance()});

        try {
            ObjectSchema serializableSchema = getSchema(SerializableOntology.SERIALIZABLE);
            SerializableOntology.getInstance().add(serializableSchema, java.util.HashMap.class);

            add(Offert.class);
            add(PlanEntry.class);
            add(Plan.class);
            add(ApplyPlan.class);
            add(CheckPlan.class);
            add(AcceptNewMember.class);
            
            add(Herb.class);
            add(Potion.class);
            add(Essence.class);
            add(SellHerb.class);
            add(SellPotion.class);
            add(SellEssence.class);
            add(GiveHerbOffert.class);
            add(GivePotionOffert.class);
            add(GiveEssenceOffert.class);

        } catch (BeanOntologyException ex) {
            log.log(Level.SEVERE, null, ex);
            assert false;
        } catch (OntologyException e) {
            log.log(Level.SEVERE, null, e);

        }

    }

}
