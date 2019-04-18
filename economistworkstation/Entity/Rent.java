/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package economistworkstation.Entity;

import economistworkstation.Database;
import economistworkstation.EconomistWorkstation;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 *
 * @author fnajer
 */

public class Rent extends Payment {
    private final ObjectProperty cost;
    private final ObjectProperty indexCost;
    
    public Rent() {
        this(0.0, null, null, null);
    }
    
    public Rent(double paid, String datePaid, Object cost, Object indexCost) {
        super(paid, datePaid);
        this.cost = new SimpleObjectProperty(cost);
        this.indexCost = new SimpleObjectProperty(indexCost);
    }
    
    public Double getCost() {
        return (Double) cost.get();
    }
    public void setCost(Double cost) {
        this.cost.set(cost);
    }
    
    public Double getIndexCost() {
        return (Double) indexCost.get();
    }
    public void setIndexCost(Double indexCost) {
        this.indexCost.set(indexCost);
    }
    
    public Double calcSumRent() {
        try {
            return getCost() * getIndexCost();
        } catch(NullPointerException e) {
            return 0.0;
        }
    }
    
    @Override
    public PreparedStatement getInsertStatement(Database db) throws SQLException {
        PreparedStatement ps = db.conn.prepareStatement("INSERT INTO RENT "
                + "(id, paid_rent, date_paid_rent, cost, index_cost) "
                + "VALUES(NULL,?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
        ps.setDouble(1, getPaid());
        ps.setString(2, getDatePaid());
        ps.setObject(3, getCost(), java.sql.Types.DOUBLE);
        ps.setObject(4, getIndexCost(), java.sql.Types.DOUBLE);
        
        return ps;
    }
    @Override
    public PreparedStatement getUpdateStatement(Database db) throws SQLException {
        PreparedStatement ps = db.conn.prepareStatement("UPDATE RENT "
                + "SET paid_rent=?, date_paid_rent=?, cost=?, index_cost=? "
                + "WHERE id=?", Statement.RETURN_GENERATED_KEYS);
        ps.setDouble(1, getPaid());
        ps.setString(2, getDatePaid());
        ps.setObject(3, getCost(), java.sql.Types.DOUBLE);
        ps.setObject(4, getIndexCost(), java.sql.Types.DOUBLE);
        ps.setInt(5, getId());
        
        return ps;
    }
    
    
    @Override
    public String toString() {
        return String.format("Платеж на аренду помещения. id = %d", getId());
    }
}
