/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package economistworkstation.Controller;

import economistworkstation.Entity.Balance;
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
    private Balance balance;
    private boolean okClicked = false;
    
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    
    public void setBalance(Balance balance) {
        this.balance = balance;

        Util.setCalledClass(this);
                
        if (isExist(balance)) {
            setText(creditRentLabel, balance.getCreditRent());
            setText(debitRentLabel, balance.getDebitRent());
            setText(creditFineLabel, balance.getCreditFine());
            setText(debitFineLabel, balance.getDebitFine());
            setText(creditTaxLandLabel, balance.getCreditTaxLand());
            setText(debitTaxLandLabel, balance.getDebitTaxLand());
            setText(creditServiceLabel, balance.getCreditService());
            setText(debitServiceLabel, balance.getDebitService());
            setText(creditEquipmentLabel, balance.getCreditEquipment());
            setText(debitEquipmentLabel, balance.getDebitEquipment());
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

