/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package economistworkstation.Controller;

import economistworkstation.ContractData;
import economistworkstation.Entity.Renter;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import economistworkstation.Model.RenterModel;
import javafx.fxml.FXML;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 *
 * @author fnajer
 */

public class RenterController extends BaseController {
    @FXML
    private TableView<Renter> renterTable;
    @FXML
    private TableColumn<Renter, String> firstNameColumn;
    @FXML
    private TableColumn<Renter, String> lastNameColumn;
    
    @FXML
    private Label firstNameLabel;
    @FXML
    private Label lastNameLabel;
    @FXML
    private Label patronymicLabel;
    @FXML
    private Label addressLabel;
    @FXML
    private Label birthdayLabel;
    @FXML
    private Label subjectLabel;
   
    private ObservableList<Renter> renters;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
        renters = RenterModel.getRenters();
        renterTable.setItems(renters);

        firstNameColumn.setCellValueFactory(
            cellData -> cellData.getValue().firstNameProperty());
        lastNameColumn.setCellValueFactory(
            cellData -> cellData.getValue().lastNameProperty());
        
        // Очистка дополнительной информации об адресате.
        showDetails(null);

        // Слушаем изменения выбора, и при изменении отображаем
        // дополнительную информацию об адресате.
        renterTable.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> showDetails(newValue));
    }
    
    
    public void showDetails(Renter renter) {
        
        if (renter != null) {
            firstNameLabel.setText(renter.getFirstName());
            lastNameLabel.setText(renter.getLastName());
            patronymicLabel.setText(renter.getPatronymic());
            addressLabel.setText(renter.getAddress());
            birthdayLabel.setText(renter.getBirthday());
            subjectLabel.setText(renter.getSubject());
        } else {
            firstNameLabel.setText("");
            lastNameLabel.setText("");
            patronymicLabel.setText("");
            addressLabel.setText("");
            birthdayLabel.setText("");
            subjectLabel.setText("");
        }
    } 
    
    @FXML
    private void handleDelete() {
        int selectedIndex = renterTable.getSelectionModel().getSelectedIndex();

        if (selectedIndex >= 0) {
            Renter renter = renterTable.getSelectionModel().getSelectedItem();
            int id = renter.getId();

            renterTable.getItems().remove(renter);
            RenterModel.deleteRenter(id);
        } else {
            showAlertWarning("Не выбран", 
                    "Арендатор не выбран",
                    "Пожалуйста, выберите арендатора в таблице.");
        }
    }
    
    @FXML
    private void handleNewRenter() {
        Renter tempRenter = new Renter();
        boolean okClicked = openRenterForm(tempRenter);
        if (okClicked) {
            int id = RenterModel.addRenter(tempRenter);
            tempRenter.setId(id);
            renters.add(tempRenter);
            renterTable.getSelectionModel().select(tempRenter);
        }
    }

    /**
     * Вызывается, когда пользователь кликает по кнопка Edit...
     * Открывает диалоговое окно для изменения выбранного адресата.
     */
    @FXML
    private void handleEditRenter() {
        Renter selectedRenter = renterTable.getSelectionModel().getSelectedItem();
        
        if (selectedRenter != null) {
            boolean okClicked = openRenterForm(selectedRenter);
            if (okClicked) {
                RenterModel.updateRenter(selectedRenter.getId(), selectedRenter);
                showDetails(selectedRenter);
            }
        } else {
            showAlertWarning("Не выбран", 
                    "Арендатор не выбран",
                    "Пожалуйста, выберите арендатора в таблице.");
        }
    }
    
    private boolean openRenterForm(Renter renter) {
        ContractData data = new ContractData(null, null, null, renter, null);
        
        return showForm(data, 
                "Редактировать арендатора", 
                "View/Renter/RenterForm.fxml");
    }
}
