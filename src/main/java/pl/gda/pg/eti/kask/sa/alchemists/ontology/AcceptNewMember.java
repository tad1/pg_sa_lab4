package pl.gda.pg.eti.kask.sa.alchemists.ontology;

import jade.content.AgentAction;
import jade.core.AID;
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
public class AcceptNewMember implements AgentAction {
    private AID newMember;
}
