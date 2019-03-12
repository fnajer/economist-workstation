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
public class BuildingProfileController implements Initializable {

    public BuildingProfileController() {
        id = BuildingController.getIdCurrentBuilding();
    }
    
    private int id;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Building building = BuildingModel.getBuilding(id);
        type.setText(building.type);
        square.setText(Double.toString(building.square));
        cost_balance.setText(Double.toString(building.cost_balance));
        cost_residue.setText(Double.toString(building.cost_residue));
    }   

    @FXML
    private Button updateBtn;
    @FXML
    private Label type;
    @FXML
    private Label square;
    @FXML
    private Label cost_balance;
    @FXML
    private Label cost_residue;
    
    @FXML
    public void runEditForm(ActionEvent event) throws IOException {
        BuildingController.getBuildingController()
                .showBuildingForm("Обновить", "Редактировать здание");
    } 
}
