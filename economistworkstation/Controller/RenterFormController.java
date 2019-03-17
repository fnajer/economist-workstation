/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package economistworkstation.Controller;

import economistworkstation.Entity.Renter;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


/**
 *
 * @author fnajer
 */
public class RenterFormController {
  
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField patronymicField;
    @FXML
    private TextField addressField;
    @FXML
    private TextField birthdayField;
    @FXML
    private TextField subjectField;
    
    private Stage dialogStage;
    private Renter renter;
    private boolean okClicked = false;
    
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    
    public void setRenter(Renter renter) {
        this.renter = renter;

        firstNameField.setText(renter.getFirstName());
        lastNameField.setText(renter.getLastName());
        patronymicField.setText(renter.getPatronymic());
        addressField.setText(renter.getAddress());
        birthdayField.setText(renter.getBirthday());
        subjectField.setText(renter.getSubject());
    }
    
    public boolean isOkClicked() {
        return okClicked;
    }
    
    @FXML
    private void handleOk() {
        if (isInputValid()) {
            renter.setFirstName(firstNameField.getText());
            renter.setLastName(lastNameField.getText());
            renter.setPatronymic(patronymicField.getText());
            renter.setAddress(addressField.getText());
            renter.setBirthday(birthdayField.getText());
            renter.setSubject(subjectField.getText());

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

        if (firstNameField.getText() == null || firstNameField.getText().length() == 0) {
            errorMessage += "Введите имя!\n"; 
        }
        if (lastNameField.getText() == null || lastNameField.getText().length() == 0) {
            errorMessage += "Введите фамилию!\n"; 
        }
        if (patronymicField.getText() == null || patronymicField.getText().length() == 0) {
            errorMessage += "Введите отчество!\n"; 
        }
        if (addressField.getText() == null || addressField.getText().length() == 0) {
            errorMessage += "Введите адрес!\n"; 
        }
        if (birthdayField.getText() == null || birthdayField.getText().length() == 0) {
            errorMessage += "Неверная дата рождения!\n"; 
        }
        if (subjectField.getText() == null || subjectField.getText().length() == 0) {
            errorMessage += "Введите субьъекта аренды! Например, физ. лицо или ЧП.\n";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Показываем сообщение об ошибке.
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Некорректные данные");
            alert.setHeaderText("Заполните поля корректно");
            alert.setContentText(errorMessage);
            
            alert.showAndWait();
            
            return false;
    }}
} 

