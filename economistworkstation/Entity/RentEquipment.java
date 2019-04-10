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

public class RentEquipment extends Payment {
    private final DoubleProperty costEquipment;
    
    public RentEquipment() {
        this(0.0, null, 0.0);
    }
    
    public RentEquipment(double paid, String datePaid, double costEquipment) {
        super(paid, datePaid);
        this.costEquipment = new SimpleDoubleProperty(costEquipment);
    }
    
    public double getCostEquipment() {
        return costEquipment.get();
    }
    public void setCostEquipment(double costEquipment) {
        this.costEquipment.set(costEquipment);
    }
    
    @Override
    public String toString() {
        return String.format("Платеж на аренду за оборудование. id = %d", this.getId());
    }
}
