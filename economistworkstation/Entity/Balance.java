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
    
    public String calcWithCredit(double credit, double diff) {
        String text = "";
        if (credit == diff) {
            text = String.format("взято с кредита: %.2f, без остатка", diff);
            setCreditRent(null);
            setDebitRent(null);
        } else if (credit > diff) {
            credit -= diff;
            text = String.format("взято с кредита: %.2f, остаток кредита: %.2f", diff, credit);
            setCreditRent(credit);
            setDebitRent(null);
        } else if (credit < diff) {
            diff -= credit;
            text = String.format("взято с кредита %.2f, уйдет в дебет: %.2f", credit, diff);
            setDebitRent(diff);
            setCreditRent(null);
        }
        System.out.println(text);
        return text;
    }
    public String calcWithDebit(double debit, double diff) {
        String text = "";
        if (debit == diff) {
            text = String.format("оплачен дебет: %.2f, без остатка", debit);
            setCreditRent(null);
            setDebitRent(null);
        } else if (debit > diff) {
            debit -= diff;
            text = String.format("оплачен дебет: %.2f, остаток дебета %.2f", diff, debit);
            setCreditRent(null);
            setDebitRent(debit);
        } else if (debit < diff) {
            diff -= debit;
            text = String.format("оплачено дебета %.2f, уйдет в кредит: %.2f", debit, diff);
            setDebitRent(null);
            setCreditRent(diff);
        }
        System.out.println(text);
        return text;
    }
    
    private void calc(Balance prevBalance, Double diff, Period period) {
        Double credit = prevBalance.getCreditRent();
        Double debit = prevBalance.getDebitRent();
    
        if (diff != null) {
            
            if (diff == 0.0) {
//                if (isExist(credit)) {
//                    setDebitRent(null);
//                    setCreditRent(credit);
//                    period.setBalance(this);
//                } else if (isExist(debit)) {
//                    setDebitRent(debit);
//                    setCreditRent(null);
//                    period.setBalance(this);
//                } else {
//                    setDebitRent(null);
//                    setCreditRent(null);
//                    period.setBalance(this);
//                }
            } else if (diff > 0) {
                if (isExist(credit)) {
                    String st = calcWithCredit(credit, diff);
                    period.setBalance(this);
                } else if (isExist(debit)) {
                    setDebitRent(diff + debit);
                    setCreditRent(null);
                    period.setBalance(this);
                } else {
                    setDebitRent(diff);
                    setCreditRent(null);
                    period.setBalance(this);
                }
            } else {
                diff = Math.abs(diff);
                if (isExist(debit)) {
                    String st = calcWithDebit(debit, diff);
                    period.setBalance(this);
                } else if (isExist(credit)) {
                    setDebitRent(null);
                    setCreditRent(diff + credit);//
                    period.setBalance(this);
                } else {
                    setCreditRent(diff);
                    setDebitRent(null);
                    period.setBalance(this);
                }
            }
        } else {
            //
        }
    }
    
    public void recalculate(Balance prevBalance, Period period) {
        Rent rent = (Rent) period.getRentPayment();
        Fine fine = (Fine) period.getFinePayment();
        if (fine == null) {
            fine = new Fine();
            fine.setFine(0.0);
        }
        try {
            Double paid = rent.getPaid();
            Double needPay = rent.calcSumRent() + fine.getFine();
            Double diff = needPay - paid;
            calc(prevBalance, diff, period);
        } catch(NullPointerException e) {
            System.err.println(String.format(
                    "%s: null recalculation",
                    this.getClass().getSimpleName()));
        }
        
                
//        setCreditRent(
//                sum(getCreditRent(), prevBalance.getCreditRent()));
//        setDebitRent(
//                sum(getDebitRent(), prevBalance.getDebitRent()));
//        setCreditFine(
//                sum(getCreditFine(), prevBalance.getCreditFine()));
//        setDebitFine(
//                sum(getDebitFine(), prevBalance.getDebitFine()));
//        setCreditTaxLand(
//                sum(getCreditTaxLand(), prevBalance.getCreditTaxLand()));
//        setDebitTaxLand(
//                sum(getDebitTaxLand(), prevBalance.getDebitTaxLand()));
//        setCreditService(
//                sum(getCreditService(), prevBalance.getCreditService()));
//        setDebitService(
//                sum(getDebitService(), prevBalance.getDebitService()));
//        setCreditEquipment(
//                sum(getCreditEquipment(), prevBalance.getCreditEquipment()));
//        setDebitEquipment(
//                sum(getDebitEquipment(), prevBalance.getDebitEquipment()));
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
    
    @Override
    public String toString() {
        return String.format("Сальдо. id = %d", getId());
    }
}
