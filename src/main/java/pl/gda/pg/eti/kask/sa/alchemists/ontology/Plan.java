package pl.gda.pg.eti.kask.sa.alchemists.ontology;

import java.util.ArrayList;
import java.util.HashMap;

import jade.content.AgentAction;
import jade.content.Concept;
import jade.core.AID;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@ToString

public class Plan implements Concept {
    public Plan(){
        entries = new HashMap<>();
    }

    private HashMap<AID, PlanEntry> entries;
}
