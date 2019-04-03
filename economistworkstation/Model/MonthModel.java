/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package economistworkstation.Model;

import economistworkstation.ContractData;
import economistworkstation.Database;
import economistworkstation.EconomistWorkstation;
import economistworkstation.Entity.Month;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import static java.time.temporal.TemporalAdjusters.firstDayOfYear;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author fnajer
 */
public class MonthModel {
    private static Database db = Database.getInstance();
 
    public static void addMonth(Month month) {
        try {
//            PreparedStatement ps = db.conn.prepareStatement("insert into MONTH "
//                    + "values(NULL,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

            PreparedStatement ps = db.conn.prepareStatement("insert into MONTH "
                    + "(id, number, date, cost, index_cost, fine, count_water,"
                    + "count_electricity, count_heading, paid_rent, paid_communal,"
                    + "id_contract, tariff_water, tariff_electricity, tariff_heading,"
                    + "count_garbage, tariff_garbage, tax_land, paid_tax_land,"
                    + "cost_internet, cost_telephone) "
                    + "values(NULL,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            ps.setInt(1, month.getNumber());
            ps.setString(2, month.getDate());
            ps.setDouble(3, month.getCost());
            ps.setDouble(4, month.getIndexCost());
            ps.setDouble(5, month.getFine());
            ps.setDouble(6, month.getCountWater());
            ps.setDouble(7, month.getCountElectricity());
            ps.setDouble(8, month.getCountHeading());
            ps.setBoolean(9, month.getPaidRent());
            ps.setBoolean(10, month.getPaidCommunal());
            ps.setInt(11, month.getIdContract());
            ps.setDouble(12, month.getTariffWater());
            ps.setDouble(13, month.getTariffElectricity());
            ps.setDouble(14, month.getTariffHeading());
            ps.setDouble(15, month.getCountGarbage());
            ps.setDouble(16, month.getTariffGarbage());
            ps.setDouble(17, month.getTaxLand());
            ps.setBoolean(18, month.getPaidTaxLand());
            ps.setDouble(19, month.getCostInternet());
            ps.setDouble(20, month.getCostTelephone());
            
            ps.executeUpdate();
            System.out.println("Добавлено: " + month.getNumber());
        } catch (SQLException ex) {
            Logger.getLogger(EconomistWorkstation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void addMonths(int id, int count, Month lastMonth) {
        LocalDate date = LocalDate.parse(lastMonth.getDate());
        int daysOfMonth = date.getDayOfMonth();
        if (daysOfMonth > 1) {
            daysOfMonth--;
            date = date.minusDays(daysOfMonth);  
            date = date.plusMonths(1);
        } else {
            daysOfMonth = 0;
        }
        
        lastMonth.setDate(date.toString());
        updateMonth(lastMonth.getId(), lastMonth);
          
        if(count == 1 && daysOfMonth > 1) {
            date = date.plusDays(daysOfMonth);
        } else {
            date = date.plusMonths(1);
        }
        
        for(int i = 1; i <= count; i++) {
            Month newMonth = new Month();
            newMonth.setNumber(lastMonth.getNumber() + i);
            newMonth.setDate(date.toString());
            newMonth.setIdContract(id);
            MonthModel.addMonth(newMonth);

            if(i == count - 1 && daysOfMonth > 1) {
                date = date.plusDays(daysOfMonth);
                continue;
            }
            
            date = date.plusMonths(1);
        }

        System.out.println("Осуществлено продление аренды.");
    }
    
    public static void updateMonth(int id, Month month) {
        try {
            PreparedStatement ps = db.conn.prepareStatement("UPDATE MONTH\n" +
                            "SET date=?, cost=?, fine=?, count_water=?,\n" +
                            "count_electricity=?, count_heading=?, paid_rent=?, paid_communal=?,\n" +
                            "tariff_water=?, tariff_electricity=?, tariff_heading=?,\n" +
                            "index_cost=?, count_garbage=?, tariff_garbage=?,\n" +
                            "tax_land=?, paid_tax_land=?, cost_internet=?, cost_telephone=?\n" +
                            "WHERE id=?;");
            ps.setString(1, month.getDate());
            ps.setDouble(2, month.getCost());
            ps.setDouble(3, month.getFine());
            ps.setDouble(4, month.getCountWater());
            ps.setDouble(5, month.getCountElectricity());
            ps.setDouble(6, month.getCountHeading());
            ps.setBoolean(7, month.getPaidRent());
            ps.setBoolean(8, month.getPaidCommunal());
            ps.setDouble(9, month.getTariffWater());
            ps.setDouble(10, month.getTariffElectricity());
            ps.setDouble(11, month.getTariffHeading());
            ps.setDouble(12, month.getIndexCost());
            ps.setDouble(13, month.getCountGarbage());
            ps.setDouble(14, month.getTariffGarbage());
            ps.setDouble(15, month.getTaxLand());
            ps.setBoolean(16, month.getPaidTaxLand());
            ps.setDouble(17, month.getCostInternet());
            ps.setDouble(18, month.getCostTelephone());
            ps.setInt(19, id);
            
            ps.executeUpdate();
            System.out.println("Изменено: " + month.getNumber());
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
    
    public static ObservableList<Month> getMonths(int id) {
        ObservableList months = FXCollections.observableArrayList();
        try {
            ResultSet rs = db.stmt.executeQuery("SELECT * FROM MONTH WHERE id_contract='" + 
                    id + "' ORDER BY number;");
            
            
            while (rs.next()) {
                months.add(createObjectMonth(rs));
            }
            
            System.out.println("Извлечение месяцев завершено.");
            
        } catch (SQLException ex) {
            Logger.getLogger(EconomistWorkstation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return months;
    }
    
    public static ObservableList<ContractData> getContractData(LocalDate month) {
        ObservableList months = FXCollections.observableArrayList();
        LocalDate nextMonth = month.plusMonths(1);
        
        try {
            ResultSet rs = db.stmt.executeQuery("SELECT * FROM MONTH LEFT JOIN "
                    + "CONTRACT ON MONTH.ID_CONTRACT=CONTRACT.id "
                    + "WHERE date >= '" + month + "' AND date < '" + nextMonth + "' "
                    + "ORDER BY id_contract;");
            
            
            while (rs.next()) {
                months.add(new ContractData(null, createObjectMonth(rs),
                        BuildingModel.createObjectBuilding(rs),
                        RenterModel.createObjectRenter(rs), 
                        ContractModel.createObjectContract(rs), null));
            }
            
            System.out.println("Извлечение целых контрактов завершено.");
            
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
    
    public static void updateAccountNumbers() {
        try {
            LocalDate currentYear = LocalDate.now().with(firstDayOfYear());
            LocalDate nextYear = currentYear.plusYears(1);
            
            ResultSet rs = db.stmt.executeQuery("SELECT id \n" +
                "FROM MONTH\n" +
                "WHERE date >= '" + currentYear + "' AND date < '" + nextYear + "' \n" +
                "ORDER BY date, id_contract;");
            int number = 1;
            int idMonth;
            
            while (rs.next()) {
                idMonth = rs.getInt("id");
                PreparedStatement ps = db.conn.prepareStatement("UPDATE MONTH\n" +
                            "SET number_rent_acc=?, number_communal_acc=?\n" +
                            "WHERE id=?;");
                ps.setInt(1, number);
                ps.setInt(2, number + 1);
                ps.setInt(3, idMonth);
                
                number += 2;
                ps.executeUpdate();
            }
            
            System.out.println("Обновление порядка счета месяцев завершено.");
            
        } catch (SQLException ex) {
            Logger.getLogger(EconomistWorkstation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private static Month createObjectMonth(ResultSet rs) throws SQLException {
        Month month = new Month(rs.getInt("number"), rs.getInt("number_rent_acc"),
                rs.getInt("number_communal_acc"),
                rs.getString("date"), rs.getDouble("cost"),
                rs.getDouble("index_cost"), rs.getDouble("fine"), rs.getDouble("count_water"),
                rs.getDouble("count_electricity"), rs.getDouble("count_heading"),
                rs.getDouble("count_garbage"), rs.getDouble("cost_internet"), 
                rs.getDouble("cost_telephone"), rs.getBoolean("paid_rent"),
                rs.getBoolean("paid_communal"), rs.getInt("id_contract"), rs.getDouble("tariff_water"),
                rs.getDouble("tariff_electricity"), rs.getDouble("tariff_heading"),
                rs.getDouble("tariff_garbage"), rs.getDouble("tax_land"), rs.getBoolean("paid_tax_land"));
        month.setId(rs.getInt("id"));
        
        return month;
    }
}
