/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package economistworkstation;

import java.sql.Statement;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.util.ArrayList;
/**
 *
 * @author fnajer
 */

public class RenterModel {
    public static void addRenter(Statement stmt, String name) {
        try {
            stmt.executeUpdate("INSERT INTO RENTER VALUES(NULL, '" + name + "')");
            System.out.println("Добавлено: " + name);
        } catch (SQLException ex) {
            Logger.getLogger(EconomistWorkstation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static ArrayList<String> getRenters(Statement stmt) {
        ArrayList renters = new ArrayList<String>();
        try {
            ResultSet rs = stmt.executeQuery("SELECT * FROM RENTER");
            
            
            while (rs.next()) {
                renters.add(rs.getString("name"));
            }
            
            System.out.println("Извлечение арендаторов завершено.");
            
        } catch (SQLException ex) {
            Logger.getLogger(EconomistWorkstation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return renters;
    }
}
