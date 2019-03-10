/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package economistworkstation;

import economistworkstation.Controller.MenuController;
import economistworkstation.Controller.RenterController;
import economistworkstation.Controller.MainPageController;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.VBox;
import javafx.scene.layout.BorderPane;

/**
 *
 * @author fnajer
 */
public class EconomistWorkstation extends Application {
    private static BorderPane root;
    
    public static BorderPane getRootContainer() {
        return root;
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception {

        MenuBar menu = FXMLLoader.load(getClass().getResource("/economistworkstation/View/Menu.fxml"));
        root = FXMLLoader.load(getClass().getResource("/economistworkstation/View/MainPage.fxml"));
        
        root.setTop(menu);
        Scene scene = new Scene(root);
        
        primaryStage.setTitle("Economist Workstation");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
