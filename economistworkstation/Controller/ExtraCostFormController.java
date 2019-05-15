/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package economistworkstation.Controller;

import economistworkstation.ContractData;
import economistworkstation.Entity.ExtraCost;
import economistworkstation.Entity.Period;
import economistworkstation.Util.Util;
import static economistworkstation.Util.Util.setText;
import static economistworkstation.Util.Util.parseField;
import static economistworkstation.Util.Util.isExist;
import static economistworkstation.Entity.Field.isFilled;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import java.net.URL;
import java.util.ResourceBundle;

/**
 *
 * @author fnajer
 */
public class ExtraCostFormController extends BaseFormController {
    @Override
    public void initialize(URL location, ResourceBundle bundle) {}
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

    private ContractData data;
    
    @Override
    protected void setData(ContractData data) {
        ExtraCost extraCost = data.getPeriod().getExtraCost();

        Util.setCalledClass(this);
                
        if (isExist(extraCost)) {
            setText(extraCostRentField, extraCost.getCostRent());
            setText(extraCostFineField, extraCost.getCostFine());
            setText(extraCostTaxLandField, extraCost.getCostTaxLand());
            setText(extraCostServicesField, extraCost.getCostServices());
            setText(extraCostEquipmentField, extraCost.getCostEquipment());
        }
    }
    
    @FXML
    @Override
    protected void handleOk() {
        Period period = data.getPeriod();
        ExtraCost extraCost = period.getExtraCost();
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

