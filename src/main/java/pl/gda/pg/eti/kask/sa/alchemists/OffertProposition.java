package pl.gda.pg.eti.kask.sa.alchemists;

import jade.content.Concept;
import jade.core.AID;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import pl.gda.pg.eti.kask.sa.alchemists.ontology.Item;
import pl.gda.pg.eti.kask.sa.alchemists.ontology.Offert;

/**
 * OffertProposition
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class OffertProposition {
    Item item;
    Offert offert;
    private AID seller;
}