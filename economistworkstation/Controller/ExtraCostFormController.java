/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package economistworkstation.Controller;

import economistworkstation.ContractData;
import economistworkstation.Entity.ExtraCost;
import economistworkstation.Entity.Period;
import economistworkstation.Util.Util;
import static economistworkstation.Util.Util.setText;
import static economistworkstation.Util.Util.parseField;
import static economistworkstation.Util.Util.isExist;
import static economistworkstation.Entity.Field.isFilled;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

/**
 *
 * @author fnajer
 */
public class ExtraCostFormController extends BaseFormController {
    @FXML
    private TextField extraCostRentField;
    @FXML
    private TextField extraCostFineField;
    @FXML
    private TextField extraCostTaxLandField;
    @FXML
    private TextField extraCostServicesField;
    @FXML
    private TextField extraCostEquipmentField;

    private ContractData data;
    
    @Override
    protected void setData(ContractData data) {
        this.data = data;
        ExtraCost extraCost = data.getPeriod().getExtraCost();

        Util.setCalledClass(this);
                
        if (isExist(extraCost)) {
            setText(extraCostRentField, extraCost.getCostRent());
            setText(extraCostFineField, extraCost.getCostFine());
            setText(extraCostTaxLandField, extraCost.getCostTaxLand());
            setText(extraCostServicesField, extraCost.getCostServices());
            setText(extraCostEquipmentField, extraCost.getCostEquipment());
        }
    }
    
    @FXML
    @Override
    protected void handleOk() {
        Period period = data.getPeriod();
        ExtraCost extraCost = period.getExtraCost();
        if (isInputValid()) {
            if (isFilled(extraCostRentField, extraCostFineField, extraCostTaxLandField,
                    extraCostServicesField, extraCostEquipmentField)) {
                if (extraCost == null)
                    extraCost = new ExtraCost();
                extraCost.setCostRent(parseField(extraCostRentField));
                extraCost.setCostFine(parseField(extraCostFineField));
                extraCost.setCostTaxLand(parseField(extraCostTaxLandField));
                extraCost.setCostServices(parseField(extraCostServicesField));
                extraCost.setCostEquipment(parseField(extraCostEquipmentField));
                period.setExtraCost(extraCost);
            } else if (isExist(extraCost)) {
                ExtraCost extraCostForDelete = new ExtraCost();
                extraCostForDelete.setCostRent(-1.0);
                period.setExtraCost(extraCostForDelete);
            }

            closeForm();
        }
    }
    
    @Override
    protected boolean isInputValid() {
        String errorMessage = "";

        if (costIsInvalid(extraCostRentField)) {
            errorMessage += "Поле аренды задано неверно!\n"; 
        }
        if (costIsInvalid(extraCostFineField)) {
            errorMessage += "Поле пени задано неверно!\n"; 
        }
        if (costIsInvalid(extraCostTaxLandField)) {
            errorMessage += "Поле земельного налога задано неверно!\n"; 
        }
        if (costIsInvalid(extraCostServicesField)) {
            errorMessage += "Поле коммунальных услуг задано неверно!\n"; 
        }
        if (costIsInvalid(extraCostEquipmentField)) {
            errorMessage += "Поле аренды оборудования задано неверно!\n"; 
        }

        return errorNotExist(errorMessage);
    }
    
    @Override
    protected boolean costIsInvalid(TextField tf) {
        if (fieldIsEmpty(tf)) return false;
        try {
            Double.parseDouble(tf.getText());
            return false;
        } catch(NumberFormatException e) {
            return true;
        }
    }
} 

