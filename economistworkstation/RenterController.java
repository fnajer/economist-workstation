/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package economistworkstation;

import java.sql.Statement;
import java.util.ArrayList;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 *
 * @author fnajer
 */
public class RenterController {
    public static void updateListRenters(Statement stmt, VBox containerRenters) {
        ArrayList renters = new ArrayList<String>();
        renters = RenterModel.getRenters(stmt);

        ObservableList listRenters = containerRenters.getChildren();
        listRenters.clear();
        for(Object renterName : renters){
            //System.out.println(renterName);
            Label lblRent = new Label(renterName.toString());
            listRenters.add(lblRent);
        }
    }
}
