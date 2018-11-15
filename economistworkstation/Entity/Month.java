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
public class Month {
    public int id;
    public int number;
    public String date;
    public double cost;
    public double fine;
    public double cost_water;
    public double cost_electricity;
    public double cost_heading;
    public boolean paid_rent;
    public boolean paid_communal;
    public int id_contract;
    
    public Month(int number, String date, double cost, double fine,
            double cost_water, double cost_electricity, double cost_heading, 
            boolean paid_rent, boolean paid_communal, int id_contract) {
       this.number = number;
       this.date = date; 
       this.cost = cost; 
       this.fine = fine; 
       this.cost_water = cost_water; 
       this.cost_electricity = cost_electricity; 
       this.cost_heading = cost_heading; 
       this.paid_rent = paid_rent;  
       this.paid_communal = paid_communal;
       this.id_contract = id_contract;
    }
    
    @Override
    public String toString() {
        return this.date + ' ' + this.number;
    }
}
