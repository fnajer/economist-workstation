/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package economistworkstation.Model;

import economistworkstation.Database;
import economistworkstation.EconomistWorkstation;
import economistworkstation.Entity.Renter;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
/**
 *
 * @author fnajer
 */

public class RenterModel {
    private static Database db = Database.getInstance();
    
    //static {
        //db = Database.getInstance();
    //}
    
    public static int addRenter(Renter renter) {
        int idContract = -1;
        try {
            PreparedStatement ps = db.conn.prepareStatement("insert into RENTER values(NULL,?, ?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, renter.getFirstName());
            ps.setString(2, renter.getLastName());
            ps.setString(3, renter.getPatronymic());
            ps.setString(4, renter.getAddress());
            ps.setString(5, renter.getBirthday());
            ps.setString(6, renter.getSubject());
            
            ps.executeUpdate();
            
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                idContract = rs.getInt(1);
            }
            
            System.out.println("Добавлено: " + renter.getFirstName());
        } catch (SQLException ex) {
            Logger.getLogger(EconomistWorkstation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return idContract;
    }
    
     public static void updateRenter(int id, Renter renter) {
        try {
            PreparedStatement ps = db.conn.prepareStatement("UPDATE RENTER\n" +
                            "SET first_name=?, last_name=?, patronymic=?, address=?, birthday=?, subject=?\n" +
                            "WHERE id=?;");
            ps.setString(1, renter.getFirstName());
            ps.setString(2, renter.getLastName());
            ps.setString(3, renter.getPatronymic());
            ps.setString(4, renter.getAddress());
            ps.setString(5, renter.getBirthday());
            ps.setString(6, renter.getSubject());
            ps.setInt(7, id);
            
            ps.executeUpdate();
            System.out.println("Изменено: " + renter.getFirstName());
        } catch (SQLException ex) {
            Logger.getLogger(EconomistWorkstation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void deleteRenter(int id) {
        try {
            db.stmt.executeUpdate("DELETE FROM RENTER WHERE id='" + id + "'");
            System.out.println("Удалено: " + id);
        } catch (SQLException ex) {
            Logger.getLogger(EconomistWorkstation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static ObservableList<Renter> getRenters() {
        ObservableList renters = FXCollections.observableArrayList();
        try {
            ResultSet rs = db.stmt.executeQuery("SELECT * FROM RENTER");
            
            
            while (rs.next()) {
                renters.add(createObjectRenter(rs));
            }
            
            System.out.println("Извлечение арендаторов завершено.");
            
        } catch (SQLException ex) {
            Logger.getLogger(EconomistWorkstation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return renters;
    }
    
    public static Renter getRenter(int id) {
        Renter renter = null;
        try {
            ResultSet rs = db.stmt.executeQuery("SELECT * FROM RENTER WHERE id='" + id + "'");
    
            if (rs.next()) {
                renter = createObjectRenter(rs);
            }
            
            System.out.println("Извлечение арендатора завершено.");
            
        } catch (SQLException ex) {
            Logger.getLogger(EconomistWorkstation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return renter;
    }
    
    public static Renter createObjectRenter(ResultSet rs) throws SQLException {
        Renter renter = new Renter(rs.getString("first_name"), rs.getString("last_name"),
            rs.getString("patronymic"), rs.getString("address"), rs.getString("birthday"),
            rs.getString("subject"));
        renter.setId(rs.getInt("id"));
        
        return renter;
    }
}

