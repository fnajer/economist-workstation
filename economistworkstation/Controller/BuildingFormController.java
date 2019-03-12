/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package economistworkstation.Controller;

import economistworkstation.Entity.Building;
import economistworkstation.Model.BuildingModel;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author fnajer
 */
public class BuildingFormController implements Initializable {  
    
    public BuildingFormController() {
        id = BuildingController.getIdCurrentBuilding();
        typeForm = BuildingController.getTypeForm();
    }
    
    private String typeForm;
    private int id;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btn.setText(typeForm);
        
        if (typeForm == "Обновить") {
            Building building = BuildingModel.getBuilding(id);
            type.setText(building.type);
            square.setText(Double.toString(building.square));
            cost_balance.setText(Double.toString(building.cost_balance));
            cost_residue.setText(Double.toString(building.cost_residue));
        }
    }
    
    @FXML
    private Button btn;
    @FXML
    private TextField type;
    @FXML
    private TextField square;
    @FXML
    private TextField cost_balance;
    @FXML
    private TextField cost_residue;
    
    @FXML
    public void handleBtn(ActionEvent event) {
        
        Building building = createBuilding();
        
        if (typeForm == "Обновить") {
            BuildingModel.updateBuilding(id, building);
        } else if (typeForm == "Добавить") {
            BuildingModel.addBuilding(building);
        }

        Stage stage = (Stage) btn.getScene().getWindow();
        BuildingController.getBuildingController().closeForm(stage);
    }
    
    public Building createBuilding() {
        Building building = new Building(type.getText(), Double.parseDouble(square.getText()), Double.parseDouble(cost_balance.getText()), Double.parseDouble(cost_residue.getText()));
        return building;
    }
    
}
