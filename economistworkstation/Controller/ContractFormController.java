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
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.SingleSelectionModel;

/**
 * FXML Controller class
 *
 * @author fnajer
 */
public class ContractFormController implements Initializable {
    public ContractFormController() {
        id = ContractController.getIdCurrentContract();
        typeForm = ContractController.getTypeForm();
    }
    
    private String typeForm;
    private int id;
    
    private int id_renter;
    private int id_building;
    
//    @Override
//    public void initialize(URL url, ResourceBundle rb) {
//        btn.setText(typeForm);
//        
//        if (typeForm == "Обновить") {
//            Contract renter = ContractModel.getContract(id);
//            name.setText(renter.name);
//            surname.setText(renter.surname);
//            patronymic.setText(renter.patronymic);
//            address.setText(renter.address);
//            birthday.setText(renter.birthday);
//            person.setText(renter.person);
//        }
//    }
    
    @FXML
    private Button btn;
    @FXML
    private ComboBox langsListView;
    @FXML
    private ComboBox buildingsListView;
    @FXML
    private DatePicker date_start;
    @FXML
    private DatePicker date_end;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btn.setText(typeForm);
        
        ArrayList renters = RenterModel.getRenters();
        ObservableList<Renter> langs = FXCollections.observableArrayList(renters);
        langsListView.setItems(langs);
        
        ArrayList buildings = BuildingModel.getBuildings();
        ObservableList<Building> langsBuildings = FXCollections.observableArrayList(buildings);
        buildingsListView.setItems(langsBuildings);
        
        SingleSelectionModel<Renter> langsSelectionModel = langsListView.getSelectionModel();
        // устанавливаем слушатель для отслеживания изменений
        langsSelectionModel.selectedItemProperty().addListener(new ChangeListener<Renter>(){  
            public void changed(ObservableValue<? extends Renter> changed, Renter oldValue, Renter newValue){  
                id_renter = newValue.id;
            }
        });
        
        SingleSelectionModel<Building> buildingsSelectionModel = buildingsListView.getSelectionModel();
        buildingsSelectionModel.selectedItemProperty().addListener(new ChangeListener<Building>(){
            public void changed(ObservableValue<? extends Building> changed, Building oldValue, Building newValue){
                id_building = newValue.id;
            }
        });
        
        date_start.setValue(LocalDate.now());
        date_end.setValue(LocalDate.now().plusMonths(6));
    }    
    
    @FXML
    public void handleBtn(ActionEvent event) {
        
        Contract contract = createContract();
        
        //int diffOfDates = date_end.getValue() - date_start.getValue();
        long diffOfDates = ChronoUnit.MONTHS.between(date_start.getValue(), date_end.getValue());
          
        if (typeForm == "Обновить") {
            ContractModel.updateContract(id, contract);
        } else if (typeForm == "Добавить") {
            ContractModel.addContract(contract, diffOfDates, date_start.getValue());
        }

        Stage stage = (Stage) btn.getScene().getWindow();
        ContractController.getContractController().closeForm(stage);
    }
    
    public Contract createContract() {
        Contract contract = new Contract(date_start.getValue().toString(), 
                date_end.getValue().toString(), id_renter, id_building);
        return contract;
    }
}
