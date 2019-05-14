/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package economistworkstation.Controller;

import economistworkstation.EconomistWorkstation;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author fnajer
 */
public abstract class BaseController implements Initializable {
    @Override
    public abstract void initialize(URL location, ResourceBundle bundle);
    
    protected EconomistWorkstation mainApp;
    protected void setMainApp(EconomistWorkstation mainApp) {
        this.mainApp = mainApp;
    }
}
