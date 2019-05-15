/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package economistworkstation.Controller;

import economistworkstation.ContractData;
import economistworkstation.Entity.Renter;
import static economistworkstation.Util.Util.setText;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
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
    private DatePicker birthdayField;
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
        setText(birthdayField, renter.getBirthday());
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
            renter.setBirthday(birthdayField.getValue().toString());
            renter.setSubject(subjectField.getText());

            closeForm();
        }
    }
    
    @Override
    protected boolean isInputValid() {
        String errorMessage = "";

        if (fieldIsEmpty(firstNameField)) {
            errorMessage += "Введите имя!\n"; 
        }
        if (fieldIsEmpty(lastNameField)) {
            errorMessage += "Введите фамилию!\n"; 
        }
        if (fieldIsEmpty(patronymicField)) {
            errorMessage += "Введите отчество!\n"; 
        }
        if (fieldIsEmpty(addressField)) {
            errorMessage += "Введите адрес!\n"; 
        }
        if (fieldIsEmpty(birthdayField)) {
            errorMessage += "Неверная дата рождения!\n"; 
        }
        if (fieldIsEmpty(subjectField)) {
            errorMessage += "Введите субьъекта аренды! Например, физ. лицо или ЧП.\n";
        }

        return errorNotExist(errorMessage);
    }
} 

