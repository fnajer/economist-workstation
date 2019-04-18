/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package economistworkstation.Entity;

import economistworkstation.Database;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 *
 * @author fnajer
 */

public class Services extends Payment {
    private final ObjectProperty countWater;
    private final ObjectProperty countElectricity;
    private final ObjectProperty costHeading;
    private final ObjectProperty costGarbage;
    private final ObjectProperty costInternet;
    private final ObjectProperty costTelephone;
    private final ObjectProperty tariffWater;
    private final ObjectProperty tariffElectricity;
    
    public Services() {
        this(0.0, null, null, null, null, null, null, null, null, null);
    }
    
    public Services(double paid, String datePaid, Object countWater, 
            Object countElectricity, Object costHeading, Object costGarbage,
            Object costInternet, Object costTelephone, Object tariffWater, 
            Object tariffElectricity) {
        super(paid, datePaid);
        this.countWater = new SimpleObjectProperty(countWater);
        this.countElectricity = new SimpleObjectProperty(countElectricity);
        this.costHeading = new SimpleObjectProperty(costHeading);
        this.costGarbage = new SimpleObjectProperty(costGarbage);
        this.costInternet = new SimpleObjectProperty(costInternet); 
        this.costTelephone = new SimpleObjectProperty(costTelephone);
        this.tariffWater = new SimpleObjectProperty(tariffWater);
        this.tariffElectricity = new SimpleObjectProperty(tariffElectricity);
    }
      
    public Double getCountWater() {
        return (Double) countWater.get();
    }
    public void setCountWater(Double countWater) {
        this.countWater.set(countWater);
    }
    
    public Double getCountElectricity() {
        return (Double) countElectricity.get();
    }
    public void setCountElectricity(Double countElectricity) {
        this.countElectricity.set(countElectricity);
    }
     
    public Double getCostHeading() {
        return (Double) costHeading.get();
    }
    public void setCostHeading(Double costHeading) {
        this.costHeading.set(costHeading);
    }
    
    public Double getCostGarbage() {
        return (Double) costGarbage.get();
    }
    public void setCostGarbage(Double costGarbage) {
        this.costGarbage.set(costGarbage);
    }
    
     public Double getCostInternet() {
        return (Double) costInternet.get();
    }
    public void setCostInternet(Double costInternet) {
        this.costInternet.set(costInternet);
    }
    
     public Double getCostTelephone() {
        return (Double) costTelephone.get();
    }
    public void setCostTelephone(Double costTelephone) {
        this.costTelephone.set(costTelephone);
    }
    
    public Double getTariffWater() {
        return (Double) tariffWater.get();
    }
    public void setTariffWater(Double tariffWater) {
        this.tariffWater.set(tariffWater);
    }
    
    public Double getTariffElectricity() {
        return (Double) tariffElectricity.get();
    }
    public void setTariffElectricity(Double tariffElectricity) {
        this.tariffElectricity.set(tariffElectricity);
    }
    
    public Double calcCostWater() {
        try {
            return getCountWater() * getTariffWater();
        } catch(NullPointerException e) {
            return 0.0;
        }
    }
    public Double calcCostElectricity() {
        try {
            return getCountElectricity() * getTariffElectricity();
        } catch(NullPointerException e) {
            return 0.0;
        }
    }
    public Double calcCostHeading() {
        Double cost = getCostHeading();
        if (cost != null)
            return cost;
        else
            return 0.0;
    }
    public Double calcCostGarbage() {
        Double cost = getCostGarbage();
        if (cost != null)
            return cost;
        else
            return 0.0;
    }
    public Double calcCostInternet() {
        Double cost = getCostInternet();
        if (cost != null)
            return cost;
        else
            return 0.0;
    }
    public Double calcCostTelephone() {
        Double cost = getCostTelephone();
        if (cost != null)
            return cost;
        else
            return 0.0;
    }
    
    @Override
    public PreparedStatement getInsertStatement(Database db) throws SQLException{
        PreparedStatement ps = db.conn.prepareStatement("INSERT INTO SERVICES "
                + "(id, paid_services, date_paid_services, count_water, tariff_water, "
                + "count_electricity, tariff_electricity, cost_meter_heading, "
                + "cost_meter_garbage, cost_internet, cost_telephone) "
                + "VALUES(NULL,?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
        ps.setDouble(1, getPaid());
        ps.setString(2, getDatePaid());
        ps.setObject(3, getCountWater(), java.sql.Types.DOUBLE);
        ps.setObject(4, getTariffWater(), java.sql.Types.DOUBLE);
        ps.setObject(5, getCountElectricity(), java.sql.Types.DOUBLE);
        ps.setObject(6, getTariffElectricity(), java.sql.Types.DOUBLE);
        ps.setObject(7, getCostHeading(), java.sql.Types.DOUBLE);
        ps.setObject(8, getCostGarbage(), java.sql.Types.DOUBLE);
        ps.setObject(9, getCostInternet(), java.sql.Types.DOUBLE);
        ps.setObject(10, getCostTelephone(), java.sql.Types.DOUBLE);
        
        return ps;
    }
    @Override
    public PreparedStatement getUpdateStatement(Database db) throws SQLException {
        PreparedStatement ps = db.conn.prepareStatement("UPDATE SERVICES "
                + "SET paid_services=?, date_paid_services=?, count_water=?, tariff_water=?, "
                + "count_electricity=?, tariff_electricity=?, cost_meter_heading=?, "
                + "cost_meter_garbage=?, cost_internet=?, cost_telephone=? "
                + "WHERE id=?", Statement.RETURN_GENERATED_KEYS);
        ps.setDouble(1, getPaid());
        ps.setString(2, getDatePaid());
        ps.setObject(3, getCountWater(), java.sql.Types.DOUBLE);
        ps.setObject(4, getTariffWater(), java.sql.Types.DOUBLE);
        ps.setObject(5, getCountElectricity(), java.sql.Types.DOUBLE);
        ps.setObject(6, getTariffElectricity(), java.sql.Types.DOUBLE);
        ps.setObject(7, getCostHeading(), java.sql.Types.DOUBLE);
        ps.setObject(8, getCostGarbage(), java.sql.Types.DOUBLE);
        ps.setObject(9, getCostInternet(), java.sql.Types.DOUBLE);
        ps.setObject(10, getCostTelephone(), java.sql.Types.DOUBLE);
        ps.setInt(11, getId());
        
        return ps;
    }
    
   
    @Override
    public String toString() {
        return String.format("Платеж на коммунальные и прочие услуги. id = %d", getId());
    }
}
