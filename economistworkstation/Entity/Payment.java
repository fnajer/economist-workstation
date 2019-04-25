/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package economistworkstation.Entity;

import economistworkstation.Database;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

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
            return new Balance();
        return balance.get();
    }
    public void setBalance(Balance balance) {
        this.balance.set(balance);
    }
    
    public Double getDiff() {
        Double needPay = sumToPay();
        Double diff = needPay - getPaid();
        return diff;
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
}
