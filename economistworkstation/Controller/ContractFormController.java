/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package economistworkstation.Controller;

import static economistworkstation.Controller.RenterFormController.parentWindow;
import economistworkstation.Model.BuildingModel;
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
    
    public static Object parentWindow;
    
    public void setWindow(Object parent) {
        parentWindow = parent;
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ArrayList renters = RenterModel.getRenters();
        ObservableList<String> langs = FXCollections.observableArrayList(renters);
        langsListView.setItems(langs);
        
        ArrayList buildings = BuildingModel.getBuildings();
        ObservableList<String> langsBuildings = FXCollections.observableArrayList(buildings);
        buildingsListView.setItems(langsBuildings);
    }    
    
    @FXML
    public void displayPage(String typeForm) throws Exception {
        Parent container = FXMLLoader.load(getClass().getResource("/economistworkstation/View/Contract/ContractForm.fxml"));
        
        Stage stage = new Stage();
        stage.setTitle("Создание договора");
        stage.setScene(new Scene(container));
        stage.show();
    }
}
