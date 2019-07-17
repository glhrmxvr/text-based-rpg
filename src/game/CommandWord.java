package game;

/**
 * Representations for all the valid command words for the game
 * along with a string in a particular language.
 * 
 * @author Michael Kolling and David J. Barnes
 * @version 2008.03.30
 */
public enum CommandWord
{
    // A value for each command word along with its
    // corresponding user interface string.
    USE("use"), BUY("buy"), EQUIP("equip"), UNEQUIP("unequip"), ATTACK("attack"), EAT("eat"), PICK("pick"),DROP("drop"), GO("go"), QUIT("quit"), UNKNOWN("@");
    
    // The command string.
    private String commandString;
    
    /**
     * Initialise with the corresponding command word.
     * @param commandWord The command string.
     */
    CommandWord(String commandString)
    {
        this.commandString = commandString;
    }
    
    /**
     * @return The command word as a string.
     */
    public String toString()
    {
        return commandString;
    }
}
