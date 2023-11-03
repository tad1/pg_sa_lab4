package pl.gda.pg.eti.kask.sa.alchemists.gui;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import jade.content.Concept;
import java.awt.Toolkit;
import pl.gda.pg.eti.kask.sa.alchemists.agents.SingleConceptMerchant;

public class SingleConceptMerchantGUI<T extends Concept> extends JFrame{
    SingleConceptMerchant<T> myMerchant;
    String agentName;

    public SingleConceptMerchantGUI(SingleConceptMerchant<T> a){
        this.myMerchant = a;
        this.agentName = this.myMerchant.getLocalName();

        // thanks chatGPT
        setTitle(this.agentName + " - Merchant");
        setSize(600, 200);

        JPanel panel = new JPanel();
        JLabel titleLabel = new JLabel(agentName + " is selling:");
        panel.add(titleLabel);

        String[] columnNames = {"Name", "Price", "Quantity"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        for (T item : myMerchant.getItemPrice().keySet()) {
            int price = myMerchant.getItemPrice().get(item);
            Integer quantity = myMerchant.getItemNumber().get(item);
            model.addRow(new Object[]{item, price, quantity});
        }

        // Create a JTable with the table model
        JTable table = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane);
        add(panel);
    }

    public void showCorrect() {
      this.pack();
      Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
      int centerX = (int)screenSize.getWidth() / 2;
      int centerY = (int)screenSize.getHeight() / 2;
      this.setLocation(centerX - this.getWidth() / 2, centerY - this.getHeight() / 2);
      this.setVisible(true);
   }
}
