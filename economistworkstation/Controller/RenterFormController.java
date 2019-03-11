/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package economistworkstation.Controller;

import economistworkstation.Entity.Renter;
import economistworkstation.Model.RenterModel;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


/**
 *
 * @author fnajer
 */
public class RenterFormController implements Initializable {
    public RenterFormController() {
        id = RenterController.getIdCurrentRenter();
        typeForm = RenterController.getTypeForm();
    }
    
    private String typeForm;
    private int id;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btn.setText(typeForm);
    }
    
    @FXML
    private Button btn;
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
    public void handleBtn(ActionEvent event) {
        
        Renter renter = createRenter();
        
        if (typeForm == "Обновить") {
            RenterModel.updateRenter(id, renter);
        } else if (typeForm == "Добавить") {
            RenterModel.addRenter(renter);
        }

        Stage stage = (Stage) btn.getScene().getWindow();
        RenterController.getRenterController().closeForm(stage);
    }
    
    public Renter createRenter() {
        Renter renter = new Renter(name.getText(), surname.getText(), patronymic.getText(), address.getText(), birthday.getText(), person.getText());
        return renter;
    }
}
