/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package economistworkstation.Model;

import economistworkstation.Database;
import economistworkstation.EconomistWorkstation;
import economistworkstation.Entity.Renter;
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
    
    //static {
        //db = Database.getInstance();
    //}
    
    public static void addContract(Contract contract) {
        try {
           
            PreparedStatement ps = db.conn.prepareStatement("insert into CONTRACT values(NULL,?, ?, ?, ?, ?, ?)");
            ps.setString(1, contract.name);
            ps.setString(2, contract.surname);
            ps.setString(3, contract.patronymic);
            ps.setString(4, contract.address);
            ps.setString(5, contract.birthday);
            ps.setString(6, contract.person);
            
            ps.executeUpdate();
            System.out.println("Добавлено: " + contract.name);
        } catch (SQLException ex) {
            Logger.getLogger(EconomistWorkstation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
     public static void updateContract(int id, Contract contract) {
        try {
            PreparedStatement ps = db.conn.prepareStatement("UPDATE CONTRACT\n" +
                            "SET name=?, surname=?, patronymic=?, address=?, birthday=?, person=?\n" +
                            "WHERE id=?;");
            ps.setString(1, contract.name);
            ps.setString(2, contract.surname);
            ps.setString(3, contract.patronymic);
            ps.setString(4, contract.address);
            ps.setString(5, contract.birthday);
            ps.setString(6, contract.person);
            ps.setInt(7, id);
            
            ps.executeUpdate();
            System.out.println("Изменено: " + contract.name);
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
            ResultSet rs = db.stmt.executeQuery("SELECT * FROM RENTER");
            
            
            while (rs.next()) {
                contracts.add(createObjectContract(rs));
            }
            
            System.out.println("Извлечение арендаторов завершено.");
            
        } catch (SQLException ex) {
            Logger.getLogger(EconomistWorkstation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return contracts;
    }
    
    public static Renter getContract(int id) {
        Contract contract = null;
        try {
            ResultSet rs = db.stmt.executeQuery("SELECT * FROM RENTER WHERE id='" + id + "'");
    
            if (rs.next()) {
                contract = createObjectContract(rs);
            }
            
            System.out.println("Извлечение арендатора завершено.");
            
        } catch (SQLException ex) {
            Logger.getLogger(EconomistWorkstation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return contract;
    }
    
    private static Renter createObjectContract(ResultSet rs) throws SQLException {
        Contract contract = new Contract(rs.getString("name"), rs.getString("surname"), rs.getString("patronymic"), 
        rs.getString("address"), rs.getString("birthday"), rs.getString("person"));
        contract.id = rs.getInt("id");
        
        return contract;
    }
}
