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

public class TaxLand extends Payment {
    private final DoubleProperty taxLand;
    
    public TaxLand() {
        this(0.0, null, 0.0);
    }
    
    public TaxLand(double paid, String datePaid, double taxLand) {
        super(paid, datePaid);
        this.taxLand = new SimpleDoubleProperty(taxLand);
    }
    
    public double getTaxLand() {
        return taxLand.get();
    }
    public void setTaxLand(double taxLand) {
        this.taxLand.set(taxLand);
    }
    
    @Override
    public String toString() {
        return String.format("Платеж на земельный налог. id = %d", this.getId());
    }
}
