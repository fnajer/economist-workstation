/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package economistworkstation.Controller;

import economistworkstation.Database;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;

/**
 *
 * @author fnajer
 */
public class SidebarController {

    public static VBox createSidebar(BorderPane root, Database db) {
        Button renters = new Button("Арендаторы");
        Button contracts = new Button("Договоры");
        VBox leftSidebar = new VBox(10, renters, contracts);
        
        renters.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                RenterController.displayPage(root, db);
            }
        });
        
        renters.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                RenterController.displayPage(root, db);
            }
        });
   
        return leftSidebar;
    }
}
