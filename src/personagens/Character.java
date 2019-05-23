/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package personagens;

/**
 *
 * @author aluno
 */
public class Character {
    
    private String name;
    private Integer energy;

    public Character(String name) {
        this.name = name;
        energy = 10;
    }

    public String getName() {
        return name;
    }

    public Integer getEnergy() {
        return energy;
    }
    
    public void increment() {
        if(energy <= 10)
            energy++;
    }
    
    public void decrement() {
        energy--;
        if(energy == 0)
            System.out.println(name + " died");
    }
    
    public void print() {
        System.out.println("#####################");
        System.out.println("# Nome: " + name);
        System.out.println("# Energia" + energy);
        System.out.println("#####################");
    }
}
