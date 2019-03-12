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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

/**
 *
 * @author fnajer
 */
public class MainPageController implements Initializable {
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        staticRoot = root;
        
        renters.fire();
    }
    private static BorderPane staticRoot;
    
    public static BorderPane getRootContainer() {
        return staticRoot;
    }
    
    @FXML
    private BorderPane root;
    @FXML
    private Button renters;
    @FXML
    private Button buildings;
    @FXML
    private Button contracts;
    
    @FXML
    void displayPage(ActionEvent event) throws IOException {
        String pathName = getPathName((Node) event.getSource());
        
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(EconomistWorkstation.class.getResource(pathName));
        Parent container = loader.load();
            
        root.setCenter(container);
        root.setRight(null);
    }
    
    private String getPathName(Node src) {
        String id = src.getId();
        String name = id.substring(0, 1).toUpperCase() + id.substring(1, id.length() - 1);
        
        String pathName = String.format("View/%s/%s.fxml", name, name);
        return pathName;
    }
}
