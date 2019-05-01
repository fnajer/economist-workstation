/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package economistworkstation.Entity;

import static economistworkstation.Util.Util.isExist;
import java.util.Objects;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author fnajer
 */

public class Balance {
    private final IntegerProperty id;
    private final ObjectProperty<Double> creditBefore;
    private final ObjectProperty<Double> debitBefore;
    private final ObjectProperty<Double> creditAfter;
    private final ObjectProperty<Double> debitAfter;
    private final StringProperty state;
    private final StringProperty info;
    
    public Balance() {
        this(null, null);
    }
    
    public Balance(Object creditBefore, Object debitBefore) {
        this.id = new SimpleIntegerProperty(0);
        this.creditBefore = new SimpleObjectProperty(creditBefore);
        this.debitBefore = new SimpleObjectProperty(debitBefore);
        this.creditAfter = new SimpleObjectProperty(null);
        this.debitAfter = new SimpleObjectProperty(null);
        this.state = new SimpleStringProperty();
        this.info = new SimpleStringProperty();
    }
    
    public int getId() {
        return id.get();
    }
    public void setId(int id) {
        this.id.set(id);
    }
    
    public Double getCreditBefore() {
        return creditBefore.get();
    }
    public void setCreditBefore(Double creditBefore) {
        this.creditBefore.set(creditBefore);
    }
    
    public Double getDebitBefore() {
        return debitBefore.get();
    }
    public void setDebitBefore(Double debitBefore) {
        this.debitBefore.set(debitBefore);
    }
    
    public Double getCreditAfter() {
        return creditAfter.get();
    }
    public void setCreditAfter(Double creditAfter) {
        this.creditAfter.set(creditAfter);
    }
    
    public Double getDebitAfter() {
        return debitAfter.get();
    }
    public void setDebitAfter(Double debitAfter) {
        this.debitAfter.set(debitAfter);
    }
    
    public String getState() {
        return state.get();
    }
    public void setState(String state) {
        this.state.set(state);
    }
    
    public String getInfo() {
        return info.get();
    }
    public void setInfo(String info) {
        this.info.set(info);
    }

    private void log(String text) {
        System.out.println(text);
    }
    private String format(String text) {
        String formatString = text;
        return formatString;
    }
    private String format(String text, double value) {
        String formatString = String.format(text, value);
        return formatString;
    }
    private String format(String text, double value1, double value2) {
        String formatString = String.format(text, value1, value2);
        return formatString;
    }
    private void saveResult(String state, String info) {
        setState(state);
        setInfo(info);
        log(info);
    }
    
    public void calcWithCredit(Double credit, Double diff) {
        if (Objects.equals(credit, diff)) {
            setCreditAfter(null);
            setDebitAfter(null);
            saveResult("Недооплачено",
                    format("взято с кредита: %.2f, без остатка", diff));
        } else if (credit > diff) {
            credit -= diff;
            setCreditAfter(credit);
            setDebitAfter(null);
            saveResult("Недооплачено",
                    format("взято с кредита: %.2f, остаток кредита: %.2f", diff, credit));
        } else { //if (credit < diff)
            diff -= credit;
            setCreditAfter(null);
            setDebitAfter(diff);
            saveResult("Недооплачено",
                    format("взято с кредита %.2f, уйдет в дебет: %.2f", credit, diff));
        }
    }
    public void calcWithDebit(Double debit, Double diff) {
        if (Objects.equals(debit, diff)) {
            setCreditAfter(null);
            setDebitAfter(null);
            saveResult("Переоплачено",
                    format("оплачен дебет: %.2f, без остатка", debit));
        } else if (debit > diff) {
            debit -= diff;
            setCreditAfter(null);
            setDebitAfter(debit);
            saveResult("Переоплачено",
                    format("оплачен дебет: %.2f, остаток дебета %.2f", diff, debit));
        } else { //if (debit < diff)
            diff -= debit;
            setCreditAfter(diff);
            setDebitAfter(null);
            saveResult("Переоплачено",
                    format("оплачено дебета %.2f, уйдет в кредит: %.2f", debit, diff));
        }
    }
    
    private void calcIfZero(Double credit, Double debit) {
        if (isExist(credit)) {
            setDebitAfter(null);
            setCreditAfter(credit);
            saveResult("Оплачено",
                    format("остается кредит: %.2f", credit));
        } else if (isExist(debit)) {
            setDebitAfter(debit);
            setCreditAfter(null);
            saveResult("Оплачено",
                    format("остается дебет: %.2f", debit));
        } else {
            setDebitAfter(null);
            setCreditAfter(null);
            saveResult("Оплачено",
                    format("долгов нет"));
        }
    }
    
    private void calcIfNotEnought(Double credit, Double debit, Double diff) {
        if (isExist(credit)) {
            calcWithCredit(credit, diff);
        } else if (isExist(debit)) {
            setDebitAfter(diff + debit);
            setCreditAfter(null);
            saveResult("Недооплачено",
                    format("текущий дебет: %.2f + новый %.2f", debit, diff));
        } else {
            setDebitAfter(diff);
            setCreditAfter(null);
            saveResult("Недооплачено",
                    format("уйдет в дебет %.2f", diff));
        }
    }
    
    private void calcIfOverpaid(Double credit, Double debit, Double diff) {
        diff = Math.abs(diff);
        if (isExist(debit)) {
            calcWithDebit(debit, diff);
        } else if (isExist(credit)) {
            setDebitAfter(null);
            setCreditAfter(diff + credit);
            saveResult("Переоплачено",
                    format("текущий кредит: %.2f + новый %.2f", credit, diff));
        } else {
            setCreditAfter(diff);
            setDebitAfter(null);
            saveResult("Переоплачено",
                    format("уйдет в кредит %.2f", diff));
        }
    }
    
    public void calculateValuesAfter(Double diff) {
        Double prevCredit = getCreditBefore();
        Double prevDebit = getDebitBefore();
        
        if (diff == 0.0) {
            calcIfZero(prevCredit, prevDebit);
        } else if (diff > 0) {
            calcIfNotEnought(prevCredit, prevDebit, diff);
        } else {
            calcIfOverpaid(prevCredit, prevDebit, diff);
        }
    }
    
    public boolean hasBeforeValues() {
        return getCreditBefore() != null || getDebitBefore() != null;
    }
    
    public boolean hasAfterValues() {
        return getCreditAfter() != null || getDebitAfter()!= null;
    }
    
    public void fillBeforeValues(Balance balance) {
        setCreditBefore(balance.getCreditAfter());
        setDebitBefore(balance.getDebitAfter());
    }
    
    public Balance copy() {
        Balance balance = new Balance(getCreditBefore(), getDebitBefore());
        balance.setCreditAfter(getCreditAfter());
        balance.setDebitAfter(getDebitAfter());
        balance.setId(getId());
        return balance;
    }
    
    @Override
    public String toString() {
        return String.format("Сальдо. id = %d", getId());
    }
}
