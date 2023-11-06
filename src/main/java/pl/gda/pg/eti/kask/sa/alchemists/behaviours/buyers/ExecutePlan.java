package pl.gda.pg.eti.kask.sa.alchemists.behaviours.buyers;

import java.util.ArrayList;

import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.ParallelBehaviour;
import pl.gda.pg.eti.kask.sa.alchemists.OffertProposition;
import pl.gda.pg.eti.kask.sa.alchemists.agents.Mage;
import pl.gda.pg.eti.kask.sa.alchemists.behaviours.ReceiveResultBehaviour;
import pl.gda.pg.eti.kask.sa.alchemists.behaviours.RequestActionBehaviour;
import pl.gda.pg.eti.kask.sa.alchemists.ontology.Herb;
import pl.gda.pg.eti.kask.sa.alchemists.ontology.PlanEntry;
import pl.gda.pg.eti.kask.sa.alchemists.ontology.Potion;
import pl.gda.pg.eti.kask.sa.alchemists.ontology.SellHerb;

public class ExecutePlan extends OneShotBehaviour {

    PlanEntry myPlan;


    public ExecutePlan(Mage agent, PlanEntry action){
        super(agent);
        myPlan = action;
    }    

    @Override
    public void action() {
        ParallelBehaviour behaviour = new ParallelBehaviour(myAgent, ParallelBehaviour.WHEN_ALL);
        ArrayList<OffertProposition> herbOfferts, potionOfferts, essenceOffert;

        for (OffertProposition offert : myPlan.getOfferts()) {
            Class<?> type = offert.getItem().getClass();

            System.out.println("this should be implemented at the end");
            // create multiple lists
        }
    }




    
    
}
