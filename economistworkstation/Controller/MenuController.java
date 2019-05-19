/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package economistworkstation.Controller;

import economistworkstation.ContractData;
import economistworkstation.EconomistWorkstation;
import economistworkstation.Model.PeriodModel;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.MenuItem;

/**
 *
 * @author fnajer
 */
public class MenuController extends BaseController {
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
    
    @FXML
    private BorderPane root;
    @FXML
    private MenuItem renters;
    @FXML
    private MenuItem buildings;
    @FXML
    private MenuItem contracts;
    
    private MenuItem currentOpenPage;
     
    @FXML
    void showList(ActionEvent event) throws IOException {
        currentOpenPage = (MenuItem) event.getSource();
        String pathName = getPathName((MenuItem) event.getSource());
        
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(EconomistWorkstation.class.getResource(pathName));
        Parent container = loader.load();
            
        rootLayout.setCenter(container);
        
        BaseController controller = loader.getController();
        controller.setDialogStage(dialogStage);
    }
    
    private String getPathName(MenuItem src) {
        String id = src.getId();
        String name = id.substring(0, 1).toUpperCase() + id.substring(1, id.length() - 1);

        String pathName = String.format("View/%s/%s.fxml", name, name);
        return pathName;
    }
    
    @FXML
    private void handleAbout() {
        showAlertSuccess("Информация", 
                    "О программе",
                    "Некоммерческий учебный продукт, разрабатываемый "
                            + "в ходе дипломной работы.\nАвтор: Ткаченко А.");
    }
    
    @FXML
    private void updateAccounts() {
        PeriodModel.updateAccountNumbers();
        showAlertSuccess("Успех", 
                    "Обновление номеров счетов",
                    "Переназначение номеров завершено.");
        contracts.fire();
    }
    
    @FXML
    private void handleShowStatement() {
        boolean okClicked = openStatementForm();
        if (okClicked) {
            showAlertSuccess("Успех", 
                    "Создание ведомости",
                    "Ведомость создана успешно.");
        }
    }
    private boolean openStatementForm() {
        ContractData data = null;
        
        return showForm(data, 
                "Управление ведомостями", 
                "View/StatementForm.fxml");
    }
    
    @FXML
    private void handleLoadPattern() {
        boolean okClicked = openPatternForm();
        if (okClicked) {
            showAlertSuccess("Успех", 
                    "Замена шаблона",
                    "Произведена замена шаблона.");
        }
    }
    private boolean openPatternForm() {
        ContractData data = null;
        
        return showForm(data, 
                "Загрузка шаблонов", 
                "View/PatternForm.fxml");
    }

    @FXML
    private void handleAutoFill() {
        boolean okClicked = openAutoFillForm();
        if (okClicked) {
            showAlertSuccess("Успех", 
                    "Автоматическое заполнение",
                    "Авто-заполнение завершено.");
            if (currentOpenPage == contracts)
                currentOpenPage.fire();
        }
    }
    private boolean openAutoFillForm() {
        ContractData data = null;
        
        return showForm(data, 
                "Авто-заполнение", 
                "View/AutoFillForm.fxml");
    }
    
    @FXML
    private void handleShowConsolidated() {
        boolean okClicked = openConsolidatedForm();
        if (okClicked) {
            showAlertSuccess("Успех", 
                    "Создание сводной ведомости",
                    "Сводная ведомость создана успешно.");
        }
    }
    private boolean openConsolidatedForm() {
        ContractData data = null;
        
        return showForm(data, 
                "Управление сводной ведомостью", 
                "View/ConsolidatedForm.fxml");
    }
    
    /**
     * Закрывает приложение.
     */
    @FXML
    private void handleExit() {
        System.exit(0);
    }
}
