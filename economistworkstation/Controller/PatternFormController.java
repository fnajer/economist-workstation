/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package economistworkstation.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import economistworkstation.Util.Precedency;
import economistworkstation.Util.Pattern;
import static economistworkstation.Util.Util.isExist;
import java.io.File;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;

/**
 * FXML Controller class
 *
 * @author fnajer
 */
public class PatternFormController {

    @FXML
    private ComboBox<Pattern> patternsListField;
    
    private Stage dialogStage;
    private File newFile;
    private boolean okClicked = false;
    private Precedency userPrefs;
    
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    
    public void setPattern() {  
        userPrefs = new Precedency();
        patternsListField.setItems(Pattern.getPatterns());
    }
    
    public boolean isOkClicked() {
        return okClicked;
    }
    
    @FXML
    private void handleOpenPath() {      
        FileChooser fileChooser = new FileChooser();
        String currentPath = userPrefs.getLoadDirectory();

        fileChooser.setInitialDirectory(new File(currentPath));
	fileChooser.setTitle("Загрузка");
        
        FileChooser.ExtensionFilter xlsFilter = new FileChooser.ExtensionFilter(
                "XLS file", "*.xls");
        FileChooser.ExtensionFilter xlsxFilter = new FileChooser.ExtensionFilter(
                "XLSX file", "*.xlsx");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "All files", "*");
        fileChooser.getExtensionFilters().addAll(xlsFilter, xlsxFilter, extFilter);

        File file = fileChooser.showOpenDialog(dialogStage);

        if (isExist(file)) {
            newFile = file;
            userPrefs.setLoadDirectory(newFile.getParent());
	}
    }
    
    @FXML
    private void handleOk() {
        if (isInputValid()) {
            Pattern pattern = patternsListField.getSelectionModel().getSelectedItem();
            pattern.replacePattern(newFile.getAbsolutePath());
            
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
        Pattern pattern = patternsListField.getSelectionModel().getSelectedItem();
        
        if (!isExist(pattern)) {
            errorMessage += "Выберите тип шаблона!\n"; 
        }
        if (!isExist(newFile)) {
            errorMessage += "Укажите новый шаблонный файл!\n"; 
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
