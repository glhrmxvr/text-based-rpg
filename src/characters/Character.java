/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package characters;

/**
 *
 * @author aluno
 */
public class Character {
    
    private String name;
    private Integer health;

    public Character(String name) {
        this.name = name;
        health = 15;
    }

    public String getName() {
        return name;
    }

    public Integer getHealth() {
        return health;
    }
    
    public void increment() {
        if(health <= 10)
            health++;
    }
    
    public void decrement() {
        health--;
        if(health == 0)
            System.out.println(name + " died");
    }
    
    public void print() {
        System.out.println("#####################");
        System.out.println("# Nome: " + name);
        System.out.println("# Energia" + health);
        System.out.println("#####################");
    }
}
