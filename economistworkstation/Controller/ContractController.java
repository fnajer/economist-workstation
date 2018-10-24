/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package economistworkstation.Controller;

import economistworkstation.Database;
import economistworkstation.Model.RenterModel;
import java.util.ArrayList;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.scene.control.ListView;
/**
 *
 * @author fnajer
 */
public class ContractController {
    public static void displayPage(BorderPane root, Database db) {
        Label lbl = new Label("Выберите арендатора");
   
        ArrayList renters = new ArrayList<String>();
        renters = RenterModel.getRenters(db.stmt);

        // создаем список объектов
        ObservableList<String> langs = FXCollections.observableArrayList(renters);
        ListView<String> langsListView = new ListView<String>(langs);
        langsListView.setPrefSize(250, 150);
        
        FlowPane container = new FlowPane(Orientation.VERTICAL, 10, 10, lbl, langsListView);
        container.setAlignment(Pos.CENTER);
        
        root.setCenter(container);
    }
}
