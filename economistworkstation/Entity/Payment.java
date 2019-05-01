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
    private final ObjectProperty<Balance> balance;
    
    public Payment() {
        this(null, null, null);
    }
    
    public Payment(Object paid, String datePaid, Balance balance) {
        this.id = new SimpleIntegerProperty(0);
        this.paid = new SimpleObjectProperty(paid);
        this.datePaid = new SimpleStringProperty(datePaid);
        this.balance = new SimpleObjectProperty(balance);
    }
    
    public abstract PreparedStatement getInsertStatement(Database db) throws SQLException;
    public abstract PreparedStatement getUpdateStatement(Database db) throws SQLException;
    public abstract PreparedStatement getDeleteStatement(Database db) throws SQLException;
    
    public abstract Double sumToPay();
    public abstract boolean isEmpty();
    public abstract Payment copy();
    /**
     * @param prevPeriod
     * @return existing Payment or new Payment
     */
    public abstract Payment getPrevPayment(Period prevPeriod);
    public abstract void bindPeriod(Period period);
    /**
     *
     * @param field
     * @param period
     */
    public abstract void saveValuesOf(Field field, Period period);
    public abstract boolean fieldsIsFilled(Field field);
    public abstract void fill(Field field);
    public abstract void setLabels(Map<String, Label> labels);
    public abstract Payment createNewPayment();
    
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
    
    public Balance getBalance() {
        if (balance.get() == null)
            this.balance.set(new Balance());

        return balance.get();
    }
    public void setBalance(Balance balance) {
        this.balance.set(balance);
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
        Balance balance = getBalance();
        balance.setState("Нет платежа");
        balance.setInfo("");
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
        setBalance(null);
    }
    
    public void calculate() {
            double diff = getDiff();
//            if (!isExist(prevPayment) && fieldsIsEmpty()) {
//                setEmptyBalance();
//                return;
//            }
//                
//            Balance prevBalance = isExist(prevPayment) 
//                    ? prevPayment.getBalance()
//                    : new Balance();
            getBalance().calculateValuesAfter(diff);
    }
}
