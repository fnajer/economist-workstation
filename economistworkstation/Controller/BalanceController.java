/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package economistworkstation.Controller;

import economistworkstation.Entity.Balance;
import economistworkstation.Entity.Equipment;
import economistworkstation.Entity.Fine;
import economistworkstation.Entity.Period;
import economistworkstation.Entity.Rent;
import economistworkstation.Entity.Services;
import economistworkstation.Entity.TaxLand;
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
                
        if (isExist(period.getBalance())) {
            Balance balanceRent, 
                    balanceFine, 
                    balanceTaxLand, 
                    balanceServices,
                    balanceEquipment;
            
            Rent rent = (Rent) period.getRentPayment();
            Fine fine = (Fine) period.getFinePayment();
            TaxLand taxLand = (TaxLand) period.getTaxLandPayment();
            Services services = (Services) period.getServicesPayment();
            Equipment equipment = (Equipment) period.getEquipmentPayment();
 
            if (isExist(rent)) 
                balanceRent = period.getRentPayment().getBalance();
            else 
                balanceRent = new Balance();
            setText(creditRentLabel, balanceRent.getCreditBefore());
            setText(debitRentLabel, balanceRent.getDebitBefore());
            
            if (isExist(fine))
                balanceFine = period.getFinePayment().getBalance();
            else 
                balanceFine = new Balance();
            setText(creditFineLabel, balanceFine.getCreditBefore());
            setText(debitFineLabel, balanceFine.getDebitBefore());

            if (isExist(taxLand))
                balanceTaxLand = period.getTaxLandPayment().getBalance();
            else 
                balanceTaxLand = new Balance();
            setText(creditTaxLandLabel, balanceTaxLand.getCreditBefore());
            setText(debitTaxLandLabel, balanceTaxLand.getDebitBefore());
            
            if (isExist(services))
                balanceServices = period.getServicesPayment().getBalance();
            else 
                balanceServices = new Balance();
            setText(creditServiceLabel, balanceServices.getCreditBefore());
            setText(debitServiceLabel, balanceServices.getDebitBefore());
            
            if (isExist(equipment))
                balanceEquipment = period.getEquipmentPayment().getBalance();
            else 
                balanceEquipment = new Balance();
            setText(creditEquipmentLabel, balanceEquipment.getCreditBefore());
            setText(debitEquipmentLabel, balanceEquipment.getDebitBefore());
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

