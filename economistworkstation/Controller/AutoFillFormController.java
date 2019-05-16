/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package economistworkstation.Controller;

import economistworkstation.ContractData;
import economistworkstation.ContractDataParameters;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import economistworkstation.Util.Precedency;
import economistworkstation.Util.Pattern;
import static economistworkstation.Util.Util.isExist;
import java.io.File;
import java.time.LocalDate;
import javafx.scene.control.DatePicker;
import javafx.stage.FileChooser;

/**
 * FXML Controller class
 *
 * @author fnajer
 */
public class StatementFormController extends BaseFormController {
    @FXML
    private ComboBox<Pattern> statementsListField;
    @FXML
    private DatePicker dateStartField;
    
    private File newFile;
    private Precedency userPrefs;
    
    @Override
    public void setData(ContractData data) {  
        userPrefs = new Precedency();
        statementsListField.setItems(Pattern.getStatements());
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
    @Override
    protected void handleOk() {
        if (isInputValid()) {
            Pattern pattern = statementsListField.getSelectionModel().getSelectedItem();
            pattern.setPathToSave(newFile.getAbsolutePath());
            LocalDate date = dateStartField.getValue();
            
            ContractDataParameters dataParams = new ContractDataParameters();
            dataParams.constructDataList(date);
            
            pattern.print(dataParams);

            closeForm();
        }
    }
    
    @Override
    protected boolean isInputValid() {
        String errorMessage = "";
        Pattern statement = statementsListField.getSelectionModel().getSelectedItem();
        
        if (!isExist(statement)) {
            errorMessage += "Выберите ведомость!\n"; 
        }
        if (fieldIsEmpty(dateStartField)) {
            errorMessage += "Поставьте дату!\n"; 
        }
        if (!isExist(newFile)) {
            errorMessage += "Укажите путь для сохранения файла!\n"; 
        }

        return errorNotExist(errorMessage);
    }
}
