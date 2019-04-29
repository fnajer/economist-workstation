/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package economistworkstation.Entity;

import economistworkstation.Database;
import static economistworkstation.Util.Util.isExist;
import static economistworkstation.Util.Util.parseField;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 *
 * @author fnajer
 */

public class Services extends Payment {
    private final ObjectProperty<Double> countWater;
    private final ObjectProperty<Double> countElectricity;
    private final ObjectProperty<Double> costHeading;
    private final ObjectProperty<Double> costGarbage;
    private final ObjectProperty<Double> costInternet;
    private final ObjectProperty<Double> costTelephone;
    private final ObjectProperty<Double> tariffWater;
    private final ObjectProperty<Double> tariffElectricity;
    
    public Services() {
        this(null, null, null, null, null, null, null, null, null, null, null);
    }
    
    public Services(Object paid, String datePaid, Object countWater, 
            Object countElectricity, Object costHeading, Object costGarbage,
            Object costInternet, Object costTelephone, Object tariffWater, 
            Object tariffElectricity, Balance balance) {
        super(paid, datePaid, balance);
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
        return countWater.get();
    }
    public void setCountWater(Double countWater) {
        this.countWater.set(countWater);
    }
    
    public Double getCountElectricity() {
        return countElectricity.get();
    }
    public void setCountElectricity(Double countElectricity) {
        this.countElectricity.set(countElectricity);
    }
     
    public Double getCostHeading() {
        return costHeading.get();
    }
    public void setCostHeading(Double costHeading) {
        this.costHeading.set(costHeading);
    }
    
    public Double getCostGarbage() {
        return costGarbage.get();
    }
    public void setCostGarbage(Double costGarbage) {
        this.costGarbage.set(costGarbage);
    }
    
     public Double getCostInternet() {
        return costInternet.get();
    }
    public void setCostInternet(Double costInternet) {
        this.costInternet.set(costInternet);
    }
    
     public Double getCostTelephone() {
        return costTelephone.get();
    }
    public void setCostTelephone(Double costTelephone) {
        this.costTelephone.set(costTelephone);
    }
    
    public Double getTariffWater() {
        return tariffWater.get();
    }
    public void setTariffWater(Double tariffWater) {
        this.tariffWater.set(tariffWater);
    }
    
    public Double getTariffElectricity() {
        return tariffElectricity.get();
    }
    public void setTariffElectricity(Double tariffElectricity) {
        this.tariffElectricity.set(tariffElectricity);
    }
    
    public Double calcCostWater() {
        return safeGetSum(getCountWater(), getTariffWater());
    }
    public Double calcCostElectricity() {
        return safeGetSum(getCountElectricity(), getTariffElectricity());
    }
    public Double calcCostHeading() {
        return safeGetSum(getCostHeading());
    }
    public Double calcCostGarbage() {
        return safeGetSum(getCostGarbage());
    }
    public Double calcCostInternet() {
        return safeGetSum(getCostInternet());
    }
    public Double calcCostTelephone() {
        return safeGetSum(getCostTelephone());
    }
    
    @Override
    public PreparedStatement getInsertStatement(Database db) throws SQLException{
        PreparedStatement ps = db.conn.prepareStatement("INSERT INTO SERVICES "
                + "(id_services, paid_services, date_paid_services, count_water, tariff_water, "
                + "count_electricity, tariff_electricity, cost_meter_heading, "
                + "cost_meter_garbage, cost_internet, cost_telephone) "
                + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, getId());
        ps.setObject(2, getPaid());
        ps.setString(3, getDatePaid());
        ps.setObject(4, getCountWater());
        ps.setObject(5, getTariffWater());
        ps.setObject(6, getCountElectricity());
        ps.setObject(7, getTariffElectricity());
        ps.setObject(8, getCostHeading());
        ps.setObject(9, getCostGarbage());
        ps.setObject(10, getCostInternet());
        ps.setObject(11, getCostTelephone());
        
        return ps;
    }
    @Override
    public PreparedStatement getUpdateStatement(Database db) throws SQLException {
        PreparedStatement ps = db.conn.prepareStatement("UPDATE SERVICES "
                + "SET paid_services=?, date_paid_services=?, count_water=?, tariff_water=?, "
                + "count_electricity=?, tariff_electricity=?, cost_meter_heading=?, "
                + "cost_meter_garbage=?, cost_internet=?, cost_telephone=? "
                + "WHERE id_services=?", Statement.RETURN_GENERATED_KEYS);
        ps.setObject(1, getPaid());
        ps.setString(2, getDatePaid());
        ps.setObject(3, getCountWater());
        ps.setObject(4, getTariffWater());
        ps.setObject(5, getCountElectricity());
        ps.setObject(6, getTariffElectricity());
        ps.setObject(7, getCostHeading());
        ps.setObject(8, getCostGarbage());
        ps.setObject(9, getCostInternet());
        ps.setObject(10, getCostTelephone());
        ps.setInt(11, getId());
        
        return ps;
    }
    @Override
    public PreparedStatement getDeleteStatement(Database db) throws SQLException {
        PreparedStatement ps = db.conn.prepareStatement("DELETE FROM SERVICES "
                + "WHERE id_services=?");
        ps.setInt(1, getId());
        
        return ps;
    }
    
    @Override
    public Double sumToPay() {
        return sum(
            calcCostWater(),
            calcCostElectricity(),
            calcCostHeading(),
            calcCostGarbage(),
            calcCostInternet(),
            calcCostTelephone()
        );
    }
    
    private Double sum(Double ...values) {
        Double sum = 0.0;
        for (Double value : values) {
            try {
                sum += value;
            } catch(NullPointerException e) {}
        }
        return sum; 
    }
    
    @Override
    public boolean isEmpty() {
        return getCountWater() == null 
                && getTariffWater() == null 
                && getCountElectricity()== null 
                && getTariffElectricity()== null
                && getCostHeading()== null 
                && getCostGarbage()== null
                && getCostInternet()== null 
                && getCostTelephone()== null
                && getPaid() == null
                && getDatePaid() == null;
    }
    
    @Override
    public Services copy() {
        Services services = new Services(getPaid(), getDatePaid(), 
            getCountWater(), getTariffWater(), getCountElectricity(),
            getTariffElectricity(), getCostHeading(), getCostGarbage(),
            getCostInternet(), getCostTelephone(), getBalance().copy());
        services.setId(getId());
        return services;
    }
    
    @Override
    public void saveValuesOf(Field field, Period period) {
        setCountWater(parseField(field.getCountWater()));
        setTariffWater(parseField(field.getTariffWater()));
        setCountElectricity(parseField(field.getCountElectricity()));
        setTariffElectricity(parseField(field.getTariffElectricity()));
        setCostHeading(parseField(field.getCostHeading()));
        setCostGarbage(parseField(field.getCostGarbage()));
        setCostInternet(parseField(field.getCostInternet()));
        setCostTelephone(parseField(field.getCostTelephone()));

        setPaid(parseField(field.getPaymentServices()));
        setDatePaid(parseField(field.getDatePaidServices()));
        if (isExist(period.getServicesPayment()) && isExist(period.getServicesPayment().getBalance()))
            setBalance(period.getServicesPayment().getBalance().copy());
    }
    
    @Override
    public void bindPayment(Period period) {
        period.setServicesPayment(this);
    }
    
    @Override
    public boolean fieldsIsFilled(Field fields) {
        return fields.servicesIsFilled();
    }
    
   
    @Override
    public String toString() {
        return String.format("Платеж на коммунальные и прочие услуги. id = %d", getId());
    }
}
