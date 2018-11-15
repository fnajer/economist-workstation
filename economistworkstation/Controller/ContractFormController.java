/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package economistworkstation.Controller;

import economistworkstation.Entity.Building;
import economistworkstation.Model.BuildingModel;
import economistworkstation.Model.ContractModel;
import economistworkstation.Model.RenterModel;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import economistworkstation.Entity.Contract;
import economistworkstation.Entity.Renter;
import economistworkstation.Model.ContractModel;
import java.time.LocalDate;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.SingleSelectionModel;

/**
 * FXML Controller class
 *
 * @author fnajer
 */
public class ContractFormController implements Initializable {
    @FXML
    private ComboBox langsListView;
    @FXML
    private ComboBox buildingsListView;
    
    public static ContractController parentWindow;
    
    public void setWindow(ContractController parent) {
        parentWindow = parent;
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
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
    public void displayPage(String typeForm) throws Exception {
        Parent container = FXMLLoader.load(getClass().getResource("/economistworkstation/View/Contract/ContractForm.fxml"));
        
        Stage stage = new Stage();
        stage.setTitle("Создание договора");
        stage.setScene(new Scene(container));
        stage.show();
    }

    @FXML
    private Button addBtn;
    @FXML
    private DatePicker date_start;
    @FXML
    private DatePicker date_end;
    
    
    private int id_renter;
    
    private int id_building;
    
    @FXML
    public void addContract(ActionEvent event) {
        
        Contract contract = createContract();
        ContractModel.addContract(contract);
        Stage stage = (Stage) addBtn.getScene().getWindow();

        stage.close();
        parentWindow.showListContracts();
    }
    
    public Contract createContract() {
        Contract contract = new Contract(date_start.getValue().toString(), 
                date_end.getValue().toString(), id_renter, id_building);
        return contract;
    }
}
