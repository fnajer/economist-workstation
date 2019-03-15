/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package economistworkstation.Controller;

import economistworkstation.EconomistWorkstation;
import economistworkstation.Entity.Building;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

import economistworkstation.Entity.Contract;
import economistworkstation.Entity.Month;
import economistworkstation.Entity.Renter;
import economistworkstation.Model.BuildingModel;
import economistworkstation.Model.ContractModel;
import economistworkstation.Model.MonthModel;
import economistworkstation.Model.RenterModel;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
/**
 *
 * @author fnajer
 */
public class ContractController implements Initializable, BaseController {
    
    @FXML
    private TableView<Contract> contractTable;

    @FXML
    private TableColumn<Contract, String> numberContractColumn;

    @FXML
    private TableColumn<Contract, String> idRenterColumn;

    @FXML
    private Label firstNameLabel;

    @FXML
    private Label lastNameLabel;

    @FXML
    private Label patronymicLabel;

    @FXML
    private Label typeLabel;

    @FXML
    private Label squareLabel;

    @FXML
    private TextField extendCount;

    @FXML
    private TableView<Month> monthTable;

    @FXML
    private TableColumn<Month, String> numberMonthColumn;

    @FXML
    private TableColumn<Month, String> dateMonthColumn;

    @FXML
    private Label costLabel;

    @FXML
    private Label indexCostLabel;

    @FXML
    private Label fineLabel;

    @FXML
    private Label isPaidRentLabel;

    @FXML
    private Label costWaterLabel;

    @FXML
    private Label indexWaterLabel;

    @FXML
    private Label costElectricityLabel;

    @FXML
    private Label indexElectricityLabel;

    @FXML
    private Label costHeadingLabel;

    @FXML
    private Label indexHeadingLabel;

    @FXML
    private Label isPaidCommunalLabel;

    @FXML
    void extendContract(ActionEvent event) {
        int countMonths = Integer.parseInt(extendCount.getText());
        
        if (countMonths > 0 && countMonths < 100) {
            monthTable.getSelectionModel().selectLast();
            Month lastMonth = monthTable.getSelectionModel().getSelectedItem();
            
            LocalDate date = LocalDate.parse(contract.getDateEnd());
            System.out.println(date);
            date = date.plusMonths(countMonths);
            contract.setDateEnd(date.toString());
            System.out.println(date);
            ContractModel.updateContract(contract.getId(), contract);
            MonthModel.addMonths(contract.getId(), countMonths, lastMonth);
        
            showDetails(contract);
            //showListMonths(contract.getId());
        }
    }

    @FXML
    void handleEditMonth(ActionEvent event) {

    }
    
    private ObservableList<Contract> contracts;
    private ObservableList<Month> months;
    private EconomistWorkstation mainApp;
    
    @Override
    public void setMainApp(EconomistWorkstation mainApp) {
        this.mainApp = mainApp;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
        contracts = ContractModel.getContracts();
        contractTable.setItems(contracts);

        numberContractColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        idRenterColumn.setCellValueFactory(new PropertyValueFactory<>("idRenter"));
        
        // Очистка дополнительной информации об адресате.
        showDetails(null);

        // Слушаем изменения выбора, и при изменении отображаем
        // дополнительную информацию об адресате.
        contractTable.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> showDetails(newValue));
    }
    
    private Contract contract;
    private Renter renter;
    private Building building;
    
    public void showDetails(Contract contract) {
        System.out.println(contract);
        this.contract = contract;
        if (contract != null) {
            this.contract = contract;
            renter = RenterModel.getRenter(contract.getIdRenter());
            building = BuildingModel.getBuilding(contract.getIdBuilding());
            
            firstNameLabel.setText(renter.getFirstName());
            lastNameLabel.setText(renter.getLastName());
            patronymicLabel.setText(renter.getPatronymic());
            
            typeLabel.setText(building.getType());
            squareLabel.setText(Double.toString(building.getSquare()));
            
            showListMonths(contract.getId());
        } else {
            firstNameLabel.setText("");
            lastNameLabel.setText("");
            patronymicLabel.setText("");
            
            typeLabel.setText("");
            squareLabel.setText("");
        }
    }
    private final ChangeListener<Month> listener = (observable, oldValue, newValue) -> 
            showMonthDetails(newValue);
    
    public void showListMonths(int id) {
        months = MonthModel.getMonths(id);
       
        monthTable.getSelectionModel().selectedItemProperty().removeListener(this.listener);
        monthTable.setItems(months);
        
        numberMonthColumn.setCellValueFactory(new PropertyValueFactory<>("number"));
        dateMonthColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
 
        // Очистка дополнительной информации об адресате.
        showMonthDetails(null);

        // Слушаем изменения выбора, и при изменении отображаем
        // дополнительную информацию об адресате.
        monthTable.getSelectionModel().selectedItemProperty().addListener(listener);

//        ArrayList<Month> months = MonthModel.getMonths(id);
//        
//        ObservableList listMonths = containerMonths.getChildren();  
//        listMonths.clear();
//        
//        for(Month month : months){
//            Label lblName = new Label(month.date + ' ' + month.number);
//            Button infoBtn = new Button("Подробно");
//            Button paymentBtn = new Button("Вывод расчетов");
//            Button accountBtn = new Button("Выписать счет");
//           
//            lastMonth = month;
//            
//            infoBtn.setOnAction((ActionEvent event) -> {
//                showMonthForm(month.id);
//            });
//            
//            paymentBtn.setOnAction((ActionEvent event) -> {
//            });
//            
//            accountBtn.setOnAction((ActionEvent event) -> {
//            });
//            
//            FlowPane contractContainer = new FlowPane(10, 10, lblName, infoBtn, paymentBtn, accountBtn);
//            listMonths.add(contractContainer);
//        }
    }
    
    public void showMonthDetails(Month month) {
        System.out.println(month);
        if (month != null) {
            costLabel.setText(Double.toString(month.getCost()));
            indexCostLabel.setText(Double.toString(month.getIndexCost()));
            fineLabel.setText(Double.toString(month.getFine()));
            isPaidRentLabel.setText(Boolean.toString(month.getPaidRent()));
            
            costWaterLabel.setText(Double.toString(month.getCostWater()));
            indexWaterLabel.setText(Double.toString(month.getIndexWater()));
            costElectricityLabel.setText(Double.toString(month.getCostElectricity()));
            indexElectricityLabel.setText(Double.toString(month.getIndexElectricity()));
            costHeadingLabel.setText(Double.toString(month.getCostHeading()));
            indexHeadingLabel.setText(Double.toString(month.getIndexHeading()));
            isPaidCommunalLabel.setText(Boolean.toString(month.getPaidCommunal()));
            
        } else {
            costLabel.setText("");
            indexCostLabel.setText("");
            fineLabel.setText("");
            isPaidRentLabel.setText("");
            
            costWaterLabel.setText("");
            indexWaterLabel.setText("");
            costElectricityLabel.setText("");
            indexElectricityLabel.setText("");
            costHeadingLabel.setText("");
            indexHeadingLabel.setText("");
            isPaidCommunalLabel.setText("");
        }
    }
    
    @FXML
    private void handleDelete() {
        int selectedIndex = contractTable.getSelectionModel().getSelectedIndex();

        if (selectedIndex >= 0) {
            int id = contract.getId();
            ObservableList<Contract> items = contractTable.getItems();
            int lastIndex = items.lastIndexOf(contract);
            //contractTable.getSelectionModel().select(selectedIndex);
            
            System.out.println(selectedIndex);
System.out.println(lastIndex);
            contractTable.getItems().remove(contract);
            ContractModel.deleteContract(id);
            
            if (selectedIndex == lastIndex) {
                contractTable.getSelectionModel().focus(lastIndex);
                contractTable.getSelectionModel().selectLast();
            } else {
                contractTable.getSelectionModel().focus(selectedIndex);
                contractTable.getSelectionModel().select(selectedIndex);
            }
            contract = null;
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Contract Selected");
            alert.setContentText("Please select a contract in the table.");
    
            alert.showAndWait();
        }
    }
    
    @FXML
    private void handleNewContract() {
        Contract tempContract = new Contract();
        renter = null;
        building = null;
        boolean okClicked = showContractForm(tempContract);
        if (okClicked) {
            int id = ContractModel.addContract(tempContract);
            tempContract.setId(id);
            contracts.add(tempContract);
            contractTable.getSelectionModel().select(tempContract);
        }
    }

    /**
     * Вызывается, когда пользователь кликает по кнопка Edit...
     * Открывает диалоговое окно для изменения выбранного адресата.
     */
    @FXML
    private void handleEditContract() {
        Contract selectedContract = contractTable.getSelectionModel().getSelectedItem();
        if (selectedContract != null) {
            boolean okClicked = showContractForm(selectedContract);
            if (okClicked) {
                ContractModel.updateContract(selectedContract.getId(), selectedContract);
                showDetails(selectedContract);
            }

        } else {
            // Ничего не выбрано.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Person Selected");
            alert.setContentText("Please select a person in the table.");

            alert.showAndWait();
        }
    }
    
    public boolean showContractForm(Contract contract) {
        try {
            // Загружаем fxml-файл и создаём новую сцену
            // для всплывающего диалогового окна.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(EconomistWorkstation.class.getResource("View/Contract/ContractForm.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            
            // Создаём диалоговое окно Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Person");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(mainApp.getPrimaryStage());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            
            // Передаём адресата в контроллер.
            ContractFormController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setContract(contract, renter, building);
            
            // Отображаем диалоговое окно и ждём, пока пользователь его не закроет
            dialogStage.showAndWait();
            
            return controller.isOkClicked();
        } catch (IOException ex) {
            Logger.getLogger(ContractController.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
//    
//    public ContractController() {
//        //root = MainPageController.getRootContainer();
//        contractController = this;
//    }

//    @Override
//    public void initialize(URL url, ResourceBundle rb) {    
//        showListContracts();
//    }
//
//    private static int currentId;
//    private static String typeForm;
//    private static ContractController contractController;
//    private BorderPane root;
//    
//    public static int getIdCurrentContract() {
//        return currentId;
//    }
//    public static String getTypeForm() {
//        return typeForm;
//    }
//    public static ContractController getContractController() {
//        return contractController;
//    }
//    
//    @FXML
//    private VBox containerContracts;
//       
//    @FXML
//    public void showListContracts() {
//        ArrayList<Contract> contracts = ContractModel.getContracts();
//
//        ObservableList listContracts = containerContracts.getChildren();  
//        listContracts.clear();
//        
//        for(Contract contract : contracts){
//            Label lblName = new Label(Integer.toString(contract.id));
//            Button delBtn = new Button("X");
//            Button infoBtn = new Button("Подробно");
//            
//            delBtn.setOnAction((ActionEvent event) -> {
//                delContract(contract.id);
//            });
//            
//            infoBtn.setOnAction((ActionEvent event) -> {
//                try {
//                    currentId = contract.id;
//                    openProfile();
//                } catch (IOException ex) {
//                    Logger.getLogger(ContractController.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            });
//            
//            FlowPane contractContainer = new FlowPane(10, 10, lblName, delBtn, infoBtn);
//            listContracts.add(contractContainer);
//        }
//    }
//    
//    public void delContract(int id) {
//        ContractModel.deleteContract(id);
//        showListContracts();
//    }
//    
//    public void openProfile() throws IOException {
//        Parent container = FXMLLoader.load(getClass().getResource("/economistworkstation/View/Contract/ContractProfile.fxml"));
//
//        root.setRight(container);
//    }
//    
//    @FXML
//    public void runAddForm(ActionEvent event) throws IOException {
//        showContractForm("Добавить", "Создание");
//    }
//    
//    @FXML
//    public void showContractForm(String type, String title) throws IOException {
//        typeForm = type;
//        Parent container = FXMLLoader.load(getClass().getResource("/economistworkstation/View/Contract/ContractForm.fxml"));
//        
//        Stage stage = new Stage();
//        stage.setTitle(String.format("%s договора", title));
//        stage.setScene(new Scene(container));
//        stage.show();
//    }
//    
//    public void closeForm(Stage stage) {
//        stage.close();
//        root.setRight(null);
//        
//        showListContracts();
//    }
}
