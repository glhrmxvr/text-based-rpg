/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jogorpg.world_of_zuul;

import personagens.Hero;
import personagens.Character;
import personagens.Villain;

/**
 *
 * @author aluno
 */
public class SimuladorDeBatalha {
  
    private Character player1;
    private Character player2;
    
    public SimuladorDeBatalha(Hero hero, Villain villain){
        player1 = hero;
        player2 = villain;
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
