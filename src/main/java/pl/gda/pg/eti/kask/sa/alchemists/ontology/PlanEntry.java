package pl.gda.pg.eti.kask.sa.alchemists.ontology;

import java.util.ArrayList;

import jade.content.AgentAction;
import jade.content.Concept;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import pl.gda.pg.eti.kask.sa.alchemists.OffertProposition;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class PlanEntry implements Concept {
    public ArrayList<OffertProposition> offerts;
    public int maxMoney;
}
