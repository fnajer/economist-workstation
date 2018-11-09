/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package economistworkstation.Controller;

import static economistworkstation.Controller.RenterController.root;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

import economistworkstation.Database;
import economistworkstation.Entity.Contract;
import economistworkstation.Entity.Renter;
import economistworkstation.Model.RenterModel;
import economistworkstation.Model.ContractModel;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.event.EventHandler;
/**
 *
 * @author fnajer
 */
public class ContractController implements Initializable {
    @FXML
    private VBox containerContracts;
     
    public void delContract(int id) {
        //String name = renterName.getText();
        ContractModel.deleteContract(id);
        showListContracts();
    }
    
    @FXML
    public void showListContracts() {
        ArrayList<Contract> contracts = ContractModel.getContracts();

        ObservableList listContracts = containerContracts.getChildren();  
        listContracts.clear();
        
        for(Contract contract : contracts){
            Label lblName = new Label(Integer.toString(contract.id));
            Button delBtn = new Button("X");
            Button infoBtn = new Button("Подробно");
            
            delBtn.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    delContract(contract.id);
                }
            });
            
            infoBtn.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    //openProfile(contract.id);
                }
            });
            
            FlowPane root = new FlowPane(10, 10, lblName, delBtn, infoBtn);
            listContracts.add(root);
        }
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {    
        showListContracts();
    }   
    
    @FXML
    public void showContractForm(ActionEvent event) throws IOException {
        ContractFormController contractFormController = new ContractFormController();
        contractFormController.setWindow(this);
        try {
            contractFormController.displayPage("Добавить");
        } catch (Exception ex) {
            Logger.getLogger(RenterController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     *
     * @param root
     * @throws java.lang.Exception
     */
   
    public void displayPage(BorderPane root) throws Exception {
        Parent container = FXMLLoader.load(getClass().getResource("/economistworkstation/View/Contract/Contract.fxml"));
 
        root.setCenter(container);
    }
}
