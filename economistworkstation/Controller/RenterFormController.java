/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package economistworkstation.Controller;

import economistworkstation.Database;
import economistworkstation.Entity.Renter;
import economistworkstation.Model.RenterModel;
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
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 *
 * @author fnajer
 */
public class RenterFormController implements Initializable {
    public static RenterController parentWindow; 
// non-static dont work, becouse bind action-handlers happen with first values. And constructor - causes error
// in case with db - its not a static fault , its - any creational constructor destroy executing program    
    public void setWindow(RenterController parent) {
        parentWindow = parent;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
    
    @FXML
    public void displayPage() throws Exception { 
        Parent container = FXMLLoader.load(getClass().getResource("/economistworkstation/View/Renter/RenterForm.fxml"));
        
        Stage stage = new Stage();
        stage.setTitle("Создание арендатора");
        stage.setScene(new Scene(container));
        stage.show();
    }
    
    @FXML
    private Button addBtn;
    @FXML
    private TextField name;
    @FXML
    private TextField surname;
    @FXML
    private TextField patronymic;
    @FXML
    private TextField address;
    @FXML
    private TextField birthday;
    @FXML
    private TextField person;
    
    @FXML
    public void addRenter(ActionEvent event) {
        
        Renter renter = createRenter();
        RenterModel.addRenter(renter);
        Stage stage = (Stage) addBtn.getScene().getWindow();
        
        stage.close();
        parentWindow.showListRenters();
    }
    
    public Renter createRenter() {
        Renter renter = new Renter(name.getText(), surname.getText(), patronymic.getText(), address.getText(), birthday.getText(), person.getText());
        return renter;
    }
}
