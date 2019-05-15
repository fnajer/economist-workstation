/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package economistworkstation.Controller;

import economistworkstation.ContractData;
import economistworkstation.Entity.Renter;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;


/**
 *
 * @author fnajer
 */
public class RenterFormController extends BaseFormController {
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
    
    private Renter renter;
    
    @Override
    public void setData(ContractData data) {
        this.renter = data.getRenter();

        firstNameField.setText(renter.getFirstName());
        lastNameField.setText(renter.getLastName());
        patronymicField.setText(renter.getPatronymic());
        addressField.setText(renter.getAddress());
        birthdayField.setText(renter.getBirthday());
        subjectField.setText(renter.getSubject());
    }

    @FXML
    @Override
    protected void handleOk() {
        if (isInputValid()) {
            renter.setFirstName(firstNameField.getText());
            renter.setLastName(lastNameField.getText());
            renter.setPatronymic(patronymicField.getText());
            renter.setAddress(addressField.getText());
            renter.setBirthday(birthdayField.getText());
            renter.setSubject(subjectField.getText());

            closeForm();
        }
    }
    
    @Override
    protected boolean isInputValid() {
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

        return errorNotExist(errorMessage);
    }
} 

