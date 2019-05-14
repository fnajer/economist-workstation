/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package economistworkstation.Controller;

import economistworkstation.ContractDataParameters;
import economistworkstation.Entity.Contract;
import economistworkstation.Entity.Period;
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
public class InvoiceFormController {

    @FXML
    private ComboBox<Pattern> invoicesListField;
    
    private Stage dialogStage;
    private Contract contract;
    private Period period;
    private File newFile;
    private boolean okClicked = false;
    private Precedency userPrefs;
    
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    
    public void setInvoice(Contract contract, Period period) {  
        this.contract = contract;
        this.period = period;
        
        userPrefs = new Precedency();
        invoicesListField.setItems(Pattern.getInvoices());
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
            Pattern pattern = invoicesListField.getSelectionModel().getSelectedItem();
            pattern.setPathToSave(newFile.getAbsolutePath());
            
            ContractDataParameters data = new ContractDataParameters();
            data.constructDataObject(contract, period);
            
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
