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
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 *
 * @author fnajer
 */

public class BalanceTable {
    private final IntegerProperty id;
    private final ObjectProperty<Double> creditRent;
    private final ObjectProperty<Double> debitRent;
    private final ObjectProperty<Double> creditFine;
    private final ObjectProperty<Double> debitFine;
    private final ObjectProperty<Double> creditTaxLand;
    private final ObjectProperty<Double> debitTaxLand;
    private final ObjectProperty<Double> creditService;
    private final ObjectProperty<Double> debitService;
    private final ObjectProperty<Double> creditEquipment;
    private final ObjectProperty<Double> debitEquipment;
    
    public BalanceTable() {
        this(null, null, null, null, null, null, null, null, null, null);
    }
    
    public BalanceTable(Object creditRent, Object debitRent, Object creditFine, 
            Object debitFine, Object creditTaxLand, Object debitTaxLand,
            Object creditService, Object debitService, Object creditEquipment,
            Object debitEquipment) {
        this.id = new SimpleIntegerProperty(0);
        this.creditRent = new SimpleObjectProperty(creditRent);
        this.debitRent = new SimpleObjectProperty(debitRent);
        this.creditFine = new SimpleObjectProperty(creditFine);
        this.debitFine = new SimpleObjectProperty(debitFine);
        this.creditTaxLand = new SimpleObjectProperty(creditTaxLand);
        this.debitTaxLand = new SimpleObjectProperty(debitTaxLand);
        this.creditService = new SimpleObjectProperty(creditService);
        this.debitService = new SimpleObjectProperty(debitService);
        this.creditEquipment = new SimpleObjectProperty(creditEquipment);
        this.debitEquipment = new SimpleObjectProperty(debitEquipment);
    }
    
    public int getId() {
        return id.get();
    }
    public void setId(int id) {
        this.id.set(id);
    }
    
    public Double getCreditRent() {
        return creditRent.get();
    }
    public void setCreditRent(Double creditRent) {
        this.creditRent.set(creditRent);
    }
    
    public Double getDebitRent() {
        return debitRent.get();
    }
    public void setDebitRent(Double debitRent) {
        this.debitRent.set(debitRent);
    }
    
    public Double getCreditFine() {
        return creditFine.get();
    }
    public void setCreditFine(Double creditFine) {
        this.creditFine.set(creditFine);
    }
    
    public Double getDebitFine() {
        return debitFine.get();
    }
    public void setDebitFine(Double debitFine) {
        this.debitFine.set(debitFine);
    }
    
    public Double getCreditTaxLand() {
        return creditTaxLand.get();
    }
    public void setCreditTaxLand(Double creditTaxLand) {
        this.creditTaxLand.set(creditTaxLand);
    }
    
    public Double getDebitTaxLand() {
        return debitTaxLand.get();
    }
    public void setDebitTaxLand(Double debitTaxLand) {
        this.debitTaxLand.set(debitTaxLand);
    }
    
    public Double getCreditService() {
        return creditService.get();
    }
    public void setCreditService(Double creditService) {
        this.creditService.set(creditService);
    }
    
    public Double getDebitService() {
        return debitService.get();
    }
    public void setDebitService(Double debitService) {
        this.debitService.set(debitService);
    }
    
    public Double getCreditEquipment() {
        return creditEquipment.get();
    }
    public void setCreditEquipment(Double creditEquipment) {
        this.creditEquipment.set(creditEquipment);
    }
    
    public Double getDebitEquipment() {
        return debitEquipment.get();
    }
    public void setDebitEquipment(Double debitEquipment) {
        this.debitEquipment.set(debitEquipment);
    }
    
    public PreparedStatement getInsertStatement(Database db) throws SQLException {
        PreparedStatement ps = db.conn.prepareStatement("INSERT INTO BALANCE "
                + "(id_balance, credit_rent, debit_rent, credit_fine, debit_fine, "
                + "credit_tax_land, debit_tax_land, credit_services, debit_services,"
                + "credit_equipment, debit_equipment) "
                + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, getId());
        ps.setObject(2, getCreditRent());
        ps.setObject(3, getDebitRent());
        ps.setObject(4, getCreditFine());
        ps.setObject(5, getDebitFine());
        ps.setObject(6, getCreditTaxLand());
        ps.setObject(7, getDebitTaxLand());
        ps.setObject(8, getCreditService());
        ps.setObject(9, getDebitService());
        ps.setObject(10, getCreditEquipment());
        ps.setObject(11, getDebitEquipment());
        
        return ps;
    }
    public PreparedStatement getUpdateStatement(Database db) throws SQLException {
        PreparedStatement ps = db.conn.prepareStatement("UPDATE BALANCE "
                + "SET credit_rent=?, debit_rent=?, credit_fine=?, debit_fine=?, "
                + "credit_tax_land=?, debit_tax_land=?, credit_services=?, "
                + "debit_services=?, credit_equipment=?, debit_equipment=? "
                + "WHERE id_balance=?", Statement.RETURN_GENERATED_KEYS);
        System.out.println(getCreditRent());
        ps.setObject(1, getCreditRent());
        ps.setObject(2, getDebitRent());
        ps.setObject(3, getCreditFine());
        ps.setObject(4, getDebitFine());
        ps.setObject(5, getCreditTaxLand());
        ps.setObject(6, getDebitTaxLand());
        ps.setObject(7, getCreditService());
        ps.setObject(8, getDebitService());
        ps.setObject(9, getCreditEquipment());
        ps.setObject(10, getDebitEquipment());
        ps.setInt(11, getId());
        
        return ps;
    }
    public PreparedStatement getDeleteStatement(Database db) throws SQLException {
        PreparedStatement ps = db.conn.prepareStatement("DELETE FROM BALANCE "
                + "WHERE id_balance=?");
        ps.setInt(1, getId());
        
        return ps;
    }
    
    public void prepareToDelete() {
        setCreditRent(-1.0);
    }
    
//    public void bindPeriod(Period period) {
//        period.setBalance(this);
//    }
    
    public boolean isEmpty() {
        return getCreditRent() == null 
                && getDebitRent() == null 
                && getCreditFine() == null 
                && getDebitFine() == null
                && getCreditTaxLand() == null
                && getDebitTaxLand() == null 
                && getCreditService() == null
                && getDebitService() == null
                && getCreditEquipment() == null 
                && getDebitEquipment() == null;
    }
    
    public BalanceTable copy() {
        BalanceTable balanceTable = new BalanceTable(
                getCreditRent(), getDebitRent(),
                getCreditFine(), getDebitFine(),
                getCreditTaxLand(), getDebitTaxLand(),
                getCreditService(), getDebitService(),
                getCreditEquipment(), getDebitEquipment());
        return balanceTable;
    }
    
    @Override
    public String toString() {
        return String.format("Таблица баланса. id = %d", getId());
    }
}
