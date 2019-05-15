/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package economistworkstation.Controller;

import economistworkstation.ContractData;
import economistworkstation.Entity.BalanceTable;
import economistworkstation.Entity.Building;
import java.net.URL;
import java.util.ResourceBundle;

import economistworkstation.Entity.Contract;
import economistworkstation.Entity.Field;
import economistworkstation.Entity.Period;
import economistworkstation.Entity.Renter;
import economistworkstation.Model.BuildingModel;
import economistworkstation.Model.ContractModel;
import economistworkstation.Model.PeriodModel;
import economistworkstation.Model.RenterModel;
import static economistworkstation.Util.Util.isExist;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.Label;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
/**
 *
 * @author fnajer
 */
public class ContractController extends BaseController implements Initializable {
    
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
    private Label taxLandLabel;
    @FXML
    private Label equipmentLabel;
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
    private Label costInternetLabel;
    @FXML
    private Label costTelephoneLabel;

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
            showAlertWarning("Не выбран", 
                    "Значение для продления не выбрано",
                    "Пожалуйста, введите количество месяцев для продления аренды.");
        }
    }
    
    public void recalculateBalance(Period period) {
        Period nextPeriod;
        
        int length = periods.size();
        int i = periods.indexOf(period) + 1;
        boolean tableIsExist = false;
        for (; i < length; i++) {
            nextPeriod = periods.get(i);
            
            BalanceTable balanceTable = nextPeriod.getBalanceTable();
            BalanceTable prevBalanceTable = period.getNextBalanceTable();
            
            if (isExist(balanceTable))
                tableIsExist = true;
            
            if (!prevBalanceTable.isEmpty() && tableIsExist) { //update
                prevBalanceTable.setId(nextPeriod.getId());
            } else if (tableIsExist && prevBalanceTable.isEmpty()) {
                prevBalanceTable.prepareToDelete();
                prevBalanceTable.setId(nextPeriod.getId());
            }
            nextPeriod.setBalanceTable(prevBalanceTable);
            PeriodModel.updateBalancePeriod(nextPeriod); //BalanceTable will be empty or filled. Not null.
            nextPeriod.calculateBalance();
            
            period = nextPeriod;
            tableIsExist = false;
        }
    }
    
    @FXML
    private void handleEditPeriod(ActionEvent event) {
        Period selectedPeriod = periodTable.getSelectionModel().getSelectedItem();
        
        if (selectedPeriod != null) {
            boolean okClicked = openPeriodForm(selectedPeriod);
            if (okClicked) {
                PeriodModel.updatePeriod(selectedPeriod.getId(), selectedPeriod);
                recalculateBalance(selectedPeriod);
                showPeriodDetails(selectedPeriod);
            }
        } else {
            showAlertWarning("Не выбран", 
                    "Период не выбран",
                    "Пожалуйста, выберите период в таблице.");
        }
    }
 
    private boolean openPeriodForm(Period period) {
        ContractData data = new ContractData(null, period, null, null, contract);
        
        return showForm(data, 
                "Редактировать период", 
                "View/Period/PeriodForm.fxml");
    }
    
    @FXML
    private void handlePrintInvoice() {
        Period selectedPeriod = periodTable.getSelectionModel().getSelectedItem();
        
        if (selectedPeriod != null) {
            boolean okClicked = openInvoiceForm(selectedPeriod);
            if (okClicked) {
                showAlertSuccess("Успех", 
                    "Создание счета",
                    "Счет создан успешно.");
            }
        } else {
            showAlertWarning("Не выбран", 
                    "Период не выбран",
                    "Пожалуйста, выберите период в таблице.");
        }
    }
    
    private boolean openInvoiceForm(Period period) {
        ContractData data = new ContractData(null, period, null, null, contract);
        
        return showForm(data, 
                "Управление счетами", 
                "View/InvoiceForm.fxml");
    }
    
    private ObservableList<Contract> contracts;
    private ObservableList<Period> periods;
    
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
    
    private Field fields;
    private void bindFields() {
        this.fields = new Field();
        
        Map<String, Label> labels = new HashMap<>();
        labels.put("costRent", costLabel);
        labels.put("indexCostRent", indexCostLabel);
        labels.put("costFine", fineLabel);
        labels.put("costTaxLand", taxLandLabel);
        labels.put("costEquipment", equipmentLabel);
        labels.put("costCountWater", countWaterLabel);
        labels.put("tariffWater", tariffWaterLabel);
        labels.put("costCountElectricity", countElectricityLabel);
        labels.put("tariffElectricity", tariffElectricityLabel);
        labels.put("costHeading", costHeadingLabel);
        labels.put("costGarbage", costGarbageLabel);
        labels.put("costInternet", costInternetLabel);
        labels.put("costTelephone", costTelephoneLabel);
     
        fields.setLabels(labels);
    }
    
    public void showPeriodDetails(Period period) {
        clearDetails();
        if (period != null) {
            bindFields();
            
            fields.fillLabels(period);
        }
    }
    
    private void clearDetails() {
        costLabel.setText("Нет");
        indexCostLabel.setText("Нет");
        fineLabel.setText("Нет");
        taxLandLabel.setText("Нет");
        equipmentLabel.setText("Нет");

        countWaterLabel.setText("Нет");
        tariffWaterLabel.setText("Нет");
        countElectricityLabel.setText("Нет");
        tariffElectricityLabel.setText("Нет");
        costHeadingLabel.setText("Нет");
        costGarbageLabel.setText("Нет");
        costInternetLabel.setText("Нет");
        costTelephoneLabel.setText("Нет");
    }
    
    @FXML
    private void handleDelete() {
        int selectedIndex = contractTable.getSelectionModel().getSelectedIndex();

        if (selectedIndex >= 0) {
            ContractModel.deleteContract(contract.getId());
            contractTable.getItems().remove(contract);

        } else {
            showAlertWarning("Не выбран", 
                    "Контракт не выбран",
                    "Пожалуйста, выберите контракт в таблице.");
        }
    }
    
    @FXML
    private void handleNewContract() {
        Contract tempContract = new Contract();
        renter = null;
        building = null;
        boolean okClicked = openContractForm(tempContract);
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
            boolean okClicked = openContractForm(selectedContract);
            if (okClicked) {
                ContractModel.updateContract(selectedContract.getId(), selectedContract, false);
                showDetails(selectedContract);
            }

        } else {
            showAlertWarning("Не выбран", 
                    "Контракт не выбран",
                    "Пожалуйста, выберите контракт в таблице.");
        }
    }
    
    private boolean openContractForm(Contract contract) {
        ContractData data = new ContractData(null, null, building, renter, contract);
        
        return showForm(data, 
                "Редактировать контракт", 
                "View/Contract/ContractForm.fxml");
    }
}
