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
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author fnajer
 */
public class MonthFormController {

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
        
        initCalc();
    }
    
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
    
   
    }
}
