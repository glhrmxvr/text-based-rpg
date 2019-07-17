/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package characters;

/**
 *
 * @author Guilherme
 */
public abstract class Fighter extends Character{
    
    private Integer health;
    private Integer coins;

    public Fighter(Integer health, Integer coins, String name) {
        super(name);
        this.health = health;
        this.coins = coins;
    }
    
    public Integer getCoins(){
        return coins;
    }

    public void setCoins(Integer coins) {
        this.coins = coins;
    }
    
    public Integer getHealth() {
        return health;
    }
    
    public void increment(int h) {
        if(health + h <= 100)
            health += h;
    }
    
    public void decrement(int damage) {
        health -= damage;
    }

    public boolean isDead() {
        if(health <= 0)
            return true;
        return false;
    }
}
