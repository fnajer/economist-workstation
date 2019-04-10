/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package economistworkstation.Model;

import economistworkstation.ContractData;
import economistworkstation.Database;
import economistworkstation.EconomistWorkstation;
import economistworkstation.Entity.Fine;
import economistworkstation.Entity.Payment;
import economistworkstation.Entity.Period;
import economistworkstation.Entity.Rent;
import economistworkstation.Entity.RentEquipment;
import economistworkstation.Entity.Services;
import economistworkstation.Entity.TaxLand;
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
public class PeriodModel {
    private static Database db = Database.getInstance();
 
    public static void addPeriod(Period period) {
        try {
            PreparedStatement ps = db.conn.prepareStatement("insert into Period "
                    + "(id, number, end_period, id_contract, id_rent, id_fine,"
                    + "id_tax_land, id_services, id_equipment) "
                    + "values(NULL,?, ?, ?, ?, ?, ?, ?)");
            ps.setInt(1, period.getNumber());
            ps.setString(2, period.getEndPeriod());
            ps.setInt(3, period.getIdContract());
            ps.setInt(4, addPayment(period.getRentPayment()));
            ps.setInt(5, period.getFinePayment().getId());
            ps.setInt(6, period.getTaxLandPayment().getId());
            ps.setInt(7, period.getServicesPayment().getId());
            ps.setInt(8, period.getEquipmentPayment().getId());
            
            ps.executeUpdate();
            System.out.println("Добавлен период: " + period.getNumber());
        } catch (SQLException ex) {
            Logger.getLogger(EconomistWorkstation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
  
    
    public static void addPeriods(int id, int count, Period lastPeriod) {
        LocalDate date = LocalDate.parse(lastPeriod.getEndPeriod());
        int daysAfterCalends = date.getDayOfMonth();
        if (daysAfterCalends > 1) {
            daysAfterCalends--;
            date = date.minusDays(daysAfterCalends);  
            date = date.plusMonths(1);
        } else {
            daysAfterCalends = 0;
        }
        
        lastPeriod.setEndPeriod(date.toString());
        updatePeriod(lastPeriod.getId(), lastPeriod);
          
        if(count == 1 && daysAfterCalends > 1) {
            date = date.plusDays(daysAfterCalends);
        } else {
            date = date.plusMonths(1);
        }
        
        for(int i = 1; i <= count; i++) {
            Period newPeriod = new Period();
            newPeriod.setNumber(lastPeriod.getNumber() + i);
            newPeriod.setEndPeriod(date.toString());
            newPeriod.setIdContract(id);
            PeriodModel.addPeriod(newPeriod);

            if(i == count - 1 && daysAfterCalends > 1) {
                date = date.plusDays(daysAfterCalends);
                continue;
            }
            
            date = date.plusMonths(1);
        }

        System.out.println("Осуществлено продление аренды.");
    }
    
    public static void updatePeriod(int id, Period month) {
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
     
    public static void deletePeriods(int id) {
        try {
            db.stmt.executeUpdate("DELETE FROM MONTH WHERE id_contract='" + id + "'");
            System.out.println("Удалены месяцы для договора: " + id);
        } catch (SQLException ex) {
            Logger.getLogger(EconomistWorkstation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static ObservableList<Period> getPeriods(int id) {
        ObservableList months = FXCollections.observableArrayList();
        try {
            ResultSet rs = db.stmt.executeQuery("SELECT * FROM MONTH WHERE id_contract='" + 
                    id + "' ORDER BY number;");
            
            
            while (rs.next()) {
                months.add(createObjectPeriod(rs));
            }
            
            System.out.println("Извлечение месяцев завершено.");
            
        } catch (SQLException ex) {
            Logger.getLogger(EconomistWorkstation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return months;
    }
    
    public static ObservableList<ContractData> getContractData(LocalDate month) {
        ObservableList contractsData = FXCollections.observableArrayList();
        LocalDate nextMonth = month.plusMonths(1);
        
        try {
            ResultSet rs = db.stmt.executeQuery("SELECT * FROM MONTH "
                    + "LEFT JOIN CONTRACT ON MONTH.ID_CONTRACT=CONTRACT.id "
                    + "LEFT JOIN RENTER ON CONTRACT.ID_RENTER=RENTER.id "
                    + "LEFT JOIN BUILDING ON CONTRACT.ID_BUILDING=BUILDING.id "
                    + "WHERE date >= '" + month + "' AND date < '" + nextMonth + "' "
                    + "ORDER BY id_contract;");
            
            while (rs.next()) {
                contractsData.add(new ContractData(null, createObjectPeriod(rs),
                        BuildingModel.createObjectBuilding(rs),
                        RenterModel.createObjectRenter(rs), 
                        ContractModel.createObjectContract(rs), null));
            }
            
            System.out.println("Извлечение целых контрактов завершено.");
            
        } catch (SQLException ex) {
            Logger.getLogger(EconomistWorkstation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return contractsData;
    }
    
    public static Period getPeriod(int id) {
        Period month = null;
        try {
            ResultSet rs = db.stmt.executeQuery("SELECT * FROM MONTH WHERE id='" + id + "'");
    
            if (rs.next()) {
                month = createObjectPeriod(rs);
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
            int idPeriod;
            
            while (rs.next()) {
                idPeriod = rs.getInt("id");
                PreparedStatement ps = db.conn.prepareStatement("UPDATE MONTH\n" +
                            "SET number_rent_acc=?, number_communal_acc=?\n" +
                            "WHERE id=?;");
                ps.setInt(1, number);
                ps.setInt(2, number + 1);
                ps.setInt(3, idPeriod);
                
                number += 2;
                ps.executeUpdate();
            }
            
            System.out.println("Обновление порядка счета месяцев завершено.");
            
        } catch (SQLException ex) {
            Logger.getLogger(EconomistWorkstation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private static Period createObjectPeriod(ResultSet rs) throws SQLException {
        Period period = new Period(rs.getInt("number"), 
                    rs.getInt("number_rent_acc"),
                    rs.getInt("number_communal_acc"),
                    rs.getString("end_period"),  
                    rs.getInt("id_contract"),
                    createObjectRent(rs),
                    createObjectFine(rs),
                    createObjectTaxLand(rs),
                    createObjectServices(rs),
                    createObjectEquipment(rs));
        period.setId(rs.getInt("id"));
        
        return period;
    }
}
