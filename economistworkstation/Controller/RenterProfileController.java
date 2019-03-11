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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;


/**
 * FXML Controller class
 *
 * @author fnajer
 */
public class RenterProfileController implements Initializable {

    public RenterProfileController() {
        id = RenterController.getIdCurrentRenter();
    }
    
    private int id;
    
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
    public void runEditForm(ActionEvent event) throws IOException {
        RenterController.getRenterController()
                .showRenterForm("Обновить", "Редактирование");
    } 
}
