/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package economistworkstation.Controller;

import economistworkstation.ContractData;
import economistworkstation.Entity.Building;
import economistworkstation.Model.BuildingModel;
import economistworkstation.Model.RenterModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import economistworkstation.Entity.Contract;
import economistworkstation.Entity.Renter;
import static economistworkstation.Util.Util.isExist;
import java.time.LocalDate;
import javafx.scene.control.DatePicker;

/**
 * FXML Controller class
 *
 * @author fnajer
 */
public class ContractFormController extends BaseFormController {
    @FXML
    private ComboBox<Renter> rentersListField;
    @FXML
    private ComboBox<Building> buildingsListField;
    @FXML
    private DatePicker dateStartField;
    @FXML
    private DatePicker dateEndField;
    
    private Contract contract;
    
    @Override
    public void setData(ContractData data) {
        this.contract = data.getContract();
        
        ObservableList renters = RenterModel.getRenters();
        ObservableList<Renter> rentersCollection = FXCollections.observableArrayList(renters);
        rentersListField.setItems(rentersCollection);
        rentersListField.setValue(data.getRenter());

        ObservableList buildings = BuildingModel.getBuildings();
        ObservableList<Building> buildingsCollection = FXCollections.observableArrayList(buildings);
        buildingsListField.setItems(buildingsCollection);
        buildingsListField.setValue(data.getBuilding());

        if (contract.getDateStart() != null) {
            dateStartField.setValue(LocalDate.parse(contract.getDateStart()));
            dateEndField.setValue(LocalDate.parse(contract.getDateEnd()));
        } else {
            dateStartField.setValue(LocalDate.now());
            dateEndField.setValue(LocalDate.now().plusMonths(6));
        }  
    }
    
    @FXML
    @Override
    protected void handleOk() {
        if (isInputValid()) {
            Renter renter = rentersListField.getSelectionModel().getSelectedItem();
            Building building = buildingsListField.getSelectionModel().getSelectedItem();
           
            contract.setIdRenter(renter.getId());
            contract.setIdBuilding(building.getId());

            contract.setDateStart(dateStartField.getValue().toString());
            contract.setDateEnd(dateEndField.getValue().toString());
            
            closeForm();
        }
    }
    
    @Override
    protected boolean isInputValid() {
        String errorMessage = "";
        
        Renter renter = rentersListField.getSelectionModel().getSelectedItem();
        Building building = buildingsListField.getSelectionModel().getSelectedItem();
        if (!isExist(renter)) {
            errorMessage += "Выберите арендатора!\n"; 
        }
        if (!isExist(building)) {
            errorMessage += "Выберите здание!\n"; 
        }
        if (fieldIsEmpty(dateStartField)) {
            errorMessage += "Отсутствует дата заключения контракта!\n"; 
        }
        if (fieldIsEmpty(dateEndField)) {
            errorMessage += "Отсутствует дата истечения срока контракта!\n"; 
        }
        
        return errorNotExist(errorMessage);
    }
}
