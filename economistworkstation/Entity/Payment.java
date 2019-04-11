/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package economistworkstation.Entity;

import economistworkstation.Database;
import java.sql.SQLException;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author fnajer
 */
public abstract class Payment {
    private final IntegerProperty id;
    private final DoubleProperty paid;
    private final StringProperty datePaid;
    
    public Payment() {
        this(0.0, null);
    }
    
    public Payment(double paid, String datePaid) {
        this.id = new SimpleIntegerProperty(0);
        this.paid = new SimpleDoubleProperty(paid);
        this.datePaid = new SimpleStringProperty(datePaid);
    }
    
    public abstract int addPaymentToDb(Database db) throws SQLException;
    public abstract int updatePaymentToDb(Database db) throws SQLException;
    
    public int getId() {
        return id.get();
    }
    public void setId(int id) {
        this.id.set(id);
    }
    
    public double getPaid() {
        return paid.get();
    }
    public void setPaid(double paid) {
        this.paid.set(paid);
    }
    
    public String getDatePaid() {
        return datePaid.get();
    }
    public void setDatePaid(String datePaid) {
        this.datePaid.set(datePaid);
    }
}
