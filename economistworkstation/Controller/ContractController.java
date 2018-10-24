/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package economistworkstation.Controller;

import economistworkstation.Database;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
/**
 *
 * @author fnajer
 */
public class ContractController {
    public static void displayPage(BorderPane root, Database db) {
        Label lbl = new Label("Выберите арендатора");
        TextField textField = new TextField("Имя");
        VBox containerContracts = new VBox(10);
        
        Label selectedLbl = new Label();
        // создаем список объектов
        ObservableList<String> langs = FXCollections.observableArrayList("Java", "JavaScript", "C#", "Python");
        ListView<String> langsListView = new ListView<String>(langs);
        langsListView.setPrefSize(250, 150);
        
        FlowPane container = new FlowPane(Orientation.VERTICAL, 10, 10, lbl, textField, containerContracts);
        container.setAlignment(Pos.CENTER);
        
        root.setCenter(container);
    }
}
