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

public class Rent {
    public int id;
    public String date;
    public double cost;
    public double fine;
    public double paid;
    
    public Rent(String date, double cost, double fine, double paid) {
       this.date = date; 
       this.cost = cost; 
       this.fine = fine; 
       this.paid = paid;  
    }
    
    @Override
    public String toString() {
        return this.date;
    }
}
