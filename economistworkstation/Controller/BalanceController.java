/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package economistworkstation.Controller;

import economistworkstation.Entity.BalanceTable;
import economistworkstation.Entity.Period;
import economistworkstation.Util.Util;
import static economistworkstation.Util.Util.setText;
import static economistworkstation.Util.Util.isExist;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;


/**
 *
 * @author fnajer
 */
public class BalanceController {
    @FXML
    private Label creditRentLabel;
    @FXML
    private Label debitRentLabel;
    @FXML
    private Label debitFineLabel;
    @FXML
    private Label creditFineLabel;
    @FXML
    private Label creditTaxLandLabel;
    @FXML
    private Label debitTaxLandLabel;
    @FXML
    private Label creditServiceLabel;
    @FXML
    private Label debitServiceLabel;
    @FXML
    private Label creditEquipmentLabel;
    @FXML
    private Label debitEquipmentLabel;

    private Stage dialogStage;
    private final boolean okClicked = false;
    
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    
    public void setBalance(Period period) {
        Util.setCalledClass(this);
        
        BalanceTable balanceTable = period.getBalanceTable();
        if (isExist(balanceTable)) {
            setText(creditRentLabel, balanceTable.getCreditRent());
            setText(debitRentLabel, balanceTable.getDebitRent());

            setText(creditFineLabel, balanceTable.getCreditFine());
            setText(debitFineLabel, balanceTable.getDebitFine());

            setText(creditTaxLandLabel, balanceTable.getCreditTaxLand());
            setText(debitTaxLandLabel, balanceTable.getDebitTaxLand());

            setText(creditServiceLabel, balanceTable.getCreditService());
            setText(debitServiceLabel, balanceTable.getDebitService());

            setText(creditEquipmentLabel, balanceTable.getCreditEquipment());
            setText(debitEquipmentLabel, balanceTable.getDebitEquipment());
        } else {
            creditRentLabel.setText("Нет");
            debitRentLabel.setText("Нет");
            creditFineLabel.setText("Нет");
            debitFineLabel.setText("Нет");
            creditTaxLandLabel.setText("Нет");
            debitTaxLandLabel.setText("Нет");
            creditServiceLabel.setText("Нет");
            debitServiceLabel.setText("Нет");
            creditEquipmentLabel.setText("Нет");
            debitEquipmentLabel.setText("Нет");
        }
    }
    
    public boolean isOkClicked() {
        return okClicked;
    }
    
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }
} 

