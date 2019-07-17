package gui;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import game.Item;

public class ItemOnSalePanel extends JPanel{
    
	private JLabel itemWeight;
	private JLabel itemPower;
        private JLabel itemPrice;

	public ItemOnSalePanel() {
            setBackground(Color.black);
            setBorder(BorderFactory.createLineBorder(Color.black, 2));
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            
            itemWeight = new JLabel("Weight: ");
            itemWeight.setForeground(Color.white);
            itemPower = new JLabel("Power: ");
            itemPower.setForeground(Color.white);
            itemPrice = new JLabel("Price: ");
            itemPrice.setForeground(Color.white);
            add(itemWeight);
            add(itemPower);
            add(itemPrice);
	}
	
	public void refresh(Item item) {
            itemWeight.setText("Weight: " + item.getItemWeight());
            itemPower.setText("Power: " + item.getItemPower());
            itemPrice.setText("Price: " + item.getItemPrice());
	}

	public void clear() {
            itemWeight.setText("Weight: ");
            itemPower.setText("Power: ");
            itemPrice.setText("Price: ");
        }
}
