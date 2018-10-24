/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package economistworkstation.Controller;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
/**
 *
 * @author fnajer
 */
public class MenuController {
    public static MenuBar createMenu() {
        MenuBar menuBar = new MenuBar();
        
        Menu fileMenu = new Menu("Файл");
        Menu rentersMenu = new Menu("Арендаторы");
        
        MenuItem newItem = new MenuItem("Новый");
        MenuItem exitItem = new MenuItem("Выход");
        
        fileMenu.getItems().addAll(newItem, exitItem);
        menuBar.getMenus().addAll(fileMenu, rentersMenu);
        
        return menuBar;
    }
}
