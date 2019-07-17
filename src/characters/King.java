/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package characters;

import game.Item;
import game.ItemType;

/**
 *
 * @author Guilherme
 */
public class King extends Enemy {
    
    public King(String name, Integer health, Integer coins) {
        super(name, health, coins, new Item(ItemType.ATTACK, "longsword", "king's weapon", 0, 45, 0));
    }
    
}
