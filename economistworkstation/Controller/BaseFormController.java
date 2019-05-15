/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package economistworkstation.Controller;

import economistworkstation.ContractData;
import javafx.fxml.FXML;

/**
 *
 * @author fnajer
 */
public abstract class BaseFormController extends BaseController {
    protected abstract boolean isInputValid();
    protected abstract void handleOk();
    protected abstract void setData(ContractData data);
    
    private boolean okClicked = false;
    public boolean isOkClicked() {
        return okClicked;
    }
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }
    protected void closeForm() {
        okClicked = true;
        dialogStage.close();
    }
}
