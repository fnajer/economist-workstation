/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package economistworkstation.Controller;

import economistworkstation.EconomistWorkstation;
import economistworkstation.ExcelCreator;
import economistworkstation.Model.PeriodModel;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuItem;

/**
 *
 * @author fnajer
 */
public class MenuController implements Initializable {
    // Ссылка на главное приложение
    private BorderPane rootLayout;

    /**
     * Вызывается главным приложением, чтобы оставить ссылку на самого себя.
     * 
     * @param rootLayout
     */
    public void setRoot(BorderPane rootLayout) {
        this.rootLayout = rootLayout;
    }
    
    private EconomistWorkstation mainApp;
    
    public void setMainApp(EconomistWorkstation mainApp) {
        this.mainApp = mainApp;
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //staticRoot = root;
    }
    
    @FXML
    private BorderPane root;
    @FXML
    private MenuItem renters;
    @FXML
    private MenuItem buildings;
    @FXML
    private MenuItem contracts;
     
    @FXML
    void showList(ActionEvent event) throws IOException {
        String pathName = getPathName((MenuItem) event.getSource());
        
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(EconomistWorkstation.class.getResource(pathName));
        Parent container = loader.load();
            
        rootLayout.setCenter(container);
        
        BaseController controller = loader.getController();
        controller.setMainApp(mainApp);
    }
    
    private String getPathName(MenuItem src) {
        String id = src.getId();
        String name = id.substring(0, 1).toUpperCase() + id.substring(1, id.length() - 1);

        String pathName = String.format("View/%s/%s.fxml", name, name);
        return pathName;
    }
    
    @FXML
    private void handleAbout() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("AddressApp");
        alert.setHeaderText("About");
        alert.setContentText("Author: Alexey\nWebsite: http://vk.com");

        alert.showAndWait();
    }
    /**
     * Открывает диалоговое окно about.
     */
    @FXML
    private void updateAccounts() {
        PeriodModel.updateAccountNumbers();
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Информация");
        alert.setHeaderText("Обновление номеров счетов");
        alert.setContentText("Переназначение номеров завершено.");
        alert.showAndWait();
        contracts.fire();
    }
    
    @FXML
    private void printAcruals() throws IOException {
        ExcelCreator.printAcruals();
    }

    @FXML
    private void printMemorialOrder() throws IOException {
        ExcelCreator.printMemorialOrder();
    }
    /**
     * Закрывает приложение.
     */
    @FXML
    private void handleExit() {
        System.exit(0);
    }
}
