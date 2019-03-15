/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package economistworkstation.Controller;

import economistworkstation.Entity.Building;
import economistworkstation.Model.BuildingModel;
import economistworkstation.Model.RenterModel;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import economistworkstation.Entity.Contract;
import economistworkstation.Entity.Renter;
import economistworkstation.Model.ContractModel;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.SingleSelectionModel;

/**
 * FXML Controller class
 *
 * @author fnajer
 */
public class ContractFormController implements Initializable {
//    public ContractFormController() {
//        id = ContractController.getIdCurrentContract();
//        typeForm = ContractController.getTypeForm();
//    }
//    
//    private String typeForm;
//    private int id;
//    
//    private int id_renter;
//    private int id_building;

    @FXML
    private ComboBox<Renter> rentersListField;
    @FXML
    private ComboBox<Building> buildingsListField;
    @FXML
    private DatePicker dateStartField;
    @FXML
    private DatePicker dateEndField;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
//        btn.setText(typeForm);
//        
//        ArrayList renters = RenterModel.getRenters();
//        ObservableList<Renter> langs = FXCollections.observableArrayList(renters);
//        langsListView.setItems(langs);
//        
//        ArrayList buildings = BuildingModel.getBuildings();
//        ObservableList<Building> langsBuildings = FXCollections.observableArrayList(buildings);
//        buildingsListView.setItems(langsBuildings);
//        
//        SingleSelectionModel<Renter> langsSelectionModel = langsListView.getSelectionModel();
//        // устанавливаем слушатель для отслеживания изменений
//        langsSelectionModel.selectedItemProperty().addListener(new ChangeListener<Renter>(){  
//            public void changed(ObservableValue<? extends Renter> changed, Renter oldValue, Renter newValue){  
//                id_renter = newValue.id;
//            }
//        });
//        
//        SingleSelectionModel<Building> buildingsSelectionModel = buildingsListView.getSelectionModel();
//        buildingsSelectionModel.selectedItemProperty().addListener(new ChangeListener<Building>(){
//            public void changed(ObservableValue<? extends Building> changed, Building oldValue, Building newValue){
//                id_building = newValue.id;
//            }
//        });
//        
//        date_start.setValue(LocalDate.now());
//        date_end.setValue(LocalDate.now().plusMonths(6));
    }    
    
//    @FXML
//    public void handleBtn(ActionEvent event) {
//        
//        Contract contract = createContract();
//        
//        //int diffOfDates = date_end.getValue() - date_start.getValue();
//        long diffOfDates = ChronoUnit.MONTHS.between(date_start.getValue(), date_end.getValue());
//          
//        if (typeForm == "Обновить") {
//            ContractModel.updateContract(id, contract);
//        } else if (typeForm == "Добавить") {
//            ContractModel.addContract(contract, diffOfDates, date_start.getValue());
//        }
//
//        Stage stage = (Stage) btn.getScene().getWindow();
//        ContractController.getContractController().closeForm(stage);
//    }
//    
//    public Contract createContract() {
//        Contract contract = new Contract(date_start.getValue().toString(), 
//                date_end.getValue().toString(), id_renter, id_building);
//        return contract;
//    }
    
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
        

//        rentersListField.setText(contract.getIdRenter());
//        buildingsListField.setText(contract.getIdBuilding());
//        dateStartField.setText(contract.getDateStart());
//        dateEndField.setText(contract.getDateEnd());

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
           // long diffOfDates = ChronoUnit.MONTHS.between(dateStart.getValue(), dateEnd.getValue());
           System.out.println("==");
            System.out.println(LocalDate.parse(dateStartField.getValue().toString()));
            System.out.println(dateStartField.getValue());
            System.out.println("==");
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
