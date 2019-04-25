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

public class Fine extends Payment {
    private final ObjectProperty<Double> fine;
    
    public Fine() {
        this(null, null, null, null);
    }
    
    public Fine(Object paid, String datePaid, Object fine, Balance balance) {
        super(paid, datePaid, balance);
        this.fine = new SimpleObjectProperty(fine);
    }
    
    public Double getFine() {
        return fine.get();
    }
    public void setFine(Double fine) {
        this.fine.set(fine);
    }
    
    @Override
    public PreparedStatement getInsertStatement(Database db) throws SQLException {
        PreparedStatement ps = db.conn.prepareStatement("INSERT INTO FINE "
                + "(id_fine, paid_fine, date_paid_fine, fine) "
                + "VALUES(?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, getId());
        ps.setDouble(2, getPaid());
        ps.setString(3, getDatePaid());
        ps.setDouble(4, getFine());

        return ps;
    }
    @Override
    public PreparedStatement getUpdateStatement(Database db) throws SQLException {
        PreparedStatement ps = db.conn.prepareStatement("UPDATE FINE "
                + "SET paid_fine=?, date_paid_fine=?, fine=? "
                + "WHERE id_fine=?", Statement.RETURN_GENERATED_KEYS);
        ps.setDouble(1, getPaid());
        ps.setString(2, getDatePaid());
        ps.setDouble(3, getFine());
        ps.setInt(4, getId());
        
        return ps;
    }
    @Override
    public PreparedStatement getDeleteStatement(Database db) throws SQLException {
        PreparedStatement ps = db.conn.prepareStatement("DELETE FROM FINE "
                + "WHERE id_fine=?");
        ps.setInt(1, getId());
        
        return ps;
    }
    
    @Override
    public Double sumToPay() {
        return safeGetSum(getFine());
    }
    
    @Override
    public String toString() {
        return String.format("Платеж на пени. id = %d", this.getId());
    }
}
