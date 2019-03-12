/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package economistworkstation.Controller;

import economistworkstation.Entity.Renter;
import java.util.ArrayList;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import economistworkstation.Model.RenterModel;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author fnajer
 */

public class RenterController implements Initializable {
   
    public RenterController() {
        root = MainPageController.getRootContainer();
        renterController = this;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        showListRenters();
    }
    
    private static int currentId;
    private static String typeForm;
    private static RenterController renterController;
    private BorderPane root;
    
    public static int getIdCurrentRenter() {
        return currentId;
    }
    public static String getTypeForm() {
        return typeForm;
    }
    public static RenterController getRenterController() {
        return renterController;
    }
    
    
    @FXML
    private Button showBtn;
    @FXML
    private VBox containerRenters;

    @FXML
    public void showListRenters() {
        ArrayList<Renter> renters = RenterModel.getRenters();

        ObservableList listRenters = containerRenters.getChildren();  
        listRenters.clear();

        for(Renter renter : renters){
            Label lblName = new Label(renter.name);
            Button delBtn = new Button("X");
            Button infoBtn = new Button("Подробно");
            
            delBtn.setOnAction((ActionEvent event) -> {
                delRenter(renter.id);
            });
            
            infoBtn.setOnAction((ActionEvent event) -> {
                try {
                    currentId = renter.id;
                    openProfile();
                } catch (IOException ex) {
                    Logger.getLogger(RenterController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            
            FlowPane renterContainer = new FlowPane(10, 10, lblName, delBtn, infoBtn);
            listRenters.add(renterContainer);
        }
    }
    
    public void delRenter(int id) {
        RenterModel.deleteRenter(id);
        showListRenters();
    }
    
    public void openProfile() throws IOException {
        Parent container = FXMLLoader.load(getClass().getResource("/economistworkstation/View/Renter/RenterProfile.fxml"));

        root.setRight(container);
    }
    
    @FXML
    public void runAddForm(ActionEvent event) throws IOException {
        showRenterForm("Добавить", "Создание");
    }
    

    public void showRenterForm(String type, String title) throws IOException {
        typeForm = type;
        Parent container = FXMLLoader.load(getClass().getResource("/economistworkstation/View/Renter/RenterForm.fxml"));
        
        Stage stage = new Stage();
        stage.setTitle(String.format("%s арендатора", title));
        stage.setScene(new Scene(container));
        stage.show();
    }
    
    public void closeForm(Stage stage) {
        stage.close();
        root.setRight(null);
        
        showListRenters();
    }
}
