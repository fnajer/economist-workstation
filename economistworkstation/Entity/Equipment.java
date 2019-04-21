/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package economistworkstation.Entity;

import economistworkstation.Database;
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
    private final ObjectProperty costEquipment;
    
    public Equipment() {
        this(null, null, null);
    }
    
    public Equipment(Object paid, String datePaid, Object costEquipment) {
        super(paid, datePaid);
        this.costEquipment = new SimpleObjectProperty(costEquipment);
    }
    
    public Double getCostEquipment() {
        return (Double) costEquipment.get();
    }
    public void setCostEquipment(Double costEquipment) {
        this.costEquipment.set(costEquipment);
    }
    
    @Override
    public PreparedStatement getInsertStatement(Database db) throws SQLException {
        PreparedStatement ps = db.conn.prepareStatement("INSERT INTO EQUIPMENT "
                + "(id, paid_equipment, date_paid_equipment, cost_equipment) "
                + "VALUES(NULL,?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
        ps.setObject(1, getPaid(), java.sql.Types.DOUBLE);
        ps.setString(2, getDatePaid());
        ps.setObject(3, getCostEquipment(), java.sql.Types.DOUBLE);

        return ps;
    }
    @Override
    public PreparedStatement getUpdateStatement(Database db) throws SQLException {
        PreparedStatement ps = db.conn.prepareStatement("UPDATE EQUIPMENT "
                + "SET paid_equipment=?, date_paid_equipment=?, cost_equipment=? "
                + "WHERE id=?", Statement.RETURN_GENERATED_KEYS);
        ps.setObject(1, getPaid(), java.sql.Types.DOUBLE);
        ps.setString(2, getDatePaid());
        ps.setObject(3, getCostEquipment(), java.sql.Types.DOUBLE);
        ps.setInt(4, getId());
        
        return ps;
    }
    @Override
    public PreparedStatement getDeleteStatement(Database db) throws SQLException {
        PreparedStatement ps = db.conn.prepareStatement("DELETE FROM EQUIPMENT "
                + "WHERE id=?", Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, getId());
        
        return ps;
    }
    
    @Override
    public String toString() {
        return String.format("Платеж на аренду за оборудование. id = %d", this.getId());
    }
}
