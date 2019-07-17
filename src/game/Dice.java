/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.util.Random;

/**
 *
 * @author Guilherme
 */
public abstract class Dice {
    
    private static Random random = new Random();
    
    public static int dice(int max){
        return Dice.random.nextInt(max);
    }
    
}
