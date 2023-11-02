package pl.gda.pg.eti.kask.sa.alchemists.ontology;

import jade.content.AgentAction;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class GiveHerbOffert implements AgentAction {
    // offerts should have ID, but we will assume that all offerts are the same
    private Herb item;
}
