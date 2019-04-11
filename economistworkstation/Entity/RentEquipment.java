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
    public int addPaymentToDb(Database db) throws SQLException {
        int idFine = 0;
        
        PreparedStatement ps = db.conn.prepareStatement("insert into RENTEQUIPMENT "
                + "(id, paid_equipment, date_paid_equipment, cost_equipment) "
                + "values(NULL,?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
        ps.setDouble(1, getPaid());
        ps.setString(2, getDatePaid());
        ps.setDouble(3, getCostEquipment());

        ResultSet rs = ps.getGeneratedKeys();
        
        if (rs.next()) {
            idFine = rs.getInt(1);
        }
        ps.executeUpdate();
        System.out.println("Добавлен платеж на аренду помещения: " + idFine);
        return idFine;
    }
    @Override
    public int updatePaymentToDb(Database db) throws SQLException {
        int idFine = 0;
        
        PreparedStatement ps = db.conn.prepareStatement("UPDATE RENTEQUIPMENT "
                + "SET paid_equipment=?, date_paid_equipment=?, cost_equipment=? "
                + "WHERE id=?;", Statement.RETURN_GENERATED_KEYS);
        ps.setDouble(1, getPaid());
        ps.setString(2, getDatePaid());
        ps.setDouble(3, getCostEquipment());
        ps.setInt(4, getId());
        
        ResultSet rs = ps.getGeneratedKeys();
        
        if (rs.next()) {
            idFine = rs.getInt(1);
        }
        ps.executeUpdate();
        System.out.println("Изменен платеж на аренду помещения: " + idFine);
        return idFine;
    }
    
    @Override
    public String toString() {
        return String.format("Платеж на аренду за оборудование. id = %d", this.getId());
    }
}
