/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package economistworkstation.Controller;

import economistworkstation.Entity.Month;
import economistworkstation.Util.TagParser;
import economistworkstation.Util.Util;
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
public class MonthFormController {
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
    private Month month;
    private boolean okClicked = false;
    
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    
    public void setMonth(Month month) {
        this.month = month;

        numberField.setText(Integer.toString(month.getNumber()));
        numberRentAccField.setText(Integer.toString(month.getNumberRentAcc()));
        numberServicesAccField.setText(Integer.toString(month.getNumberCommunalAcc()));
        endPeriodField.setText(month.getDate());
        
        costRentField.setText(Double.toString(month.getCost()));
        indexCostRentField.setText(Double.toString(month.getIndexCost()));
        fineField.setText(Double.toString(month.getFine()));
//        statePaymentRentLabel.setText(Util.boolToString(month.getPaidRent()));

        taxLandField.setText(Double.toString(month.getTaxLand()));
//        isPaidTaxLandField.setText(Util.boolToString(month.getPaidTaxLand()));
        
        countWaterField.setText(Double.toString(month.getCountWater()));
        tariffWaterField.setText(Double.toString(month.getTariffWater()));
        countElectricityField.setText(Double.toString(month.getCountElectricity()));
        tariffElectricityField.setText(Double.toString(month.getTariffElectricity()));
        costHeadingField.setText(Double.toString(month.getCountHeading()));
        costGarbageField.setText(Double.toString(month.getCountHeading()));
        costInternetField.setText(Double.toString(month.getCostInternet()));
        costTelephoneField.setText(Double.toString(month.getCostTelephone()));
        
        setMatchArrays();
        setColorLabels(textFields);
        
        initCalc();
    }
    
    public boolean isOkClicked() {
        return okClicked;
    }
    
    @FXML
    private void handleOk() {
        if (isInputValid()) {
 
            month.setNumber(Integer.parseInt(numberField.getText()));
            month.setDate(endPeriodField.getText());
            
            month.setCost(Double.parseDouble(costRentField.getText()));
            month.setIndexCost(Double.parseDouble(indexCostRentField.getText()));
            month.setFine(Double.parseDouble(fineField.getText()));
//            month.setPaidRent(Util.stringToBool(paidRentField.getText()));

            month.setTaxLand(Double.parseDouble(taxLandField.getText()));
//            month.setPaidTaxLand(Util.stringToBool(isPaidTaxLandField.getText()));
            
            month.setCountWater(Double.parseDouble(countWaterField.getText()));
            month.setTariffWater(Double.parseDouble(tariffWaterField.getText()));
            month.setCountElectricity(Double.parseDouble(countElectricityField.getText()));
            month.setTariffElectricity(Double.parseDouble(tariffElectricityField.getText()));
            month.setCountHeading(Double.parseDouble(costHeadingField.getText()));
            month.setCountGarbage(Double.parseDouble(costGarbageField.getText()));
            month.setCostInternet(Double.parseDouble(costInternetField.getText()));
            month.setCostTelephone(Double.parseDouble(costTelephoneField.getText()));

            okClicked = true;
            dialogStage.close();
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
            System.out.println(false);
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
    
    private void setCost(TextField count, TextField tariff, Label label) {
        try {
            double total = Double.parseDouble(count.getText()) * Double.parseDouble(tariff.getText());
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

            for (Control control: controls) {
                if (control instanceof Label) {
                    value = Double.parseDouble(((Label) control).getText());
                    if (value < 0) throw new NumberFormatException();
                    sum += value;
                } else if(control instanceof TextField) {
                    value = Double.parseDouble(((TextField) control).getText());
                    if (value < 0) throw new NumberFormatException();
                    sum += value;
                }
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
           printSum("communal");
           setColorLabels(costGarbageField);
        });
        
        costInternetField.textProperty().addListener((observable, oldValue, newValue) -> {
            printSum("communal");
            setColorLabels(costInternetField);
        });
        costTelephoneField.textProperty().addListener((observable, oldValue, newValue) -> {
            printSum("communal");
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
