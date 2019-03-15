/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package economistworkstation.Controller;

import economistworkstation.Entity.Building;
import economistworkstation.Entity.Contract;
import economistworkstation.Entity.Month;
import economistworkstation.Entity.Renter;
import economistworkstation.Model.BuildingModel;
import economistworkstation.Model.ContractModel;
import economistworkstation.Model.MonthModel;
import economistworkstation.Model.RenterModel;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author fnajer
 */
public class ContractProfileController implements Initializable {
    
    public ContractProfileController() {
       // id = ContractController.getIdCurrentContract();
    }
    
    private int id;
    private Month lastMonth;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) { 
        Contract contract = ContractModel.getContract(id);
        
//        Renter renter = RenterModel.getRenter(contract.id_renter);
//        Building building = BuildingModel.getBuilding(contract.id_building);
//        
//        name.setText(renter.name.get());
//        surname.setText(renter.surname);
//        patronymic.setText(renter.patronymic);
//        
//        type.setText(building.type);
//        square.setText(Double.toString(building.square));
        showListMonths();
    }   
    
    @FXML
    private Button updateBtn;
    @FXML
    private TextField extendCount;
    @FXML
    private Label name;
    @FXML
    private Label surname;
    @FXML
    private Label patronymic;
    @FXML
    private Label type;
    @FXML
    private Label square;
    @FXML
    private VBox containerMonths;
    
    @FXML
    public void runEditForm(ActionEvent event) throws IOException {
        //ContractController.getContractController()
         //       .showContractForm("Обновить", "Редактирование");
    } 

    @FXML
    public void showListMonths() {
       // ArrayList<Month> months = MonthModel.getMonths(id);
        
        ObservableList listMonths = containerMonths.getChildren();  
        listMonths.clear();
        
//        for(Month month : months){
//            //Label lblName = new Label(month.date + ' ' + month.number);
//            Button infoBtn = new Button("Подробно");
//            Button paymentBtn = new Button("Вывод расчетов");
//            Button accountBtn = new Button("Выписать счет");
//           
//            lastMonth = month;
//            
//            infoBtn.setOnAction((ActionEvent event) -> {
//                //showMonthForm(month.id);
//            });
//            
//            paymentBtn.setOnAction((ActionEvent event) -> {
//            });
//            
//            accountBtn.setOnAction((ActionEvent event) -> {
//            });
//            
//            //FlowPane contractContainer = new FlowPane(10, 10, lblName, infoBtn, paymentBtn, accountBtn);
//            //listMonths.add(contractContainer);
        //}
    }

    @FXML
    public void extendContract() {
        int countMonths = Integer.parseInt(extendCount.getText());
        
        if (countMonths > 0) {
            MonthModel.addMonths(id, countMonths, lastMonth);
        
            showListMonths();
        }
    }
    
    @FXML
    public void showMonthForm(int id_month) {
        MonthFormController monthFormController = new MonthFormController();
        monthFormController.setWindow(this);
        monthFormController.setId(id_month);
        try {
            monthFormController.displayPage();
        } catch (Exception ex) {
            Logger.getLogger(ContractProfileController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
