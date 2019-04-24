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
        this(null, null, null, null);
    }
    
    public TaxLand(Object paid, String datePaid, Object taxLand, Balance balance) {
        super(paid, datePaid, balance);
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
                + "(id_tax_land, paid_tax_land, date_paid_tax_land, tax_land) "
                + "VALUES(?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, getId());
        ps.setObject(2, getPaid(), java.sql.Types.DOUBLE);
        ps.setString(3, getDatePaid());
        ps.setObject(4, getTaxLand(), java.sql.Types.DOUBLE);

        return ps;
    }
    @Override
    public PreparedStatement getUpdateStatement(Database db) throws SQLException {
        PreparedStatement ps = db.conn.prepareStatement("UPDATE TAXLAND "
                + "SET paid_tax_land=?, date_paid_tax_land=?, tax_land=? "
                + "WHERE id_tax_land=?", Statement.RETURN_GENERATED_KEYS);
        ps.setObject(1, getPaid(), java.sql.Types.DOUBLE);
        ps.setString(2, getDatePaid());
        ps.setObject(3, getTaxLand(), java.sql.Types.DOUBLE);
        ps.setInt(4, getId());
        
        return ps;
    }
    @Override
    public PreparedStatement getDeleteStatement(Database db) throws SQLException {
        PreparedStatement ps = db.conn.prepareStatement("DELETE FROM TAXLAND "
                + "WHERE id_tax_land=?", Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, getId());
        
        return ps;
    }
    
    @Override
    public String toString() {
        return String.format("Платеж на земельный налог. id = %d", this.getId());
    }
}
