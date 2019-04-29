/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package economistworkstation.Entity;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 *
 * @author fnajer
 */
public class Field {
//    private final IntegerProperty id;
    private final ObjectProperty<TextField> costRent;
    private final ObjectProperty<TextField> indexCostRent;
    private final ObjectProperty<TextField> paymentRent;
    private final ObjectProperty<DatePicker> datePaidRent;
    
    private final ObjectProperty<TextField> costFine;
    private final ObjectProperty<TextField> paymentFine;
    private final ObjectProperty<DatePicker> datePaidFine;
    
    private final ObjectProperty<TextField> costTaxLand;
    private final ObjectProperty<TextField> paymentTaxLand;
    private final ObjectProperty<DatePicker> datePaidTaxLand;

    private final ObjectProperty<TextField> countWater;
    private final ObjectProperty<TextField> countElectricity;
    private final ObjectProperty<TextField> costHeading;
    private final ObjectProperty<TextField> costGarbage;
    private final ObjectProperty<TextField> costInternet;
    private final ObjectProperty<TextField> costTelephone;
    private final ObjectProperty<TextField> tariffWater;
    private final ObjectProperty<TextField> tariffElectricity;
    private final ObjectProperty<TextField> paymentServices;
    private final ObjectProperty<DatePicker> datePaidServices;
    
    private final ObjectProperty<TextField> costEquipment;
    private final ObjectProperty<TextField> paymentEquipment;
    private final ObjectProperty<DatePicker> datePaidEquipment;

    public Field() {
        this.costRent = new SimpleObjectProperty(null);
        this.indexCostRent = new SimpleObjectProperty(null);
        this.paymentRent = new SimpleObjectProperty(null);
        this.datePaidRent = new SimpleObjectProperty(null);
        
        this.costFine = new SimpleObjectProperty(null);
        this.paymentFine = new SimpleObjectProperty(null);
        this.datePaidFine = new SimpleObjectProperty(null);
        
        this.costTaxLand = new SimpleObjectProperty(null);
        this.paymentTaxLand = new SimpleObjectProperty(null);
        this.datePaidTaxLand = new SimpleObjectProperty(null);
        
        this.countWater = new SimpleObjectProperty(null);
        this.countElectricity = new SimpleObjectProperty(null);
        this.costHeading = new SimpleObjectProperty(null);
        this.costGarbage = new SimpleObjectProperty(null);
        this.costInternet = new SimpleObjectProperty(null);
        this.costTelephone = new SimpleObjectProperty(null);
        this.tariffWater = new SimpleObjectProperty(null);
        this.tariffElectricity = new SimpleObjectProperty(null);
        this.paymentServices = new SimpleObjectProperty(null);
        this.datePaidServices = new SimpleObjectProperty(null);
        
        this.costEquipment = new SimpleObjectProperty(null);
        this.paymentEquipment = new SimpleObjectProperty(null);
        this.datePaidEquipment = new SimpleObjectProperty(null);
    }
    
//    public Field(Object credit, Object debit) {
//        this.id = new SimpleIntegerProperty(0);
//        this.credit = new SimpleObjectProperty(credit);
//        this.debit = new SimpleObjectProperty(debit);
//    }
    
//    
    public TextField getCostRent() {
        return costRent.get();
    }
    public void setCostRent(TextField costRent) {
        this.costRent.set(costRent);
    }
    
    public TextField getIndexCostRent() {
        return indexCostRent.get();
    }
    public void setIndexCostRent(TextField indexCostRent) {
        this.indexCostRent.set(indexCostRent);
    }
    
    public TextField getPaymentRent() {
        return paymentRent.get();
    }
    public void setPaymentRent(TextField paymentRent) {
        this.paymentRent.set(paymentRent);
    }
    
    public DatePicker getDatePaidRent() {
        return datePaidRent.get();
    }
    public void setDatePaidRent(DatePicker datePaidRent) {
        this.datePaidRent.set(datePaidRent);
    }
    
    public TextField getCostFine() {
        return costFine.get();
    }
    public void setCostFine(TextField costFine) {
        this.costFine.set(costFine);
    }
    
    public TextField getPaymentFine() {
        return paymentFine.get();
    }
    public void setPaymentFine(TextField paymentFine) {
        this.paymentFine.set(paymentFine);
    }
    
    public DatePicker getDatePaidFine() {
        return datePaidFine.get();
    }
    public void setDatePaidFine(DatePicker datePaidFine) {
        this.datePaidFine.set(datePaidFine);
    }
    
    public TextField getCostTaxLand() {
        return costTaxLand.get();
    }
    public void setCostTaxLand(TextField costTaxLand) {
        this.costTaxLand.set(costTaxLand);
    }
    
    public TextField getPaymentTaxLand() {
        return paymentTaxLand.get();
    }
    public void setPaymentTaxLand(TextField paymentTaxLand) {
        this.paymentTaxLand.set(paymentTaxLand);
    }
    
    public DatePicker getDatePaidTaxLand() {
        return datePaidTaxLand.get();
    }
    public void setDatePaidTaxLand(DatePicker datePaidTaxLand) {
        this.datePaidTaxLand.set(datePaidTaxLand);
    }
    
    public TextField getCostEquipment() {
        return costEquipment.get();
    }
    public void setCostEquipment(TextField costEquipment) {
        this.costEquipment.set(costEquipment);
    }
    
    public TextField getPaymentEquipment() {
        return paymentEquipment.get();
    }
    public void setPaymentEquipment(TextField paymentEquipment) {
        this.paymentEquipment.set(paymentEquipment);
    }
    
    public DatePicker getDatePaidEquipment() {
        return datePaidEquipment.get();
    }
    public void setDatePaidEquipment(DatePicker datePaidEquipment) {
        this.datePaidEquipment.set(datePaidEquipment);
    }
    
    public TextField getCountWater() {
        return countWater.get();
    }
    public void setCountWater(TextField countWater) {
        this.countWater.set(countWater);
    }
    
    public TextField getCountElectricity() {
        return countElectricity.get();
    }
    public void setCountElectricity(TextField countElectricity) {
        this.countElectricity.set(countElectricity);
    }
    
    public TextField getCostHeading() {
        return costHeading.get();
    }
    public void setCostHeading(TextField costHeading) {
        this.costHeading.set(costHeading);
    }
    
    public TextField getCostGarbage() {
        return costGarbage.get();
    }
    public void setCostGarbage(TextField costGarbage) {
        this.costGarbage.set(costGarbage);
    }
    
    public TextField getCostInternet() {
        return costInternet.get();
    }
    public void setCostInternet(TextField costInternet) {
        this.costInternet.set(costInternet);
    }
    
    public TextField getCostTelephone() {
        return costTelephone.get();
    }
    public void setCostTelephone(TextField costTelephone) {
        this.costTelephone.set(costTelephone);
    }
    
    public TextField getTariffWater() {
        return tariffWater.get();
    }
    public void setTariffWater(TextField tariffWater) {
        this.tariffWater.set(tariffWater);
    }
    
    public TextField getTariffElectricity() {
        return tariffElectricity.get();
    }
    public void setTariffElectricity(TextField tariffElectricity) {
        this.tariffElectricity.set(tariffElectricity);
    }
    
    public TextField getPaymentServices() {
        return paymentServices.get();
    }
    public void setPaymentServices(TextField paymentServices) {
        this.paymentServices.set(paymentServices);
    }
    
    public DatePicker getDatePaidServices() {
        return datePaidServices.get();
    }
    public void setDatePaidServices(DatePicker datePaidServices) {
        this.datePaidServices.set(datePaidServices);
    }

    public void bindRent(TextField costRentField, TextField indexCostRentField,
        TextField paymentRentField, DatePicker datePaidRentField) 
    {
        setCostRent(costRentField);
        setIndexCostRent(indexCostRentField);
        setPaymentRent(paymentRentField);
        setDatePaidRent(datePaidRentField);
    }
    public boolean rentIsFilled() {
        return isFilled(getCostRent(), 
                getIndexCostRent(), 
                getPaymentRent());
    }
    
    public void bindFine(TextField costFineField, TextField paymentFineField,
            DatePicker datePaidFineField) 
    {
        setCostFine(costFineField);
        setPaymentFine(paymentFineField);
        setDatePaidFine(datePaidFineField);
    }
    public boolean fineIsFilled() {
        return isFilled(getCostFine(),
                getPaymentFine());
    }
    
    public void bindTaxLand(TextField costTaxLandField, TextField paymentTaxLandField,
            DatePicker datePaidTaxLandField) 
    {
        setCostTaxLand(costTaxLandField);
        setPaymentTaxLand(paymentTaxLandField);
        setDatePaidTaxLand(datePaidTaxLandField);
    }
    public boolean taxLandIsFilled() {
        return isFilled(getCostTaxLand(),
                getPaymentTaxLand());
    }
    
    public void bindEquipment(TextField costEquipmentField, TextField paymentEquipmentField,
            DatePicker datePaidEquipmentField) 
    {
        setCostEquipment(costEquipmentField);
        setPaymentEquipment(paymentEquipmentField);
        setDatePaidEquipment(datePaidEquipmentField);
    }
    public boolean equipmentIsFilled() {
        return isFilled(getCostEquipment(),
                getPaymentEquipment());
    }
    
    public void bindServices(TextField countWaterField, TextField countElectricityField,
            TextField costHeadingField, TextField costGarbageField, 
            TextField costInternetField, TextField costTelephoneField, 
            TextField tariffWaterField, TextField tariffElectricityField,
            TextField paymentServicesField, DatePicker datePaidServicesField) 
    {
        setCountWater(countWaterField);
        setCountElectricity(countElectricityField);
        setCostHeading(costHeadingField);
        setCostGarbage(costGarbageField);
        setCostInternet(costInternetField);
        setCostTelephone(costTelephoneField);
        setTariffWater(tariffWaterField);
        setTariffElectricity(tariffElectricityField);
        setPaymentServices(paymentServicesField);
        setDatePaidServices(datePaidServicesField);
    }
    public boolean servicesIsFilled() {
        return isFilled(getCostEquipment(), getCountElectricity(), getCostHeading(),
                getCostGarbage(), getCostInternet(), getCostTelephone(), getTariffWater(),
                getTariffElectricity(), getPaymentServices());
    }
    
    public static boolean isFilled(TextField ...tfs) {
        for (TextField tf : tfs) {
            if (tf.getText().length() != 0)
                return true;
        }
        return false;
    }
    public static boolean isFilled(Label ...lbls) {
        for (Label lbl : lbls) {
            String text = lbl.getText();
            if (text.length() != 0 && !text.equals("...") && !text.equals("0"))
                return true;
        }
        return false;
    }
}
