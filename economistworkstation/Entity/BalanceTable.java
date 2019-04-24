/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package economistworkstation.Entity;

import economistworkstation.Database;
import static economistworkstation.Util.Util.isExist;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 *
 * @author fnajer
 */

public class BalanceTable {
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
                + "(id_balance, credit_rent, debit_rent, credit_fine, debit_fine, "
                + "credit_tax_land, debit_tax_land, credit_services, debit_services,"
                + "credit_equipment, debit_equipment) "
                + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, getId());
        ps.setObject(2, getCreditRent(), java.sql.Types.DOUBLE);
        ps.setObject(3, getDebitRent(), java.sql.Types.DOUBLE);
        ps.setObject(4, getCreditFine(), java.sql.Types.DOUBLE);
        ps.setObject(5, getDebitFine(), java.sql.Types.DOUBLE);
        ps.setObject(6, getCreditTaxLand(), java.sql.Types.DOUBLE);
        ps.setObject(7, getDebitTaxLand(), java.sql.Types.DOUBLE);
        ps.setObject(8, getCreditService(), java.sql.Types.DOUBLE);
        ps.setObject(9, getDebitService(), java.sql.Types.DOUBLE);
        ps.setObject(10, getCreditEquipment(), java.sql.Types.DOUBLE);
        ps.setObject(11, getDebitEquipment(), java.sql.Types.DOUBLE);
        
        return ps;
    }
    public PreparedStatement getUpdateStatement(Database db) throws SQLException {
        PreparedStatement ps = db.conn.prepareStatement("UPDATE BALANCE "
                + "SET credit_rent=?, debit_rent=?, credit_fine=?, debit_fine=?, "
                + "credit_tax_land=?, debit_tax_land=?, credit_services=?, "
                + "debit_services=?, credit_equipment=?, debit_equipment=? "
                + "WHERE id_balance=?", Statement.RETURN_GENERATED_KEYS);
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
                + "WHERE id_balance=?", Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, getId());
        
        return ps;
    }
    
    public void recalculate(Period prevPeriod, Period period) {
        Rent prevRent = (Rent) prevPeriod.getRentPayment();
        Balance prevBalance = prevRent.getBalance();
        Rent rent = (Rent) period.getRentPayment();
        Balance balance = rent.getBalance();
        Fine fine = (Fine) period.getFinePayment();
        if (fine == null) {
            fine = new Fine();
            fine.setFine(0.0);
        }
        try {
            Double paid = rent.getPaid();
            Double needPay = rent.calcSumRent() + fine.getFine();
            Double diff = needPay - paid;
            balance.calc(prevBalance, diff, period);
        } catch(NullPointerException e) {
            System.err.println(String.format(
                    "%s: null recalculation",
                    this.getClass().getSimpleName()));
        }
    }
    
    public Double sum(Double value, Double prevValue) {
        Double sum = null;        
        try {
            sum = value + prevValue;
        } catch(NullPointerException e) {
            if (value == null) return prevValue;
            if (prevValue == null) return value;
        }
        
        return sum;
    }
    
    public void buildTable(Period period) {
        Payment rent = period.getRentPayment();
        Payment fine = period.getFinePayment();
        Payment taxLand = period.getTaxLandPayment();
        Payment services = period.getServicesPayment();
        Payment equipment = period.getEquipmentPayment();
        
        if (isExist(rent)) {
            Balance rentBalance = rent.getBalance();
            setCreditRent(rentBalance.getCredit());
            setDebitRent(rentBalance.getDebit());
        }
        if (isExist(fine)) {
            Balance fineBalance = fine.getBalance();
            setCreditFine(fineBalance.getCredit());
            setDebitFine(fineBalance.getDebit());
        }
        if (isExist(taxLand)) {
            Balance taxLandBalance = taxLand.getBalance();
            setCreditTaxLand(taxLandBalance.getCredit());
            setDebitTaxLand(taxLandBalance.getDebit());
        }
        if (isExist(services)) {
            Balance servicesBalance = services.getBalance();
            setCreditService(servicesBalance.getCredit());
            setDebitService(servicesBalance.getDebit());
        }
        if (isExist(equipment)) {
            Balance equipmentBalance = equipment.getBalance();
            setCreditEquipment(equipmentBalance.getCredit());
            setDebitEquipment(equipmentBalance.getDebit());
        }
    }
    
    @Override
    public String toString() {
        return String.format("Сальдо. id = %d", getId());
    }
}