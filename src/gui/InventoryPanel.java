package gui;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import static gui.MainWindow.getPlayer;

@SuppressWarnings("unchecked")
public class InventoryPanel extends JPanel{
    
        private JList itemsList;
        private JPanel inventory;
        private String selectedItem;

	public InventoryPanel() {

            setBackground(Color.white);
            setBorder(BorderFactory.createLineBorder(Color.black, 2));
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

            inventory = new JPanel(new GridLayout(2,0));
            JLabel inventoryL = new JLabel("Inventory");
            inventoryL.setHorizontalAlignment(JLabel.CENTER);
            inventory.add(inventoryL);

            itemsList = new JList();
            itemsList.setModel(new DefaultListModel());
            itemsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            itemsList.setLayoutOrientation(JList.VERTICAL);
            itemsList.setVisibleRowCount(-1);
            itemsList.addListSelectionListener(new InventorySelectionHandler());
            JScrollPane itemsL = new JScrollPane(itemsList);
            inventory.add(itemsL);

            add(inventory);
                
	}
        
        public void refresh() {

            ((DefaultListModel) itemsList.getModel()).removeAllElements();
            for (String name : getPlayer().getKeysFromInventory()) 
                    ((DefaultListModel) itemsList.getModel()).addElement(name + ","+ getPlayer().getItem(name).getItemQuantity());
		
	}        
	
	public String takeSelectedItem() {
            if(selectedItem != null){
                String[] item;
                item = selectedItem.split(",");
                return item[0];
            }
            return null;
	}
        
        class InventorySelectionHandler implements ListSelectionListener {
            public void valueChanged(ListSelectionEvent e) { 
                if (itemsList.isSelectionEmpty()) 
                    selectedItem = null;
                else 
                    selectedItem = (String) itemsList.getSelectedValue();
            }
	}
}
