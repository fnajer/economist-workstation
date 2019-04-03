/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package economistworkstation.Model;

import economistworkstation.Database;
import economistworkstation.EconomistWorkstation;
import economistworkstation.Entity.Building;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author fnajer
 */

public class BuildingModel {
    private static Database db = Database.getInstance();
    
    public static void addBuilding(Building building) {
        try {
            PreparedStatement ps = db.conn.prepareStatement("insert into BUILDING values(NULL,?, ?, ?, ?)");
            ps.setString(1, building.getType());
            ps.setDouble(2, building.getSquare());
            ps.setDouble(3, building.getCostBalance());
            ps.setDouble(4, building.getCostResidue());
            
            ps.executeUpdate();
            System.out.println("Добавлено: " + building.getType());
        } catch (SQLException ex) {
            Logger.getLogger(EconomistWorkstation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void updateBuilding(int id, Building building) {
        try {
            PreparedStatement ps = db.conn.prepareStatement("UPDATE BUILDING\n" +
                            "SET type=?, square=?, cost_balance=?, cost_residue=?\n" +
                            "WHERE id=?;");
            ps.setString(1, building.getType());
            ps.setString(2, Double.toString(building.getSquare()));
            ps.setString(3, Double.toString(building.getCostBalance()));
            ps.setString(4, Double.toString(building.getCostResidue()));
            ps.setInt(5, id);
            
            ps.executeUpdate();
            System.out.println("Изменено: " + building.getType());
        } catch (SQLException ex) {
            Logger.getLogger(EconomistWorkstation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void deleteBuilding(int id) {
        try {
            db.stmt.executeUpdate("DELETE FROM BUILDING WHERE id='" + id + "'");
            System.out.println("Удалено: " + id);
        } catch (SQLException ex) {
            Logger.getLogger(EconomistWorkstation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static ObservableList<Building> getBuildings() {
        ObservableList buildings = FXCollections.observableArrayList();
        try {
            ResultSet rs = db.stmt.executeQuery("SELECT * FROM BUILDING");
            
            
            while (rs.next()) {
                buildings.add(createObjectBuilding(rs));
            }
            
            System.out.println("Извлечение зданий завершено.");
            
        } catch (SQLException ex) {
            Logger.getLogger(EconomistWorkstation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return buildings;
    }
    
    public static Building createObjectBuilding(ResultSet rs) throws SQLException {
        Building building = new Building(rs.getString("type"), rs.getDouble("square"), rs.getDouble("cost_balance"), 
        rs.getDouble("cost_residue"));
        building.setId(rs.getInt("id"));
        
        return building;
    }
    
     public static Building getBuilding(int id) {
        Building building = null;
        try {
            ResultSet rs = db.stmt.executeQuery("SELECT * FROM BUILDING WHERE id='" + id + "'");
    
            if (rs.next()) {
                building = createObjectBuilding(rs);
            }
            
            System.out.println("Извлечение здания завершено.");
            
        } catch (SQLException ex) {
            Logger.getLogger(EconomistWorkstation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return building;
    }
}