package pl.gda.pg.eti.kask.sa.alchemists.ontology;

import jade.content.Concept;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author psysiu
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Herb implements Concept {
    
    private String name;
    
}
