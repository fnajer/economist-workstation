/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package economistworkstation.Entity;

/**
 *
 * @author fnajer
 */
public class Building {
    public int id;
    public String type;
    public double square;
    public double cost_balance;
    public double cost_residue;
    
    public Building(String type, double square, double cost_balance, double cost_residue) {
       this.type = type; 
       this.square = square; 
       this.cost_balance = cost_balance; 
       this.cost_residue = cost_residue;
    }
    
    @Override
    public String toString() {
        return this.type;
    } 
}
