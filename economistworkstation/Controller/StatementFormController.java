/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package economistworkstation.Controller;

import economistworkstation.ContractDataParameters;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import economistworkstation.Util.Precedency;
import economistworkstation.Util.Statement;
import static economistworkstation.Util.Util.isExist;
import java.io.File;
import java.time.LocalDate;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.stage.FileChooser;

/**
 * FXML Controller class
 *
 * @author fnajer
 */
public class StatementFormController {

    @FXML
    private ComboBox<Statement> statementsListField;
    @FXML
    private DatePicker dateStartField;
    
    private Stage dialogStage;
//    private Contract contract;
    private String directory;
    private boolean okClicked = false;
    Precedency userPrefs;
    
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    
    public void setStatement() {  
        userPrefs = new Precedency();
        statementsListField.setItems(userPrefs.getStatements());
    }
    
    public boolean isOkClicked() {
        return okClicked;
    }
    
    @FXML
    private void handleOpenPath() {
//        Precedency statement = statementsListField.getSelectionModel().getSelectedItem();
        
        FileChooser fileChooser = new FileChooser();
//        Preferences userPrefs = Preferences.userRoot().node("economist-save"); 
      
//        String currentPath = "C:";
        String currentPath = userPrefs.getSaveDirectory();
//        if ("Ведомость начисления".equals(statement))
//            currentPath = userPrefs.get("Ведомость начисления", "C:");
//        if ("Мемориальный ордер".equals(statement))
//            currentPath = userPrefs.get("Мемориальный ордер", "C:");
//        if ("Накопительная ведомость".equals(statement))
//            currentPath = userPrefs.get("Накопительная ведомость", "C:");
        
        fileChooser.setInitialDirectory(new File(currentPath));
	fileChooser.setTitle("Сохранение");
        // Задаём фильтр расширений
        FileChooser.ExtensionFilter xlsFilter = new FileChooser.ExtensionFilter(
                "XLS file", "*.xls");
        FileChooser.ExtensionFilter xlsxFilter = new FileChooser.ExtensionFilter(
                "XLSX file", "*.xlsx");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "All files", "*");
        fileChooser.getExtensionFilters().addAll(xlsFilter, xlsxFilter, extFilter);

        // Показываем диалог загрузки файла
        File file = fileChooser.showSaveDialog(dialogStage);
        //need class for params - for each type statement self path
        if (isExist(file)) {
            directory = file.getParent();
            String path = file.getAbsolutePath();
            System.out.println(file.getAbsolutePath());
            System.out.println(file.getParent());
//            if ("Ведомость начисления".equals(statement))
//                userPrefs.put("Ведомость начисления", path);
//            if ("Мемориальный ордер".equals(statement))
//                userPrefs.put("Мемориальный ордер", path);
//            if ("Накопительная ведомость".equals(statement))
//                userPrefs.put("Накопительная ведомость", path);
            userPrefs.setSaveDirectory(directory);
            userPrefs.setSavePath(path);
	}
    }
    
    @FXML
    private void handleOk() {
        if (isInputValid()) {
            Statement statement = statementsListField.getSelectionModel().getSelectedItem();
            LocalDate date = dateStartField.getValue();
            
            ContractDataParameters data = new ContractDataParameters();
            data.constructDataList(date, userPrefs.getSavePath());
            
            statement.print(data);
//            if ("Ведомость начисления".equals(statement))
//                printAccrualStatement(data);
//            if ("Мемориальный ордер".equals(statement))
//                printMemorialOrder(data);
//            if ("Накопительная ведомость".equals(statement))
//                printAccumulationStatement(data);
        
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
        Statement statement = statementsListField.getSelectionModel().getSelectedItem();
        
        if (!isExist(statement)) {
            errorMessage += "Выберите ведомость!\n"; 
        }
        if (!isExist(dateStartField.getValue())) {
            errorMessage += "Поставьте дату!\n"; 
        }
        if (!isExist(directory)) {
            errorMessage += "Укажите путь для сохранения файла!\n"; 
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Показываем сообщение об ошибке.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Некорректные данные");
            alert.setHeaderText("Заполните поля корректно");
            alert.setContentText(errorMessage);
            
            alert.showAndWait();
            
            return false;
    }}
    
}
