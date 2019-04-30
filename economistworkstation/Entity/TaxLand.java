/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package economistworkstation.Entity;

import economistworkstation.Database;
import static economistworkstation.Util.Util.isExist;
import static economistworkstation.Util.Util.parseField;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 *
 * @author fnajer
 */

public class TaxLand extends Payment {
    private final ObjectProperty<Double> costTaxLand;
    
    public TaxLand() {
        this(null, null, null, null);
    }
    
    public TaxLand(Object paid, String datePaid, Object costTaxLand, Balance balance) {
        super(paid, datePaid, balance);
        this.costTaxLand = new SimpleObjectProperty(costTaxLand);
    }
    
    public Double getTaxLand() {
        return costTaxLand.get();
    }
    public void setTaxLand(Double costTaxLand) {
        this.costTaxLand.set(costTaxLand);
    }
    
    @Override
    public PreparedStatement getInsertStatement(Database db) throws SQLException {
        PreparedStatement ps = db.conn.prepareStatement("INSERT INTO TAXLAND "
                + "(id_tax_land, paid_tax_land, date_paid_tax_land, tax_land) "
                + "VALUES(?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, getId());
        ps.setObject(2, getPaid());
        ps.setString(3, getDatePaid());
        ps.setObject(4, getTaxLand());

        return ps;
    }
    @Override
    public PreparedStatement getUpdateStatement(Database db) throws SQLException {
        PreparedStatement ps = db.conn.prepareStatement("UPDATE TAXLAND "
                + "SET paid_tax_land=?, date_paid_tax_land=?, tax_land=? "
                + "WHERE id_tax_land=?", Statement.RETURN_GENERATED_KEYS);
        ps.setObject(1, getPaid());
        ps.setString(2, getDatePaid());
        ps.setObject(3, getTaxLand());
        ps.setInt(4, getId());
        
        return ps;
    }
    @Override
    public PreparedStatement getDeleteStatement(Database db) throws SQLException {
        PreparedStatement ps = db.conn.prepareStatement("DELETE FROM TAXLAND "
                + "WHERE id_tax_land=?");
        ps.setInt(1, getId());
        
        return ps;
    }
    
    @Override
    public Double sumToPay() {
        return safeGetSum(getTaxLand());
    }
    
    @Override
    public boolean isEmpty() {
        return getTaxLand() == null
                && getPaid() == null
                && getDatePaid() == null;
    }
    
    @Override
    public TaxLand copy() {
        TaxLand taxLand = new TaxLand(getPaid(), getDatePaid(), getTaxLand(),
            getBalance().copy());
        taxLand.setId(getId());
        return taxLand;
    }
    
    @Override
    public void saveValuesOf(Field field, Period period) {
        setTaxLand(parseField(field.getCostTaxLand()));

        setPaid(parseField(field.getPaymentTaxLand()));
        setDatePaid(parseField(field.getDatePaidTaxLand()));
        if (isExist(period.getTaxLandPayment()) && isExist(period.getTaxLandPayment().getBalance()))
            setBalance(period.getTaxLandPayment().getBalance().copy());
    }
    
    @Override
    public void bindPeriod(Period period) {
        period.setTaxLandPayment(this);
    }
    
    @Override
    public boolean fieldsIsFilled(Field fields) {
        return fields.taxLandIsFilled();
    }
    
    @Override
    public void fill(Field field) {
        field.fillTaxLand(this);
    }

    @Override
    public Payment getPrevPayment(Period prevPeriod) {
        Payment prevPayment = null;
        if (isExist(prevPeriod))
            prevPayment = prevPeriod.getTaxLandPayment();
        
        if (isExist(prevPayment))
            return prevPayment;
        
        return prevPayment;
    }
    
    @Override
    public String toString() {
        return String.format("Платеж на земельный налог. id = %d", this.getId());
    }
}
