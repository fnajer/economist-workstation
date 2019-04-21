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

public class Balance {
    private final IntegerProperty id;
    private final ObjectProperty creditRent;
    private final ObjectProperty debitRent;
    private final ObjectProperty creditFine;
    private final ObjectProperty debitFine;
    private final ObjectProperty creditTaxLand;
    private final ObjectProperty debitTaxLand;
    private final ObjectProperty creditService;
    private final ObjectProperty debitService;
    private final ObjectProperty creditEquipment;
    private final ObjectProperty debitEquipment;
    
    public Balance() {
        this(null, null, null, null, null, null, null, null, null, null);
    }
    
    public Balance(Object creditRent, Object debitRent, Object creditFine, 
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
        return (Double) creditRent.get();
    }
    public void setCreditRent(Double creditRent) {
        this.creditRent.set(creditRent);
    }
    
    public Double getDebitRent() {
        return (Double) debitRent.get();
    }
    public void setDebitRent(Double debitRent) {
        this.debitRent.set(debitRent);
    }
    
    public Double getCreditFine() {
        return (Double) creditFine.get();
    }
    public void setCreditFine(Double creditFine) {
        this.creditFine.set(creditFine);
    }
    
    public Double getDebitFine() {
        return (Double) debitFine.get();
    }
    public void setDebitFine(Double debitFine) {
        this.debitFine.set(debitFine);
    }
    
    public Double getCreditTaxLand() {
        return (Double) creditTaxLand.get();
    }
    public void setCreditTaxLand(Double creditTaxLand) {
        this.creditTaxLand.set(creditTaxLand);
    }
    
    public Double getDebitTaxLand() {
        return (Double) debitTaxLand.get();
    }
    public void setDebitTaxLand(Double debitTaxLand) {
        this.debitTaxLand.set(debitTaxLand);
    }
    
    public Double getCreditService() {
        return (Double) creditService.get();
    }
    public void setCreditService(Double creditService) {
        this.creditService.set(creditService);
    }
    
    public Double getDebitService() {
        return (Double) debitService.get();
    }
    public void setDebitService(Double debitService) {
        this.debitService.set(debitService);
    }
    
    public Double getCreditEquipment() {
        return (Double) creditEquipment.get();
    }
    public void setCreditEquipment(Double creditEquipment) {
        this.creditEquipment.set(creditEquipment);
    }
    
    public Double getDebitEquipment() {
        return (Double) debitEquipment.get();
    }
    public void setDebitEquipment(Double debitEquipment) {
        this.debitEquipment.set(debitEquipment);
    }
    
    public PreparedStatement getInsertStatement(Database db) throws SQLException {
        PreparedStatement ps = db.conn.prepareStatement("INSERT INTO BALANCE "
                + "(id, credit_rent, debit_rent, credit_fine, debit_fine, "
                + "credit_tax_land, debit_tax_land, credit_services, debit_services,"
                + "credit_equipment, debit_equipment) "
                + "VALUES(NULL,?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
        ps.setObject(1, getCreditRent(), java.sql.Types.DOUBLE);
        ps.setObject(2, getDebitRent(), java.sql.Types.DOUBLE);
        ps.setObject(3, getCreditFine(), java.sql.Types.DOUBLE);
        ps.setObject(4, getDebitFine(), java.sql.Types.DOUBLE);
        ps.setObject(5, getCreditTaxLand(), java.sql.Types.DOUBLE);
        ps.setObject(6, getDebitTaxLand(), java.sql.Types.DOUBLE);
        ps.setObject(7, getCreditService(), java.sql.Types.DOUBLE);
        ps.setObject(8, getDebitService(), java.sql.Types.DOUBLE);
        ps.setObject(9, getCreditEquipment(), java.sql.Types.DOUBLE);
        ps.setObject(10, getDebitEquipment(), java.sql.Types.DOUBLE);
        
        return ps;
    }
    public PreparedStatement getUpdateStatement(Database db) throws SQLException {
        PreparedStatement ps = db.conn.prepareStatement("UPDATE BALANCE "
                + "SET credit_rent=?, debit_rent=?, credit_fine=?, debit_fine=?, "
                + "credit_tax_land=?, debit_tax_land=?, credit_services=?, "
                + "debit_services=?, credit_equipment=?, debit_equipment=? "
                + "WHERE id=?", Statement.RETURN_GENERATED_KEYS);
        ps.setObject(1, getCreditRent(), java.sql.Types.DOUBLE);
        ps.setObject(2, getDebitRent(), java.sql.Types.DOUBLE);
        ps.setObject(3, getCreditFine(), java.sql.Types.DOUBLE);
        ps.setObject(4, getDebitFine(), java.sql.Types.DOUBLE);
        ps.setObject(5, getCreditTaxLand(), java.sql.Types.DOUBLE);
        ps.setObject(6, getDebitTaxLand(), java.sql.Types.DOUBLE);
        ps.setObject(7, getCreditService(), java.sql.Types.DOUBLE);
        ps.setObject(8, getDebitService(), java.sql.Types.DOUBLE);
        ps.setObject(9, getCreditEquipment(), java.sql.Types.DOUBLE);
        ps.setObject(10, getDebitEquipment(), java.sql.Types.DOUBLE);
        ps.setInt(11, getId());
        
        return ps;
    }
    public PreparedStatement getDeleteStatement(Database db) throws SQLException {
        PreparedStatement ps = db.conn.prepareStatement("DELETE FROM BALANCE "
                + "WHERE id=?", Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, getId());
        
        return ps;
    }
    
    @Override
    public String toString() {
        return String.format("Сальдо. id = %d", getId());
    }
}
