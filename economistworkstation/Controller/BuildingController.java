/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package economistworkstation.Controller;

import economistworkstation.Entity.Building;
import economistworkstation.Model.BuildingModel;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author fnajer
 */
public class BuildingController implements Initializable {

    @FXML
    private VBox containerBuildings;

    @FXML
    public void showListBuildings() {
        ArrayList<Building> buildings = BuildingModel.getBuildings();

        ObservableList listBuildings = containerBuildings.getChildren();  
        listBuildings.clear();
        
        for(Building building : buildings){
            Label lblName = new Label(building.type);
            Button delBtn = new Button("X");
            
            delBtn.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    delBuilding(building.id);
                }
            });
            
            FlowPane root = new FlowPane(10, 10, lblName, delBtn);
            listBuildings.add(root);
        }
    }
    
    public void delBuilding(int id) {
        BuildingModel.deleteBuilding(id);
        showListBuildings();
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        showListBuildings();
    }
    
    @FXML
    public void displayPage(BorderPane root) throws Exception {
        Parent container = FXMLLoader.load(getClass().getResource("/economistworkstation/View/Building/Building.fxml"));
        
        root.setCenter(container);
    }  
    
}
