/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package economistworkstation.Controller;

import economistworkstation.Entity.Fine;
import economistworkstation.Entity.Month;
import economistworkstation.Entity.Payment;
import economistworkstation.Entity.Period;
import economistworkstation.Entity.Rent;
import economistworkstation.Entity.Equipment;
import economistworkstation.Entity.Services;
import economistworkstation.Entity.TaxLand;
import economistworkstation.Util.TagParser;
import economistworkstation.Util.Util;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Arrays;
import java.util.Locale;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author fnajer
 */
public class PeriodFormController {
    @FXML
    private Label numberField;
    @FXML
    private Label numberRentAccField;
    @FXML
    private Label numberServicesAccField;
    @FXML
    private Label startPeriodField;
    @FXML
    private Label endPeriodField;
    
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
    private Label addCostRentLabel;
    @FXML
    private Label sumAddCostRentLabel;
    //payment
    @FXML
    private TextField paymentRentField;
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
    private Label addCostFineLabel;
    @FXML
    private Label sumAddCostFineLabel;
    @FXML
    private Label sumRentWithFineLabel;
    //payment
    @FXML
    private TextField paymentFineField;
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
    private Label addCostTaxLandLabel;
    @FXML
    private Label sumAddCostTaxLandLabel;
    //payment
    @FXML
    private TextField paymentTaxLandField;
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
    private Label addCostServicesLabel;
    //payment
    @FXML
    private TextField paymentServicesField;
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
    private Label addCostEquipmentLabel;
    @FXML
    private Label sumAddCostEquipmentLabel;
    //payment
    @FXML
    private TextField paymentEquipmentField;
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
            costTelephoneLabel};
        this.textFields = new TextField[]{costRentField, indexCostRentField, fineField, 
            taxLandField, countWaterField, tariffWaterField, countElectricityField,
            tariffElectricityField, costHeadingField,
            costGarbageField, costInternetField,
            costTelephoneField};
    }

    private Stage dialogStage;
    private Period period;
    private boolean okClicked = false;
    
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    
    public void setPeriod(Period period) {
        this.period = period;
        
        numberField.setText(Integer.toString(period.getNumber()));
        numberRentAccField.setText(Integer.toString(period.getNumberRentAcc()));
        numberServicesAccField.setText(Integer.toString(period.getNumberServicesAcc()));
        endPeriodField.setText(period.getEndPeriod());
        
        if (period.getRentPayment() != null) {
            Rent rent = (Rent) period.getRentPayment();
            costRentField.setText(Double.toString(rent.getCost()));
            indexCostRentField.setText(Double.toString(rent.getIndexCost()));
//        statePaymentRentLabel.setText(Util.boolToString(period.getPaidRent()));
        }
        if (period.getFinePayment() != null) {
            Fine fine = (Fine) period.getFinePayment();
            fineField.setText(Double.toString(fine.getFine()));
        }
        if (period.getTaxLandPayment() != null) {
            TaxLand taxLand = (TaxLand) period.getTaxLandPayment();
            taxLandField.setText(Double.toString(taxLand.getTaxLand()));
            //        isPaidTaxLandField.setText(Util.boolToString(period.getPaidTaxLand()));
        }
        if (period.getServicesPayment() != null) {
            Services services = (Services) period.getServicesPayment();
            setText(countWaterField, services.getCountWater());
            setText(tariffWaterField, services.getTariffWater());
            setText(countElectricityField, services.getCountElectricity());
            setText(tariffElectricityField, services.getTariffElectricity());
            setText(costHeadingField, services.getCostHeading());
            setText(costGarbageField, services.getCostGarbage());
            setText(costInternetField, services.getCostInternet());
            setText(costTelephoneField, services.getCostTelephone());
        }
        if (period.getEquipmentPayment() != null) {
            Equipment equipment = (Equipment) period.getEquipmentPayment();
            costEquipmentField.setText(Double.toString(equipment.getCostEquipment()));
        }
        
        setMatchArrays();
        setColorLabels(textFields);
        
        initCalc();
    }
    
    private void setText(TextField tf, Double value) {
        try {
            String text = Double.toString(value);
            tf.setText(text);
        } catch (NullPointerException e) {
            System.err.println(String.format(
                    "%s: value for %s from db is null", 
                    this.getClass().getSimpleName(),
                    tf.getId()));
            tf.setText("");
        }
    }
    
    public boolean isOkClicked() {
        return okClicked;
    }
    
    @FXML
    private void handleOk() {
        if (isInputValid()) {
 
            period.setNumber(Integer.parseInt(numberField.getText()));
            period.setEndPeriod(endPeriodField.getText());
            
            if (isFilled(costRentField, indexCostRentField)) {
                Rent rent = (Rent) period.getRentPayment();
                if (rent == null)
                    rent = new Rent();
                rent.setCost(Double.parseDouble(costRentField.getText()));
                rent.setIndexCost(Double.parseDouble(indexCostRentField.getText()));
//            period.setPaidRent(Util.stringToBool(paidRentField.getText()));
                period.setRentPayment(rent);
            }
            if (isFilled(fineField)) {
                Fine fine = (Fine) period.getFinePayment();
                if (fine == null)
                    fine = new Fine();
                fine.setFine(Double.parseDouble(fineField.getText()));
                period.setFinePayment(fine);
            }
            if (isFilled(taxLandField)) {
                TaxLand taxLand = (TaxLand) period.getTaxLandPayment();
                if (taxLand == null)
                    taxLand = new TaxLand();
                taxLand.setTaxLand(Double.parseDouble(taxLandField.getText()));
//            period.setPaidTaxLand(Util.stringToBool(isPaidTaxLandField.getText()));
                period.setTaxLandPayment(taxLand);
            }
            
            if (isFilled(countWaterField, tariffWaterField, countElectricityField,
                    tariffElectricityField, costHeadingField, costGarbageField,
                    costInternetField, costTelephoneField)) {
                Services services = (Services) period.getServicesPayment();
                if (services == null)
                    services = new Services();
                services.setCountWater(parseField(countWaterField));
                services.setTariffWater(parseField(tariffWaterField));
                services.setCountElectricity(parseField(countElectricityField));
                services.setTariffElectricity(parseField(tariffElectricityField));
                services.setCostHeading(parseField(costHeadingField));
                services.setCostGarbage(parseField(costGarbageField));
                services.setCostInternet(parseField(costInternetField));
                services.setCostTelephone(parseField(costTelephoneField));
//            period.setPaidRent(Util.stringToBool(paidRentField.getText()));
                period.setServicesPayment(services);
            }
            
            okClicked = true;
            dialogStage.close();
        }
    }
    
    public boolean isFilled(TextField ...tfs) {
        for (TextField tf : tfs) {
            if (tf.getText().length() != 0)
                return true;
        }
        return false;
    }
    
    public Double parseField(TextField field) {
        String fieldString = field.getText();
        try {
            double value = Double.parseDouble(fieldString);
            DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.US);
            DecimalFormat df = new DecimalFormat("#.##", otherSymbols);
            df.setRoundingMode(RoundingMode.HALF_UP);

            return Double.parseDouble(df.format(value));
        } catch(NumberFormatException e) {
            System.err.println(String.format(
                    "%s: %s value '%s' is not correct. Replaced by null",
                    this.getClass().getSimpleName(),
                    field.getId(),
                    fieldString));
            return null;
        }
    }
    
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }
    
    private boolean isInputValid() {
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

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Показываем сообщение об ошибке.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Некорректные данные");
            alert.setHeaderText("Заполните поля корректно");
            alert.setContentText(errorMessage);
            
            alert.showAndWait();
            
            return false;
    }}
    
    private boolean isPositive(TextField tf) {
        try {
            double value = Double.parseDouble(tf.getText());
            System.out.println(value > 0);
            return value > 0;
        } catch(NumberFormatException e) {
            System.out.println("false - set gray color");
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
        setCost(costRentField, indexCostRentField, sumRentLabel);
        printSum("rentFine");
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
            String formatTotal = TagParser.getDecimalFormat(Locale.US).format(total);
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
                
                if ("".equals(givenText)) continue;
                value = Double.parseDouble(givenText);
                if (value < 0) throw new NumberFormatException();
                sum += value;
            }
            formatSum = TagParser.getDecimalFormat(Locale.US).format(sum);
            label.setText(formatSum);
        } catch(NumberFormatException e) {
            label.setText("...");
        }
    }
    
    private void printSum(String sumFor) {
        if (sumFor.equals("services"))
            calcSum(sumServicesLabel, costWaterLabel, costElectricityLabel, costHeadingField,
                    costGarbageField, costInternetField, costTelephoneField);
        else if(sumFor.equals("rentFine"))
            calcSum(sumRentWithFineLabel, sumRentLabel, fineField);
    }
   
    public void addCalcListeners() {
        
        countWaterField.textProperty().addListener((observable, oldValue, newValue) -> {
            setCost(countWaterField, tariffWaterField, costWaterLabel);
            printSum("services");
            setColorLabels(countWaterField);
        });
        tariffWaterField.textProperty().addListener((observable, oldValue, newValue) -> {
            setCost(countWaterField, tariffWaterField, costWaterLabel);
            printSum("services");
            setColorLabels(tariffWaterField);
        });
        
        countElectricityField.textProperty().addListener((observable, oldValue, newValue) -> {
            setCost(countElectricityField, tariffElectricityField, costElectricityLabel);
            printSum("services");
            setColorLabels(countElectricityField);
        });
        tariffElectricityField.textProperty().addListener((observable, oldValue, newValue) -> {
            setCost(countElectricityField, tariffElectricityField, costElectricityLabel);
            printSum("services");
            setColorLabels(tariffElectricityField);
        });
        
        costHeadingField.textProperty().addListener((observable, oldValue, newValue) -> {
           printSum("services");
           setColorLabels(costHeadingField);
        });
        
        costGarbageField.textProperty().addListener((observable, oldValue, newValue) -> {
           printSum("services");
           setColorLabels(costGarbageField);
        });
        
        costInternetField.textProperty().addListener((observable, oldValue, newValue) -> {
            printSum("services");
            setColorLabels(costInternetField);
        });
        costTelephoneField.textProperty().addListener((observable, oldValue, newValue) -> {
            printSum("services");
            setColorLabels(costTelephoneField);
        });
        
        costRentField.textProperty().addListener((observable, oldValue, newValue) -> {
            setCost(costRentField, indexCostRentField, sumRentLabel);
            printSum("rentFine");
            setColorLabels(costRentField);
        });
        indexCostRentField.textProperty().addListener((observable, oldValue, newValue) -> {
            setCost(costRentField, indexCostRentField, sumRentLabel);
            printSum("rentFine");
            setColorLabels(indexCostRentField);
        });
        fineField.textProperty().addListener((observable, oldValue, newValue) -> {
            printSum("rentFine");
            setColorLabels(fineField);
        });
        
        taxLandField.textProperty().addListener((observable, oldValue, newValue) -> {
            setColorLabels(taxLandField);
        });
    }
}
