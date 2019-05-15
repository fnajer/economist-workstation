/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package economistworkstation.Controller;

import economistworkstation.ContractData;
import economistworkstation.EconomistWorkstation;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author fnajer
 */
public abstract class BaseController implements Initializable {
    @Override
    public abstract void initialize(URL location, ResourceBundle bundle);
    
    protected EconomistWorkstation mainApp;
    public void setMainApp(EconomistWorkstation mainApp) {
        this.mainApp = mainApp;
    }
    
    protected boolean showForm(ContractData data, String dialogName, String path) {
        try {
            FXMLLoader loader = new FXMLLoader();
            AnchorPane container = loadFXML(path, loader);
            
            Stage dialogStage = createDialog(dialogName, container);
            
            // Передаём адресата в контроллер.
            BaseFormController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setData(data);
            
            // Отображаем диалоговое окно и ждём, пока пользователь его не закроет
            dialogStage.showAndWait();
            
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
        Stage dialogStage = new Stage();
        dialogStage.setTitle(title);
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(mainApp.getPrimaryStage());
        Scene scene = new Scene(container);
        dialogStage.setScene(scene);
        return dialogStage;
    }
    
    protected void showAlertWarning(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.initOwner(mainApp.getPrimaryStage());
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        alert.showAndWait();
    }
    protected void showAlertSuccess(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initOwner(mainApp.getPrimaryStage());
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        alert.showAndWait();
    }
}
