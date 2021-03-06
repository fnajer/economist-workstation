/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package economistworkstation.Entity;

import economistworkstation.Database;
import static economistworkstation.Util.Util.parseField;
import static economistworkstation.Util.Util.setText;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Label;

/**
 *
 * @author fnajer
 */

public class Rent extends Payment {
    private final ObjectProperty<Double> cost;
    private final ObjectProperty<Double> indexCost;
    
    public Rent() {
        this(null, null, null, null);
    }
    
    public Rent(Object paid, String datePaid, Object cost, Object indexCost) {
        super(paid, datePaid);
        this.cost = new SimpleObjectProperty(cost);
        this.indexCost = new SimpleObjectProperty(indexCost);
    }
    
    public Double getCost() {
        return cost.get();
    }
    public void setCost(Double cost) {
        this.cost.set(cost);
    }
    
    public Double getIndexCost() {
        return indexCost.get();
    }
    public void setIndexCost(Double indexCost) {
        this.indexCost.set(indexCost);
    }
    
    @Override
    public PreparedStatement getInsertStatement(Database db) throws SQLException {
        PreparedStatement ps = db.conn.prepareStatement("INSERT INTO RENT "
                + "(id_rent, paid_rent, date_paid_rent, cost, index_inflation) "
                + "VALUES(?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, getId());
        ps.setObject(2, getPaid());
        ps.setString(3, getDatePaid());
        ps.setObject(4, getCost());
        ps.setObject(5, getIndexCost());
        
        return ps;
    }
    @Override
    public PreparedStatement getUpdateStatement(Database db) throws SQLException {
        PreparedStatement ps = db.conn.prepareStatement("UPDATE RENT "
                + "SET paid_rent=?, date_paid_rent=?, cost=?, index_inflation=? "
                + "WHERE id_rent=?", Statement.RETURN_GENERATED_KEYS);
        ps.setObject(1, getPaid());
        ps.setString(2, getDatePaid());
        ps.setObject(3, getCost());
        ps.setObject(4, getIndexCost());
        ps.setInt(5, getId());
        
        return ps;
    }
    @Override
    public PreparedStatement getDeleteStatement(Database db) throws SQLException {
        PreparedStatement ps = db.conn.prepareStatement("DELETE FROM RENT "
                + "WHERE id_rent=?");
        ps.setInt(1, getId());
        
        return ps;
    }
    
    @Override
    public Double sumToPay() {
        return safeGetSum(getCost(), getIndexCost());
    }
    
    @Override
    public boolean isEmpty() {
        return getCost() == null 
                && getIndexCost() == null 
                && getPaid() == null
                && getDatePaid() == null;
    }
    
    @Override
    public Rent copy() {
        Rent rent = new Rent(getPaid(), getDatePaid(), getCost(), getIndexCost());
        rent.setId(getId());
        return rent;
    }

    @Override
    public void saveValuesOf(Field field) {
        setCost(parseField(field.getCostRent()));
        setIndexCost(parseField(field.getIndexCostRent()));

        setPaid(parseField(field.getPaymentRent()));
        setDatePaid(parseField(field.getDatePaidRent()));
    }
    
    @Override
    public void bindPeriod(Period period) {
        period.setRentPayment(this);
    }
    
    @Override
    public String checkFields(Field field) {
        return field.checkRentFields();
    }
    @Override
    public boolean fieldsIsFilled(Field fields) {
        return fields.rentIsFilled();
    }
    
    @Override
    public void fill(Field field) {
        field.fillRent(this);
    }
    
    @Override
    public void setLabels(Map<String, Label> labels) {
        setText(labels.get("costRent"), getCost());
        setText(labels.get("indexCostRent"), getIndexCost());
    }
    
    @Override
    public Payment createNewPayment() {
        return new Rent();
    }
    
    @Override
    public Double getCredit(BalanceTable balanceTable) {
        return balanceTable.getCreditRent();
    }
    @Override
    public void setCredit(BalanceTable balanceTable, Double credit) {
        balanceTable.setCreditRent(credit);
    }
    @Override
    public Double getDebit(BalanceTable balanceTable) {
        return balanceTable.getDebitRent();
    }
    @Override
    public void setDebit(BalanceTable balanceTable, Double debit) {
        balanceTable.setDebitRent(debit);
    }
                
    @Override
    public String toString() {
        return String.format("Платеж на аренду помещения. id = %d", getId());
    }
}
