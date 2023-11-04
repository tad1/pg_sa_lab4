package pl.gda.pg.eti.kask.sa.alchemists.gui;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import jade.content.Concept;
import java.awt.Toolkit;
import java.util.Map;

import pl.gda.pg.eti.kask.sa.alchemists.agents.Mage;
import pl.gda.pg.eti.kask.sa.alchemists.agents.SingleConceptMerchant;
import pl.gda.pg.eti.kask.sa.alchemists.ontology.Essence;
import pl.gda.pg.eti.kask.sa.alchemists.ontology.Herb;
import pl.gda.pg.eti.kask.sa.alchemists.ontology.Potion;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Map;

public class MageGUI extends JFrame {
    Mage myMerchant;
    String agentName;
    DefaultTableModel requiredItemsTableModel;
    JTable requiredItemsTable;

    public MageGUI(Mage a) {
        this.myMerchant = a;
        this.agentName = this.myMerchant.getLocalName();

        setTitle(this.agentName + " - Merchant");
        setSize(300, 200);

        JPanel panel = new JPanel();
        JLabel titleLabel = new JLabel(agentName + " wants to buy:");
        panel.add(titleLabel);

        requiredItemsTableModel = new DefaultTableModel();
        requiredItemsTable = new JTable(requiredItemsTableModel);
        requiredItemsTableModel.addColumn("Required Items");

        JScrollPane scrollPane = new JScrollPane(requiredItemsTable);
        scrollPane.setPreferredSize(getSize()); // Half the height
        panel.add(scrollPane);

        add(panel);
    }

    public void showCorrect() {
        requiredItemsTableModel.setRowCount(0);

        displayRequiredItems("Potions", myMerchant.getRequiredPotions());
        displayRequiredItems("Herbs", myMerchant.getRequiredHerbs());
        displayRequiredItems("Essences", myMerchant.getRequiredEssences());

        this.pack();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int centerX = (int) screenSize.getWidth() / 2;
        int centerY = (int) screenSize.getHeight() / 2;
        this.setLocation(centerX - this.getWidth() / 2, centerY - this.getHeight() / 2);
        this.setVisible(true);
    }

    private void displayRequiredItems(String category, Map<?, Integer> items) {
        for (Map.Entry<?, Integer> entry : items.entrySet()) {
            requiredItemsTableModel.addRow(new Object[]{entry.getValue() + " x " + entry.getKey().toString()});
        }
    }
}
