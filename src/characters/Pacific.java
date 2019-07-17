/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package characters;

import static gui.MainWindow.consoleAppend;
import java.util.HashMap;
import java.util.Set;
import game.Command;
import static gui.MainWindow.getPlayer;
import game.Item;
import game.ItemType;

/**
 *
 * @author Guilherme
 */
public class Pacific extends Character{
    
    private HashMap<String, Item> shop;

    public Pacific(String name, PacificType type) {
        super(name);
        shop = new HashMap<>();
        if(type.equals(PacificType.MERCHANT))
            setupMerchant();
        else if(type.equals(PacificType.BLACKSMITH))
            setupBlacksmith();
        else
            setupSmuggler();
    }
    
    public int getPrice(String name){
        return getItem(name).getItemPrice();
    }
    
    public Set<String> getShopItems(){
        return shop.keySet();
    }
    
    public void sale(Command command){
        
        String name = command.getSecondWord();
        
        if(getPlayer().getCoins() >= getPrice(name)){
            if(getPlayer().getCurrentWeight() + getItem(name).getItemWeight() <= getPlayer().getWeightLimit()){
                getPlayer().removeCoins(getPrice(name));
                getPlayer().addItem(name, getItem(name));
                if(name.equals("key"))
                    getPlayer().boughtKey();
                consoleAppend("You added " + name.toUpperCase() + " to your inventory\n");
            }
            else
                consoleAppend("You need to unload your inventory first\n");
        }
        else
            consoleAppend("You don't have enough coins.\n");       
    }
    
    public Item getItem(String name){
        for(Item item : shop.values())
            if(item.getItemName().equals(name))
                return item;
        return null;
    }
    
    public void setupMerchant(){
        shop.put("meat", new Item(ItemType.FOOD, "fish", "+15 health", 7.0, 15, 300));
        shop.put("fish", new Item(ItemType.FOOD, "meat", "+10 health", 7.0, 10, 200));
        shop.put("bread", new Item(ItemType.FOOD, "bread", "+5 health", 5.0, 5, 100));
    }
    
    public void setupBlacksmith(){
        shop.put("longsword", new Item(ItemType.ATTACK, "longsword", "+25 damage", 60.0, 25, 1000));
        shop.put("hammer", new Item(ItemType.ATTACK, "hammer", "+40 damage", 80.0, 40, 4000));
        shop.put("spear", new Item(ItemType.ATTACK, "spear", "+30 damage", 35.0, 30, 1500));
        shop.put("axe", new Item(ItemType.ATTACK, "axe", "+35 damage", 60.0, 35, 3000));
        shop.put("shield", new Item(ItemType.DEFENSE, "shield", "+15 protection", 70.0, 15, 5000));
    }
    
    public void setupSmuggler(){
        shop.put("key", new Item(ItemType.ATTACK, "key", "open safehouse door", 5.0, 0, 10000));
    }

}
