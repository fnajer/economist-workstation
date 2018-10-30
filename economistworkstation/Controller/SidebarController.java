/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package economistworkstation.Controller;

import economistworkstation.Database;
import java.util.logging.Level;
import java.util.logging.Logger;
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

    public static VBox createSidebar(BorderPane root) {
        Database db = Database.getInstance();
        
        Button renters = new Button("Арендаторы");
        Button contracts = new Button("Договоры");
        VBox leftSidebar = new VBox(10, renters, contracts);
        
        renters.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                RenterController renterController = new RenterController();
                try {
                    renterController.displayPage(root);
                } catch (Exception ex) {
                    Logger.getLogger(SidebarController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        contracts.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                ContractController contractController = new ContractController();
                try {
                    contractController.displayPage(root);
                } catch (Exception ex) {
                    Logger.getLogger(SidebarController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
   
        return leftSidebar;
    }
}
