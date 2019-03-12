/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package economistworkstation.Controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

import economistworkstation.Entity.Contract;
import economistworkstation.Model.ContractModel;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
/**
 *
 * @author fnajer
 */
public class ContractController implements Initializable {
    
    public ContractController() {
        root = MainPageController.getRootContainer();
        contractController = this;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {    
        showListContracts();
    }

    private static int currentId;
    private static String typeForm;
    private static ContractController contractController;
    private BorderPane root;
    
    public static int getIdCurrentContract() {
        return currentId;
    }
    public static String getTypeForm() {
        return typeForm;
    }
    public static ContractController getContractController() {
        return contractController;
    }
    
    @FXML
    private VBox containerContracts;
       
    @FXML
    public void showListContracts() {
        ArrayList<Contract> contracts = ContractModel.getContracts();

        ObservableList listContracts = containerContracts.getChildren();  
        listContracts.clear();
        
        for(Contract contract : contracts){
            Label lblName = new Label(Integer.toString(contract.id));
            Button delBtn = new Button("X");
            Button infoBtn = new Button("Подробно");
            
            delBtn.setOnAction((ActionEvent event) -> {
                delContract(contract.id);
            });
            
            infoBtn.setOnAction((ActionEvent event) -> {
                try {
                    currentId = contract.id;
                    openProfile();
                } catch (IOException ex) {
                    Logger.getLogger(ContractController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            
            FlowPane contractContainer = new FlowPane(10, 10, lblName, delBtn, infoBtn);
            listContracts.add(contractContainer);
        }
    }
    
    public void delContract(int id) {
        ContractModel.deleteContract(id);
        showListContracts();
    }
    
    public void openProfile() throws IOException {
        Parent container = FXMLLoader.load(getClass().getResource("/economistworkstation/View/Contract/ContractProfile.fxml"));

        root.setRight(container);
    }
    
    @FXML
    public void runAddForm(ActionEvent event) throws IOException {
        showContractForm("Добавить", "Создание");
    }
    
    @FXML
    public void showContractForm(String type, String title) throws IOException {
        typeForm = type;
        Parent container = FXMLLoader.load(getClass().getResource("/economistworkstation/View/Contract/ContractForm.fxml"));
        
        Stage stage = new Stage();
        stage.setTitle(String.format("%s договора", title));
        stage.setScene(new Scene(container));
        stage.show();
    }
    
    public void closeForm(Stage stage) {
        stage.close();
        root.setRight(null);
        
        showListContracts();
    }
}
