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
import economistworkstation.Util.Pattern;
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
    private ComboBox<Pattern> statementsListField;
    @FXML
    private DatePicker dateStartField;
    
    private Stage dialogStage;
    private File newFile;
    private boolean okClicked = false;
    private Precedency userPrefs;
    
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    
    public void setStatement() {  
        userPrefs = new Precedency();
        statementsListField.setItems(Pattern.getStatements());
    }
    
    public boolean isOkClicked() {
        return okClicked;
    }
    
    @FXML
    private void handleOpenPath() {
        FileChooser fileChooser = new FileChooser();
        String currentPath = userPrefs.getSaveDirectory();

        fileChooser.setInitialDirectory(new File(currentPath));
	fileChooser.setTitle("Сохранение");
        
        FileChooser.ExtensionFilter xlsFilter = new FileChooser.ExtensionFilter(
                "XLS file", "*.xls");
        FileChooser.ExtensionFilter xlsxFilter = new FileChooser.ExtensionFilter(
                "XLSX file", "*.xlsx");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "All files", "*");
        fileChooser.getExtensionFilters().addAll(xlsFilter, xlsxFilter, extFilter);

        File file = fileChooser.showSaveDialog(dialogStage);
       
        if (isExist(file)) {
            newFile = file;
            
            userPrefs.setSaveDirectory(newFile.getParent());
	}
    }
    
    @FXML
    private void handleOk() {
        if (isInputValid()) {
            Pattern pattern = statementsListField.getSelectionModel().getSelectedItem();
            pattern.setPathToSave(newFile.getAbsolutePath());
            LocalDate date = dateStartField.getValue();
            
            ContractDataParameters data = new ContractDataParameters();
            data.constructDataList(date);
            
            pattern.print(data);

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
        Pattern statement = statementsListField.getSelectionModel().getSelectedItem();
        
        if (!isExist(statement)) {
            errorMessage += "Выберите ведомость!\n"; 
        }
        if (!isExist(dateStartField.getValue())) {
            errorMessage += "Поставьте дату!\n"; 
        }
        if (!isExist(newFile)) {
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
