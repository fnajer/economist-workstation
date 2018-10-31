/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package economistworkstation.Model;

import economistworkstation.Database;
import economistworkstation.EconomistWorkstation;
import economistworkstation.Entity.Renter;
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
    public static void addRenter(Statement stmt, Renter renter) {
        try {
            //String query = "INSERT INTO RENTER VALUES(NULL, '" + renter.name + ", " + renter.surname + "')";
            //stmt.executeUpdate(query);
            Database db = Database.getInstance();
            PreparedStatement ps = db.conn.prepareStatement("insert into RENTER values(NULL,?, ?, ?, ?, ?, ?)");
            ps.setString(1,renter.name);
            ps.setString(2,renter.surname);
            ps.setString(3,renter.patronymic);
            ps.setString(4,renter.address);
            ps.setString(5,renter.birthday);
            ps.setString(6,renter.person);
            
            ps.executeUpdate();
            System.out.println("Добавлено: " + renter.name);
        } catch (SQLException ex) {
            Logger.getLogger(EconomistWorkstation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void deleteRenter(Statement stmt, int id) {
        try {
            stmt.executeUpdate("DELETE FROM RENTER WHERE id='" + id + "'");
            System.out.println("Удалено: " + id);
        } catch (SQLException ex) {
            Logger.getLogger(EconomistWorkstation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static ArrayList<Renter> getRenters(Statement stmt) {
        ArrayList renters = new ArrayList<Renter>();
        try {
            ResultSet rs = stmt.executeQuery("SELECT * FROM RENTER");
            
            
            while (rs.next()) {
                renters.add(createObjectRenter(rs));
            }
            
            System.out.println("Извлечение арендаторов завершено.");
            
        } catch (SQLException ex) {
            Logger.getLogger(EconomistWorkstation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return renters;
    }
    
    private static Renter createObjectRenter(ResultSet rs) throws SQLException {
        Renter renter = new Renter(rs.getString("name"), rs.getString("surname"), rs.getString("patronymic"), 
        rs.getString("address"), rs.getString("birthday"), rs.getString("person"));
        renter.id = rs.getInt("id");
        
        return renter;
    }
}
