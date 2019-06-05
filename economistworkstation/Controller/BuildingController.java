/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package economistworkstation.Controller;

import economistworkstation.ContractData;
import economistworkstation.Entity.Building;
import economistworkstation.Model.BuildingModel;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;


/**
 * FXML Controller class
 *
 * @author fnajer
 */
public class BuildingController extends BaseController implements Initializable {
    @FXML
    private TableView<Building> buildingTable;
    @FXML
    private TableColumn<Building, String> typeColumn;
    @FXML
    private TableColumn<Building, String> squareColumn;
    
    @FXML
    private Label typeLabel;
    @FXML
    private Label squareLabel;
    @FXML
    private Label costBalanceLabel;
    @FXML
    private Label costResidueLabel;
   
    private ObservableList<Building> buildings;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        buildings = BuildingModel.getBuildings();
        buildingTable.setItems(buildings);

        typeColumn.setCellValueFactory(
            cellData -> cellData.getValue().typeProperty());
//        squareColumn.setCellValueFactory(
//            cellData -> cellData.getValue().squareProperty().asString());
        squareColumn.setCellValueFactory(new PropertyValueFactory<>("square"));
        
        // Очистка дополнительной информации об адресате.
        showDetails(null);

        // Слушаем изменения выбора, и при изменении отображаем
        // дополнительную информацию об адресате.
        buildingTable.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> showDetails(newValue));
    }
    
    public void showDetails(Building building) {
        if (building != null) {
            typeLabel.setText(building.getType());
            squareLabel.setText(Double.toString(building.getSquare()));
            costBalanceLabel.setText(Double.toString(building.getCostBalance()));
            costResidueLabel.setText(Double.toString(building.getCostResidue()));
        } else {
            typeLabel.setText("");
            squareLabel.setText("");
            costBalanceLabel.setText("");
            costResidueLabel.setText("");
        }
    } 
    
    @FXML
    private void handleDelete() {
        int selectedIndex = buildingTable.getSelectionModel().getSelectedIndex();

        if (selectedIndex >= 0) {
            Building building = buildingTable.getSelectionModel().getSelectedItem();
            int id = building.getId();

            buildingTable.getItems().remove(building);
            BuildingModel.deleteBuilding(id);
        } else {
            showAlertWarning("Не выбран", 
                    "Здание не выбрано",
                    "Пожалуйста, выберите здание в таблице.");
        }
    }
    
    @FXML
    private void handleNewBuilding() {
        Building tempBuilding = new Building();
        boolean okClicked = openBuildingForm(tempBuilding);
        if (okClicked) {
            BuildingModel.addBuilding(tempBuilding);
            buildings.add(tempBuilding);
        }
    }

    /**
     * Вызывается, когда пользователь кликает по кнопка Edit...
     * Открывает диалоговое окно для изменения выбранного адресата.
     */
    @FXML
    private void handleEditBuilding() {
        Building selectedBuilding = buildingTable.getSelectionModel().getSelectedItem();
        if (selectedBuilding != null) {
            boolean okClicked = openBuildingForm(selectedBuilding);
            if (okClicked) {
                BuildingModel.updateBuilding(selectedBuilding.getId(), selectedBuilding);
                showDetails(selectedBuilding);
            }
        } else {
            showAlertWarning("Не выбран", 
                    "Здание не выбрано",
                    "Пожалуйста, выберите здание в таблице.");
        }
    }
    
    private boolean openBuildingForm(Building building) {
        ContractData data = new ContractData(null, null, building, null, null);
        
        return showForm(data, 
                "Редактировать здание", 
                "View/Building/BuildingForm.fxml");
    }
}
