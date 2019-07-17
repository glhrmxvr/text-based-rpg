/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

/**
 *
 * @author Guilherme
 */

import characters.Pacific;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import java.awt.GridLayout;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

@SuppressWarnings("unchecked")
public class ShopPanel extends JPanel{
	
        private JList itemsOnSaleList;
        private JPanel shop;
        private ItemOnSalePanel itemsInfo;
        private String selectedItem;
        private Pacific npc;

	public ShopPanel() {
		
            setBackground(Color.white);
            setBorder(BorderFactory.createLineBorder(Color.black, 2));
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

            shop = new JPanel(new GridLayout(2,0));

            itemsOnSaleList = new JList();
            itemsOnSaleList.setModel(new DefaultListModel());
            itemsOnSaleList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            itemsOnSaleList.setLayoutOrientation(JList.VERTICAL);
            itemsOnSaleList.setVisibleRowCount(-1);
            itemsOnSaleList.addListSelectionListener(new ShopSelectionHandler());
            JScrollPane itemsL = new JScrollPane(itemsOnSaleList);
            shop.add(itemsL);

            itemsInfo = new ItemOnSalePanel();
            shop.add(itemsInfo);

            add(shop);
                
	}
        
        public void setNpcShop(Pacific npc){
            this.npc = npc;
        }
        
        public void refresh() {

            ((DefaultListModel) itemsOnSaleList.getModel()).removeAllElements();
            for (String name : npc.getShopItems()) 
                    ((DefaultListModel) itemsOnSaleList.getModel()).addElement(name);
		
	}       
        
        public String takeSelectedItem() {
            return selectedItem;
	}
        
        public void clear(){
            ((DefaultListModel) itemsOnSaleList.getModel()).removeAllElements();
        }
        
        public void refreshItem() {
            if (selectedItem == null) {
                    itemsInfo.clear();
            } else {
                    itemsInfo.refresh(npc.getItem(selectedItem));
            }
	}
        
        class ShopSelectionHandler implements ListSelectionListener {
            public void valueChanged(ListSelectionEvent e) { 
                if (itemsOnSaleList.isSelectionEmpty())
                    selectedItem = null;
                else 
                    selectedItem = (String) itemsOnSaleList.getSelectedValue();
                        
                refreshItem();
            }
	}
}

