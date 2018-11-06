/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package economistworkstation.Controller;

import economistworkstation.Entity.Renter;
import economistworkstation.Model.RenterModel;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

/**
 * FXML Controller class
 *
 * @author fnajer
 */
public class RenterProfileController implements Initializable {

    public static int id;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Renter renter = RenterModel.getRenter(id);
        name.setText(renter.name);
        surname.setText(renter.surname);
        patronymic.setText(renter.patronymic);
        address.setText(renter.address);
        birthday.setText(renter.birthday);
        person.setText(renter.person);
    }    
    
    @FXML
    private Button updateBtn;
    @FXML
    private Label name;
    @FXML
    private Label surname;
    @FXML
    private Label patronymic;
    @FXML
    private Label address;
    @FXML
    private Label birthday;
    @FXML
    private Label person;
    
    @FXML
    public void displayPage(BorderPane root, int id) throws Exception {
        this.id = id;
        Parent container = FXMLLoader.load(getClass().getResource("/economistworkstation/View/Renter/RenterProfile.fxml"));
        
        root.setCenter(container);
    }
    
}
