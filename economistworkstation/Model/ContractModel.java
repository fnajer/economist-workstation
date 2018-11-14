/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package economistworkstation.Model;

import economistworkstation.Database;
import economistworkstation.EconomistWorkstation;
import economistworkstation.Entity.Contract;
import economistworkstation.Entity.Month;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author fnajer
 */
public class ContractModel {
    private static Database db = Database.getInstance();
 
    public static void addContract(Contract contract) {
        try {
           System.out.println("1: " + contract.date_start);
           System.out.println("1: " + contract.date_end);
           System.out.println("1: " + contract.id_renter);
           System.out.println("1: " + contract.id_building);
            PreparedStatement ps = db.conn.prepareStatement("insert into CONTRACT values(NULL,?, ?, ?, ?)");
            ps.setString(1, contract.date_start);
            ps.setString(2, contract.date_end);
            ps.setInt(3, contract.id_renter);
            ps.setInt(4, contract.id_building);
            
            ps.executeUpdate();
            
            ResultSet rs = ps.getResultSet();
            int id = 0;
            
            while (rs.next()) {
                id = rs.getInt("id");
            }

            for(int i = 1; i <= 12; i++) {
                MonthModel.addMonth(i, new Month(i, "1997-10-10", 0.00, 0.00, 0.00,
                    0.00, 0.00, 0.00, false, false, id));
            }

            
            System.out.println("Добавлено: " + contract.date_start);
        } catch (SQLException ex) {
            Logger.getLogger(EconomistWorkstation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
     public static void updateContract(int id, Contract contract) {
        try {
            PreparedStatement ps = db.conn.prepareStatement("UPDATE CONTRACT\n" +
                            "SET date_start=?, date_end=?, id_renter=?, id_building=?\n" +
                            "WHERE id=?;");
            ps.setString(1, contract.date_start);
            ps.setString(2, contract.date_end);
            ps.setInt(3, contract.id_renter);
            ps.setInt(4, contract.id_building);
            ps.setInt(5, id);
            
            ps.executeUpdate();
            System.out.println("Изменено: " + contract.id);
        } catch (SQLException ex) {
            Logger.getLogger(EconomistWorkstation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void deleteContract(int id) {
        try {
            db.stmt.executeUpdate("DELETE FROM CONTRACT WHERE id='" + id + "'");
            System.out.println("Удалено: " + id);
        } catch (SQLException ex) {
            Logger.getLogger(EconomistWorkstation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static ArrayList<Contract> getContracts() {
        ArrayList contracts = new ArrayList<Contract>();
        try {
            ResultSet rs = db.stmt.executeQuery("SELECT * FROM CONTRACT");
            
            
            while (rs.next()) {
                contracts.add(createObjectContract(rs));
            }
            
            System.out.println("Извлечение договоров завершено.");
            
        } catch (SQLException ex) {
            Logger.getLogger(EconomistWorkstation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return contracts;
    }
    
    public static Contract getContract(int id) {
        Contract contract = null;
        try {
            //ResultSet rs = db.stmt.executeQuery("SELECT * FROM CONTRACT WHERE id='" + id + "'");
            ResultSet rs = db.stmt.executeQuery("SELECT * FROM CONTRACT,RENTER, BUILDING WHERE CONTRACT.id=" + id + " AND CONTRACT.id_renter=RENTER.id AND CONTRACT.id_building=BUILDING.id;");
    
            if (rs.next()) {
                contract = createObjectContract(rs);
            }
            
            System.out.println("Извлечение договора завершено.");
            
        } catch (SQLException ex) {
            Logger.getLogger(EconomistWorkstation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return contract;
    }
    
    private static Contract createObjectContract(ResultSet rs) throws SQLException {
        Contract contract = new Contract(rs.getString("date_start"), rs.getString("date_end"), rs.getInt("id_renter"), 
        rs.getInt("id_building"));
        contract.id = rs.getInt("id");
        
        return contract;
    }
}
