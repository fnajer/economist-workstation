/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package economistworkstation.Controller;

import economistworkstation.ContractData;
import economistworkstation.Entity.BalanceTable;
import economistworkstation.Entity.ExtraCost;
import economistworkstation.Entity.Fine;
import economistworkstation.Entity.Payment;
import economistworkstation.Entity.Period;
import economistworkstation.Entity.Rent;
import economistworkstation.Entity.Equipment;
import economistworkstation.Entity.Field;
import economistworkstation.Entity.Services;
import economistworkstation.Entity.TaxLand;
import economistworkstation.Model.PeriodModel;
import economistworkstation.Util.Util;
import static economistworkstation.Util.Util.parseField;
import static economistworkstation.Util.Util.isExist;
import static economistworkstation.Util.Util.decFormat;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.paint.Color;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author fnajer
 */
public class PeriodFormController extends BaseFormController {
    @Override
    public void initialize(URL location, ResourceBundle bundle) {}
    @FXML
    private Label numberLabel;
    @FXML
    private Label numberRentAccLabel;
    @FXML
    private Label numberServicesAccLabel;
    @FXML
    private Label startPeriodLabel;
    @FXML
    private Label endPeriodLabel;
    
    //calculation
    @FXML
    private Label costRentLabel;
    @FXML
    private TextField costRentField;
    @FXML
    private TextField indexCostRentField;
    @FXML
    private Label sumRentLabel;
    @FXML
    private Label extraCostRentLabel;
    @FXML
    private Label sumExtraCostRentLabel;
    //payment
    @FXML
    private TextField paymentRentField;
    @FXML
    private DatePicker datePaidRentField;
    @FXML
    private Label statePaymentRentLabel;
    @FXML
    private Label balancePaymentRentLabel;
    
    //calculation
    @FXML
    private TextField fineField;
    @FXML
    private Label fineLabel;
    @FXML
    private Label extraCostFineLabel;
    @FXML
    private Label sumExtraCostFineLabel;
    @FXML
    private Label sumRentWithFineLabel;
    //payment
    @FXML
    private TextField paymentFineField;
    @FXML
    private DatePicker datePaidFineField;
    @FXML
    private Label statePaymentFineLabel;
    @FXML
    private Label balancePaymentFineLabel;
    
    //calculation
    @FXML
    private TextField taxLandField;
    @FXML
    private Label taxLandLabel;
    @FXML
    private Label extraCostTaxLandLabel;
    @FXML
    private Label sumExtraCostTaxLandLabel;
    //payment
    @FXML
    private TextField paymentTaxLandField;
    @FXML
    private DatePicker datePaidTaxLandField;
    @FXML
    private Label statePaymentTaxLandLabel;
    @FXML
    private Label balancePaymentTaxLandLabel;
    
    //calculation
    @FXML
    private TextField countWaterField;
    @FXML
    private Label countWaterLabel;
    @FXML
    private TextField tariffWaterField;
    @FXML
    private Label costWaterLabel;
    @FXML
    private TextField countElectricityField;
    @FXML
    private Label countElectricityLabel;
    @FXML
    private TextField tariffElectricityField;
    @FXML
    private Label costElectricityLabel;
    @FXML
    private TextField costHeadingField;
    @FXML
    private Label costHeadingLabel;
    @FXML
    private TextField costGarbageField;
    @FXML
    private Label costGarbageLabel;
    @FXML
    private TextField costInternetField;
    @FXML
    private Label costInternetLabel;
    @FXML
    private TextField costTelephoneField;
    @FXML
    private Label costTelephoneLabel;
    @FXML
    private Label sumServicesLabel;
    @FXML
    private Label extraCostServicesLabel;
    //payment
    @FXML
    private TextField paymentServicesField;
    @FXML
    private DatePicker datePaidServicesField;
    @FXML
    private Label statePaymentServicesLabel;
    @FXML
    private Label balancePaymentServicesLabel;
    
    //calculation
    @FXML
    private TextField costEquipmentField;
    @FXML
    private Label costEquipmentLabel;
    @FXML
    private Label extraCostEquipmentLabel;
    @FXML
    private Label sumExtraCostEquipmentLabel;
    //payment
    @FXML
    private TextField paymentEquipmentField;
    @FXML
    private DatePicker datePaidEquipmentField;
    @FXML
    private Label statePaymentEquipmentLabel;
    @FXML
    private Label balancePaymentEquipmentLabel;
    
    private Label[] labels;
    private TextField[] textFields;
    
    private void setMatchArrays() {
        this.labels = new Label[]{costRentLabel, costRentLabel, fineLabel, 
            taxLandLabel, countWaterLabel, countWaterLabel, countElectricityLabel, 
            countElectricityLabel, costHeadingLabel,
            costGarbageLabel, costInternetLabel,
            costTelephoneLabel, costEquipmentLabel};
        this.textFields = new TextField[]{costRentField, indexCostRentField, fineField, 
            taxLandField, countWaterField, tariffWaterField, countElectricityField,
            tariffElectricityField, costHeadingField,
            costGarbageField, costInternetField,
            costTelephoneField, costEquipmentField};
    }
    
    private Field fields;
    private void initField() {
        this.fields = new Field();
        fields.bindRent(costRentField, indexCostRentField,
                        paymentRentField, datePaidRentField);
        fields.bindFine(fineField, paymentFineField, 
                        datePaidFineField);
        fields.bindTaxLand(taxLandField, paymentTaxLandField, 
                        datePaidTaxLandField);
        fields.bindServices(countWaterField, countElectricityField,
                        costHeadingField, costGarbageField, costInternetField,
                        costTelephoneField, tariffWaterField, tariffElectricityField,
                        paymentServicesField, datePaidServicesField);
        fields.bindEquipment(costEquipmentField, paymentEquipmentField, 
                        datePaidEquipmentField);
        fields.bindExtraCost(extraCostRentLabel, extraCostFineLabel, 
                        extraCostTaxLandLabel, extraCostServicesLabel,
                        extraCostEquipmentLabel);
    }
    
    private ArrayList<Payment> payments;
    private void copyPayments() {
        payments = new ArrayList(10);
        
        Rent rentSrc = period.getRentPayment();
        Fine fineSrc = period.getFinePayment();
        TaxLand taxLandSrc = period.getTaxLandPayment();
        Services servicesSrc = period.getServicesPayment();
        Equipment equipmentSrc = period.getEquipmentPayment();
        
        if (isExist(rentSrc))
            rent = rentSrc.copy();
        else rent = new Rent();
        if (isExist(fineSrc))
            fine = fineSrc.copy();
        else fine = new Fine();
        if (isExist(taxLandSrc))
            taxLand = taxLandSrc.copy();
        else taxLand = new TaxLand();
        if (isExist(servicesSrc))
            services = servicesSrc.copy();
        else services = new Services();
        if (isExist(equipmentSrc))
            equipment = equipmentSrc.copy();
        else equipment = new Equipment();
        
        payments.add(rent);
        payments.add(rentSrc);
        payments.add(fine);
        payments.add(fineSrc);
        payments.add(taxLand);
        payments.add(taxLandSrc);
        payments.add(services);
        payments.add(servicesSrc);
        payments.add(equipment);
        payments.add(equipmentSrc);
    }

    private Period period;
    
    private Rent rent = null;
    private Fine fine = null;
    private TaxLand taxLand = null;
    private Services services = null;
    private Equipment equipment = null;
    
    @Override
    public void setData(ContractData data) {
        this.period = data.getPeriod();
        
        Util.setCalledClass(this);
        
        initField();
        
        numberLabel.setText(Integer.toString(period.getNumber()));
        numberRentAccLabel.setText(Integer.toString(period.getNumberRentAcc()));
        numberServicesAccLabel.setText(Integer.toString(period.getNumberServicesAcc()));
        startPeriodLabel.setText(period.getStartPeriod(data.getContract().getDateStart()));
        endPeriodLabel.setText(period.getEndPeriod());
        
        copyPayments();
        
        fillPayments();
        
        refreshExtraCost();
        
        setMatchArrays();
        setColorLabels(textFields);
        
        initCalc();
        
        this.nextBalanceTable = new BalanceTable();
        setPaymentState();
    }
    private BalanceTable nextBalanceTable;
    
    private void fillPayments() {
        Payment payment;
        for (int i = 0; i < payments.size(); i += 2) {
            payment = payments.get(i);
            payment.fill(fields);
        }
    }

    private void setBalanceTable() {
        period.setNextBalanceTable(nextBalanceTable);
    }
    
    private void setPaymentState() {
        handleBalanceFields(rent, statePaymentRentLabel, balancePaymentRentLabel);
        handleBalanceFields(fine, statePaymentFineLabel, balancePaymentFineLabel);
        handleBalanceFields(taxLand, statePaymentTaxLandLabel, balancePaymentTaxLandLabel);
        handleBalanceFields(services, statePaymentServicesLabel, balancePaymentServicesLabel);
        handleBalanceFields(equipment, statePaymentEquipmentLabel, balancePaymentEquipmentLabel);
    }
    
    private void handleBalanceFields(Payment payment,
            Label statePaymentLbl, Label balancePaymentLbl) 
    {
        payment.calcPartOfBalance(period.getBalanceTable(), nextBalanceTable);
        
        statePaymentLbl.setText(payment.getState());
        balancePaymentLbl.setText(payment.getInfo());
    }
    
    private void refreshExtraCost() {
        ExtraCost extraCost = period.getExtraCost();
        if (isExist(extraCost)) {
            fields.fillExtraCost(extraCost);
        } else {
            fields.fillExtraCostInitial();
        }
    }
    
    private void savePaymentValues() {
        Payment payment, paymentSrc;
        for (int i = 0; i < payments.size(); i += 2) {
            payment = payments.get(i);
            paymentSrc = payments.get(i + 1);

            if (payment.fieldsIsFilled(fields)) {
                payment.saveValuesOf(fields);
                payment.bindPeriod(period);
            } else if (isExist(paymentSrc) && !payment.fieldsIsFilled(fields)) {
                payment.prepareToDelete();
                payment.bindPeriod(period);
            }
        }
    }
    
    @FXML
    @Override
    protected void handleOk() {
        if (isInputValid()) {

            savePaymentValues();
            
            setBalanceTable();
               
            closeForm();
        }
    }
    
    @Override
    protected boolean isInputValid() {
        String errorMessage = "";

//        if (firstNameField.getText() == null || firstNameField.getText().length() == 0) {
//            errorMessage += "Введите имя!\n"; 
//        }
//        if (lastNameField.getText() == null || lastNameField.getText().length() == 0) {
//            errorMessage += "Введите фамилию!\n"; 
//        }
//        if (patronymicField.getText() == null || patronymicField.getText().length() == 0) {
//            errorMessage += "Введите отчество!\n"; 
//        }
//        if (addressField.getText() == null || addressField.getText().length() == 0) {
//            errorMessage += "Введите адрес!\n"; 
//        }
//        if (birthdayField.getText() == null || birthdayField.getText().length() == 0) {
//            errorMessage += "Неверная дата рождения!\n"; 
//        }
//        if (personField.getText() == null || personField.getText().length() == 0) {
//            errorMessage += "Введите физ. лицо!\n";
//        }

        return errorNotExist(errorMessage);
    }
    
    private boolean isPositive(TextField tf) {
        try {
            double value = Double.parseDouble(tf.getText());
            return value > 0;
        } catch(NumberFormatException e) {
//            System.out.println("false - set gray color");
            return false;
        }
    }
    
    private void setColorLabels(TextField ...tfs) {
        for (TextField tf: tfs) {
            int index = Arrays.asList(this.textFields).indexOf(tf);
            if (!isPositive(tf)) {
                labels[index].setTextFill(Color.web("#C8C1C1"));
            } else {
                labels[index].setTextFill(Color.web("#0F0A0A"));
            }
        }
    }
    
    
    private void setTextAndColor(boolean isPaid, Label label, Button button) {
        if (isPaid) {
            label.setTextFill(Color.web("#96DA1B"));
            button.setText("Отменить");
        }
        else {
            label.setTextFill(Color.web("#DA221B"));
            button.setText("Оплатить");
        }
    }
    @FXML
    private TitledPane servicesPane;
    
    private void initCalc() {
        servicesPane.setTextFill(Color.web("#96DA1B"));
        displaySum();
        addCalcListeners();
    }
    
    private void displaySum() {
        setCost(countWaterField, tariffWaterField, costWaterLabel);
        setCost(countElectricityField, tariffElectricityField, costElectricityLabel);
        printSum("services");
        printSum("servicesExtra");
        printSum("taxLandExtra");
        printSum("equipmentExtra");
        printSum("servicesExtra");
        setCost(costRentField, indexCostRentField, sumRentLabel);
        printSum("rentExtra");
        printSum("fineExtra");
        printSum("rentFineExtra");
    }
       
    private void setCost(TextField firstField, TextField secondField, Label label) {
        try {
            String count = firstField.getText();
            String tariff = secondField.getText();
            
            if ("".equals(count) && "".equals(tariff)) {
                label.setText("");  
                return;
            }
            
            double total = Double.parseDouble(count) * Double.parseDouble(tariff);
            if (total < 0) throw new NumberFormatException();
            String formatTotal = decFormat(Locale.US).format(total);
            label.setText(formatTotal);
        } catch(NumberFormatException e) {
            label.setText("...");    
        }
    }
    
    private void calcSum(Label label, Control ...controls) {
        try {
            double sum = 0;
            double value;
            String formatSum;
            String givenText = "";

            for (Control control: controls) {
                if (control instanceof Label)
                    givenText = ((Label) control).getText();
                else if(control instanceof TextField)
                    givenText = ((TextField) control).getText();
                
                if ("".equals(givenText) || "Нет".equals(givenText)) continue;
                value = Double.parseDouble(givenText);
                if (value < 0) throw new NumberFormatException();
                sum += value;
            }
            formatSum = decFormat(Locale.US).format(sum);
            label.setText(formatSum);
        } catch(NumberFormatException e) {
            label.setText("...");
        }
    }
    
    private void printSum(String sumFor) {
        if (sumFor.equals("servicesExtra"))
            calcSum(sumServicesLabel, costWaterLabel, costElectricityLabel, costHeadingField,
                    costGarbageField, costInternetField, costTelephoneField, extraCostServicesLabel);
        else if(sumFor.equals("rentExtra"))
            calcSum(sumExtraCostRentLabel, sumRentLabel, extraCostRentLabel);
        else if(sumFor.equals("fineExtra"))
            calcSum(sumExtraCostFineLabel, fineField, extraCostFineLabel);
        else if(sumFor.equals("rentFineExtra"))
            calcSum(sumRentWithFineLabel, sumExtraCostRentLabel, sumExtraCostFineLabel);
        else if(sumFor.equals("taxLandExtra"))
            calcSum(sumExtraCostTaxLandLabel, taxLandField, extraCostTaxLandLabel);
        else if(sumFor.equals("equipmentExtra"))
            calcSum(sumExtraCostEquipmentLabel, costEquipmentField, extraCostEquipmentLabel);
    }
   
    public void addCalcListeners() {
        
        countWaterField.textProperty().addListener((observable, oldValue, newValue) -> {
            services.setCountWater(parseField(countWaterField));
            setCost(countWaterField, tariffWaterField, costWaterLabel);
            setColorLabels(countWaterField);
        });
        tariffWaterField.textProperty().addListener((observable, oldValue, newValue) -> {
            services.setTariffWater(parseField(tariffWaterField));
            setCost(countWaterField, tariffWaterField, costWaterLabel);
            setColorLabels(tariffWaterField);
        });
        costWaterLabel.textProperty().addListener((observable, oldValue, newValue) -> {
            printSum("servicesExtra");
            setPaymentState();
        });
        
        countElectricityField.textProperty().addListener((observable, oldValue, newValue) -> {
            services.setCountElectricity(parseField(countElectricityField));
            setCost(countElectricityField, tariffElectricityField, costElectricityLabel);
            setColorLabels(countElectricityField);
        });
        tariffElectricityField.textProperty().addListener((observable, oldValue, newValue) -> {
            services.setTariffElectricity(parseField(tariffElectricityField));
            setCost(countElectricityField, tariffElectricityField, costElectricityLabel);
            setColorLabels(tariffElectricityField);
        });
        costElectricityLabel.textProperty().addListener((observable, oldValue, newValue) -> {
            printSum("servicesExtra");
            setPaymentState();
        });
        
        costHeadingField.textProperty().addListener((observable, oldValue, newValue) -> {
           services.setCostHeading(parseField(costHeadingField));
           printSum("servicesExtra");
           setColorLabels(costHeadingField);
           setPaymentState();
        });
        
        costGarbageField.textProperty().addListener((observable, oldValue, newValue) -> {
           services.setCostGarbage(parseField(costGarbageField));
           printSum("servicesExtra");
           setColorLabels(costGarbageField);
           setPaymentState();
        });
        
        costInternetField.textProperty().addListener((observable, oldValue, newValue) -> {
            services.setCostInternet(parseField(costInternetField));
            printSum("servicesExtra");
            setColorLabels(costInternetField);
            setPaymentState();
        });
        costTelephoneField.textProperty().addListener((observable, oldValue, newValue) -> {
            services.setCostTelephone(parseField(costTelephoneField));
            printSum("servicesExtra");
            setColorLabels(costTelephoneField);
            setPaymentState();
        });
        extraCostServicesLabel.textProperty().addListener((observable, oldValue, newValue) -> {
            printSum("servicesExtra");
        });
        paymentServicesField.textProperty().addListener((observable, oldValue, newValue) -> {
            services.setPaid(parseField(paymentServicesField));
            setPaymentState(); 
        });
        
        costRentField.textProperty().addListener((observable, oldValue, newValue) -> {
            rent.setCost(parseField(costRentField));
            setCost(costRentField, indexCostRentField, sumRentLabel);
            printSum("rentFineExtra");
            setColorLabels(costRentField);
        });
        indexCostRentField.textProperty().addListener((observable, oldValue, newValue) -> {
            rent.setIndexCost(parseField(indexCostRentField));
            setCost(costRentField, indexCostRentField, sumRentLabel);
            printSum("rentFineExtra");
            setColorLabels(indexCostRentField);
        });
        sumRentLabel.textProperty().addListener((observable, oldValue, newValue) -> {
            printSum("rentExtra");
            setPaymentState();
        });
        extraCostRentLabel.textProperty().addListener((observable, oldValue, newValue) -> {
            printSum("rentExtra");
        });
        fineField.textProperty().addListener((observable, oldValue, newValue) -> {
            fine.setFine(parseField(fineField));
            printSum("fineExtra");
            setColorLabels(fineField);
            setPaymentState();
        });
        extraCostFineLabel.textProperty().addListener((observable, oldValue, newValue) -> {
            printSum("fineExtra");
        });
        sumExtraCostRentLabel.textProperty().addListener((observable, oldValue, newValue) -> {
            printSum("rentFineExtra");
        });
        sumExtraCostFineLabel.textProperty().addListener((observable, oldValue, newValue) -> {
            printSum("rentFineExtra");
        });
        paymentRentField.textProperty().addListener((observable, oldValue, newValue) -> {
            rent.setPaid(parseField(paymentRentField));
            setPaymentState(); 
        });
        paymentFineField.textProperty().addListener((observable, oldValue, newValue) -> {
            fine.setPaid(parseField(paymentFineField));
            setPaymentState(); 
        });
        
        taxLandField.textProperty().addListener((observable, oldValue, newValue) -> {
            taxLand.setTaxLand(parseField(taxLandField));
            printSum("taxLandExtra");
            setColorLabels(taxLandField);
            setPaymentState();
        });
        extraCostTaxLandLabel.textProperty().addListener((observable, oldValue, newValue) -> {
            printSum("taxLandExtra");
        });
        paymentTaxLandField.textProperty().addListener((observable, oldValue, newValue) -> {
            taxLand.setPaid(parseField(paymentTaxLandField));
            setPaymentState(); 
        });
        
        costEquipmentField.textProperty().addListener((observable, oldValue, newValue) -> {
            equipment.setCostEquipment(parseField(costEquipmentField));
            printSum("equipmentExtra");
            setColorLabels(costEquipmentField);
            setPaymentState();
        });
        extraCostEquipmentLabel.textProperty().addListener((observable, oldValue, newValue) -> {
            printSum("equipmentExtra");
        });
        paymentEquipmentField.textProperty().addListener((observable, oldValue, newValue) -> {
            equipment.setPaid(parseField(paymentEquipmentField));
            setPaymentState(); 
        });
    }
    
    @FXML
    private void handleExtraCost(ActionEvent event) {
        boolean okClicked = openExtraCostForm(period);
        if (okClicked) {
            PeriodModel.updateExtraCostPeriod(period.getId(), period);
            refreshExtraCost();
        }
    }
    
    private boolean openExtraCostForm(Period period) {
        ContractData data = new ContractData(null, period, null, null, null);
        
        return showForm(data, 
                "Добавить дополнительную стоимость", 
                "View/Period/ExtraCostForm.fxml");
    }
    
    @FXML
    private void handleBalance(ActionEvent event) {
        showBalance(period);
    }
    
    private boolean showBalance(Period period) {
        ContractData data = new ContractData(null, period, null, null, null);
        
        return showForm(data, 
                "Просмотр сальдо", 
                "View/Period/Balance.fxml");
    }
}
