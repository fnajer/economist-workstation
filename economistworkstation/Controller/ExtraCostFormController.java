/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package economistworkstation.Controller;

import economistworkstation.Entity.ExtraCost;
import economistworkstation.Entity.Period;
import economistworkstation.Util.Util;
import static economistworkstation.Util.Util.setText;
import static economistworkstation.Util.Util.parseField;
import static economistworkstation.Util.Util.isExist;
import static economistworkstation.Util.Util.isFilled;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


/**
 *
 * @author fnajer
 */
public class ExtraCostFormController {
    @FXML
    private TextField extraCostRentField;
    @FXML
    private TextField extraCostFineField;
    @FXML
    private TextField extraCostTaxLandField;
    @FXML
    private TextField extraCostServicesField;
    @FXML
    private TextField extraCostEquipmentField;

    private Stage dialogStage;
    private ExtraCost extraCost;
    private Period period;
    private boolean okClicked = false;
    
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    
    public void setExtraCost(ExtraCost extraCost, Period period) {
        this.extraCost = extraCost;
        this.period = period;

        Util.setCalledClass(this);
                
        if (isExist(extraCost)) {
            setText(extraCostRentField, extraCost.getCostRent());
            setText(extraCostFineField, extraCost.getCostFine());
            setText(extraCostTaxLandField, extraCost.getCostTaxLand());
            setText(extraCostServicesField, extraCost.getCostServices());
            setText(extraCostEquipmentField, extraCost.getCostEquipment());
        }
    }
    
    public boolean isOkClicked() {
        return okClicked;
    }
    
    @FXML
    private void handleOk() {
        if (isInputValid()) {
            if (isFilled(extraCostRentField, extraCostFineField, extraCostTaxLandField,
                    extraCostServicesField, extraCostEquipmentField)) {
                if (extraCost == null)
                    extraCost = new ExtraCost();
                extraCost.setCostRent(parseField(extraCostRentField));
                extraCost.setCostFine(parseField(extraCostFineField));
                extraCost.setCostTaxLand(parseField(extraCostTaxLandField));
                extraCost.setCostServices(parseField(extraCostServicesField));
                extraCost.setCostEquipment(parseField(extraCostEquipmentField));
                period.setExtraCost(extraCost);
            } else if (isExist(extraCost)) {
                ExtraCost extraCostForDelete = new ExtraCost();
                extraCostForDelete.setCostRent(-1.0);
                period.setExtraCost(extraCostForDelete);
            }

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
//        if (subjectField.getText() == null || subjectField.getText().length() == 0) {
//            errorMessage += "Введите субьъекта аренды! Например, физ. лицо или ЧП.\n";
//        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Показываем сообщение об ошибке.
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Некорректные данные");
            alert.setHeaderText("Заполните поля корректно");
            alert.setContentText(errorMessage);
            
            alert.showAndWait();
            
            return false;
    }}
} 

