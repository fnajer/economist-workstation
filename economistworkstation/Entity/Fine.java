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
    private final ObjectProperty fine;
    
    public Fine() {
        this(null, null, null);
    }
    
    public Fine(Object paid, String datePaid, Object fine) {
        super(paid, datePaid);
        this.fine = new SimpleObjectProperty(fine);
    }
    
    public Double getFine() {
        return (Double) fine.get();
    }
    public void setFine(Double fine) {
        this.fine.set(fine);
    }
    
    @Override
    public PreparedStatement getInsertStatement(Database db) throws SQLException {
        PreparedStatement ps = db.conn.prepareStatement("INSERT INTO FINE "
                + "(id, paid_fine, date_paid_fine, fine) "
                + "VALUES(NULL,?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
        ps.setObject(1, getPaid(), java.sql.Types.DOUBLE);
        ps.setString(2, getDatePaid());
        ps.setObject(3, getFine(), java.sql.Types.DOUBLE);

        return ps;
    }
    @Override
    public PreparedStatement getUpdateStatement(Database db) throws SQLException {
        PreparedStatement ps = db.conn.prepareStatement("UPDATE FINE "
                + "SET paid_fine=?, date_paid_fine=?, fine=? "
                + "WHERE id=?", Statement.RETURN_GENERATED_KEYS);
        ps.setObject(1, getPaid(), java.sql.Types.DOUBLE);
        ps.setString(2, getDatePaid());
        ps.setObject(3, getFine(), java.sql.Types.DOUBLE);
        ps.setInt(4, getId());
        
        return ps;
    }
    @Override
    public PreparedStatement getDeleteStatement(Database db) throws SQLException {
        PreparedStatement ps = db.conn.prepareStatement("DELETE FROM FINE "
                + "WHERE id=?", Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, getId());
        
        return ps;
    }
    
    @Override
    public String toString() {
        return String.format("Платеж на пени. id = %d", this.getId());
    }
}
