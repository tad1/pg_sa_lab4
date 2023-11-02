package pl.gda.pg.eti.kask.sa.alchemists.agents;

import java.util.HashMap;
import jade.content.Concept;
import lombok.Getter;


// merchant can sell alchemy item
public abstract class SingleConceptMerchant<T extends Concept> extends BaseAgent{
    

    // 
    @Getter
    protected HashMap<T, Integer> itemPrice;
    @Getter
    protected HashMap<T, Integer> itemNumber;


    public SingleConceptMerchant(){
    }

    @Override
    protected void setup() {
        super.setup();

        itemPrice = new HashMap<>();
        itemNumber = new HashMap<>();
        // add from parameters
        // name, value, total
        assert getArguments().length % 3 == 0;

        for (int i = 0; i < getArguments().length; i+=3) {
            T item = retriveConcept(getArguments()[i]);
            int price = Integer.parseInt(getArguments()[i+1].toString());
            int number = Integer.parseInt(getArguments()[i+2].toString());

            itemNumber.put(item, number);
            itemPrice.put(item, price);
        }

    }

    protected abstract T retriveConcept(Object arg);


    public boolean haveEnought(T item, int requested){
        return itemNumber.get(item) >= requested;
    }

    // this should not be the sell, but removeFromStock, however that method should not be public
    public void sell(T item, int requested){
        if(haveEnought(item, requested)){
            int new_val = itemNumber.get(item) - requested;
            itemNumber.put(item, new_val);
        }
    }

}
