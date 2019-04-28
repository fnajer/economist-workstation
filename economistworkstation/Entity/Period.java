/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package economistworkstation.Entity;

import static economistworkstation.Util.Util.isExist;
import java.time.LocalDate;
import java.util.ArrayList;
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
public class Period {
    private final IntegerProperty id;
    private final IntegerProperty number;
    private final IntegerProperty numberRentAcc;
    private final IntegerProperty numberServicesAcc;
    private final StringProperty endPeriod;
    private final IntegerProperty idContract;
    private final ObjectProperty<Rent> rentPayment;
    private final ObjectProperty<Fine> finePayment;
    private final ObjectProperty<TaxLand> taxLandPayment;
    private final ObjectProperty<Services> servicesPayment;
    private final ObjectProperty<Equipment> equipmentPayment;
    private final ObjectProperty<ExtraCost> extraCost;
    private final ObjectProperty<BalanceTable> balance;
    
    public Period() {
        this(0, 0, 0, null, 0, null, null, null, null, null, null, null);
    }
    
    public Period(int number, int numberRentAcc, int numberServicesAcc, 
            String endPeriod, int idContract, Payment rentPayment,
            Payment finePayment, Payment taxLandPayment, Payment servicesPayment,
            Payment equipmentPayment, ExtraCost extraCost, BalanceTable balance) {
       this.id = new SimpleIntegerProperty(0);
       this.number = new SimpleIntegerProperty(number);
       this.numberRentAcc = new SimpleIntegerProperty(numberRentAcc);
       this.numberServicesAcc = new SimpleIntegerProperty(numberServicesAcc);
       this.endPeriod = new SimpleStringProperty(endPeriod);
       this.idContract = new SimpleIntegerProperty(idContract);
       
       this.rentPayment = new SimpleObjectProperty(rentPayment);
       this.finePayment = new SimpleObjectProperty(finePayment);
       this.taxLandPayment = new SimpleObjectProperty(taxLandPayment);
       this.servicesPayment = new SimpleObjectProperty(servicesPayment);
       this.equipmentPayment = new SimpleObjectProperty(equipmentPayment);
       
       this.extraCost = new SimpleObjectProperty(extraCost);
       this.balance = new SimpleObjectProperty(balance);
    }
    
    public ArrayList<Payment> getListPayments() {
        ArrayList<Payment> list = new ArrayList();
        list.add(getRentPayment());
        list.add(getFinePayment());
        list.add(getTaxLandPayment());
        list.add(getServicesPayment());
        list.add(getEquipmentPayment());
        
        return list;
    }
    
    
    public int getId() {
        return id.get();
    }
    public void setId(int id) {
        this.id.set(id);
    }
    
    public int getNumber() {
        return number.get();
    }
    public void setNumber(int number) {
        this.number.set(number);
    }
    public IntegerProperty numberProperty() {
        return number;
    }
    
    public int getNumberRentAcc() {
        return numberRentAcc.get();
    }
    public void setNumberRentAcc(int numberRentAcc) {
        this.numberRentAcc.set(numberRentAcc);
    }
    
    public int getNumberServicesAcc() {
        return numberServicesAcc.get();
    }
    public void setNumberServicesAcc(int numberServicesAcc) {
        this.numberServicesAcc.set(numberServicesAcc);
    }
    
    public String getEndPeriod() {
        return endPeriod.get();
    }
    public void setEndPeriod(String endPeriod) {
        this.endPeriod.set(endPeriod);
    }
    public StringProperty endPeriodProperty() {
        return endPeriod;
    }
     
    public int getIdContract() {
        return idContract.get();
    }
    public void setIdContract(int idContract) {
        this.idContract.set(idContract);
    }
    
    public Rent getRentPayment() {
        return rentPayment.get();  
    }
    public void setRentPayment(Rent rentPayment) {
        this.rentPayment.set(rentPayment);
    }
    
    public Fine getFinePayment() {
        return finePayment.get();
    }
    public void setFinePayment(Fine finePayment) {
        this.finePayment.set(finePayment);
    }
    
    public TaxLand getTaxLandPayment() {
        return taxLandPayment.get();
    }
    public void setTaxLandPayment(TaxLand taxLandPayment) {
        this.taxLandPayment.set(taxLandPayment);
    }
    
    public Services getServicesPayment() {
        return servicesPayment.get();
    }
    public void setServicesPayment(Services servicesPayment) {
        this.servicesPayment.set(servicesPayment);
    }
    
    public Equipment getEquipmentPayment() {
        return equipmentPayment.get();
    }
    public void setEquipmentPayment(Equipment equipmentPayment) {
        this.equipmentPayment.set(equipmentPayment);
    }
    
    public ExtraCost getExtraCost() {
        return extraCost.get();
    }
    public void setExtraCost(ExtraCost extraCost) {
        this.extraCost.set(extraCost);
    }
            
    public BalanceTable getBalance() {
        return balance.get();
    }
    public void setBalance(BalanceTable balance) {
        this.balance.set(balance);
    }

    public String getMonthName(int monthNum, boolean genitive) {
        String monthName = "";
        switch(monthNum) {
            case 1:
                monthName = "январь";
                break;
            case 2:
                monthName = "февраль";
                break;
            case 3:
                monthName = "март";
                break;
            case 4:
                monthName = "апрель";
                break;
            case 5:
                monthName = "май";
                break;
            case 6:
                monthName = "июнь";
                break;
            case 7:
                monthName = "июль";
                break;
            case 8:
                monthName = "август";
                break;
            case 9:
                monthName = "сентябрь";
                break;
            case 10:
                monthName = "октябрь";
                break;
            case 11:
                monthName = "ноябрь";
                break;
            case 12:
                monthName = "декабрь";
                break;
            default:
                monthName = "Not found";
        }
        if (genitive) {
            if (monthNum != 3 && monthNum != 8) {
                monthName = monthName.substring(0, monthName.length() - 1);
                monthName = monthName + 'я';
            } else {
                monthName = monthName + 'а';
            }
        }
        return monthName;
    }
    
    public String getStartPeriod(String dateStartContract) {
        int currentNumber = getNumber();
        
        if (currentNumber == 1)
            return dateStartContract;
        
        LocalDate startPeriod;
        LocalDate endPeriod = LocalDate.parse(getEndPeriod());
        int numDay = endPeriod.getDayOfMonth();
        if (numDay == 1)
            startPeriod = endPeriod.minusMonths(1);
        else
            startPeriod = endPeriod.minusDays(numDay - 1);
        return startPeriod.toString();
    }
    
    @Override
    public String toString() {
        return String.format("Период №%d, до %s. id = %d. ", getNumber(), 
                getEndPeriod(), getId());
    }
}