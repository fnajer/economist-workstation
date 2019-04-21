/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package economistworkstation.Controller;

import economistworkstation.Entity.Building;
import economistworkstation.Model.BuildingModel;
import economistworkstation.Model.RenterModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import economistworkstation.Entity.Contract;
import economistworkstation.Entity.Renter;
import java.time.LocalDate;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;

/**
 * FXML Controller class
 *
 * @author fnajer
 */
public class ContractFormController {

    @FXML
    private ComboBox<Renter> rentersListField;
    @FXML
    private ComboBox<Building> buildingsListField;
    @FXML
    private DatePicker dateStartField;
    @FXML
    private DatePicker dateEndField;
    
    private Stage dialogStage;
    private Contract contract;
    private boolean okClicked = false;
    
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    
    public void setContract(Contract contract, Renter renter, Building building) {
        this.contract = contract;
        
        ObservableList renters = RenterModel.getRenters();
        ObservableList<Renter> rentersCollection = FXCollections.observableArrayList(renters);
        rentersListField.setItems(rentersCollection);
        rentersListField.setValue(renter);

        ObservableList buildings = BuildingModel.getBuildings();
        ObservableList<Building> buildingsCollection = FXCollections.observableArrayList(buildings);
        buildingsListField.setItems(buildingsCollection);
        buildingsListField.setValue(building);

        if (contract.getDateStart() != null) {
            dateStartField.setValue(LocalDate.parse(contract.getDateStart()));
            dateEndField.setValue(LocalDate.parse(contract.getDateEnd()));
        } else {
            dateStartField.setValue(LocalDate.now());
            dateEndField.setValue(LocalDate.now().plusMonths(6));
        }  
    }
    
    public boolean isOkClicked() {
        return okClicked;
    }
    
    @FXML
    private void handleOk() {
        if (isInputValid()) {
            Renter renter = rentersListField.getSelectionModel().getSelectedItem();
            Building building = buildingsListField.getSelectionModel().getSelectedItem();
           
            contract.setIdRenter(renter.getId());
            contract.setIdBuilding(building.getId());

            contract.setDateStart(dateStartField.getValue().toString());
            contract.setDateEnd(dateEndField.getValue().toString());
            
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
}
