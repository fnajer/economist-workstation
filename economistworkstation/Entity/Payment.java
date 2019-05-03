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
import java.util.Map;
import java.util.Objects;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Label;

/**
 *
 * @author fnajer
 */
public abstract class Payment {
    private final IntegerProperty id;
    private final ObjectProperty<Double> paid;
    private final StringProperty datePaid;
    
    public Payment() {
        this(null, null);
    }
    
    public Payment(Object paid, String datePaid) {
        this.id = new SimpleIntegerProperty(0);
        this.paid = new SimpleObjectProperty(paid);
        this.datePaid = new SimpleStringProperty(datePaid);
        this.state = new SimpleStringProperty();
        this.info = new SimpleStringProperty();
    }
    
    public abstract PreparedStatement getInsertStatement(Database db) throws SQLException;
    public abstract PreparedStatement getUpdateStatement(Database db) throws SQLException;
    public abstract PreparedStatement getDeleteStatement(Database db) throws SQLException;
    
    public abstract Double sumToPay();
    public abstract boolean isEmpty();
    public abstract Payment copy();
    public abstract void bindPeriod(Period period);
    /**
     *
     * @param field
     */
    public abstract void saveValuesOf(Field field);
    public abstract boolean fieldsIsFilled(Field field);
    public abstract void fill(Field field);
    public abstract void setLabels(Map<String, Label> labels);
    public abstract Payment createNewPayment();
    
    public abstract Double getCredit(BalanceTable balanceTable);
    public abstract void setCredit(BalanceTable balanceTable, Double credit);
    public abstract Double getDebit(BalanceTable balanceTable);
    public abstract void setDebit(BalanceTable balanceTable, Double debit);
    
    public int getId() {
        return id.get();
    }
    public void setId(int id) {
        this.id.set(id);
    }
    
    public Double getPaid() {
        return paid.get();
    }
    public void setPaid(Double paid) {
        this.paid.set(paid);
    }
    
    public String getDatePaid() {
        return datePaid.get();
    }
    public void setDatePaid(String datePaid) {
        this.datePaid.set(datePaid);
    }
    
    public Double getDiff() {
        try {
            Double needPay = sumToPay();
            Double diff = needPay - safeGetSum(getPaid());
            return diff;
        } catch(NullPointerException e) {
            System.out.println(String.format(
                    "%s: %s diff is null",
                    this.getClass().getSimpleName(),
                    this));
            return 0.0;
        }
    }
    
    protected Double safeGetSum(Double value) {
        if (value == null)
            return 0.0;
        else
            return value;
    }
    protected Double safeGetSum(Double value1, Double value2) {
        try {
            return value1 * value2;
        } catch(NullPointerException e) {
            return 0.0;
        }
    }
    
    private void setEmptyBalance() {
        setState("Нет платежа");
        setInfo("");
    }
     
    protected boolean fieldsIsEmpty() {
        try {
            Double needPay = sumToPay();
            Double paid = safeGetSum(getPaid());

            return needPay == 0.0 && paid == 0.0;
        } catch(NullPointerException e) {
            return true;
        }
    }
    
    public void prepareToDelete() {
        setPaid(-1.0);
    }
    
    private BalanceTable balanceTable;
    private BalanceTable nextBalanceTable;
    public void calcPartOfBalance(BalanceTable balanceTable, BalanceTable nextBalanceTable) {
            if (fieldsIsEmpty() && !isExist(balanceTable)) {
                setEmptyBalance();
                return;
            }
            if (!isExist(balanceTable))
                balanceTable = new BalanceTable();
            this.balanceTable = balanceTable;
            this.nextBalanceTable = nextBalanceTable;
            double diff = getDiff();

            calculateValuesAfter(diff);
            this.balanceTable = null;
            this.nextBalanceTable = null;
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
            setCredit(nextBalanceTable, null);
            setDebit(nextBalanceTable, null);
            saveResult("Недооплачено",
                    format("взято с кредита: %.2f, без остатка", diff));
        } else if (credit > diff) {
            credit -= diff;
            setCredit(nextBalanceTable, credit);
            setDebit(nextBalanceTable, null);
            saveResult("Недооплачено",
                    format("взято с кредита: %.2f, остаток кредита: %.2f", diff, credit));
        } else { //if (credit < diff)
            diff -= credit;
            setCredit(nextBalanceTable, null);
            setDebit(nextBalanceTable, diff);
            saveResult("Недооплачено",
                    format("взято с кредита %.2f, уйдет в дебет: %.2f", credit, diff));
        }
    }
    public void calcWithDebit(Double debit, Double diff) {
        if (Objects.equals(debit, diff)) {
            setCredit(nextBalanceTable, null);
            setDebit(nextBalanceTable, null);
            saveResult("Переоплачено",
                    format("оплачен дебет: %.2f, без остатка", debit));
        } else if (debit > diff) {
            debit -= diff;
            setCredit(nextBalanceTable, null);
            setDebit(nextBalanceTable, debit);
            saveResult("Переоплачено",
                    format("оплачен дебет: %.2f, остаток дебета %.2f", diff, debit));
        } else { //if (debit < diff)
            diff -= debit;
            setCredit(nextBalanceTable, diff);
            setDebit(nextBalanceTable, null);
            saveResult("Переоплачено",
                    format("оплачено дебета %.2f, уйдет в кредит: %.2f", debit, diff));
        }
    }
    
    private void calcIfZero(Double credit, Double debit) {
        if (isExist(credit)) {
            setDebit(nextBalanceTable, null);
            setCredit(nextBalanceTable, credit);
            saveResult("Оплачено",
                    format("остается кредит: %.2f", credit));
        } else if (isExist(debit)) {
            setDebit(nextBalanceTable, debit);
            setCredit(nextBalanceTable, null);
            saveResult("Оплачено",
                    format("остается дебет: %.2f", debit));
        } else {
            setDebit(nextBalanceTable, null);
            setCredit(nextBalanceTable, null);
            saveResult("Оплачено",
                    format("долгов нет"));
        }
    }
    
    private void calcIfNotEnought(Double credit, Double debit, Double diff) {
        if (isExist(credit)) {
            calcWithCredit(credit, diff);
        } else if (isExist(debit)) {
            setDebit(nextBalanceTable, diff + debit);
            setCredit(nextBalanceTable, null);
            saveResult("Недооплачено",
                    format("текущий дебет: %.2f + новый %.2f", debit, diff));
        } else {
            setDebit(nextBalanceTable, diff);
            setCredit(nextBalanceTable, null);
            saveResult("Недооплачено",
                    format("уйдет в дебет %.2f", diff));
        }
    }
    
    private void calcIfOverpaid(Double credit, Double debit, Double diff) {
        diff = Math.abs(diff);
        if (isExist(debit)) {
            calcWithDebit(debit, diff);
        } else if (isExist(credit)) {
            setDebit(nextBalanceTable, null);
            setCredit(nextBalanceTable, diff + credit);
            saveResult("Переоплачено",
                    format("текущий кредит: %.2f + новый %.2f", credit, diff));
        } else {
            setCredit(nextBalanceTable, diff);
            setDebit(nextBalanceTable, null);
            saveResult("Переоплачено",
                    format("уйдет в кредит %.2f", diff));
        }
    }
    
    private void calculateValuesAfter(Double diff) {
        Double prevCredit = getCredit(balanceTable);
        Double prevDebit = getDebit(balanceTable);
        
        if (diff == 0.0) {
            calcIfZero(prevCredit, prevDebit);
        } else if (diff > 0) {
            calcIfNotEnought(prevCredit, prevDebit, diff);
        } else {
            calcIfOverpaid(prevCredit, prevDebit, diff);
        }
    }
    
    private StringProperty state;
    private StringProperty info;
    
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
}
