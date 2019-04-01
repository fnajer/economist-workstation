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
import economistworkstation.ExcelCreator;
import economistworkstation.Model.BuildingModel;
import economistworkstation.Model.ContractModel;
import economistworkstation.Model.MonthModel;
import economistworkstation.Model.RenterModel;
import economistworkstation.Util.Util;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.Label;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
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
    private Label countWaterLabel;

    @FXML
    private Label tariffWaterLabel;

    @FXML
    private Label countElectricityLabel;

    @FXML
    private Label tariffElectricityLabel;

    @FXML
    private Label countHeadingLabel;

    @FXML
    private Label tariffHeadingLabel;

    @FXML
    private Label countGarbageLabel;

    @FXML
    private Label tariffGarbageLabel;
    
    @FXML
    private Label isPaidCommunalLabel;

    @FXML
    void extendContract(ActionEvent event) {
        int countMonths = Integer.parseInt(extendCount.getText());
        
        if (countMonths > 0 && countMonths < 100) {
            monthTable.getSelectionModel().selectLast();
            Month lastMonth = monthTable.getSelectionModel().getSelectedItem();
            
            LocalDate date = LocalDate.parse(contract.getDateEnd());
            date = date.plusMonths(countMonths);
            contract.setDateEnd(date.toString());

            ContractModel.updateContract(contract.getId(), contract, true);
            MonthModel.addMonths(contract.getId(), countMonths, lastMonth);
        
            showDetails(contract);
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("Не выбрано");
            alert.setHeaderText("Значение для продления не выбрано");
            alert.setContentText("Пожалуйста, введите количество месяцев для продления аренды.");
    
            alert.showAndWait();
        }
    }

    @FXML
    void handleEditMonth(ActionEvent event) {
        Month selectedMonth = monthTable.getSelectionModel().getSelectedItem();
        
        if (selectedMonth != null) {
            boolean okClicked = showMonthForm(selectedMonth);
            if (okClicked) {
                MonthModel.updateMonth(selectedMonth.getId(), selectedMonth);
                showMonthDetails(selectedMonth);
            }

        } else {
            // Ничего не выбрано.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Month Selected");
            alert.setContentText("Please select a month in the table.");

            alert.showAndWait();
        }
    }
 
    
    public boolean showMonthForm(Month month) {
        try {
            // Загружаем fxml-файл и создаём новую сцену
            // для всплывающего диалогового окна.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(EconomistWorkstation.class.getResource("View/Month/MonthForm.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            
            // Создаём диалоговое окно Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Month");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(mainApp.getPrimaryStage());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            
            // Передаём адресата в контроллер.
            MonthFormController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setMonth(month);
            
            // Отображаем диалоговое окно и ждём, пока пользователь его не закроет
            dialogStage.showAndWait();
            
            return controller.isOkClicked();
        } catch (IOException ex) {
            Logger.getLogger(ContractController.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    @FXML
    void handleAccount(ActionEvent event) throws IOException {
        Month month = monthTable.getSelectionModel().getSelectedItem();
        ExcelCreator.printAccountPayment(contract, month);
    }
    
    @FXML
    void handleCalculation(ActionEvent event) throws IOException {
        Month month = monthTable.getSelectionModel().getSelectedItem();
        ExcelCreator.printAccountCalculation(contract, month);
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
        idRenterColumn.setCellValueFactory(new PropertyValueFactory<>("renter"));
        
        // Очистка дополнительной информации об адресате.
        showDetails(null);

        // Слушаем изменения выбора, и при изменении отображаем
        // дополнительную информацию об адресате.
        contractTable.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> showDetails(newValue));
     
        
        contractTable.setRowFactory((tableView) -> createRow());
    }
    
    private TableRow<Contract> createRow() {
        TableRow<Contract> row = new TableRow<>();
            
        ChangeListener<String> timeoutContractListener = (obs, oldEndDate, newEndDate) -> {
            checkTimeoutContract(row, newEndDate);
        };
        row.itemProperty().addListener((obs, previousContract, currentContract) -> {

            if (previousContract != null) {
                previousContract.dateEndProperty().removeListener(timeoutContractListener);
            }
            if (currentContract != null) {
                currentContract.dateEndProperty().addListener(timeoutContractListener);
                checkTimeoutContract(row, currentContract.getDateEnd());
            } else {
                row.getStyleClass().removeAll(Collections.singleton("redRow"));
            }
        });

        return row;
    }
    
    private void checkTimeoutContract(TableRow<Contract> row, String newEndDate) {
        LocalDate currentDate = LocalDate.now();
        LocalDate endDate = LocalDate.parse(newEndDate);
        LocalDate warningDate = endDate.minusMonths(3);
        if (currentDate.isAfter(warningDate) || currentDate.isEqual(warningDate)) {
            row.getStyleClass().add("redRow");
        } else {
            row.getStyleClass().removeAll(Collections.singleton("redRow"));
        }
    }
    
    private Contract contract;
    private Renter renter;
    private Building building;
    
    public void showDetails(Contract contract) {
        System.out.println(contract);
        this.contract = contract;
        if (contract != null) {
            
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

//            Button paymentBtn = new Button("Вывод расчетов");
//            Button accountBtn = new Button("Выписать счет");

    }
    
    public void showMonthDetails(Month month) {
        if (month != null) {
            costLabel.setText(Double.toString(month.getCost()));
            indexCostLabel.setText(Double.toString(month.getIndexCost()));
            fineLabel.setText(Double.toString(month.getFine()));
            isPaidRentLabel.setText(Util.boolToString(month.getPaidRent()));
            
            countWaterLabel.setText(Double.toString(month.getCountWater()));
            tariffWaterLabel.setText(Double.toString(month.getTariffWater()));
            countElectricityLabel.setText(Double.toString(month.getCountElectricity()));
            tariffElectricityLabel.setText(Double.toString(month.getTariffElectricity()));
            countHeadingLabel.setText(Double.toString(month.getCountHeading()));
            tariffHeadingLabel.setText(Double.toString(month.getTariffHeading()));
            countGarbageLabel.setText(Double.toString(month.getCountGarbage()));
            tariffGarbageLabel.setText(Double.toString(month.getTariffGarbage()));
            isPaidCommunalLabel.setText(Util.boolToString(month.getPaidCommunal()));
            
        } else {
            costLabel.setText("");
            indexCostLabel.setText("");
            fineLabel.setText("");
            isPaidRentLabel.setText("");
            
            countWaterLabel.setText("");
            tariffWaterLabel.setText("");
            countElectricityLabel.setText("");
            tariffElectricityLabel.setText("");
            countHeadingLabel.setText("");
            tariffHeadingLabel.setText("");
            countGarbageLabel.setText("");
            tariffGarbageLabel.setText("");
            isPaidCommunalLabel.setText("");
        }
    }
    
    @FXML
    private void handleDelete() {
        int selectedIndex = contractTable.getSelectionModel().getSelectedIndex();

        if (selectedIndex >= 0) {
            ContractModel.deleteContract(contract.getId());
            contractTable.getItems().remove(contract);

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
        showDetails(contract); // to anew get renter and building
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
                ContractModel.updateContract(selectedContract.getId(), selectedContract, false);
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
}
