/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package economistworkstation;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;

/**
 *
 * @author fnajer
 */
public class EconomistWorkstation extends Application {
    
    @Override
    public void start(Stage primaryStage) throws Exception {

        MenuBar menu = FXMLLoader.load(getClass().getResource("/economistworkstation/View/Menu.fxml"));
        BorderPane root = FXMLLoader.load(getClass().getResource("/economistworkstation/View/MainPage.fxml"));
        
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
