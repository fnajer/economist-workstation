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

public class TaxLand extends Payment {
    private final DoubleProperty taxLand;
    
    public TaxLand() {
        this(0.0, null, 0.0);
    }
    
    public TaxLand(double paid, String datePaid, double taxLand) {
        super(paid, datePaid);
        this.taxLand = new SimpleDoubleProperty(taxLand);
    }
    
    public double getTaxLand() {
        return taxLand.get();
    }
    public void setTaxLand(double taxLand) {
        this.taxLand.set(taxLand);
    }
    
    @Override
    public PreparedStatement getInsertStatement(Database db) throws SQLException {
        PreparedStatement ps = db.conn.prepareStatement("INSERT INTO TAXLAND "
                + "(id, paid_tax_land, date_paid_tax_land, tax_land) "
                + "VALUES(NULL,?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
        ps.setDouble(1, getPaid());
        ps.setString(2, getDatePaid());
        ps.setDouble(3, getTaxLand());

        return ps;
    }
    @Override
    public PreparedStatement getUpdateStatement(Database db) throws SQLException {
        PreparedStatement ps = db.conn.prepareStatement("UPDATE TAXLAND "
                + "SET paid_tax_land=?, date_paid_tax_land=?, tax_land=? "
                + "WHERE id=?;", Statement.RETURN_GENERATED_KEYS);
        ps.setDouble(1, getPaid());
        ps.setString(2, getDatePaid());
        ps.setDouble(3, getTaxLand());
        ps.setInt(4, getId());
        
        return ps;
    }
    
    @Override
    public String toString() {
        return String.format("Платеж на земельный налог. id = %d", this.getId());
    }
}
