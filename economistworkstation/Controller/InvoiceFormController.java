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
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author fnajer
 */
public class InvoiceFormController extends BaseFormController {
    @Override
    public void initialize(URL location, ResourceBundle bundle) {}
    @FXML
    private ComboBox<Pattern> invoicesListField;
    
    private ContractData data;
    private File newFile;
    private Precedency userPrefs;
    
    @Override
    public void setData(ContractData data) {
        userPrefs = new Precedency();
        invoicesListField.setItems(Pattern.getInvoices());
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
            Pattern pattern = invoicesListField.getSelectionModel().getSelectedItem();
            pattern.setPathToSave(newFile.getAbsolutePath());
            
            ContractDataParameters dataParams = new ContractDataParameters();
            dataParams.constructDataObject(data.getContract(), data.getPeriod());
            
            pattern.print(dataParams);

            closeForm();
        }
    }
    
    @Override
    protected boolean isInputValid() {
        String errorMessage = "";
        Pattern invoice = invoicesListField.getSelectionModel().getSelectedItem();
        
        if (!isExist(invoice)) {
            errorMessage += "Выберите ведомость!\n"; 
        }
        if (!isExist(newFile)) {
            errorMessage += "Укажите путь для сохранения файла!\n"; 
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            showAlertError("Некорректные данные", 
                    "Заполните поля корректно",
                    errorMessage);
            return false;
    }}
}
