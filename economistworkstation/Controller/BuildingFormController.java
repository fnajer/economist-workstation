/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package economistworkstation.Controller;

import economistworkstation.Entity.Building;
import economistworkstation.Model.BuildingModel;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author fnajer
 */
public class BuildingFormController implements Initializable {  
    
    public static BuildingController parentWindow; 
    
    public void setWindow(BuildingController parent) {
        parentWindow = parent;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
    
    @FXML
    public void displayPage() throws Exception { 
        Parent container = FXMLLoader.load(getClass().getResource("/economistworkstation/View/Building/BuildingForm.fxml"));
        
        Stage stage = new Stage();
        stage.setTitle("Внесение здания в реестр");
        stage.setScene(new Scene(container));
        stage.show();
    }
    
    @FXML
    private Button addBtn;
    @FXML
    private TextField type;
    @FXML
    private TextField square;
    @FXML
    private TextField cost_balance;
    @FXML
    private TextField cost_residue;
    
    @FXML
    public void addBuilding(ActionEvent event) {
        
        Building building = createBuilding();
        BuildingModel.addBuilding(building);
        Stage stage = (Stage) addBtn.getScene().getWindow();
        
        stage.close();
        parentWindow.showListBuildings();
    }
    
    public Building createBuilding() {
        Building building = new Building(type.getText(), Double.parseDouble(square.getText()), Double.parseDouble(cost_balance.getText()), Double.parseDouble(cost_residue.getText()));
        return building;
    }
    
}
