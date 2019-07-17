package gui;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import game.Item;

public class ItemPanel extends JPanel{
	private JLabel itemWeight;
	private JLabel itemPower;

	public ItemPanel() {
            setBackground(Color.black);
            setBorder(BorderFactory.createLineBorder(Color.black, 2));
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            
            itemWeight = new JLabel("Weight: ");
            itemWeight.setForeground(Color.white);
            itemPower = new JLabel("Power: ");
            itemPower.setForeground(Color.white);
            add(itemWeight);
            add(itemPower);
	}
	
	public void refresh(Item item) {
            itemWeight.setText("Weight: " + item.getItemWeight());
            itemPower.setText("Power: " + item.getItemPower());
	}

	public void clear() {
            itemWeight.setText("Weight: ");
            itemPower.setText("Power: ");
	}
}
