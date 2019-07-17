package gui;

import characters.King;
import characters.Pacific;
import characters.PacificType;
import characters.Player;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import game.Command;
import game.CommandWord;
import game.Item;
import game.ItemType;
import game.Parser;
import game.Room;

/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael Kolling and David J. Barnes
 * @version 2008.03.30
 */

public class MainWindow extends JFrame implements ActionListener
{
    private Parser parser;
    private static Room currentRoom;
    private static Player player;
    private static String endGame;
    private JPanel titlePanel, startButtonPanel, generalInfoPanel;
    private JPanel roomInfoPanel, consolePanel, buttonPanel;
    private JLabel titleLabel;
    private Font bigFont = new Font("Times New Roman", Font.PLAIN, 90);
    private Font normalFont = new Font("Times New Roman", Font.PLAIN, 20);
    private RoomPanel roomPanel;
    private PlayerStatusPanel playerStatusPanel;
    private InventoryPanel inventoryPanel;
    private ShopPanel shopPanel;
    private static JTextArea console;
    private JButton useKeyB, startB, attackB, equipB, unequipB, buyB;
    private JButton eatB, pickB, dropB, quitB;
    private JButton northB, southB, westB, eastB;

    /**
     * Create the game and initialise its internal map.
     * @param heroi
     */
    public MainWindow() 
    {
        setupGame();
        parser = new Parser();
        player = new Player();

        menuWindow();
    }
    

    /**
     * Create all the rooms and link their exits together.
     */
    private void setupGame(){
        
        ArrayList<Room> rooms = new ArrayList<>();
        
        // create rooms
        try(BufferedReader br = new BufferedReader(new FileReader("createRooms.txt"))){
            
            String line = br.readLine();
            while(line != null){
                String[] fields = line.split(",");
                rooms.add(new Room(fields[0], fields[1]));
                line = br.readLine();
            }
        }
        catch (IOException e){
            System.out.println("Error: " + e.getMessage());
        }
               
        // initialise room exits
        try(BufferedReader br = new BufferedReader(new FileReader("roomExits.txt"))){
            
            String line = br.readLine();
            while(line != null){
                String[] fields = line.split(",");
                for(Room room : rooms){
                    if(fields[0].equals(room.getRoomName()))
                        for(Room exit : rooms)
                            if(fields[2].equals(exit.getRoomName()))
                                room.setExit(fields[1], exit);
                }
                line = br.readLine();
            }
        }
        catch (IOException e){
            System.out.println("Error: " + e.getMessage());
        }
        
        // add characters or items
        for(Room room : rooms){
            if(room.getRoomName().equals("saferoom")){
                room.addEnemy("King Akbar", new King("akbar", 100, 20000));
                room.lock();
            }
            if(room.getRoomName().equals("yard")){
                room.addNpc("Blacksmith", new Pacific("blacksmith", PacificType.BLACKSMITH));
                room.addNpc("Merchant", new Pacific("merchant", PacificType.MERCHANT));
            }
            if(room.getRoomName().equals("gate"))
                room.addNpc("Smuggler", new Pacific("smuggler", PacificType.SMUGGLER));
            if(room.getRoomName().equals("gate")){
                room.addItem("sword", new Item(ItemType.ATTACK, "sword", "+15 damage", 20.0, 15, 0));
                currentRoom = room; // start game in the castle's gate
            }
        }
  
    }
    
    public void printWelcome(){
        consoleAppend("You are Bjorn Ironside one of the most powerful vikings in the world.");
        consoleAppend("A british king called Akbar attacked one of your village and killed all your people.");
        consoleAppend("Now you're in his castle looking for revenge");
        consoleAppend("May the Gods be with you.");
        consoleAppend(currentRoom.getLongDescription());
        roomPanel.refresh();
        playerStatusPanel.refresh();
    }

    public static Room getCurrentRoom() {
        return currentRoom;
    }
    
    public static Player getPlayer() {
        return player;
    }
    
    public static void setEndGame(String end){
        endGame = end;
    }

    public static void consoleAppend(String text){
        console.append(text);
        console.append("\n");
    }

    /** 
     * Try to go to one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {

        String direction = command.getSecondWord();
        

        // Try to leave current room.
        Room nextRoom = currentRoom.getRoomExits(direction);
        
        if(currentRoom.getRoomName().equals("thirdhall") && direction.equals("north")){
            if(nextRoom.isLocked()){
                consoleAppend("This room is locked");
                return;
            }
        }
        
        if (nextRoom == null) {
            console.append("There is no door to " + direction + "\n");
        }
        else {
            currentRoom = nextRoom;
            currentRoom.generateEnemies();
            console.append(currentRoom.getLongDescription());
            roomPanel.changeRoom(currentRoom);
        }
    }
    
    private void menuWindow(){
        
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.black);
        setLayout(null);
        setVisible(true);
        
        titlePanel = new JPanel();
        titlePanel.setOpaque(false);
        titlePanel.setBounds(100, 100, 600, 150);
        titleLabel = new JLabel("KINGSLAYER");
        titleLabel.setForeground(Color.white);
        titleLabel.setFont(bigFont);
        
        startButtonPanel = new JPanel();
        startButtonPanel.setBounds(300, 400, 200, 100);
        startButtonPanel.setOpaque(false);
        
        startB = new JButton("START");
        startB.setBackground(Color.black);
        startB.setForeground(Color.white);
        startB.setFont(normalFont);
        startB.addActionListener(this);
        startB.setFocusPainted(false);
        
        titlePanel.add(titleLabel);
        startButtonPanel.add(startB);
        
        add(titlePanel);
        add(startButtonPanel);
    }
    
    public void gameWindow(){
     
        titlePanel.setVisible(false);
        startButtonPanel.setVisible(false);
        remove(titlePanel);
        remove(startButtonPanel);

        setLayout(new GridLayout(4,0));
       
        //Main Window Panel
        generalInfoPanel = new JPanel();
        roomInfoPanel = new JPanel();
        consolePanel = new JPanel();
        buttonPanel = new JPanel();
        add(generalInfoPanel);
        add(roomInfoPanel);
        add(consolePanel);
        add(buttonPanel);
        generalInfoPanel.setBackground(Color.red);
        roomInfoPanel.setOpaque(false);
        consolePanel.setOpaque(false);
        buttonPanel.setOpaque(false);
        buttonPanel.setBackground(Color.white);
        generalInfoPanel.setVisible(true);
        roomInfoPanel.setVisible(true);
        consolePanel.setVisible(true);
        buttonPanel.setVisible(true);
        
        
        //Button Panel
        buttonPanel.setLayout(new GridLayout(0,2));
        buttonPanel.setOpaque(false);
        //Command Button
        JPanel commands = new JPanel();
        commands.setOpaque(false);
        commands.setLayout(new GridLayout(3,3));
        attackB = new JButton("ATTACK");
        attackB.addActionListener(this);
        attackB.setBackground(Color.black);
        attackB.setForeground(Color.white);
        attackB.setFont(normalFont);
        attackB.setFocusPainted(false);
        commands.add(attackB);
        equipB = new JButton("EQUIP");
        equipB.addActionListener(this);
        equipB.setBackground(Color.black);
        equipB.setForeground(Color.white);
        equipB.setFont(normalFont);
        equipB.setFocusPainted(false);
        commands.add(equipB);
        unequipB = new JButton("UNEQUIP");
        unequipB.addActionListener(this);
        unequipB.setBackground(Color.black);
        unequipB.setForeground(Color.white);
        unequipB.setFont(normalFont);
        unequipB.setFocusPainted(false);
        commands.add(unequipB);
        pickB = new JButton("PICK");
        pickB.addActionListener(this);
        pickB.setBackground(Color.black);
        pickB.setForeground(Color.white);
        pickB.setFont(normalFont);
        pickB.setFocusPainted(false);
        commands.add(pickB);
        dropB = new JButton("DROP");
        dropB.addActionListener(this);
        dropB.setBackground(Color.black);
        dropB.setForeground(Color.white);
        dropB.setFont(normalFont);
        dropB.setFocusPainted(false);
        commands.add(dropB);
        eatB = new JButton("EAT");
        eatB.addActionListener(this);
        eatB.setBackground(Color.black);
        eatB.setForeground(Color.white);
        eatB.setFont(normalFont);
        eatB.setFocusPainted(false);
        commands.add(eatB);
        buyB = new JButton("BUY");
        buyB.addActionListener(this);
        buyB.setBackground(Color.black);
        buyB.setForeground(Color.white);
        buyB.setFont(normalFont);
        buyB.setFocusPainted(false);
        commands.add(buyB);
        useKeyB = new JButton("USE KEY");
        useKeyB.addActionListener(this);
        useKeyB.setBackground(Color.black);
        useKeyB.setForeground(Color.white);
        useKeyB.setFont(normalFont);
        useKeyB.setFocusPainted(false);
        commands.add(useKeyB);
        quitB = new JButton("QUIT");
        quitB.addActionListener(this);
        quitB.setBackground(Color.black);
        quitB.setForeground(Color.white);
        quitB.setFont(normalFont);
        quitB.setFocusPainted(false);
        commands.add(quitB);
        buttonPanel.add(commands);
        
        //Movement Button
        JPanel movement = new JPanel();
        movement.setLayout(new BorderLayout());
        movement.setOpaque(false);
        northB = new JButton("NORTH");
        northB.addActionListener(this);
        northB.setBackground(Color.black);
        northB.setForeground(Color.white);
        northB.setFont(normalFont);
        northB.setFocusPainted(false);
        movement.add(northB, BorderLayout.NORTH);
        southB = new JButton("SOUTH");
        southB.addActionListener(this);
        southB.setBackground(Color.black);
        southB.setForeground(Color.white);
        southB.setFont(normalFont);
        southB.setFocusPainted(false);
        movement.add(southB, BorderLayout.SOUTH);
        westB = new JButton("WEST");
        westB.addActionListener(this);
        westB.setBackground(Color.black);
        westB.setForeground(Color.white);
        westB.setFont(normalFont);
        westB.setFocusPainted(false);
        movement.add(westB, BorderLayout.WEST);
        eastB = new JButton("EAST");
        eastB.addActionListener(this);
        eastB.setBackground(Color.black);
        eastB.setForeground(Color.white);
        eastB.setFont(normalFont);
        eastB.setFocusPainted(false);
        movement.add(eastB, BorderLayout.EAST);
        buttonPanel.add(movement);
        
        //Console Panel
        console = new JTextArea();
        console.setEditable(false); 
        consolePanel.setLayout( new BorderLayout() );
        consolePanel.add(new JScrollPane(console), BorderLayout.CENTER);
        
        //General Info Panel
        generalInfoPanel.setLayout(new GridLayout(0,3));
        playerStatusPanel = new PlayerStatusPanel();
        generalInfoPanel.add(playerStatusPanel);
        inventoryPanel = new InventoryPanel();
        generalInfoPanel.add(inventoryPanel);
        shopPanel = new ShopPanel();
        generalInfoPanel.add(shopPanel);
        
        //Room Info Panel
        roomPanel = new RoomPanel(currentRoom, shopPanel);
        roomInfoPanel.setLayout(new BorderLayout());
        roomInfoPanel.add(roomPanel, BorderLayout.CENTER);
        
        printWelcome();
    }
    
    public void gameOverWindow(){
        
        //When the player die or win the game, this window appears
        generalInfoPanel.setVisible(false);
        roomInfoPanel.setVisible(false);
        consolePanel.setVisible(false);
        buttonPanel.setVisible(false);
        remove(generalInfoPanel);
        remove(roomInfoPanel);
        remove(consolePanel);
        remove(buttonPanel);
        setLayout(null);
        
        JPanel endPanel = new JPanel();
        endPanel.setBounds(100, 150, 500, 200);
        endPanel.setOpaque(false);
        
        JLabel end = new JLabel(endGame);
        end.setFont(bigFont);
        end.setForeground(Color.white);
        
        endPanel.add(end);
        add(endPanel);
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startB) 
            gameWindow();
        else if (e.getSource() == northB) {
            Command comando = new Command(CommandWord.GO, "north");
            goRoom(comando);
        }
        else if (e.getSource() == southB) {
            Command comando = new Command(CommandWord.GO, "south");
            goRoom(comando);
        }
        else if (e.getSource() == eastB) {
            Command comando = new Command(CommandWord.GO, "east");
            goRoom(comando);
        }
        else if (e.getSource() == westB) {
            Command comando = new Command(CommandWord.GO, "west");
            goRoom(comando);
        }
        else if (e.getSource() == attackB) {
            Command command = new Command(CommandWord.ATTACK, roomPanel.takeSelectedCharacter());
            if(player.attack(command))
                gameOverWindow();
            playerStatusPanel.refresh();
            roomPanel.refresh();
        }
        else if(e.getSource() == pickB){
            Command command = new Command(CommandWord.PICK, roomPanel.takeSelectedItem());
            player.pick(command);
            roomPanel.refresh();
            inventoryPanel.refresh();
        }
        else if(e.getSource() == dropB){
            Command command = new Command(CommandWord.DROP, inventoryPanel.takeSelectedItem());
            player.drop(command);
            roomPanel.refresh();
            inventoryPanel.refresh();
        }
        else if(e.getSource() == eatB){
            Command command = new Command(CommandWord.EAT, inventoryPanel.takeSelectedItem());
            player.eat(command);
            inventoryPanel.refresh();
            playerStatusPanel.refresh();
        }
        else if(e.getSource() == equipB){
            Command command = new Command(CommandWord.EQUIP, inventoryPanel.takeSelectedItem());
            player.equip(command);
            playerStatusPanel.refresh();
            inventoryPanel.refresh();
        }
        else if(e.getSource() == unequipB){
            Command command = new Command(CommandWord.UNEQUIP, playerStatusPanel.takeSelectedItem());
            player.unequip(command);
            playerStatusPanel.refresh();
            inventoryPanel.refresh();
        }
        else if(e.getSource() == buyB){
            if(shopPanel.takeSelectedItem() != null && roomPanel.takeSelectedCharacter() != null){
                Command comando = new Command(CommandWord.BUY, shopPanel.takeSelectedItem());
                getCurrentRoom().getRoomNpc(roomPanel.takeSelectedCharacter()).sale(comando);
                shopPanel.refresh();
                inventoryPanel.refresh();
                playerStatusPanel.refresh();
            }
            else
                consoleAppend("You need to select an item first");
        }
        else if(e.getSource() == useKeyB){
            player.use();
        }
        else if(e.getSource() == quitB){
            System.exit(0);
        }

    }
     
}
