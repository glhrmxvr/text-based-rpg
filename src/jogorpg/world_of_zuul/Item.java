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
    
    private String item;
    private int weight;

    public Item(String item, int weight) {
        this.item = item;
        this.weight = weight;
    }
    
    public String getItem() {
        return item;
    }

    public int getWeight() {
        return weight;
    }
}
