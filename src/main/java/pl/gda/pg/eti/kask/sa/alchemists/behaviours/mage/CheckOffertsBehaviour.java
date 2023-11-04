package pl.gda.pg.eti.kask.sa.alchemists.behaviours.mage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javax.swing.JOptionPane;

import jade.content.Concept;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.ParallelBehaviour;
import jade.core.behaviours.SequentialBehaviour;
import pl.gda.pg.eti.kask.sa.alchemists.OffertProposition;
import pl.gda.pg.eti.kask.sa.alchemists.agents.Mage;

public class CheckOffertsBehaviour extends OneShotBehaviour {

    Mage myAgent;

    public CheckOffertsBehaviour(Mage agent){
        super(agent);
        this.myAgent = agent;

    }

    @Override
    public void action() {

        // check if has all required offerts
        boolean has_enough = true;
        has_enough &= checkItemRequirements(myAgent.getRequiredEssences(), myAgent.getEssenceOfferts());
        has_enough &= checkItemRequirements(myAgent.getRequiredHerbs(), myAgent.getHerbOfferts());
        has_enough &= checkItemRequirements(myAgent.getRequiredPotions(), myAgent.getPotionOfferts());

        if(!has_enough){
            System.out.println("There are not enought items on market");
            return;
        }

        // check if price is good enough
        int money_needed = 0;
        money_needed += getItemCost(myAgent.getRequiredEssences(), myAgent.getEssenceOfferts());
        money_needed += getItemCost(myAgent.getRequiredHerbs(), myAgent.getHerbOfferts());
        money_needed += getItemCost(myAgent.getRequiredPotions(), myAgent.getPotionOfferts());

        if(money_needed > myAgent.getMoney()){
            JOptionPane.showMessageDialog(null, "Damn, the prices are too high. I'm "+(money_needed-myAgent.getMoney())+" gold short");
            return;
        }


        JOptionPane.showMessageDialog(null, "Found offerts!");

        if(getParent() != null && getParent() instanceof SequentialBehaviour){
            ((SequentialBehaviour)getParent()).addSubBehaviour(new BuyItemsBehaviour(myAgent));
        } else {
            myAgent.addBehaviour(new BuyItemsBehaviour(myAgent));
        }
    }

    private <T extends Concept> boolean checkItemRequirements(HashMap<T, Integer> required, HashMap<T, ArrayList<OffertProposition>> offerts){
        for (T item : required.keySet()) {
            if(!checkItemRequirements(item, required, offerts)){
                return false;
            }
        }
        return true;
    }

    private <T extends Concept> boolean checkItemRequirements(T item, HashMap<T, Integer> required, HashMap<T, ArrayList<OffertProposition>> offerts){
        if(offerts.get(item) == null)
            return false;

        int total = offerts.get(item).stream().map(x -> x.getOffert().getQuantity()).reduce(0, Integer::sum);
        return total >= required.get(item);
    }

    private <T extends Concept> int getItemCost(HashMap<T, Integer> requiredMap, HashMap<T, ArrayList<OffertProposition>> offertMap){
        int cost = 0;
        for (T item : requiredMap.keySet()) {
            cost += getItemCost(item, requiredMap, offertMap);
        }
        return cost;
    }
    

    private <T extends Concept> int getItemCost(T item, HashMap<T, Integer> requiredMap, HashMap<T, ArrayList<OffertProposition>> offertMap){
        int cost = 0;
        int required = requiredMap.get(item);

        ArrayList<OffertProposition> offerts = offertMap.get(item);
        
        for (OffertProposition offert : offerts) {
            int count = Math.min(offert.getOffert().getQuantity(), required);
            cost += count * offert.getOffert().getPrice();

            required -= count;
            if(required <= 0){
                break;
            }
        }

        return cost;
    }

    // TODO: checkItemPrice
    
}
