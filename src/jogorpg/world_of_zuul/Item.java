/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jogorpg.world_of_zuul;

/**
 *
 * @author Guilherme
 */
public class Item {
    
    private String itemName;
    private int itemWeight;

    public Item(String itemName, int itemWeight) {
        this.itemName = itemName;
        this.itemWeight = itemWeight;
    }
    
    public String getItemName() {
        return itemName;
    }

    public int getItemWeight() {
        return itemWeight;
    }
}
