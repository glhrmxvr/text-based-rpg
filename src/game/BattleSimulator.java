/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import characters.Player;
import characters.Enemy;
import characters.Fighter;
import static gui.MainWindow.consoleAppend;
import static game.Dice.dice;
import static gui.MainWindow.getCurrentRoom;
import static gui.MainWindow.setEndGame;

/**
 *
 * @author aluno
 */
public class BattleSimulator {

    private Player player;
    private Enemy enemy;
    private int attackPrecision;
    private int damage;


    public BattleSimulator(Player player, Enemy enemy) {
        this.player = player;
        this.enemy = enemy;
    }

    public boolean fight() {

        attackPrecision = dice(10);
        damage = player.getAttack();
        attack(player, enemy);

        if (enemy.isDead()) {
            player.addCoins(enemy.getCoins());
            getCurrentRoom().removeEnemy(enemy.getName());
            consoleAppend(enemy.getName() + " died");
            if(enemy.getName().equals("akbar")){
                setEndGame("You won the game");
                return true;
            }
            return false;
        }

        attackPrecision = dice(10);
        damage = enemy.getWeapon().getItemPower() - player.getDefense();
        attack(enemy, player);

        if (player.isDead()) {
            setEndGame("You died");
            return true;
        }
        
        return false;
    }
    
    public void attack(Fighter attacker, Fighter defender) {
        if (attackPrecision <= 3) 
            consoleAppend(attacker.getName() + " missed the attack");
        else if (attackPrecision <= 6) {
            consoleAppend(attacker.getName() + " took " + damage/2 + " of his opponent's health");
            defender.decrement(damage / 2);
        }
        else{
            consoleAppend(attacker.getName() + " took " + damage + " of his opponent's health");
            defender.decrement(damage);
        }
    }
    
}
