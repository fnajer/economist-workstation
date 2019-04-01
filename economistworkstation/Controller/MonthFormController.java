/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package economistworkstation.Controller;

import economistworkstation.Entity.Month;
import economistworkstation.Util.TagParser;
import economistworkstation.Util.Util;
import java.net.URL;
import java.util.Arrays;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author fnajer
 */
public class MonthFormController {
    
    @FXML
    private Label costLabel;

    @FXML
    private Label indexCostLabel;

    @FXML
    private Label fineLabel;

    @FXML
    private Label countWaterLabel;

    @FXML
    private Label countElectricityLabel;
    
    @FXML
    private Label countHeadingLabel;
    
    @FXML
    private Label tariffWaterLabel;

    @FXML
    private Label tariffHeadingLabel;

    @FXML
    private Label tariffElectricityLabel;
    
    @FXML
    private Label countGarbageLabel;

    @FXML
    private Label tariffGarbageLabel;
    
    @FXML
    private Label costInternetLabel;

    @FXML
    private Label costTelephoneLabel;
    
    @FXML
    private Label taxLandLabel;

    
    private Label[] labels;
    private TextField[] textFields;
    
    private void setConstArrays() {
        Label[] labels = {costLabel, indexCostLabel, fineLabel, taxLandLabel,
            countWaterLabel, tariffWaterLabel, countElectricityLabel, 
            tariffElectricityLabel, countHeadingLabel, tariffHeadingLabel,
            countGarbageLabel, tariffGarbageLabel, costInternetLabel,
            costTelephoneLabel};
        TextField[] textFields = {costField, indexCostField, fineField, taxLandField,
            countWaterField, tariffWaterField, countElectricityField,
            tariffElectricityField, countHeadingField, tariffHeadingField,
            countGarbageField, tariffGarbageField, costInternetField,
            costTelephoneField};
        this.labels = labels;
        this.textFields = textFields;
    }

    @FXML
    private TextField costField;

    @FXML
    private TextField indexCostField;

    @FXML
    private TextField fineField;

    @FXML
    private TextField taxLandField;
    
    @FXML
    private Label isPaidTaxLandField;
    
    @FXML
    private TextField countHeadingField;

    @FXML
    private TextField countElectricityField;

    @FXML
    private TextField countWaterField;
    
    @FXML
    private TextField countGarbageField;

    @FXML
    private Label numberRentAccField;
    
    @FXML
    private Label numberCommunalAccField;
    
    @FXML
    private Label numberField;

    @FXML
    private Label dateField;

    @FXML
    private Label isPaidRentField;

    @FXML
    private Label isPaidCommunalField;

    @FXML
    private TextField tariffWaterField;

    @FXML
    private TextField tariffElectricityField;

    @FXML
    private TextField tariffHeadingField;
    
    @FXML
    private TextField tariffGarbageField;
    
    @FXML
    private TextField costInternetField;
    
    @FXML
    private TextField costTelephoneField;
    
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
        numberCommunalAccField.setText(Integer.toString(month.getNumberCommunalAcc()));
        dateField.setText(month.getDate());
        
        costField.setText(Double.toString(month.getCost()));
        indexCostField.setText(Double.toString(month.getIndexCost()));
        fineField.setText(Double.toString(month.getFine()));
        isPaidRentField.setText(Util.boolToString(month.getPaidRent()));

        taxLandField.setText(Double.toString(month.getTaxLand()));
        isPaidTaxLandField.setText(Util.boolToString(month.getPaidTaxLand()));
        
        countWaterField.setText(Double.toString(month.getCountWater()));
        tariffWaterField.setText(Double.toString(month.getTariffWater()));
        countElectricityField.setText(Double.toString(month.getCountElectricity()));
        tariffElectricityField.setText(Double.toString(month.getTariffElectricity()));
        countHeadingField.setText(Double.toString(month.getCountHeading()));
        tariffHeadingField.setText(Double.toString(month.getTariffHeading()));
        countGarbageField.setText(Double.toString(month.getCountHeading()));
        tariffGarbageField.setText(Double.toString(month.getTariffHeading()));
        costInternetField.setText(Double.toString(month.getCostInternet()));
        costTelephoneField.setText(Double.toString(month.getCostTelephone()));
        isPaidCommunalField.setText(Util.boolToString(month.getPaidCommunal()));
        
        setTextAndColor(month.getPaidRent(), isPaidRentField, rentBtn);
        setTextAndColor(month.getPaidTaxLand(), isPaidTaxLandField, taxLandBtn);
        setTextAndColor(month.getPaidCommunal(), isPaidCommunalField, communalBtn);
        
        setConstArrays();
        setColorLabels(textFields);
        
        initCalc();
    }
    
    @FXML
    private Button communalBtn;

    @FXML
    private Button rentBtn;

    @FXML
    private Button taxLandBtn;
    
    public boolean isOkClicked() {
        return okClicked;
    }
    
    @FXML
    private void handleOk() {
        if (isInputValid()) {
 
            month.setNumber(Integer.parseInt(numberField.getText()));
            month.setDate(dateField.getText());
            
            month.setCost(Double.parseDouble(costField.getText()));
            month.setIndexCost(Double.parseDouble(indexCostField.getText()));
            month.setFine(Double.parseDouble(fineField.getText()));
            month.setPaidRent(Util.stringToBool(isPaidRentField.getText()));

            month.setTaxLand(Double.parseDouble(taxLandField.getText()));
            month.setPaidTaxLand(Util.stringToBool(isPaidTaxLandField.getText()));
            
            month.setCountWater(Double.parseDouble(countWaterField.getText()));
            month.setTariffWater(Double.parseDouble(tariffWaterField.getText()));
            month.setCountElectricity(Double.parseDouble(countElectricityField.getText()));
            month.setTariffElectricity(Double.parseDouble(tariffElectricityField.getText()));
            month.setCountHeading(Double.parseDouble(countHeadingField.getText()));
            month.setTariffHeading(Double.parseDouble(tariffHeadingField.getText()));
            month.setCountGarbage(Double.parseDouble(countGarbageField.getText()));
            month.setTariffGarbage(Double.parseDouble(tariffGarbageField.getText()));
            month.setCostInternet(Double.parseDouble(costInternetField.getText()));
            month.setCostTelephone(Double.parseDouble(costTelephoneField.getText()));
            month.setPaidCommunal(Util.stringToBool(isPaidCommunalField.getText()));

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
    
    @FXML
    private Label sumCommunalLabel;

    @FXML
    private Label sumRentLabel;

    @FXML
    private Label sumRentWithFineLabel;
    
    @FXML
    void payAcc(ActionEvent event) {
        Button button = (Button) event.getSource();
        String id = button.getId();
        
        Label label;
        switch(id) {
            case "communalBtn":
                label = isPaidCommunalField;
                break;
            case "taxLandBtn":
                label = isPaidTaxLandField;
                break;
            case "rentBtn":
                label = isPaidRentField;
                break;
            default:
                label = null;
        }
        boolean isPaid = !Util.stringToBool(label.getText());
        label.setText(Util.boolToString(isPaid));
        
        setTextAndColor(isPaid, label, button);
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
    private Label costWaterLabel;

    @FXML
    private Label costElectricityLabel;

    @FXML
    private Label costHeadingLabel;

    @FXML
    private Label costGarbageLabel;
    
    private void initCalc() {
        displaySum();
        addCalcListeners();
    }
    
    private void displaySum() {
        setCost(countWaterField, tariffWaterField, costWaterLabel);
        setCost(countElectricityField, tariffElectricityField, costElectricityLabel);
        setCost(countHeadingField, tariffHeadingField, costHeadingLabel);
        setCost(countGarbageField, tariffGarbageField, costGarbageLabel);
        printSum("communal");
        setCost(costField, indexCostField, sumRentLabel);
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
        if (sumFor.equals("communal"))
            calcSum(sumCommunalLabel, costWaterLabel, costElectricityLabel, costHeadingLabel,
                    costGarbageLabel, costInternetField, costTelephoneField);
        else if(sumFor.equals("rentFine"))
            calcSum(sumRentWithFineLabel, sumRentLabel, fineField);
    }
   
    public void addCalcListeners() {
        
        countWaterField.textProperty().addListener((observable, oldValue, newValue) -> {
            setCost(countWaterField, tariffWaterField, costWaterLabel);
            printSum("communal");
            setColorLabels(countWaterField);
        });
        tariffWaterField.textProperty().addListener((observable, oldValue, newValue) -> {
            setCost(countWaterField, tariffWaterField, costWaterLabel);
            printSum("communal");
            setColorLabels(tariffWaterField);
        });
        
        countElectricityField.textProperty().addListener((observable, oldValue, newValue) -> {
            setCost(countElectricityField, tariffElectricityField, costElectricityLabel);
            printSum("communal");
            setColorLabels(countElectricityField);
        });
        tariffElectricityField.textProperty().addListener((observable, oldValue, newValue) -> {
            setCost(countElectricityField, tariffElectricityField, costElectricityLabel);
            printSum("communal");
            setColorLabels(tariffElectricityField);
        });
        
        countHeadingField.textProperty().addListener((observable, oldValue, newValue) -> {
           setCost(countHeadingField, tariffHeadingField, costHeadingLabel);
           printSum("communal");
           setColorLabels(countHeadingField);
        });
        tariffHeadingField.textProperty().addListener((observable, oldValue, newValue) -> {
            setCost(countHeadingField, tariffHeadingField, costHeadingLabel);
            printSum("communal");
            setColorLabels(tariffHeadingField);
        });
        
        countGarbageField.textProperty().addListener((observable, oldValue, newValue) -> {
           setCost(countGarbageField, tariffGarbageField, costGarbageLabel);
           printSum("communal");
           setColorLabels(countGarbageField);
        });
        tariffGarbageField.textProperty().addListener((observable, oldValue, newValue) -> {
            setCost(countGarbageField, tariffGarbageField, costGarbageLabel);
            printSum("communal");
            setColorLabels(tariffGarbageField);
        });
        costInternetField.textProperty().addListener((observable, oldValue, newValue) -> {
            printSum("communal");
            setColorLabels(costInternetField);
        });
        costTelephoneField.textProperty().addListener((observable, oldValue, newValue) -> {
            printSum("communal");
            setColorLabels(costTelephoneField);
        });
        
        costField.textProperty().addListener((observable, oldValue, newValue) -> {
            setCost(costField, indexCostField, sumRentLabel);
            printSum("rentFine");
            setColorLabels(costField);
        });
        indexCostField.textProperty().addListener((observable, oldValue, newValue) -> {
            setCost(costField, indexCostField, sumRentLabel);
            printSum("rentFine");
            setColorLabels(indexCostField);
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
