/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package economistworkstation.Controller;

import economistworkstation.ContractData;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import economistworkstation.Util.Precedency;
import economistworkstation.Util.Pattern;
import static economistworkstation.Util.Util.isExist;
import java.io.File;
import javafx.stage.FileChooser;

/**
 * FXML Controller class
 *
 * @author fnajer
 */
public class PatternFormController extends BaseFormController {
    @FXML
    private ComboBox<Pattern> patternsListField;
    
    private File newFile;
    private Precedency userPrefs;

    @Override
    public void setData(ContractData data) {  
        userPrefs = new Precedency();
        patternsListField.setItems(Pattern.getPatterns());
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
    @Override
    protected void handleOk() {
        if (isInputValid()) {
            Pattern pattern = patternsListField.getSelectionModel().getSelectedItem();
            pattern.replacePattern(newFile.getAbsolutePath());
            
            closeForm();
        }
    }
    
    @Override
    protected boolean isInputValid() {
        String errorMessage = "";
        Pattern pattern = patternsListField.getSelectionModel().getSelectedItem();
        
        if (!isExist(pattern)) {
            errorMessage += "Выберите тип шаблона!\n"; 
        }
        if (!isExist(newFile)) {
            errorMessage += "Укажите новый шаблонный файл!\n"; 
        }

        return errorNotExist(errorMessage);
    }
}
