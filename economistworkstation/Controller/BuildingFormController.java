/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package economistworkstation.Controller;

import economistworkstation.ContractData;
import economistworkstation.Entity.Building;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author fnajer
 */
public class BuildingFormController extends BaseFormController {
    @Override
    public void initialize(URL location, ResourceBundle bundle) {}
    @FXML
    private TextField typeField;
    @FXML
    private TextField squareField;
    @FXML
    private TextField costBalanceField;
    @FXML
    private TextField costResidueField;
    
    private Building building;
    
    @Override
    protected void setData(ContractData data) {
        this.building = data.getBuilding();

        typeField.setText(this.building.getType());
        squareField.setText(Double.toString(this.building.getSquare()));
        costBalanceField.setText(Double.toString(this.building.getCostBalance()));
        costResidueField.setText(Double.toString(this.building.getCostResidue()));
    }
    
    @FXML
    @Override
    protected void handleOk() {
        if (isInputValid()) {
            building.setType(typeField.getText());
            building.setSquare(Double.parseDouble(squareField.getText()));
            building.setCostBalance(Double.parseDouble(costBalanceField.getText()));
            building.setCostResidue(Double.parseDouble(costResidueField.getText()));

            closeForm();
        }
    }
    
    @Override
    protected boolean isInputValid() {
        String errorMessage = "";

        if (typeField.getText() == null || typeField.getText().length() == 0) {
            errorMessage += "Введите тип!\n"; 
        }
        if (squareField.getText() == null || squareField.getText().length() == 0) {
            errorMessage += "Введите площадь!\n"; 
        }
        if (costBalanceField.getText() == null || costBalanceField.getText().length() == 0) {
            errorMessage += "Введите балансовую стоимость!\n"; 
        }
        if (costResidueField.getText() == null || costResidueField.getText().length() == 0) {
            errorMessage += "Введите остаточную стоимость!\n"; 
        }

        return errorNotExist(errorMessage);
    }
}
