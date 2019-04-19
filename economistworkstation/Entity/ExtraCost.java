/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package economistworkstation.Entity;

import economistworkstation.Database;
import economistworkstation.EconomistWorkstation;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 *
 * @author fnajer
 */

public class ExtraCost {
    private final IntegerProperty id;
    private final ObjectProperty costRent;
    private final ObjectProperty costFine;
    private final ObjectProperty costTaxLand;
    private final ObjectProperty costServices;
    private final ObjectProperty costEquipment;
    
    public ExtraCost() {
        this(null, null, null, null, null);
    }
    
    public ExtraCost(Object costRent, Object costFine, Object costTaxLand, 
            Object costServices, Object costEquipment) {
        this.id = new SimpleIntegerProperty(0);
        this.costRent = new SimpleObjectProperty(costRent);
        this.costFine = new SimpleObjectProperty(costFine);
        this.costTaxLand = new SimpleObjectProperty(costTaxLand);
        this.costServices = new SimpleObjectProperty(costServices);
        this.costEquipment = new SimpleObjectProperty(costEquipment);
    }
    
    public int getId() {
        return id.get();
    }
    public void setId(int id) {
        this.id.set(id);
    }
    
    public Double getCostRent() {
        return (Double) costRent.get();
    }
    public void setCostRent(Double costRent) {
        this.costRent.set(costRent);
    }
    
    public Double getCostFine() {
        return (Double) costFine.get();
    }
    public void setCostFine(Double costFine) {
        this.costFine.set(costFine);
    }
    
    public Double getCostTaxLand() {
        return (Double) costTaxLand.get();
    }
    public void setCostTaxLand(Double costTaxLand) {
        this.costTaxLand.set(costTaxLand);
    }
    
    public Double getCostServices() {
        return (Double) costServices.get();
    }
    public void setCostServices(Double costServices) {
        this.costServices.set(costServices);
    }
    
    public Double getCostEquipment() {
        return (Double) costEquipment.get();
    }
    public void setCostEquipment(Double costEquipment) {
        this.costEquipment.set(costEquipment);
    }
    
    
    @Override
    public String toString() {
        return String.format("Оплата дополнительной стоимости. id = %d", getId());
    }
}
