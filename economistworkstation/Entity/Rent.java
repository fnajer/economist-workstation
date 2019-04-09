/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package economistworkstation.Entity;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

/**
 *
 * @author fnajer
 */

public class Rent extends Payment {
    private final DoubleProperty cost;
    private final DoubleProperty indexCost;
    
    public Rent() {
        this(0.0, null, 0.0, 0.0);
    }
    
    public Rent(double paid, String datePaid, double cost, double indexCost) {
        super(paid, datePaid);
        this.cost = new SimpleDoubleProperty(cost);
        this.indexCost = new SimpleDoubleProperty(indexCost);
    }
    
    public double getCost() {
        return cost.get();
    }
    public void setCost(double cost) {
        this.cost.set(cost);
    }
    
    public double getIndexCost() {
        return indexCost.get();
    }
    public void setIndexCost(double indexCost) {
        this.indexCost.set(indexCost);
    }
    
    public double calcSumRent(Rent rent) {
        return rent.getCost() * rent.getIndexCost();
    }
    
    @Override
    public String toString() {
        return String.format("Платеж на аренду помещения. id = %d", getId());
    }
}
