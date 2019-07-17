package game;

import characters.Enemy;
import characters.Fighter;
import characters.Pacific;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashMap;
import static game.Dice.dice;
/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  For each existing exit, the room 
 * stores a reference to the neighboring room.
 * 
 * @author  Michael Kolling and David J. Barnes
 * @version 2008.03.30
 */

public class Room 
{   
    private String roomName;
    private String roomDescription;
    private HashMap<String, Room> roomExits;        // stores exits of this room.
    private HashMap<String, Fighter> enemies;
    private HashMap<String, Pacific> npcs;
    private HashMap<String, Item> roomItems;
    private boolean isLocked;
    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String name, String description) 
    {
        this.roomName = name;
        this.roomDescription = description;
        roomExits = new HashMap<>();
        roomItems = new HashMap<>();
        enemies = new HashMap<>();
        npcs = new HashMap<>();
        isLocked = false;
    }

    /**
     * Define an exit from this room.
     * @param direction The direction of the exit.
     * @param neighbor  The room to which the exit leads.
     */
    public void setExit(String direction, Room neighbor) 
    {
        roomExits.put(direction, neighbor);
    }

    /**
     * @return The short description of the room
     * (the one that was defined in the constructor).
     */
    public String getShortDescription()
    {
        return roomDescription;
    }

    /**
     * Return a description of the room in the form:
     *     You are in the kitchen.
     *     Exits: north west
     * @return A long description of this room
     */
    public String getLongDescription()
    {
        return "You are " + roomDescription + ".\n" + getExitString();
    }

    /**
     * Return a string describing the room's exits, for example
     * "Exits: north west".
     * @return Details of the room's exits.
     */
    private String getExitString()
    {
        String returnString = "Exits:";
        Set<String> keys = roomExits.keySet();
        for(String exit : keys) {
            returnString += " " + exit;
        }
        returnString += "\n";
        return returnString;
    }

    /**
     * Return the room that is reached if we go from this room in direction
     * "direction". If there is no room in that direction, return null.
     * @param direction The exit's direction.
     * @return The room in the given direction.
     */
    public Room getRoomExits(String direction) 
    {
        return roomExits.get(direction);
    }

    public String getRoomName() {
        return roomName;
    }
    
    public HashMap<String, Item> getRoomItems() {
        return roomItems;
    }
    
    public Item getRoomItem(String name){
        return roomItems.get(name);
    }
    
    public ArrayList<String> getCharacters(){
        ArrayList<String> names = new ArrayList<>();
        for(String name : enemies.keySet())
            names.add(name);
        for(String name : npcs.keySet())
            names.add(name);
        return names;
    }
    
    public Enemy getRoomEnemy(String name){
        return (Enemy)enemies.get(name);
    }
    
    public Pacific getRoomNpc(String name){
        return npcs.get(name);
    }
  
    public boolean isNpc(String name){
        return npcs.containsKey(name);
    }
    
    public void addNpc(String name, Pacific pacific){
        npcs.put(name, pacific);
    }
    
    public void generateEnemies(){
        int qnt = dice(3);
        for(int i = 0; i < qnt; i++)
            addEnemy("Guard"+i, new Enemy("Guard"+i, dice(30), dice(202), new Item(ItemType.ATTACK, "sword", "You can use that in battle", 30.0, dice(15), 0)));
    }
    
    public void addItem(String name, Item item){
        roomItems.put(name, item);
    }
    
    public Item removeItem(String name){
        return roomItems.remove(name);
    }
    
    public boolean hasEnemyNamed(String name){
        return enemies.containsKey(name);
    }
    
    public void addEnemy(String name, Enemy enemie){
        if(!hasEnemyNamed(name))
            enemies.put(name, enemie);
    }
    
    public void removeEnemy(String name){
        enemies.remove(name);
    }
    
    public void lock(){
        isLocked = true;
    }
    
    public void unlock(){
        isLocked = false;
    }
    
    public boolean isLocked(){
        return isLocked;
    }
}

