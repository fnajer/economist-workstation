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
import economistworkstation.Entity.Fine;
import economistworkstation.Entity.Month;
import economistworkstation.Entity.Payment;
import economistworkstation.Entity.Period;
import economistworkstation.Entity.Rent;
import economistworkstation.Entity.Renter;
import economistworkstation.Entity.Services;
import economistworkstation.Entity.TaxLand;
import economistworkstation.ExcelCreator;
import economistworkstation.Model.BuildingModel;
import economistworkstation.Model.ContractModel;
import economistworkstation.Model.PeriodModel;
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
    private TableView<Period> periodTable;

    @FXML
    private TableColumn<Period, String> numberPeriodColumn;

    @FXML
    private TableColumn<Period, String> datePeriodColumn;

    @FXML
    private Label costLabel;

    @FXML
    private Label indexCostLabel;

    @FXML
    private Label fineLabel;

    @FXML
    private Label countWaterLabel;

    @FXML
    private Label tariffWaterLabel;

    @FXML
    private Label countElectricityLabel;

    @FXML
    private Label tariffElectricityLabel;

    @FXML
    private Label costHeadingLabel;

    @FXML
    private Label costGarbageLabel;

    @FXML
    void extendContract(ActionEvent event) {
        int countMonths = Integer.parseInt(extendCount.getText());
        
        if (countMonths > 0 && countMonths < 100) {
            periodTable.getSelectionModel().selectLast();
            Period lastPeriod = periodTable.getSelectionModel().getSelectedItem();
            
            LocalDate date = LocalDate.parse(contract.getDateEnd());
            date = date.plusMonths(countMonths);
            contract.setDateEnd(date.toString());

            ContractModel.updateContract(contract.getId(), contract, true);
            PeriodModel.addPeriods(contract.getId(), countMonths, lastPeriod);
        
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
    void handleEditPeriod(ActionEvent event) {
        Period selectedPeriod = periodTable.getSelectionModel().getSelectedItem();
        
        if (selectedPeriod != null) {
            boolean okClicked = showPeriodForm(selectedPeriod);
            if (okClicked) {
                PeriodModel.updatePeriod(selectedPeriod.getId(), selectedPeriod);
                showPeriodDetails(selectedPeriod);
            }

        } else {
            // Ничего не выбрано.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("Не выбран");
            alert.setHeaderText("Период не выбран");
            alert.setContentText("Пожалуйста, выберие период в таблице.");

            alert.showAndWait();
        }
    }
 
    
    public boolean showPeriodForm(Period period) {
        try {
            // Загружаем fxml-файл и создаём новую сцену
            // для всплывающего диалогового окна.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(EconomistWorkstation.class.getResource("View/Period/PeriodForm.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            
            // Создаём диалоговое окно Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Редактировать период");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(mainApp.getPrimaryStage());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            
            // Передаём адресата в контроллер.
            PeriodFormController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setPeriod(period);
            
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
        Period period = periodTable.getSelectionModel().getSelectedItem();
        ExcelCreator.printAccountPayment(contract, period);
    }
    
    @FXML
    void handleCalculation(ActionEvent event) throws IOException {
        Period period = periodTable.getSelectionModel().getSelectedItem();
        ExcelCreator.printAccountCalculation(contract, period);
    }
    
    private ObservableList<Contract> contracts;
    private ObservableList<Period> periods;
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
            
            showListPeriods(contract.getId());
        } else {
            firstNameLabel.setText("");
            lastNameLabel.setText("");
            patronymicLabel.setText("");
            
            typeLabel.setText("");
            squareLabel.setText("");
        }
    }
    
    private final ChangeListener<Period> listener = (observable, oldValue, newValue) -> 
            showPeriodDetails(newValue);
    
    public void showListPeriods(int id) {
        periods = PeriodModel.getPeriods(id);
       
        periodTable.getSelectionModel().selectedItemProperty().removeListener(this.listener);
        periodTable.setItems(periods);
        
        numberPeriodColumn.setCellValueFactory(new PropertyValueFactory<>("number"));
        datePeriodColumn.setCellValueFactory(new PropertyValueFactory<>("endPeriod"));
 
        // Очистка дополнительной информации об адресате.
        showPeriodDetails(null);

        // Слушаем изменения выбора, и при изменении отображаем
        // дополнительную информацию об адресате.
        periodTable.getSelectionModel().selectedItemProperty().addListener(listener);
    }
    
    private boolean isExist(Payment payment) {
        return payment != null;
    }
    
    public void showPeriodDetails(Period period) {
        clearDetails();
        if (period != null) {
            Rent rent = (Rent) period.getRentPayment();
            Fine fine = (Fine) period.getFinePayment();
            Services services = (Services) period.getServicesPayment();
            
            if (isExist(rent)) {
                costLabel.setText(Double.toString(rent.getCost()));
                indexCostLabel.setText(Double.toString(rent.getIndexCost()));
                
    //        statePaymentRentLabel.setText(Util.boolToString(period.getPaidRent()));
            }
            if (isExist(fine)) {
                fineLabel.setText(Double.toString(fine.getFine()));
            }
            if (isExist(services)) {
                setText(countWaterLabel, services.getCountWater());
                setText(tariffWaterLabel, services.getTariffWater());
                setText(countElectricityLabel, services.getCountElectricity());
                setText(tariffElectricityLabel, services.getTariffElectricity());
                setText(costHeadingLabel, services.getCostHeading());
                setText(costGarbageLabel, services.getCostGarbage());
            }
        }
    }
    
    private void setText(Label label, Double value) {
        try {
            String text = Double.toString(value);
            label.setText(text);
        } catch (NullPointerException e) {
            System.err.println(String.format(
                    "%s: value for %s from db is null", 
                    this.getClass().getSimpleName(),
                    label.getId()));
            label.setText("");
        }
    }
    
    private void clearDetails() {
        costLabel.setText("");
        indexCostLabel.setText("");
        fineLabel.setText("");

        countWaterLabel.setText("");
        tariffWaterLabel.setText("");
        countElectricityLabel.setText("");
        tariffElectricityLabel.setText("");
        costHeadingLabel.setText("");
        costGarbageLabel.setText("");
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
            alert.setTitle("Не выбран");
            alert.setHeaderText("Контракт не выбран");
            alert.setContentText("Пожалуйста, выберите контракт в таблице.");
    
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
            alert.setTitle("Не выбран");
            alert.setHeaderText("Контракт не выбран");
            alert.setContentText("Пожалуйста, выберите контракт в таблице.");

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
            dialogStage.setTitle("Редактировать контракт");
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
