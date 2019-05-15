/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package economistworkstation.Controller;

import economistworkstation.ContractData;
import economistworkstation.Entity.BalanceTable;
import economistworkstation.Util.Util;
import static economistworkstation.Util.Util.setText;
import static economistworkstation.Util.Util.isExist;
import javafx.fxml.FXML;
import javafx.scene.control.Label;


/**
 *
 * @author fnajer
 */
public class BalanceController extends BaseFormController {
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
    
    @Override
    protected void setData(ContractData data) {
        Util.setCalledClass(this);
        
        BalanceTable balanceTable = data.getPeriod().getBalanceTable();
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

    @Override
    protected boolean isInputValid() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    @Override
    protected void handleOk() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
} 

