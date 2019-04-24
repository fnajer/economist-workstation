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

/**
 *
 * @author fnajer
 */

public class Balance {
    private final IntegerProperty id;
    private final ObjectProperty credit;
    private final ObjectProperty debit;
    
    public Balance() {
        this(null, null);
    }
    
    public Balance(Object credit, Object debit) {
        this.id = new SimpleIntegerProperty(0);
        this.credit = new SimpleObjectProperty(credit);
        this.debit = new SimpleObjectProperty(debit);
    }
    
    public int getId() {
        return id.get();
    }
    public void setId(int id) {
        this.id.set(id);
    }
    
    public Double getCredit() {
        return (Double) credit.get();
    }
    public void setCredit(Double credit) {
        this.credit.set(credit);
    }
    
    public Double getDebit() {
        return (Double) debit.get();
    }
    public void setDebit(Double debit) {
        this.debit.set(debit);
    }
    
    public String calcWithCredit(double credit, double diff) {
        String text = "";
        if (credit == diff) {
            text = String.format("взято с кредита: %.2f, без остатка", diff);
            setCredit(null);
            setDebit(null);
        } else if (credit > diff) {
            credit -= diff;
            text = String.format("взято с кредита: %.2f, остаток кредита: %.2f", diff, credit);
            setCredit(credit);
            setDebit(null);
        } else if (credit < diff) {
            diff -= credit;
            text = String.format("взято с кредита %.2f, уйдет в дебет: %.2f", credit, diff);
            setDebit(diff);
            setCredit(null);
        }
        System.out.println(text);
        return text;
    }
    public String calcWithDebit(double debit, double diff) {
        String text = "";
        if (debit == diff) {
            text = String.format("оплачен дебет: %.2f, без остатка", debit);
            setCredit(null);
            setDebit(null);
        } else if (debit > diff) {
            debit -= diff;
            text = String.format("оплачен дебет: %.2f, остаток дебета %.2f", diff, debit);
            setCredit(null);
            setDebit(debit);
        } else if (debit < diff) {
            diff -= debit;
            text = String.format("оплачено дебета %.2f, уйдет в кредит: %.2f", debit, diff);
            setDebit(null);
            setCredit(diff);
        }
        System.out.println(text);
        return text;
    }
    
    public void calc(Balance prevBalance, Double diff, Period period) {
        Double credit = prevBalance.getCredit();
        Double debit = prevBalance.getDebit();
    
        if (diff != null) {
            
            if (diff == 0.0) {
                if (isExist(credit)) {
                    setDebit(null);
                    setCredit(credit);
                } else if (isExist(debit)) {
                    setDebit(debit);
                    setCredit(null);
                } else {
                    setDebit(null);
                    setCredit(null);
                }
            } else if (diff > 0) {
                if (isExist(credit)) {
                    String st = calcWithCredit(credit, diff);
                } else if (isExist(debit)) {
                    setDebit(diff + debit);
                    setCredit(null);
                } else {
                    setDebit(diff);
                    setCredit(null);
                }
            } else {
                diff = Math.abs(diff);
                if (isExist(debit)) {
                    String st = calcWithDebit(debit, diff);
                } else if (isExist(credit)) {
                    setDebit(null);
                    setCredit(diff + credit);
                } else {
                    setCredit(diff);
                    setDebit(null);
                }
            }
        } else {
            //
        }
    }
    
    @Override
    public String toString() {
        return String.format("Сальдо. id = %d", getId());
    }
}
