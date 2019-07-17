/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package characters;

import game.Item;

/**
 *
 * @author aluno
 */
public class Enemy extends Fighter {
    
    private Item weapon;
    
    public Enemy(String name, Integer health, Integer coins, Item weapon) {
        super(health, coins, name);
        this.weapon = weapon;
    }

    public Item getWeapon() {
        return weapon;
    }
    
}
