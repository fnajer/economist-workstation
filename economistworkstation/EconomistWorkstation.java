/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package economistworkstation;

import economistworkstation.Controller.MenuController;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

/**
 *
 * @author fnajer
 */
public class EconomistWorkstation extends Application {
    
    private Stage primaryStage;
    private BorderPane rootLayout;
    private MenuBar menu;
    
    @Override
    public void start(Stage primaryStage) throws Exception {

        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Economist Workstation");
        
        initRootLayout();
        initMenu();
        
       // showMainPage();
    }
    
    public void initRootLayout() throws IOException {
         // Загружаем корневой макет из fxml файла.
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(EconomistWorkstation.class.getResource("View/RootLayout.fxml"));
        rootLayout = (BorderPane) loader.load();

        // Отображаем сцену, содержащую корневой макет.
        Scene scene = new Scene(rootLayout);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public void initMenu() throws IOException {
        FXMLLoader loaderMenu = new FXMLLoader();
        loaderMenu.setLocation(EconomistWorkstation.class.getResource("View/Menu.fxml"));
        menu = (MenuBar) loaderMenu.load();

        rootLayout.setTop(menu); 
        
        MenuController controller = loaderMenu.getController();
        controller.setRoot(rootLayout);
        controller.setMainApp(this);
    }
    
    public void showMainPage() throws IOException {
        // Загружаем сведения об адресатах.
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(EconomistWorkstation.class.getResource("View/MainPage.fxml"));
        AnchorPane mainPage = (AnchorPane) loader.load();

        // Помещаем сведения об адресатах в центр корневого макета.
        rootLayout.setCenter(mainPage);
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
