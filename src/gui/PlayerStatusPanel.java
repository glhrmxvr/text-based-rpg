package gui;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import characters.Player;
import static gui.MainWindow.getPlayer;
import java.awt.GridLayout;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

@SuppressWarnings("unchecked")
public class PlayerStatusPanel extends JPanel{

	private JLabel name;
	private JLabel health;
        private JLabel coins;
        private JPanel equippedItemPanel;
        private JList equippedItemList;
        private String selectedItem;

	public PlayerStatusPanel() {

            setBackground(Color.white);
            setBorder(BorderFactory.createLineBorder(Color.black, 2));
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            
            name = new JLabel("Name: ");
            health = new JLabel("Health: ");
            coins = new JLabel("Coins: ");
            add(name);
            add(health);
            add(coins);

            equippedItemPanel = new JPanel(new GridLayout(2,0));
            equippedItemPanel.add(new JLabel("Attack/Defense Equipped Weapon", SwingConstants.CENTER));
            equippedItemList = new JList();
            equippedItemList.setModel(new DefaultListModel());
            equippedItemList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            equippedItemList.setLayoutOrientation(JList.VERTICAL);
            equippedItemList.setVisibleRowCount(-1);
            equippedItemList.addListSelectionListener(new ItemEquippedSelectionHandler());
            JScrollPane itemsL = new JScrollPane(equippedItemList);
            equippedItemPanel.add(itemsL);

            add(equippedItemPanel);
                
	}
        
        public String takeSelectedItem() {
            if(selectedItem != null){
                String[] item;
                item = selectedItem.split(",");
                return item[0];
            }
            return null;
	}
        
        public void refresh() {
            
            name.setText("Name: " + getPlayer().getName());
            health.setText("Health: " + getPlayer().getHealth());
            coins.setText("Coins: " + getPlayer().getCoins());

            ((DefaultListModel) equippedItemList.getModel()).removeAllElements();
            for (String name : getPlayer().getKeysFromEquipped()) 
                    ((DefaultListModel) equippedItemList.getModel()).addElement(name+","+getPlayer().getEquippedItem(name).getItemPower());
	}        
        
        class ItemEquippedSelectionHandler implements ListSelectionListener {
            public void valueChanged(ListSelectionEvent e) { 
                if (equippedItemList.isSelectionEmpty()) 
                    selectedItem = null;
                else 
                    selectedItem = (String) equippedItemList.getSelectedValue();
            }
	}
}
