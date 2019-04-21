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

public class TaxLand extends Payment {
    private final ObjectProperty taxLand;
    
    public TaxLand() {
        this(null, null, null);
    }
    
    public TaxLand(Object paid, String datePaid, Object taxLand) {
        super(paid, datePaid);
        this.taxLand = new SimpleObjectProperty(taxLand);
    }
    
    public Double getTaxLand() {
        return (Double) taxLand.get();
    }
    public void setTaxLand(Double taxLand) {
        this.taxLand.set(taxLand);
    }
    
    @Override
    public PreparedStatement getInsertStatement(Database db) throws SQLException {
        PreparedStatement ps = db.conn.prepareStatement("INSERT INTO TAXLAND "
                + "(id, paid_tax_land, date_paid_tax_land, tax_land) "
                + "VALUES(NULL,?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
        ps.setObject(1, getPaid(), java.sql.Types.DOUBLE);
        ps.setString(2, getDatePaid());
        ps.setObject(3, getTaxLand(), java.sql.Types.DOUBLE);

        return ps;
    }
    @Override
    public PreparedStatement getUpdateStatement(Database db) throws SQLException {
        PreparedStatement ps = db.conn.prepareStatement("UPDATE TAXLAND "
                + "SET paid_tax_land=?, date_paid_tax_land=?, tax_land=? "
                + "WHERE id=?;", Statement.RETURN_GENERATED_KEYS);
        ps.setObject(1, getPaid(), java.sql.Types.DOUBLE);
        ps.setString(2, getDatePaid());
        ps.setObject(3, getTaxLand(), java.sql.Types.DOUBLE);
        ps.setInt(4, getId());
        
        return ps;
    }
    @Override
    public PreparedStatement getDeleteStatement(Database db) throws SQLException {
        PreparedStatement ps = db.conn.prepareStatement("DELETE FROM TAXLAND "
                + "WHERE id=?", Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, getId());
        
        return ps;
    }
    
    @Override
    public String toString() {
        return String.format("Платеж на земельный налог. id = %d", this.getId());
    }
}
