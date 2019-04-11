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
import javafx.beans.property.SimpleDoubleProperty;

/**
 *
 * @author fnajer
 */

public class Services extends Payment {
    private final DoubleProperty countWater;
    private final DoubleProperty countElectricity;
    private final DoubleProperty costHeading;
    private final DoubleProperty costGarbage;
    private final DoubleProperty costInternet;
    private final DoubleProperty costTelephone;
    private final DoubleProperty tariffWater;
    private final DoubleProperty tariffElectricity;
    
    public Services() {
        this(0.0, null, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
    }
    
    public Services(double paid, String datePaid, double countWater, 
            double countElectricity, double costHeading, double costGarbage,
            double costInternet, double costTelephone, double tariffWater, 
            double tariffElectricity) {
        super(paid, datePaid);
        this.countWater = new SimpleDoubleProperty(countWater);
        this.countElectricity = new SimpleDoubleProperty(countElectricity);
        this.costHeading = new SimpleDoubleProperty(costHeading);
        this.costGarbage = new SimpleDoubleProperty(costGarbage);
        this.costInternet = new SimpleDoubleProperty(costInternet); 
        this.costTelephone = new SimpleDoubleProperty(costTelephone);
        this.tariffWater = new SimpleDoubleProperty(tariffWater);
        this.tariffElectricity = new SimpleDoubleProperty(tariffElectricity);
    }
      
    public double getCountWater() {
        return countWater.get();
    }
    public void setCountWater(double countWater) {
        this.countWater.set(countWater);
    }
    
    public double getCountElectricity() {
        return countElectricity.get();
    }
    public void setCountElectricity(double countElectricity) {
        this.countElectricity.set(countElectricity);
    }
     
    public double getCostHeading() {
        return costHeading.get();
    }
    public void setCostHeading(double costHeading) {
        this.costHeading.set(costHeading);
    }
    
    public double getCostGarbage() {
        return costGarbage.get();
    }
    public void setCostGarbage(double costGarbage) {
        this.costGarbage.set(costGarbage);
    }
    
     public double getCostInternet() {
        return costInternet.get();
    }
    public void setCostInternet(double costInternet) {
        this.costInternet.set(costInternet);
    }
    
     public double getCostTelephone() {
        return costTelephone.get();
    }
    public void setCostTelephone(double costTelephone) {
        this.costTelephone.set(costTelephone);
    }
    
    public double getTariffWater() {
        return tariffWater.get();
    }
    public void setTariffWater(double tariffWater) {
        this.tariffWater.set(tariffWater);
    }
    
    public double getTariffElectricity() {
        return tariffElectricity.get();
    }
    public void setTariffElectricity(double tariffElectricity) {
        this.tariffElectricity.set(tariffElectricity);
    }
    
    public double calcCostWater(Services services) {
        return services.getCountWater() * services.getTariffWater();
    }
    public double calcCostElectricity(Services services) {
        return services.getCountElectricity() * services.getTariffElectricity();
    }
    
    @Override
    public int addPaymentToDb(Database db) throws SQLException {
        int idRent = 0;
        
        PreparedStatement ps = db.conn.prepareStatement("insert into Rent "
                + "(id, paid_services, date_paid_services, count_water, tariff_water, "
                + "count_electricity, tariff_electricity, cost_meter_heading, "
                + "cost_meter_garbage, cost_internet, cost_telephone) "
                + "values(NULL,?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
        ps.setDouble(1, getPaid());
        ps.setString(2, getDatePaid());
        ps.setDouble(3, getCountWater());
        ps.setDouble(4, getTariffWater());
        ps.setDouble(5, getCountElectricity());
        ps.setDouble(6, getCostHeading());
        ps.setDouble(7, getCostGarbage());
        ps.setDouble(8, getCostInternet());
        ps.setDouble(9, getCostTelephone());

        ResultSet rs = ps.getGeneratedKeys();
        
        if (rs.next()) {
            idRent = rs.getInt(1);
        }
        ps.executeUpdate();
        System.out.println("Добавлен платеж на аренду помещения: " + idRent);
        return idRent;
    }
    @Override
    public int updatePaymentToDb(Database db) throws SQLException {
        int idRent = 0;
        
        PreparedStatement ps = db.conn.prepareStatement("UPDATE Rent "
                + "SET paid_services=?, date_paid_services=?, count_water=?, tariff_water=? "
                + "count_electricity=?, tariff_electricity=?, cost_meter_heading=?, "
                + "cost_meter_garbage=?, cost_internet=?, cost_telephone=? "
                + "WHERE id=?;", Statement.RETURN_GENERATED_KEYS);
        ps.setDouble(1, getPaid());
        ps.setString(2, getDatePaid());
        ps.setDouble(3, getCountWater());
        ps.setDouble(4, getTariffWater());
        ps.setDouble(5, getCountElectricity());
        ps.setDouble(6, getCostHeading());
        ps.setDouble(7, getCostGarbage());
        ps.setDouble(8, getCostInternet());
        ps.setDouble(9, getCostTelephone());
        ps.setInt(10, getId());
        
        ResultSet rs = ps.getGeneratedKeys();
        
        if (rs.next()) {
            idRent = rs.getInt(1);
        }
        ps.executeUpdate();
        System.out.println("Изменен платеж на аренду помещения: " + idRent);
        return idRent;
    }
   
    @Override
    public String toString() {
        return String.format("Платеж на коммунальные и прочие услуги. id = %d", getId());
    }
}
