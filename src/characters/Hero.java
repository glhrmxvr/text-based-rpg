/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package characters;

import java.util.HashMap;
import jogorpg.world_of_zuul.Item;
import jogorpg.world_of_zuul.Room;

/**
 *
 * @author aluno
 */
public class Hero extends Character{
    
    private double weightLimit;
    private HashMap<String, Item> inventory;
    private Room currentRoom;
    
    public Hero(String name) {
        super(name);
        this.weightLimit = 100;
        inventory = new HashMap<String,Item>();
    }

    public double getWeightLimit() {
        return weightLimit;
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public void setWeightLimit(int weightLimit) {
        this.weightLimit = weightLimit;
    }

    public HashMap<String, Item> getInventory() {
        return inventory;
    }
    
    public void eat() {
        for(int i = 0; i < 2; i++)
            increment();
    }   
    
     public double getCurrentWeight(){
        double w = 0.0;
        for(String item : inventory.keySet())
            w += inventory.get(item).getItemWeight();
        return w;
    }
     
    public Item getItem(String item){
        if(inventory.containsKey(item))
            return inventory.get(item);
        return null;
    }
    
    public boolean addItem(String string, Item item){
        if(getCurrentWeight() + item.getItemWeight() <= weightLimit){
            inventory.put(string, item);
            return true;
        }
        else
            System.out.println("Full Inventory");
            return false;
    }
    
    public Item removeItem(String name){
        return inventory.remove(name);
    }
}
