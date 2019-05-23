/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package personagens;

import java.util.HashMap;
import jogorpg.world_of_zuul.Item;

/**
 *
 * @author aluno
 */
public class Hero extends Character{
    
    private double weightLimit;
    private HashMap<String, Item> inventory;
    
    public Hero(String name) {
        super(name);
        this.weightLimit = 100;
        inventory = new HashMap<String,Item>();
    }

    public double getWeightLimit() {
        return weightLimit;
    }

    public void setWeightLimit(int weightLimit) {
        this.weightLimit = weightLimit;
    }
    
    public void eat() {
        for(int i = 0; i < 2; i++)
            increment();
    }   
    
     public double inventoryWeight(){
        double w = 0.0;
        for(String item : inventory.keySet())
            w += inventory.get(item).getWeight();
        return w;
    }
    
    public boolean addItem(String string, Item item){
        if(inventoryWeight() + item.getWeight() <= weightLimit){
            inventory.put(string, item);
            return true;
        }
        else
            return false;
    }
    
    public Item removeItem(String name){
        return inventory.remove(name);
    }
}
