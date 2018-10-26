/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package economistworkstation.Controller;

import economistworkstation.Database;
import economistworkstation.Model.RenterModel;
import java.sql.Statement;
import java.util.ArrayList;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import economistworkstation.Model.RenterModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.scene.Parent;
/**
 *
 * @author fnajer
 */

class Renter {
    int id;
    String name;
    String surname;
    String patronymic;
    String address;
    String birthday;
    String person;
}

public class RenterController implements Initializable {
    public static Database db;
    
    public void setDatabase(Database database) {
        db = database;
    }
    
    @FXML
    private VBox containerRenters;
    @FXML
    private TextField renterName;
        
    @FXML
    public void updateListRenters(Statement stmt, VBox containerRenters) {
        ArrayList renters = RenterModel.getRenters(stmt);

        ObservableList listRenters = containerRenters.getChildren();
        listRenters.clear();
                
        for(Object renterName : renters){
            Label lblRent = new Label(renterName.toString());
            listRenters.add(lblRent);
        }
    }
    
    @FXML
    public void addRenter(ActionEvent event) {
        String name = renterName.getText();
        RenterModel.addRenter(db.stmt, name);
        updateListRenters(db.stmt, containerRenters);
    }
    
    @FXML
    public void delRenter(ActionEvent event) {
        String name = renterName.getText();
        RenterModel.deleteRenter(db.stmt, name);
        updateListRenters(db.stmt, containerRenters);
    }
    
    @FXML
    public void showRenters(ActionEvent event) {
        updateListRenters(db.stmt, containerRenters);
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {  
        
//        ArrayList renters = RenterModel.getRenters(db.stmt);
//        ObservableList<String> langs = FXCollections.observableArrayList(renters);
//        langsListView.setItems(langs);
    }
    
    public void displayPage(BorderPane root) throws Exception {
        Parent container = FXMLLoader.load(getClass().getResource("/economistworkstation/View/Renter/Renter.fxml"));
        
        root.setCenter(container);
    }
}
