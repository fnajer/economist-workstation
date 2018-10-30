/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package economistworkstation;

import economistworkstation.Controller.RenterController;
import economistworkstation.Controller.MenuController;
import economistworkstation.Controller.SidebarController;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.geometry.Orientation;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.VBox;
import javafx.scene.layout.BorderPane;

/**
 *
 * @author fnajer
 */
public class EconomistWorkstation extends Application {
     
    @Override
    public void start(Stage primaryStage) {
        //Database db = Database.getInstance();

        BorderPane root = new BorderPane();
        
        MenuBar menu = MenuController.createMenu();
        VBox sidebar = SidebarController.createSidebar(root);
        
        root.setTop(menu);
        root.setLeft(sidebar);
        
        Scene scene = new Scene(root, 300, 250);
        
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
