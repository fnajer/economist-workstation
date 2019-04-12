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

public class Fine extends Payment {
    private final DoubleProperty fine;
    
    public Fine() {
        this(0.0, null, 0.0);
    }
    
    public Fine(double paid, String datePaid, double fine) {
        super(paid, datePaid);
        this.fine = new SimpleDoubleProperty(fine);
    }
    
    public double getFine() {
        return fine.get();
    }
    public void setFine(double fine) {
        this.fine.set(fine);
    }
    
    @Override
    public PreparedStatement getInsertStatement(Database db) throws SQLException {
        PreparedStatement ps = db.conn.prepareStatement("INSERT INTO FINE "
                + "(id, paid_fine, date_paid_fine, fine) "
                + "VALUES(NULL,?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
        ps.setDouble(1, getPaid());
        ps.setString(2, getDatePaid());
        ps.setDouble(3, getFine());

        return ps;
    }
    @Override
    public PreparedStatement getUpdateStatement(Database db) throws SQLException {
        PreparedStatement ps = db.conn.prepareStatement("UPDATE FINE "
                + "SET paid_fine=?, date_paid_fine=?, fine=? "
                + "WHERE id=?", Statement.RETURN_GENERATED_KEYS);
        ps.setDouble(1, getPaid());
        ps.setString(2, getDatePaid());
        ps.setDouble(3, getFine());
        ps.setInt(4, getId());
        
        return ps;
    }
    
    @Override
    public String toString() {
        return String.format("Платеж на пени. id = %d", this.getId());
    }
}
