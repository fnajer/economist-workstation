/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package economistworkstation.Controller;

import economistworkstation.ContractData;
import economistworkstation.Entity.Building;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author fnajer
 */
public class BuildingFormController extends BaseFormController {
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

        if (fieldIsEmpty(typeField)) {
            errorMessage += "Введите тип!\n"; 
        }
        if (fieldIsEmpty(squareField)) {
            errorMessage += "Введите площадь!\n"; 
        }
        if (fieldIsEmpty(costBalanceField)) {
            errorMessage += "Введите балансовую стоимость!\n"; 
        }
        if (fieldIsEmpty(costResidueField)) {
            errorMessage += "Введите остаточную стоимость!\n"; 
        }

        return errorNotExist(errorMessage);
    }
}
