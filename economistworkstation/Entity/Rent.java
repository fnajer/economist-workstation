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

public class Rent extends Payment {
    private final ObjectProperty cost;
    private final ObjectProperty indexCost;
    
    public Rent() {
        this(null, null, null, null);
    }
    
    public Rent(Object paid, String datePaid, Object cost, Object indexCost) {
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
                + "(id_rent, paid_rent, date_paid_rent, cost, index_cost) "
                + "VALUES(?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, getId());
        ps.setObject(2, getPaid(), java.sql.Types.DOUBLE);
        ps.setString(3, getDatePaid());
        ps.setObject(4, getCost(), java.sql.Types.DOUBLE);
        ps.setObject(5, getIndexCost(), java.sql.Types.DOUBLE);
        
        return ps;
    }
    @Override
    public PreparedStatement getUpdateStatement(Database db) throws SQLException {
        PreparedStatement ps = db.conn.prepareStatement("UPDATE RENT "
                + "SET paid_rent=?, date_paid_rent=?, cost=?, index_cost=? "
                + "WHERE id_rent=?", Statement.RETURN_GENERATED_KEYS);
        ps.setObject(1, getPaid(), java.sql.Types.DOUBLE);
        ps.setString(2, getDatePaid());
        ps.setObject(3, getCost(), java.sql.Types.DOUBLE);
        ps.setObject(4, getIndexCost(), java.sql.Types.DOUBLE);
        ps.setInt(5, getId());
        
        return ps;
    }
    @Override
    public PreparedStatement getDeleteStatement(Database db) throws SQLException {
        PreparedStatement ps = db.conn.prepareStatement("DELETE FROM FINE "
                + "WHERE id_rent=?", Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, getId());
        
        return ps;
    }
    
    @Override
    public String toString() {
        return String.format("Платеж на аренду помещения. id = %d", getId());
    }
}
