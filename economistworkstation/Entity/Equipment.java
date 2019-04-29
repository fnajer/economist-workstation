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

public class Equipment extends Payment {
    private final ObjectProperty<Double> costEquipment;
    
    public Equipment() {
        this(null, null, null, null);
    }
    
    public Equipment(Object paid, String datePaid, Object costEquipment, Balance balance) {
        super(paid, datePaid, balance);
        this.costEquipment = new SimpleObjectProperty(costEquipment);
    }
    
    public Double getCostEquipment() {
        return costEquipment.get();
    }
    public void setCostEquipment(Double costEquipment) {
        this.costEquipment.set(costEquipment);
    }
    
    @Override
    public PreparedStatement getInsertStatement(Database db) throws SQLException {
        PreparedStatement ps = db.conn.prepareStatement("INSERT INTO EQUIPMENT "
                + "(id_equipment, paid_equipment, date_paid_equipment, cost_equipment) "
                + "VALUES(?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, getId());
        ps.setObject(2, getPaid());
        ps.setString(3, getDatePaid());
        ps.setObject(4, getCostEquipment());

        return ps;
    }
    @Override
    public PreparedStatement getUpdateStatement(Database db) throws SQLException {
        PreparedStatement ps = db.conn.prepareStatement("UPDATE EQUIPMENT "
                + "SET paid_equipment=?, date_paid_equipment=?, cost_equipment=? "
                + "WHERE id_equipment=?", Statement.RETURN_GENERATED_KEYS);
        ps.setObject(1, getPaid());
        ps.setString(2, getDatePaid());
        ps.setObject(3, getCostEquipment());
        ps.setInt(4, getId());
        
        return ps;
    }
    @Override
    public PreparedStatement getDeleteStatement(Database db) throws SQLException {
        PreparedStatement ps = db.conn.prepareStatement("DELETE FROM EQUIPMENT "
                + "WHERE id_equipment=?");
        ps.setInt(1, getId());
        
        return ps;
    }
    
    @Override
    public Double sumToPay() {
        return safeGetSum(getCostEquipment());
    }
    
    @Override
    public boolean isEmpty() {
        return getCostEquipment() == null
                && getPaid() == null
                && getDatePaid() == null;
    }
    
    @Override
    public Equipment copy() {
        Equipment equipment = new Equipment(getPaid(), getDatePaid(), 
            getCostEquipment(), getBalance().copy());
        equipment.setId(getId());
        return equipment;
    }
    
    @Override
    public void saveValuesOf(Field field, Period period) {
        setCostEquipment(parseField(field.getCostEquipment()));

        setPaid(parseField(field.getPaymentEquipment()));
        setDatePaid(parseField(field.getDatePaidEquipment()));
        if (isExist(period.getEquipmentPayment()) && isExist(period.getEquipmentPayment().getBalance()))
            setBalance(period.getEquipmentPayment().getBalance().copy());
    }
    
    @Override
    public String toString() {
        return String.format("Платеж на аренду за оборудование. id = %d", this.getId());
    }
}
