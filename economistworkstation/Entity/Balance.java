/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package economistworkstation.Entity;

import static economistworkstation.Util.Util.isExist;
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
    private final ObjectProperty<Double> credit;
    private final ObjectProperty<Double> debit;
    
    public Balance() {
        this(null, null);
    }
    
    public Balance(Double credit, Double debit) {
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
        return credit.get();
    }
    public void setCredit(Double credit) {
        this.credit.set(credit);
    }
    
    public Double getDebit() {
        return debit.get();
    }
    public void setDebit(Double debit) {
        this.debit.set(debit);
    }

    private void log(String text) {
        System.out.println(text);
    }
    private void log(String text, double value) {
        System.out.println(String.format(text, value));
    }
    private void log(String text, double value1, double value2) {
        System.out.println(String.format(text, value1, value2));
    }
    
    public void calcWithCredit(double credit, double diff) {
        if (credit == diff) {
            setCredit(null);
            setDebit(null);
            log("взято с кредита: %.2f, без остатка", diff);
        } else if (credit > diff) {
            credit -= diff;
            setCredit(credit);
            setDebit(null);
            log("взято с кредита: %.2f, остаток кредита: %.2f", diff, credit);
        } else if (credit < diff) {
            diff -= credit;
            setDebit(diff);
            setCredit(null);
            log("взято с кредита %.2f, уйдет в дебет: %.2f", credit, diff);
        }
    }
    public void calcWithDebit(double debit, double diff) {
        if (debit == diff) {
            setCredit(null);
            setDebit(null);
            log("оплачен дебет: %.2f, без остатка", debit);
        } else if (debit > diff) {
            debit -= diff;
            setCredit(null);
            setDebit(debit);
            log("оплачен дебет: %.2f, остаток дебета %.2f", diff, debit);
        } else if (debit < diff) {
            diff -= debit;
            setDebit(null);
            setCredit(diff);
            log("оплачено дебета %.2f, уйдет в кредит: %.2f", debit, diff);
        }
    }
    
    public void calc(Balance prevBalance, Double diff, Period period) {
        Double credit = prevBalance.getCredit();
        Double debit = prevBalance.getDebit();
    
        if (diff == 0.0) {
            if (isExist(credit)) {
                setDebit(null);
                setCredit(credit);
                log("остается кредит: %.2f", credit);
            } else if (isExist(debit)) {
                setDebit(debit);
                setCredit(null);
                log("остается дебет: %.2f", debit);
            } else {
                setDebit(null);
                setCredit(null);
                log("долгов нет");
            }
        } else if (diff > 0) {
            if (isExist(credit)) {
                calcWithCredit(credit, diff);
            } else if (isExist(debit)) {
                setDebit(diff + debit);
                setCredit(null);
                log("текущий дебет: %.2f + новый %.2f", debit, diff);
            } else {
                setDebit(diff);
                setCredit(null);
                log("уйдет в дебит %.2f", diff);
            }
        } else {
            diff = Math.abs(diff);
            if (isExist(debit)) {
                calcWithDebit(debit, diff);
            } else if (isExist(credit)) {
                setDebit(null);
                setCredit(diff + credit);
                log("текущий кредит: %.2f + новый %.2f", credit, diff);
            } else {
                setCredit(diff);
                setDebit(null);
                log("уйдет в кредит %.2f", diff);
            }
        }
    }
    
    @Override
    public String toString() {
        return String.format("Сальдо. id = %d", getId());
    }
}
