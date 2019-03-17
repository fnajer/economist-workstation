/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package economistworkstation.Controller;

import economistworkstation.EconomistWorkstation;
import economistworkstation.Entity.Renter;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import economistworkstation.Model.RenterModel;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author fnajer
 */

public class RenterController implements Initializable, BaseController {
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
    private EconomistWorkstation mainApp;
    
    @Override
    public void setMainApp(EconomistWorkstation mainApp) {
        this.mainApp = mainApp;
    }

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
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Renter Selected");
            alert.setContentText("Please select a renter in the table.");
    
            alert.showAndWait();
        }
    }
    
    @FXML
    private void handleNewRenter() {
        Renter tempRenter = new Renter();
        boolean okClicked = showRenterForm(tempRenter);
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
            boolean okClicked = showRenterForm(selectedRenter);
            if (okClicked) {
                RenterModel.updateRenter(selectedRenter.getId(), selectedRenter);
                showDetails(selectedRenter);
            }
        } else {
            // Ничего не выбрано.
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Renter Selected");
            alert.setContentText("Please select a renter in the table.");

            alert.showAndWait();
        }
    }
    
    public boolean showRenterForm(Renter renter) {
        try {
            // Загружаем fxml-файл и создаём новую сцену
            // для всплывающего диалогового окна.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(EconomistWorkstation.class.getResource("View/Renter/RenterForm.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            
            // Создаём диалоговое окно Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Renter");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(mainApp.getPrimaryStage());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            
            // Передаём адресата в контроллер.
            RenterFormController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setRenter(renter);
            
            // Отображаем диалоговое окно и ждём, пока пользователь его не закроет
            dialogStage.showAndWait();
            
            return controller.isOkClicked();
        } catch (IOException ex) {
            Logger.getLogger(RenterController.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
}
