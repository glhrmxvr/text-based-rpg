/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jogorpg.world_of_zuul;

import characters.Hero;
import characters.Character;
import characters.Villain;

/**
 *
 * @author aluno
 */
public class BattleSimulator {
  
    private Character player1;
    private Character player2;
    
    public BattleSimulator(Character player1, Character player2){
        this.player1 = player1;
        this.player2 = player2;
    }

    public int luck() {
        return (int) (Math.random() * (7 - 1) + 1);
    }

    public void fight() {
        int luckHero = luck();
        int luckVillain = luck();

        if(luckHero == luckVillain){
            player1.decrement();
            player2.decrement();
        }
        else if (luckHero > luckVillain){
            player1.increment();
            player2.decrement();
        }
        else {
            player2.increment();
            player1.decrement();
        } 
    }
}
