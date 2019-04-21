/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package economistworkstation.Controller;

import economistworkstation.Entity.Building;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author fnajer
 */
public class BuildingFormController {  
    
    @FXML
    private TextField typeField;
    @FXML
    private TextField squareField;
    @FXML
    private TextField costBalanceField;
    @FXML
    private TextField costResidueField;
    
    private Stage dialogStage;
    private Building building;
    private boolean okClicked = false;
    
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    
    public void setBuilding(Building building) {
        this.building = building;

        typeField.setText(building.getType());
        squareField.setText(Double.toString(building.getSquare()));
        costBalanceField.setText(Double.toString(building.getCostBalance()));
        costResidueField.setText(Double.toString(building.getCostResidue()));
    }
    
    public boolean isOkClicked() {
        return okClicked;
    }
    
    @FXML
    private void handleOk() {
        if (isInputValid()) {
            building.setType(typeField.getText());
            building.setSquare(Double.parseDouble(squareField.getText()));
            building.setCostBalance(Double.parseDouble(costBalanceField.getText()));
            building.setCostResidue(Double.parseDouble(costResidueField.getText()));

            okClicked = true;
            dialogStage.close();
        }
    }
    
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }
    
    private boolean isInputValid() {
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

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Показываем сообщение об ошибке.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Некорректные данные");
            alert.setHeaderText("Заполните поля корректно");
            alert.setContentText(errorMessage);
            
            alert.showAndWait();
            
            return false;
    }}
}
