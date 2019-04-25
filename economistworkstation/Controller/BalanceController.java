/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package economistworkstation.Controller;

import economistworkstation.Entity.Balance;
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
    private Period period;
    private boolean okClicked = false;
    
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    
    public void setBalance(Period period) {
        this.period = period;
        
        Util.setCalledClass(this);
                
        if (isExist(period.getBalance())) {
            Balance balanceRent = period.getRentPayment().getBalance();
            Balance balanceFine = period.getFinePayment().getBalance();
            Balance balanceTaxLand = period.getTaxLandPayment().getBalance();
            Balance balanceServices = period.getServicesPayment().getBalance();
            Balance balanceEquipment = period.getEquipmentPayment().getBalance();
        
            setText(creditRentLabel, balanceRent.getCredit());
            setText(debitRentLabel, balanceRent.getDebit());
            setText(creditFineLabel, balanceFine.getCredit());
            setText(debitFineLabel, balanceFine.getDebit());
            setText(creditTaxLandLabel, balanceTaxLand.getCredit());
            setText(debitTaxLandLabel, balanceTaxLand.getDebit());
            setText(creditServiceLabel, balanceServices.getCredit());
            setText(debitServiceLabel, balanceServices.getDebit());
            setText(creditEquipmentLabel, balanceEquipment.getCredit());
            setText(debitEquipmentLabel, balanceEquipment.getDebit());
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

