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
    private final ObjectProperty<Double> credit;
    private final ObjectProperty<Double> debit;
    private final StringProperty state;
    private final StringProperty info;
    
    public Balance() {
        this(null, null);
    }
    
    public Balance(Object credit, Object debit) {
        this.id = new SimpleIntegerProperty(0);
        this.credit = new SimpleObjectProperty(credit);
        this.debit = new SimpleObjectProperty(debit);
        this.state = new SimpleStringProperty();
        this.info = new SimpleStringProperty();
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
            setCredit(null);
            setDebit(null);
            saveResult("Недооплачено",
                    format("взято с кредита: %.2f, без остатка", diff));
        } else if (credit > diff) {
            credit -= diff;
            setCredit(credit);
            setDebit(null);
            saveResult("Недооплачено",
                    format("взято с кредита: %.2f, остаток кредита: %.2f", diff, credit));
        } else { //if (credit < diff)
            diff -= credit;
            setCredit(null);
            setDebit(diff);
            saveResult("Недооплачено",
                    format("взято с кредита %.2f, уйдет в дебет: %.2f", credit, diff));
        }
    }
    public void calcWithDebit(Double debit, Double diff) {
        if (Objects.equals(debit, diff)) {
            setCredit(null);
            setDebit(null);
            saveResult("Переоплачено",
                    format("оплачен дебет: %.2f, без остатка", debit));
        } else if (debit > diff) {
            debit -= diff;
            setCredit(null);
            setDebit(debit);
            saveResult("Переоплачено",
                    format("оплачен дебет: %.2f, остаток дебета %.2f", diff, debit));
        } else { //if (debit < diff)
            diff -= debit;
            setCredit(diff);
            setDebit(null);
            saveResult("Переоплачено",
                    format("оплачено дебета %.2f, уйдет в кредит: %.2f", debit, diff));
        }
    }
    
    private void calcIfZero(Double credit, Double debit) {
        if (isExist(credit)) {
            setDebit(null);
            setCredit(credit);
            saveResult("-",
                    format("остается кредит: %.2f", credit));
        } else if (isExist(debit)) {
            setDebit(debit);
            setCredit(null);
            saveResult("-",
                    format("остается дебет: %.2f", debit));
        } else {
            setDebit(null);
            setCredit(null);
            saveResult("Оплачено",
                    format("долгов нет"));
        }
    }
    
    private void calcIfNotEnought(Double credit, Double debit, Double diff) {
        if (isExist(credit)) {
            calcWithCredit(credit, diff);
        } else if (isExist(debit)) {
            setDebit(diff + debit);
            setCredit(null);
            saveResult("Недооплачено",
                    format("текущий дебет: %.2f + новый %.2f", debit, diff));
        } else {
            setDebit(diff);
            setCredit(null);
            saveResult("-",
                    format("уйдет в дебет %.2f", diff));
        }
    }
    
    private void calcIfOverpaid(Double credit, Double debit, Double diff) {
        diff = Math.abs(diff);
        if (isExist(debit)) {
            calcWithDebit(debit, diff);
        } else if (isExist(credit)) {
            setDebit(null);
            setCredit(diff + credit);
            saveResult("Переоплачено",
                    format("текущий кредит: %.2f + новый %.2f", credit, diff));
        } else {
            setCredit(diff);
            setDebit(null);
            saveResult("Нет платежа",
                    format("уйдет в кредит %.2f", diff));
        }
    }
    
    public void calc(Balance prevBalance, Double diff) {
        Double prevCredit = prevBalance.getCredit();
        Double prevDebit = prevBalance.getDebit();
        
        if (diff == 0.0) {
            calcIfZero(prevCredit, prevDebit);
        } else if (diff > 0) {
            calcIfNotEnought(prevCredit, prevDebit, diff);
        } else {
            calcIfOverpaid(prevCredit, prevDebit, diff);
        }
    }
    
    public boolean containValues() {
        return getCredit() != null || getDebit() != null;
    }
    
    @Override
    public String toString() {
        return String.format("Сальдо. id = %d", getId());
    }
}
