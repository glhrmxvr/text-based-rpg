/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;
/**
 *
 * @author Guilherme
 */
public class Item {
    
    private String itemName;
    private String itemDescription;
    private double itemWeight;
    private int itemPower;
    private ItemType itemType;
    private int itemPrice;
    private int itemQuantity;

    public Item(ItemType itemType, String itemName, String itemDescription, double itemWeight, int itemPower, int itemPrice) {
        this.itemType = itemType;
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.itemWeight = itemWeight;
        this.itemPower = itemPower;
        this.itemPrice = itemPrice;
        itemQuantity = 0;
    }

    public int getItemPrice() {
        return itemPrice;
    }
    
    public ItemType getItemType() {
        return itemType;
    }

    public String getItemName() {
        return itemName;
    }
    
    public int getItemQuantity(){
        return itemQuantity;
    }
    
    public void increaseItemQuantity(){
        itemQuantity++; 
    }
    
    public void decreaseItemQuantity(){
        itemQuantity--;
    }

    public double getItemWeight() {
        return itemWeight;
    }

    public int getItemPower() {
        return itemPower;
    }

}
