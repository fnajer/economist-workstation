/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package economistworkstation.Controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

import economistworkstation.Database;
import economistworkstation.Model.RenterModel;
import java.util.ArrayList;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.scene.control.ListView;
import javafx.scene.control.ComboBox;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
/**
 *
 * @author fnajer
 */
public class ContractController implements Initializable {
    
    public static Database db;
    //public static ArrayList renters;
    @FXML
    private ComboBox langsListView;
    @FXML
    private Button btn;
    /**
     * Initializes the controller class.
     */
    
//    ContractController(Database dbase) {
//        db = dbase;
//        renters = RenterModel.getRenters(db.stmt);
//    }
//    public void setDatabase(Database database) {
//        db = database;
//    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        ArrayList renters = RenterModel.getRenters(db.stmt);
        ObservableList<String> langs = FXCollections.observableArrayList(renters);
        langsListView.setItems(langs);
    }   
    
    @FXML
    private void click(ActionEvent event) {
        btn.setText("You've clicked!");
    }
    /**
     *
     * @param root
     * @param db
     * @throws java.lang.Exception
     */
   
    public void displayPage(BorderPane root, Database db) throws Exception {
        
        //Parent container = init();
        Parent container = FXMLLoader.load(getClass().getResource("/economistworkstation/View/Contract/Contract.fxml"));
 
        root.setCenter(container);
    }
}
