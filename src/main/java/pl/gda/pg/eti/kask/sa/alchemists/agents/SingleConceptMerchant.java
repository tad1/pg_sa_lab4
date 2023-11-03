package pl.gda.pg.eti.kask.sa.alchemists.agents;

import java.util.HashMap;

import javax.swing.SwingUtilities;

import jade.content.Concept;
import jade.gui.GuiAgent;
import lombok.Getter;
import pl.gda.pg.eti.kask.sa.alchemists.gui.SingleConceptMerchantGUI;


// merchant can sell alchemy item
public abstract class SingleConceptMerchant<T extends Concept> extends BaseAgent{
    
    private transient SingleConceptMerchantGUI<T> myGui;
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

        myGui = new SingleConceptMerchantGUI<T>(this);
		myGui.showCorrect();

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


    protected void takeDown() {
        this.disposeGUI();
     }
  
     public SingleConceptMerchantGUI<T> getGui() {
        return this.myGui;
     }
  
     protected void beforeMove() {
        this.disposeGUI();
     }
  
     protected void afterMove() {
        this.restoreGUI();
     }
  
     protected void afterClone() {
        this.restoreGUI();
     }
  
     public void afterLoad() {
        this.restoreGUI();
     }
  
     public void beforeFreeze() {
        this.disposeGUI();
     }
  
     public void afterThaw() {
        this.restoreGUI();
     }
  
     public void beforeReload() {
        this.disposeGUI();
     }
  
     public void afterReload() {
        this.restoreGUI();
     }

    private void restoreGUI() {
        this.myGui = new SingleConceptMerchantGUI<T>(this);
        this.myGui.showCorrect();
     }
  
    private void disposeGUI() {
		if(myGui != null) {
			final SingleConceptMerchantGUI<T> gui = myGui;
			myGui = null;
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					gui.setVisible(false);
					gui.dispose();
				}
			});
		}
	}

}
