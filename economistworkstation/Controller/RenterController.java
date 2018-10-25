/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package economistworkstation.Controller;

import economistworkstation.Database;
import economistworkstation.Model.RenterModel;
import java.sql.Statement;
import java.util.ArrayList;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import economistworkstation.Model.RenterModel;
/**
 *
 * @author fnajer
 */

class Renter {
    int id;
    String name;
    String surname;
    String patronymic;
    String address;
    String birthday;
    String person;
}

public class RenterController {
    public static void updateListRenters(Statement stmt, VBox containerRenters) {
        ArrayList renters = new ArrayList<String>();
        renters = RenterModel.getRenters(stmt);

        ObservableList listRenters = containerRenters.getChildren();
        listRenters.clear();
        for(Object renterName : renters){
            //System.out.println(renterName);
            Label lblRent = new Label(renterName.toString());
            listRenters.add(lblRent);
        }
    }
    
    public static void displayPage(BorderPane root, Database db) {
        Label lbl = new Label("Арендатор");
        TextField textField = new TextField("Имя");
        Button addBtn = new Button("Создать");
        Button delBtn = new Button("Удалить");
        Button showBtn = new Button("Показать");
        VBox containerRenters = new VBox(10);
        
        addBtn.setOnAction(new EventHandler<ActionEvent>() {
            
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
                updateListRenters(db.stmt, containerRenters);
            }
        });
        
        showBtn.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                updateListRenters(db.stmt, containerRenters);
            }
        });
        
        FlowPane container = new FlowPane(Orientation.VERTICAL, 10, 10, lbl, textField, addBtn, delBtn, showBtn, containerRenters);
        container.setAlignment(Pos.CENTER);
        
        root.setCenter(container);
        //return container;
    }
}
