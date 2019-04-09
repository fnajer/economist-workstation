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

public class Fine extends Payment {
    private final DoubleProperty fine;
    
    public Fine() {
        this(0.0, null, 0.0);
    }
    
    public Fine(double paid, String datePaid, double fine) {
        super(paid, datePaid);
        this.fine = new SimpleDoubleProperty(fine);
    }
    
    public double getFine() {
        return fine.get();
    }
    public void setFine(double fine) {
        this.fine.set(fine);
    }
    
    @Override
    public String toString() {
        return String.format("Платеж на пени. id = %d", this.getId());
    }
}
