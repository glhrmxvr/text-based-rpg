package gui;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.SwingConstants;

import game.Room;
@SuppressWarnings("unchecked")
public class RoomPanel extends JPanel {
    
	private Room room;
	private String selectedCharacter;
        private String selectedItem;
	private JLabel description;
	private JList charactersList;
	private JList itemsList;
        private EnemyStatusPanel enemyStatus;
        private ItemPanel itemInfo;
        private ShopPanel shop;
	
	public RoomPanel(Room room, ShopPanel shop) {
            this.shop = shop;
            this.room = room;
            selectedCharacter = null;

            setBackground(Color.white);
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

            description = new JLabel("You are ...", SwingConstants.CENTER);
            description.setHorizontalAlignment(JLabel.CENTER);
            add(description);

            JPanel listPanel = new JPanel();
            listPanel.setLayout(new GridLayout(2, 2));

            listPanel.add(new JLabel("Characters", SwingConstants.CENTER));
            listPanel.add(new JLabel("Items", SwingConstants.CENTER));

            charactersList = new JList();
            charactersList.setModel(new DefaultListModel());
            charactersList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            charactersList.setLayoutOrientation(JList.VERTICAL);
            charactersList.setVisibleRowCount(-1);
            charactersList.addListSelectionListener(new EnemySelectionHandler());
            JScrollPane enemiesL = new JScrollPane(charactersList);
            listPanel.add(enemiesL);

            itemsList = new JList();
            itemsList.setModel(new DefaultListModel());
            itemsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            itemsList.setLayoutOrientation(JList.VERTICAL);
            itemsList.setVisibleRowCount(-1);
            itemsList.addListSelectionListener(new ItemSelectionHandler());
            JScrollPane itemsL = new JScrollPane(itemsList);
            listPanel.add(itemsL);

            add(listPanel);

            JPanel infoPanel = new JPanel();
            infoPanel.setLayout(new GridLayout(0,2));

            enemyStatus = new EnemyStatusPanel();
            infoPanel.add(enemyStatus);

            itemInfo = new ItemPanel();
            infoPanel.add(itemInfo);

            add(infoPanel);
		
	}
	
	public void changeRoom(Room newRoom) {
            room = newRoom;
            refresh();
	}
	
	public void refresh() {
            description.setText("You are " + room.getShortDescription());

            ((DefaultListModel) charactersList.getModel()).removeAllElements();
            for (String name : room.getCharacters()) 
                    ((DefaultListModel) charactersList.getModel()).addElement(name);


            ((DefaultListModel) itemsList.getModel()).removeAllElements();
            for (String name : room.getRoomItems().keySet()) 
                    ((DefaultListModel) itemsList.getModel()).addElement(name);

	}
      
	public void refreshEnemy() {
            if (selectedCharacter == null) {
                    enemyStatus.clear();
            } else {
                    enemyStatus.refresh(room.getRoomEnemy(selectedCharacter));
            }
	}
	
	public String takeSelectedCharacter() {
		return selectedCharacter;
	}
        
        public void refreshItem() {
            if (selectedItem == null) {
                    itemInfo.clear();
            } else {
                    itemInfo.refresh(room.getRoomItem(selectedItem));
            }
	}
	
	public String takeSelectedItem() {
            return selectedItem;
	}
	
	class EnemySelectionHandler implements ListSelectionListener {
		public void valueChanged(ListSelectionEvent e) { 
			if (charactersList.isSelectionEmpty()) {
				selectedCharacter = null;
                                shop.clear();
                                enemyStatus.clear();
			} else {
                                selectedCharacter = (String) charactersList.getSelectedValue();
                                if(room.isNpc(selectedCharacter)){
                                    shop.setNpcShop(room.getRoomNpc((String) charactersList.getSelectedValue()));
                                    shop.refresh();
                                    enemyStatus.clear();
                                }
                                else{
                                    refreshEnemy();  
                                    shop.clear();
                                }
			}
		}
	}
        
        class ItemSelectionHandler implements ListSelectionListener {
            public void valueChanged(ListSelectionEvent e) { 
                if (itemsList.isSelectionEmpty()) 
                    selectedItem = null;
                else
                    selectedItem = (String) itemsList.getSelectedValue();
                refreshItem();
            }
	}
}