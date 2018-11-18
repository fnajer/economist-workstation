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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author fnajer
 */
public class MonthModel {
    private static Database db = Database.getInstance();
 
    public static void addMonth(int id_contract, Month month) { //id contract not use
        try {
            PreparedStatement ps = db.conn.prepareStatement("insert into MONTH values(NULL,?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            ps.setInt(1, month.number);
            ps.setString(2, month.date);
            ps.setDouble(3, month.cost);
            ps.setDouble(4, month.fine);
            ps.setDouble(5, month.cost_water);
            ps.setDouble(6, month.cost_electricity);
            ps.setDouble(7, month.cost_heading);
            ps.setBoolean(8, month.paid_rent);
            ps.setBoolean(9, month.paid_communal);
            ps.setInt(10, month.id_contract);
            
            ps.executeUpdate();
            System.out.println("Добавлено: " + month.number);
        } catch (SQLException ex) {
            Logger.getLogger(EconomistWorkstation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void addMonths(int id, int count, Month lastMonth) {
        LocalDate date = LocalDate.parse(lastMonth.date);
        int daysOfMonth = date.getDayOfMonth();
        if (daysOfMonth > 1) {
            daysOfMonth--;
            date = date.minusDays(daysOfMonth);  
            date = date.plusMonths(1);
        } else {
            daysOfMonth = 0;
        }
        
        System.out.println(date.toString());
        lastMonth.date = date.toString();
        System.out.println(date.toString());
        updateMonth(lastMonth.id, lastMonth);
          
       
        
        if(count == 1 && daysOfMonth > 1) {
            date = date.plusDays(daysOfMonth);
        } else {
            date = date.plusMonths(1);
        }
        
        for(int i = 1; i <= count; i++) {
            MonthModel.addMonth(i, new Month(lastMonth.number + i, date.toString(), 0.00, 0.00,
                0.00, 0.00, 0.00, false, false, id));

            if(i == count - 1 && daysOfMonth > 1) {
                date = date.plusDays(daysOfMonth);
                continue;
            }
            
            date = date.plusMonths(1);
        }

        System.out.println("Осуществлено продление аренды.");
    }
    
    public static void updateMonth(int id, Month month) {
        System.out.println(month.date);
        try {
            PreparedStatement ps = db.conn.prepareStatement("UPDATE MONTH\n" +
                            "SET date=?, cost=?, fine=?, cost_water=?,\n" +
                            "cost_electricity=?, cost_heading=?, paid_rent=?, paid_communal=?\n" +
                            "WHERE id=?;");
            ps.setString(1, month.date);
            ps.setDouble(2, month.cost);
            ps.setDouble(3, month.fine);
            ps.setDouble(4, month.cost_water);
            ps.setDouble(5, month.cost_electricity);
            ps.setDouble(6, month.cost_heading);
            ps.setBoolean(7, month.paid_rent);
            ps.setBoolean(8, month.paid_communal);
            ps.setInt(9, id);
            
            ps.executeUpdate();
            System.out.println("Изменено: " + month.number);
        } catch (SQLException ex) {
            Logger.getLogger(EconomistWorkstation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     
    public static void deleteMonths(int id) {
        try {
            db.stmt.executeUpdate("DELETE FROM MONTH WHERE id_contract='" + id + "'");
            System.out.println("Удалены месяцы для договора: " + id);
        } catch (SQLException ex) {
            Logger.getLogger(EconomistWorkstation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static ArrayList<Month> getMonths(int id) {
        ArrayList months = new ArrayList<Month>();
        try {
            ResultSet rs = db.stmt.executeQuery("SELECT * FROM MONTH WHERE id_contract='" + id + "'");
            
            
            while (rs.next()) {
                months.add(createObjectMonth(rs));
            }
            
            System.out.println("Извлечение месяцев завершено.");
            
        } catch (SQLException ex) {
            Logger.getLogger(EconomistWorkstation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return months;
    }
    
    public static Month getMonth(int id) {
        Month month = null;
        try {
            ResultSet rs = db.stmt.executeQuery("SELECT * FROM MONTH WHERE id='" + id + "'");
    
            if (rs.next()) {
                month = createObjectMonth(rs);
            }
            
            System.out.println("Извлечение месяца завершено.");
            
        } catch (SQLException ex) {
            Logger.getLogger(EconomistWorkstation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return month;
    }
    
    private static Month createObjectMonth(ResultSet rs) throws SQLException {
        Month month = new Month(rs.getInt("number"), rs.getString("date"),
                rs.getDouble("cost"), rs.getDouble("fine"), rs.getDouble("cost_water"),
                rs.getDouble("cost_electricity"), rs.getDouble("cost_heading"), rs.getBoolean("paid_rent"),
                rs.getBoolean("paid_communal"), rs.getInt("id_contract"));
        month.id = rs.getInt("id");
        
        return month;
    }
}
