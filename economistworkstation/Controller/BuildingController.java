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
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author fnajer
 */
public class BuildingController implements Initializable {
    
    public BuildingController() {
        root = MainPageController.getRootContainer();
        buildingController = this;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        showListBuildings();
    }
    
    private static int currentId;
    private static String typeForm;
    private static BuildingController buildingController;
    private BorderPane root;
    
    public static int getIdCurrentBuilding() {
        return currentId;
    }
    public static String getTypeForm() {
        return typeForm;
    }
    public static BuildingController getBuildingController() {
        return buildingController;
    }
    
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
            Button infoBtn = new Button("Подробно");
            
            delBtn.setOnAction((ActionEvent event) -> {
                delBuilding(building.id);
            });
            
            infoBtn.setOnAction((ActionEvent event) -> {
                try {
                    currentId = building.id;
                    openProfile();
                } catch (IOException ex) {
                    Logger.getLogger(RenterController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            
            FlowPane buildingContainer = new FlowPane(10, 10, lblName, delBtn, infoBtn);
            listBuildings.add(buildingContainer);
        }
    }
    
    public void delBuilding(int id) {
        BuildingModel.deleteBuilding(id);
        showListBuildings();
    }
    
    public void openProfile() throws IOException {
        Parent container = FXMLLoader.load(getClass().getResource("/economistworkstation/View/Building/BuildingProfile.fxml"));

        root.setRight(container);
    }
    
    @FXML
    public void runAddForm(ActionEvent event) throws IOException {
        showBuildingForm("Добавить", "Добавить новое здание");
    }
    
    public void showBuildingForm(String type, String title) throws IOException {
        typeForm = type;
        Parent container = FXMLLoader.load(getClass().getResource("/economistworkstation/View/Building/BuildingForm.fxml"));
        
        Stage stage = new Stage();
        stage.setTitle(title);
        stage.setScene(new Scene(container));
        stage.show();
    }
    
    public void closeForm(Stage stage) {
        stage.close();
        root.setRight(null);
        
        showListBuildings();
    }
}
