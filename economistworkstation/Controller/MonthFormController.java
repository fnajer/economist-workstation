/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package economistworkstation.Controller;

import static economistworkstation.Controller.RenterProfileController.id;
import economistworkstation.Entity.Month;
import economistworkstation.Entity.Renter;
import economistworkstation.Model.MonthModel;
import economistworkstation.Model.RenterModel;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author fnajer
 */
public class MonthFormController implements Initializable {

    public static ContractProfileController parentWindow; 
    public static int id;
    
    public void setWindow(ContractProfileController parent) {
        parentWindow = parent;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         System.out.println(id);
        Month month = MonthModel.getMonth(id);
         System.out.println(month);
        number.setText(Integer.toString(month.number));
        date.setText(month.date);
        cost.setText(Double.toString(month.cost));
        fine.setText(Double.toString(month.fine));
        cost_water.setText(Double.toString(month.cost_water));
        cost_electricity.setText(Double.toString(month.cost_electricity));
        cost_heading.setText(Double.toString(month.cost_heading));
        paid_rent.setText(Boolean.toString(month.paid_rent));
        paid_communal.setText(Boolean.toString(month.paid_communal));
        id_contract.setText(Integer.toString(month.id_contract));
        index_water.setText(Double.toString(month.index_water));
        index_electricity.setText(Double.toString(month.index_electricity));
        index_heading.setText(Double.toString(month.index_heading));
        
        cost_water.textProperty().addListener((observable, oldValue, newValue) -> {
            double total = Double.parseDouble(cost_water.getText()) * Double.parseDouble(index_water.getText());
            label_water.setText(Double.toString(total));
        });
        index_water.textProperty().addListener((observable, oldValue, newValue) -> {
            double total = Double.parseDouble(cost_water.getText()) * Double.parseDouble(index_water.getText());
            label_water.setText(Double.toString(total));
        });
        
        cost_electricity.textProperty().addListener((observable, oldValue, newValue) -> {
            double total = Double.parseDouble(cost_electricity.getText()) * Double.parseDouble(index_electricity.getText());
            label_electricity.setText(Double.toString(total));
        });
        index_electricity.textProperty().addListener((observable, oldValue, newValue) -> {
            double total = Double.parseDouble(cost_electricity.getText()) * Double.parseDouble(index_electricity.getText());
            label_electricity.setText(Double.toString(total));
        });
        
        cost_heading.textProperty().addListener((observable, oldValue, newValue) -> {
            double total = Double.parseDouble(cost_heading.getText()) * Double.parseDouble(index_heading.getText());
            label_heading.setText(Double.toString(total));
        });
        index_heading.textProperty().addListener((observable, oldValue, newValue) -> {
            double total = Double.parseDouble(cost_heading.getText()) * Double.parseDouble(index_heading.getText());
            label_heading.setText(Double.toString(total));
        });
    }
    
    @FXML
    public void displayPage() throws Exception { 
        Parent container = FXMLLoader.load(getClass().getResource("/economistworkstation/View/Month/MonthForm.fxml"));
        
        Stage stage = new Stage();
        stage.setTitle("Редактирование месяца");
        stage.setScene(new Scene(container));
        stage.show();
    }

    @FXML
    private Button saveBtn;
    @FXML
    private Label number;
    @FXML
    private Label date;
    @FXML
    private TextField cost;
    @FXML
    private TextField fine;
    @FXML
    private TextField cost_water;
    @FXML
    private TextField cost_electricity;
    @FXML
    private TextField cost_heading;
    @FXML
    private Label paid_rent;
    @FXML
    private Label paid_communal;
    @FXML
    private Label id_contract;
    
    @FXML
    private Label label_water;
    @FXML
    private Label label_electricity;
    @FXML
    private Label label_heading;
    @FXML
    private TextField index_water;
    @FXML
    private TextField index_electricity;
    @FXML
    private TextField index_heading;
   
    @FXML
    public void updateMonth(ActionEvent event) {
        
        Month month = createMonth();
        MonthModel.updateMonth(id, month);
        Stage stage = (Stage) saveBtn.getScene().getWindow();

        stage.close();
        parentWindow.showListMonths();    
    }
    
    public Month createMonth() {
        Month month = new Month(Integer.parseInt(number.getText()), date.getText(),
                Double.parseDouble(cost.getText()), Double.parseDouble(fine.getText()),
                Double.parseDouble(cost_water.getText()), Double.parseDouble(cost_electricity.getText()),
                Double.parseDouble(cost_heading.getText()), Boolean.parseBoolean(paid_rent.getText()),
                Boolean.parseBoolean(paid_communal.getText()), Integer.parseInt(id_contract.getText()),
                Double.parseDouble(index_water.getText()), Double.parseDouble(index_electricity.getText()),
                Double.parseDouble(index_heading.getText()));
        return month;
    } 
}
