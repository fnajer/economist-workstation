/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package economistworkstation;

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
import javafx.scene.layout.VBox;
/**
 *
 * @author fnajer
 */
public class EconomistWorkstation extends Application {
     
    @Override
    public void start(Stage primaryStage) {
        
        Database db = new Database();
        
        db.connect();
        
        Label lbl = new Label("Арендатор");
        TextField textField = new TextField("Имя");
        Button btn = new Button("Создать");
        Button delBtn = new Button("Удалить");
        Button showBtn = new Button("Показать");
        VBox containerRenters = new VBox(10);
                
        btn.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                String name = textField.getText();
                RenterModel.addRenter(db.stmt, name);
                RenterController.updateListRenters(db.stmt, containerRenters);
            }
        });
        
        
        
        delBtn.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                String name = textField.getText();
                RenterModel.deleteRenter(db.stmt, name);
                RenterController.updateListRenters(db.stmt, containerRenters);
            }
        });
        
        showBtn.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                RenterController.updateListRenters(db.stmt, containerRenters);
            }
        });
        
        FlowPane root = new FlowPane(Orientation.VERTICAL, 10, 10, lbl, textField, btn, delBtn, showBtn, containerRenters);
        root.setAlignment(Pos.CENTER);
         
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
