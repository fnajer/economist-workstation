/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package economistworkstation.Entity;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author fnajer
 */
public class Month {
    private final IntegerProperty id;
    private final IntegerProperty number;
    private final IntegerProperty numberRentAcc;
    private final IntegerProperty numberCommunalAcc;
    private final StringProperty date;
    private final DoubleProperty cost;
    private final DoubleProperty indexCost;
    private final DoubleProperty fine;
    private final DoubleProperty countWater;
    private final DoubleProperty countElectricity;
    private final DoubleProperty countHeading;
    private final DoubleProperty countGarbage;
    private final DoubleProperty costInternet;
    private final DoubleProperty costTelephone;
    private final BooleanProperty isPaidRent;
    private final BooleanProperty isPaidCommunal;
    private final IntegerProperty idContract;
    private final DoubleProperty tariffWater;
    private final DoubleProperty tariffElectricity;
    private final DoubleProperty tariffHeading;
    private final DoubleProperty tariffGarbage;
    private final DoubleProperty taxLand;
    private final BooleanProperty isPaidTaxLand;
     
    public Month() {
        this(0, 0, 0, null, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
                false, false, 0, 0.0, 0.0, 0.0, 0.0, 0.0, false);
    }
    
    public Month(int number, int numberRentAcc, int numberCommunalAcc, String date,
            double cost, double indexCost, double fine, double countWater, 
            double countElectricity, double countHeading, double countGarbage,
            double costInternet, double costTelephone,
            boolean isPaidRent, boolean isPaidCommunal, int idContract,
            double tariffWater, double tariffElectricity, double tariffHeading,
            double tariffGarbage, double taxLand, boolean isPaidTaxLand) {
       this.id = new SimpleIntegerProperty(0);
       this.number = new SimpleIntegerProperty(number);
       this.numberRentAcc = new SimpleIntegerProperty(numberRentAcc);
       this.numberCommunalAcc = new SimpleIntegerProperty(numberCommunalAcc);
       this.date = new SimpleStringProperty(date); 
       this.cost = new SimpleDoubleProperty(cost); 
       this.indexCost = new SimpleDoubleProperty(indexCost); 
       this.fine = new SimpleDoubleProperty(fine);
       this.countWater = new SimpleDoubleProperty(countWater);
       this.countElectricity = new SimpleDoubleProperty(countElectricity);
       this.countHeading = new SimpleDoubleProperty(countHeading);
       this.countGarbage = new SimpleDoubleProperty(countGarbage);
       this.costInternet = new SimpleDoubleProperty(costInternet); 
       this.costTelephone = new SimpleDoubleProperty(costTelephone); 
       this.isPaidRent = new SimpleBooleanProperty(isPaidRent);  
       this.isPaidCommunal = new SimpleBooleanProperty(isPaidCommunal);
       this.idContract = new SimpleIntegerProperty(idContract);
       this.tariffWater = new SimpleDoubleProperty(tariffWater);
       this.tariffElectricity = new SimpleDoubleProperty(tariffElectricity);
       this.tariffHeading = new SimpleDoubleProperty(tariffHeading);
       this.tariffGarbage = new SimpleDoubleProperty(tariffGarbage);
       this.taxLand = new SimpleDoubleProperty(taxLand);
       this.isPaidTaxLand = new SimpleBooleanProperty(isPaidTaxLand);
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
    
    public int getNumberCommunalAcc() {
        return numberCommunalAcc.get();
    }

    public void setNumberCommunalAcc(int numberCommunalAcc) {
        this.numberCommunalAcc.set(numberCommunalAcc);
    }
    
    public String getDate() {
        return date.get();
    }

    public void setDate(String date) {
        this.date.set(date);
    }
    
    public StringProperty dateProperty() {
        return date;
    }
     
    public double getCost() {
        return cost.get();
    }

    public void setCost(double cost) {
        this.cost.set(cost);
    }
    
    public double getIndexCost() {
        return indexCost.get();
    }
    
    public void setIndexCost(double indexCost) {
        this.indexCost.set(indexCost);
    }

    public double getFine() {
        return fine.get();
    }
    
    public void setFine(double fine) {
        this.fine.set(fine);
    }
    
    public double getCountWater() {
        return countWater.get();
    }

    public void setCountWater(double countWater) {
        this.countWater.set(countWater);
    }
    
    public double getCountElectricity() {
        return countElectricity.get();
    }

    public void setCountElectricity(double countElectricity) {
        this.countElectricity.set(countElectricity);
    }
     
    public double getCountHeading() {
        return countHeading.get();
    }

    public void setCountHeading(double countHeading) {
        this.countHeading.set(countHeading);
    }
    
    public double getCountGarbage() {
        return countGarbage.get();
    }

    public void setCountGarbage(double countGarbage) {
        this.countGarbage.set(countGarbage);
    }
    
     public double getCostInternet() {
        return costInternet.get();
    }

    public void setCostInternet(double costInternet) {
        this.costInternet.set(costInternet);
    }
    
     public double getCostTelephone() {
        return costTelephone.get();
    }

    public void setCostTelephone(double costTelephone) {
        this.costTelephone.set(costTelephone);
    }
    
    public boolean getPaidRent() {
        return isPaidRent.get();
    }

    public void setPaidRent(boolean isPaidRent) {
        this.isPaidRent.set(isPaidRent);
    }
    
    public boolean getPaidCommunal() {
        return isPaidCommunal.get();
    }

    public void setPaidCommunal(boolean isPaidCommunal) {
        this.isPaidCommunal.set(isPaidCommunal);
    }
    
    public int getIdContract() {
        return idContract.get();
    }

    public void setIdContract(int idContract) {
        this.idContract.set(idContract);
    }
     
    public double getTariffWater() {
        return tariffWater.get();
    }

    public void setTariffWater(double tariffWater) {
        this.tariffWater.set(tariffWater);
    }
    
    public double getTariffElectricity() {
        return tariffElectricity.get();
    }

    public void setTariffElectricity(double tariffElectricity) {
        this.tariffElectricity.set(tariffElectricity);
    }
    
    public double getTariffHeading() {
        return tariffHeading.get();
    }

    public void setTariffHeading(double tariffHeading) {
        this.tariffHeading.set(tariffHeading);
    }
    
    public double getTariffGarbage() {
        return tariffGarbage.get();
    }

    public void setTariffGarbage(double tariffGarbage) {
        this.tariffGarbage.set(tariffGarbage);
    }
    
    public double getTaxLand() {
        return taxLand.get();
    }

    public void setTaxLand(double taxLand) {
        this.taxLand.set(taxLand);
    }
    
    public boolean getPaidTaxLand() {
        return isPaidTaxLand.get();
    }

    public void setPaidTaxLand(boolean isPaidTaxLand) {
        this.isPaidTaxLand.set(isPaidTaxLand);
    }
    
    static public double calcSumRent(Month month) {
        return month.getCost() * month.getIndexCost();
    }
    
    static public String getMonthName(int monthNum, boolean genitive) {
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
    
    static public double calcCostWater(Month month) {
        return month.getCountWater() * month.getTariffWater();
    }
    static public double calcCostElectricity(Month month) {
        return month.getCountElectricity() * month.getTariffElectricity();
    }
    static public double calcCostHeading(Month month) {
        return month.getCountHeading() * month.getTariffHeading();
    }
    static public double calcCostGarbage(Month month) {
        return month.getCountGarbage() * month.getTariffGarbage();
    }
    static public double calcCostInternet(Month month) {
        return month.getCostInternet();
    }
    static public double calcCostTelephone(Month month) {
        return month.getCostTelephone();
    }
    
    @Override
    public String toString() {
        return getDate() + ' ' + getNumber();
    }
}