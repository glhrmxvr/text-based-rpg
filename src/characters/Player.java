/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package characters;

import static gui.MainWindow.consoleAppend;
import java.util.HashMap;
import java.util.Set;
import game.BattleSimulator;
import game.Command;
import static gui.MainWindow.getCurrentRoom;
import game.Item;
import game.ItemType;
import game.Room;

/**
 *
 * @author Guilherme
 */
public class Player extends Fighter{
    
    private double weightLimit;
    private HashMap<String, Item> inventory;
    private HashMap<String, Item> equipped;
    private Integer attack;
    private Integer defense;
    private boolean hasKey;
    
    public Player() {
        super(100, 0, "Bjorn Ironside");
        this.weightLimit = 500.00;
        inventory = new HashMap<>();
        equipped = new HashMap<>();
        defense = 0;
        attack = 5;
        hasKey = false;
    }

    public double getWeightLimit() {
        return weightLimit;
    }

    public Integer getAttack() {
        return attack;
    }
    
    public Integer getDefense() {
        return defense;
    }
    
    public double getCurrentWeight(){
        double w = 0.0;
        for(String item : inventory.keySet())
            w += inventory.get(item).getItemWeight();
        return w;
    }
    
    public Set<String> getKeysFromInventory(){
        return inventory.keySet();
    }
    
    public Set<String> getKeysFromEquipped(){
        return equipped.keySet();
    }
    
    public Item getEquippedItem(String name){
        return equipped.get(name);
    }
    
    public Item getItemByType(ItemType type){
        for(Item item : equipped.values())
            if(item.getItemType().equals(type))
                return item;
        return null;
    }
    
    public Item getItem(String name){
        return inventory.get(name);
    }
    
    public void setAttack() {
        if(hasEquippedItem(ItemType.ATTACK))
            attack = getAttack() + getItemByType(ItemType.ATTACK).getItemPower();
        else
            attack = 5;
    }

    public void setDefense() {
        if(hasEquippedItem(ItemType.DEFENSE))
            defense = getDefense() + getItemByType(ItemType.DEFENSE).getItemPower();
        else
            defense = 5;
    }
    
    public boolean canPick(double weight){
        return weight + getCurrentWeight() <= weightLimit;
    }
     
    public boolean hasEquippedItem(ItemType type){
        for(Item item : equipped.values())
            if(item.getItemType().equals(type))
                return true;
        return false;
    }
     
    public void equipItem(String name, Item item){
        equipped.put(name, item);
    }
     
    public Item unequipItem(String name){
        return equipped.remove(name);
    }
    
    public boolean addItem(String name, Item item){
        if(canPick(item.getItemWeight())){
            item.increaseItemQuantity();
            inventory.put(name, item);
            return true;
        }
        else{
            consoleAppend("Full Inventory");
            return false;
        }
    }
    
    public Item removeItem(String name){
        inventory.get(name).decreaseItemQuantity();
        if(inventory.get(name).getItemQuantity() == 0)
            return inventory.remove(name);
        else
            return inventory.get(name);
    }
    
    public boolean hasCoins(){
        return getCoins() > 0;
    }
    
    public void addCoins(int coins){
        setCoins(getCoins() + coins);
    }
    
    public void removeCoins(int coins){
        setCoins(getCoins() - coins);
    }
    
    public void boughtKey(){
        hasKey = true;
    }
    
    public boolean hasKey(){
        return hasKey;
    }

    public void eat(Command command){
        
        if(command.getSecondWord() == null) {
            consoleAppend("Eat what?");
            return;
        }
        
        String foodName = command.getSecondWord();
        
        if(getItem(foodName).getItemType() == ItemType.FOOD){
            increment(getItem(foodName).getItemPower());
            removeItem(foodName);
        }
        else
            consoleAppend("You can't eat that.\n");
    }
    
    public void pick(Command command){
        
        if(command.getSecondWord() == null) {
            consoleAppend("Pick what?");
            return;
        }
        
        String itemName = command.getSecondWord();
        
        if(canPick(getCurrentRoom().getRoomItem(itemName).getItemWeight())){
            addItem(itemName, getCurrentRoom().removeItem(itemName));
            consoleAppend("You add " + itemName.toUpperCase() + " in your inventory");
        }
       else
            consoleAppend("The item exceeds the weight limit from your inventory");
        
    }
    
    public void drop(Command command){
        
        if(command.getSecondWord() == null) {
            consoleAppend("Drop what?");
            return;
        }
        
        String itemName = command.getSecondWord();
 
        getCurrentRoom().addItem(itemName, removeItem(itemName));
        consoleAppend("You took " + itemName.toUpperCase() + " out of your inventory");

    }
    
    public boolean attack(Command command){
        if(command.getSecondWord() == null) {
            consoleAppend("Attack who?");
            return false;
        }

        String enemieName = command.getSecondWord();

        if(!getCurrentRoom().hasEnemyNamed(enemieName)){
            consoleAppend("You can't attack a NPC");
            return false;
        }

        BattleSimulator battle = new BattleSimulator(this, getCurrentRoom().getRoomEnemy(enemieName));
        return battle.fight(); 
    }
    
    public void equip(Command command){
        
        if(command.getSecondWord() == null) {
            consoleAppend("Equip what?");
            return;
        }
        
        String itemName = command.getSecondWord();
        
        ItemType type = getItem(itemName).getItemType();
        if(type.equals(ItemType.FOOD)){
            consoleAppend("You can't equip that");
            return;
        }
        else if(hasEquippedItem(type))
            consoleAppend("You need to unequip the current item first");
        else{
            equipItem(itemName, removeItem(itemName));
            if(type.equals(ItemType.DEFENSE))
                setDefense();
            if(type.equals(ItemType.ATTACK))
                setAttack();
            consoleAppend("You have equipped " + itemName.toUpperCase() + " as your " + type.toString().toUpperCase());
        }
    }
    
    public void unequip(Command command){
        
        if(command.getSecondWord() == null) {
            consoleAppend("Unequip what?");
            return;
        }
        
        String itemName = command.getSecondWord();
        
        addItem(itemName, unequipItem(itemName));
        if(getItemByType(ItemType.DEFENSE) == null)
            setDefense();
        if(getItemByType(ItemType.ATTACK) == null)
            setAttack();

        consoleAppend("You have unequipped " + itemName.toUpperCase());
    }
    
    public void use(){
        if(hasKey()){
            if(getCurrentRoom().getRoomName().equals("thirdhall")){               
                Room nextRoom = getCurrentRoom().getRoomExits("north");
                consoleAppend("Saferoom unlocked");
                nextRoom.unlock();
            }
            else
                consoleAppend("I don't think this key belongs to this place");
        }
        else
            consoleAppend("You don't have a key");
    }
    
}
