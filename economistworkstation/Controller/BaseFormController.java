/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package economistworkstation.Controller;

import economistworkstation.ContractData;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

/**
 *
 * @author fnajer
 */
public abstract class BaseFormController extends BaseController {
    protected abstract boolean isInputValid();
    protected abstract void handleOk();
    protected abstract void setData(ContractData data);
    
    private boolean okClicked = false;
    public boolean isOkClicked() {
        return okClicked;
    }
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }
    protected void closeForm() {
        okClicked = true;
        dialogStage.close();
    }
    
    protected boolean fieldIsEmpty(TextField tf) {
        return tf.getText() == null || tf.getText().length() == 0;
    }
    protected boolean fieldIsEmpty(DatePicker dp) {
        String text = dp.getEditor().getText();
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            LocalDate.parse(text, formatter);
            return false;
        } catch(DateTimeParseException e) {
            System.out.println("Date with error.");
            return true;
        }
    }
    protected boolean costIsInvalid(TextField tf) {
        try {
            Double.parseDouble(tf.getText());
            return false;
        } catch(NumberFormatException e) {
            return true;
        }
    }
    
    protected boolean errorNotExist(String errorMessage) {
        if (errorMessage.length() == 0) {
            return true;
        } else {
            showAlertError("Некорректные данные", 
                    "Заполните поля корректно",
                    errorMessage);
            return false;
        }
    }
}
