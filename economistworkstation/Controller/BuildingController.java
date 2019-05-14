/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package economistworkstation.Controller;

import economistworkstation.ContractData;
import economistworkstation.Entity.Building;
import economistworkstation.Model.BuildingModel;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


/**
 * FXML Controller class
 *
 * @author fnajer
 */
public class BuildingController extends BaseController {
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
        boolean okClicked = showBuildingForm(tempBuilding);
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
            boolean okClicked = showBuildingForm(selectedBuilding);
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
    
    public boolean showBuildingForm(Building building) {
        try {
            FXMLLoader loader = new FXMLLoader();
            AnchorPane container = loadFXML("View/Building/BuildingForm.fxml", loader);
            
            Stage dialogStage = createDialog("Редактировать здание", container);
            
            // Передаём адресата в контроллер.
            BaseFormController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            ContractData data = new ContractData(null, null, building, null, null);
            controller.setData(data);
            
            // Отображаем диалоговое окно и ждём, пока пользователь его не закроет
            dialogStage.showAndWait();
            
            return controller.isOkClicked();
        } catch (IOException ex) {
            Logger.getLogger(BuildingController.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
}
