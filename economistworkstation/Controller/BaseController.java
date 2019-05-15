/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package economistworkstation.Controller;

import economistworkstation.ContractData;
import economistworkstation.EconomistWorkstation;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author fnajer
 */
public abstract class BaseController {
    protected Stage dialogStage;
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    
    protected boolean showForm(ContractData data, String dialogName, String path) {
        try {
            FXMLLoader loader = new FXMLLoader();
            AnchorPane container = loadFXML(path, loader);
            
            Stage newDialogStage = createDialog(dialogName, container);
            
            // Передаём адресата в контроллер.
            BaseFormController controller = loader.getController();
            controller.setDialogStage(newDialogStage);
            controller.setData(data);
            
            // Отображаем диалоговое окно и ждём, пока пользователь его не закроет
            newDialogStage.showAndWait();
            
            return controller.isOkClicked();
        } catch (IOException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    protected AnchorPane loadFXML(String path, FXMLLoader loader) throws IOException {
        // Загружаем fxml-файл и создаём новую сцену
        // для всплывающего диалогового окна.
        loader.setLocation(EconomistWorkstation.class.getResource(path));
        return (AnchorPane) loader.load();
    }
    
    protected Stage createDialog(String title, Parent container) throws IOException {
        // Создаём диалоговое окно Stage.
        Stage newDialogStage = new Stage();
        newDialogStage.setTitle(title);
        newDialogStage.getIcons().add(new Image("file:resources/images/icon.png"));
        newDialogStage.initModality(Modality.WINDOW_MODAL);
        newDialogStage.initOwner(dialogStage);
        Scene scene = new Scene(container);
        newDialogStage.setScene(scene);
        return newDialogStage;
    }
    
    private void showAlert(String title, String header, String content, 
            AlertType type) {
        Alert alert = new Alert(type);
        alert.initOwner(dialogStage);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        alert.showAndWait();
    }
    protected void showAlertWarning(String title, String header, String content) {
        showAlert(title, header, content, AlertType.WARNING);
    }
    protected void showAlertSuccess(String title, String header, String content) {
        showAlert(title, header, content, AlertType.INFORMATION);
    }
    protected void showAlertError(String title, String header, String content) {
        showAlert(title, header, content, AlertType.ERROR);
    }
}
