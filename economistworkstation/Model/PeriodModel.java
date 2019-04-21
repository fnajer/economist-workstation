/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package economistworkstation.Model;

import economistworkstation.ContractData;
import economistworkstation.Database;
import economistworkstation.EconomistWorkstation;
import economistworkstation.Entity.ExtraCost;
import economistworkstation.Entity.Fine;
import economistworkstation.Entity.Payment;
import economistworkstation.Entity.Period;
import economistworkstation.Entity.Rent;
import economistworkstation.Entity.Equipment;
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
            PreparedStatement ps = db.conn.prepareStatement("INSERT INTO PERIOD "
                    + "(id, number, date_end, id_contract, id_rent, id_fine,"
                    + "id_tax_land, id_services, id_equipment, id_extra_cost) "
                    + "VALUES(NULL,?, ?, ?, NULL, NULL, NULL, NULL, NULL, NULL)");
            ps.setInt(1, period.getNumber());
            ps.setString(2, period.getEndPeriod());
            ps.setInt(3, period.getIdContract());
            
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
    
    public static void updatePeriod(int id, Period period) {
        try {
            Integer idRent = getPayment(period.getRentPayment());
            if (idRent == null) period.setRentPayment(null);
            Integer idFine = getPayment(period.getFinePayment());
            if (idFine == null) period.setFinePayment(null);
            Integer idTaxLand = getPayment(period.getTaxLandPayment());
            if (idTaxLand == null) period.setTaxLandPayment(null);
            Integer idServices = getPayment(period.getServicesPayment());
            if (idServices == null) period.setServicesPayment(null);
            Integer idEquipment = getPayment(period.getEquipmentPayment());
            if (idEquipment == null) period.setEquipmentPayment(null);
            
            PreparedStatement ps = db.conn.prepareStatement("UPDATE PERIOD "
                    + "SET number=?, date_end=?, id_contract=?, id_rent=?, "
                    + "id_fine =?, id_tax_land=?, id_services=?, id_equipment=? "
                    + "WHERE id=?");
                    
            ps.setInt(1, period.getNumber());
            ps.setString(2, period.getEndPeriod());
            ps.setInt(3, period.getIdContract());
            ps.setObject(4, idRent, java.sql.Types.INTEGER);
            ps.setObject(5, idFine, java.sql.Types.INTEGER);
            ps.setObject(6, idTaxLand, java.sql.Types.INTEGER);
            ps.setObject(7, idServices, java.sql.Types.INTEGER);
            ps.setObject(8, idEquipment, java.sql.Types.INTEGER);
            ps.setInt(9, id);
            
            ps.executeUpdate();
            System.out.println("Изменен период: " + period.getNumber());
        } catch (SQLException ex) {
            Logger.getLogger(EconomistWorkstation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
            
    public static Integer getPayment(Payment payment) {
        if (payment == null) return null;
        
        PreparedStatement ps;
        String state;
        try {
            if (payment.getPaid() != null && payment.getPaid() == -1.0) {
                ps = payment.getDeleteStatement(db);
                state = "Delete";
                ps.executeUpdate();
                System.out.println(String.format("%s: %s", state, payment));
                return null;
            } else if (payment.getId() == 0) {
                ps = payment.getInsertStatement(db);
                state = "Insert";
            } else {
                ps = payment.getUpdateStatement(db);
                state = "Update";
            }
     
            ps.executeUpdate();
            
            ResultSet rs = ps.getGeneratedKeys();
            
            if (rs.next()) {
                payment.setId(rs.getInt(1));
            }
            
            System.out.println(String.format("%s: %s", state, payment));
            
        } catch (SQLException ex) {
            Logger.getLogger(PeriodModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return payment.getId();
    }
    
    public static void deletePeriods(int id) {
        try {
            db.stmt.executeUpdate("DELETE FROM PERIOD WHERE id_contract='" + id + "'");
            System.out.println("Удалены месяцы для договора: " + id);
        } catch (SQLException ex) {
            Logger.getLogger(EconomistWorkstation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static ObservableList<Period> getPeriods(int id) {
        ObservableList months = FXCollections.observableArrayList();
        try {
            ResultSet rs = db.stmt.executeQuery("SELECT * FROM PERIOD "
                    + "LEFT JOIN RENT ON PERIOD.ID_RENT=RENT.id "
                    + "LEFT JOIN FINE ON PERIOD.ID_FINE=FINE.id "
                    + "LEFT JOIN TAXLAND ON PERIOD.ID_TAX_LAND=TAXLAND.id "
                    + "LEFT JOIN EQUIPMENT ON PERIOD.ID_EQUIPMENT=EQUIPMENT.id "
                    + "LEFT JOIN SERVICES ON PERIOD.ID_SERVICES=SERVICES.id "
                    + "LEFT JOIN EXTRACOST ON PERIOD.ID_EXTRA_COST=EXTRACOST.id "
                    + "WHERE id_contract='"
                    + id + "' ORDER BY number");
            
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
            ResultSet rs = db.stmt.executeQuery("SELECT * FROM PERIOD "
                    + "LEFT JOIN RENT ON PERIOD.ID_RENT=RENT.id "
                    + "LEFT JOIN FINE ON PERIOD.ID_FINE=FINE.id "
                    + "LEFT JOIN TAXLAND ON PERIOD.ID_TAX_LAND=TAXLAND.id "
                    + "LEFT JOIN EQUIPMENT ON PERIOD.ID_EQUIPMENT=EQUIPMENT.id "
                    + "LEFT JOIN SERVICES ON PERIOD.ID_SERVICES=SERVICES.id "
                    + "LEFT JOIN EXTRACOST ON PERIOD.ID_EXTRA_COST=EXTRACOST.id "
                    + "LEFT JOIN CONTRACT ON PERIOD.ID_CONTRACT=CONTRACT.id "
                    + "LEFT JOIN RENTER ON CONTRACT.ID_RENTER=RENTER.id "
                    + "LEFT JOIN BUILDING ON CONTRACT.ID_BUILDING=BUILDING.id "
                    + "WHERE date_end >= '" + month + "' AND date_end < '" + nextMonth + "' "
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
    
    public static void updateAccountNumbers() {
        try {
            LocalDate currentYear = LocalDate.now().with(firstDayOfYear());
            LocalDate nextYear = currentYear.plusYears(1);
            
            ResultSet rs = db.stmt.executeQuery("SELECT id \n" +
                "FROM PERIOD\n" +
                "WHERE date >= '" + currentYear + "' AND date < '" + nextYear + "' \n" +
                "ORDER BY date, id_contract;");
            int number = 1;
            int idPeriod;
            
            while (rs.next()) {
                idPeriod = rs.getInt("id");
                PreparedStatement ps = db.conn.prepareStatement("UPDATE PERIOD\n" +
                            "SET number_rent_acc=?, number_services_acc=?\n" +
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
    
    public static void updateExtraCostPeriod(int id, Period period) {
        try {
            Integer idExtraCost = getExtraCost(period.getExtraCost());
            if (idExtraCost == null) period.setExtraCost(null);
            System.out.println(idExtraCost);
            PreparedStatement ps = db.conn.prepareStatement("UPDATE PERIOD "
                    + "SET id_extra_cost=? "
                    + "WHERE id=?");
                    
            ps.setObject(1, idExtraCost, java.sql.Types.INTEGER);
            ps.setInt(2, id);
            
            ps.executeUpdate();
            System.out.println("Изменен период: " + period.getNumber());
        } catch (SQLException ex) {
            Logger.getLogger(EconomistWorkstation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
            
    public static Integer getExtraCost(ExtraCost extraCost) {
        if (extraCost == null) return null;
        
        PreparedStatement ps;
        String state;
        try {
            if (extraCost.getCostRent() != null && extraCost.getCostRent() == -1.0) {
                ps = extraCost.getDeleteStatement(db);
                state = "Delete";
                ps.executeUpdate();
                System.out.println(String.format("%s: %s", state, extraCost));
                return null;
            } else if (extraCost.getId() == 0) {
                ps = extraCost.getInsertStatement(db);
                state = "Insert";
            } else {
                ps = extraCost.getUpdateStatement(db);
                state = "Update";
            }
     
            ps.executeUpdate();
            
            ResultSet rs = ps.getGeneratedKeys();
            
            if (rs.next()) {
                extraCost.setId(rs.getInt(1));
            }
            
            System.out.println(String.format("%s: %s", state, extraCost));
            
        } catch (SQLException ex) {
            Logger.getLogger(PeriodModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return extraCost.getId();
    }
    
    private static Payment createObjectRent(ResultSet rs) throws SQLException {
        if(rs.getInt("id_rent") != 0) {
            Payment rent = new Rent(rs.getObject("paid_rent"), 
                    rs.getString("date_paid_rent"),
                    rs.getObject("cost"), 
                    rs.getObject("index_cost"));
            rent.setId(rs.getInt("id_rent"));
            return rent;
        }
        return null;
    }
    private static Payment createObjectFine(ResultSet rs) throws SQLException {
        if(rs.getInt("id_fine") != 0) {
            Payment fine = new Fine(rs.getObject("paid_fine"), 
                    rs.getString("date_paid_tax_land"),
                    rs.getObject("fine"));
            fine.setId(rs.getInt("id_fine"));
            return fine;
        }
        return null;
    }
    private static Payment createObjectTaxLand(ResultSet rs) throws SQLException {
        if(rs.getInt("id_tax_land") != 0) {
            Payment fine = new TaxLand(rs.getObject("paid_tax_land"), 
                    rs.getString("date_paid_tax_land"),
                    rs.getObject("tax_land"));
            fine.setId(rs.getInt("id_tax_land"));
            return fine;
        }
        return null;
    }
    private static Payment createObjectEquipment(ResultSet rs) throws SQLException {
        if(rs.getInt("id_equipment") != 0) {
            Payment fine = new Equipment(rs.getObject("paid_equipment"), 
                    rs.getString("date_paid_equipment"),
                    rs.getObject("cost_equipment"));
            fine.setId(rs.getInt("id_equipment"));
            return fine;
        }
        return null;
    }
    private static Payment createObjectServices(ResultSet rs) throws SQLException {
        if(rs.getInt("id_services") != 0) {
            Payment services = new Services(rs.getObject("paid_services"), 
                    rs.getString("date_paid_services"),
                    rs.getObject("count_water"),
                    rs.getObject("count_electricity"), 
                    rs.getObject("cost_meter_heading"),
                    rs.getObject("cost_meter_garbage"), 
                    rs.getObject("cost_internet"), 
                    rs.getObject("cost_telephone"),
                    rs.getObject("tariff_water"),
                    rs.getObject("tariff_electricity"));
            services.setId(rs.getInt("id_services"));
            return services;
        }
        return null;
    }
    private static ExtraCost createObjectExtraCost(ResultSet rs) throws SQLException {
        if(rs.getInt("id_extra_cost") != 0) {
            ExtraCost extraCost = new ExtraCost(rs.getObject("extra_cost_rent"), 
                    rs.getObject("extra_cost_fine"),
                    rs.getObject("extra_cost_tax_land"),
                    rs.getObject("extra_cost_services"), 
                    rs.getObject("extra_cost_equipment"));
            extraCost.setId(rs.getInt("id_extra_cost"));
            return extraCost;
        }
        return null;
    }
    
    private static Period createObjectPeriod(ResultSet rs) throws SQLException {
        Period period = new Period(rs.getInt("number"), 
                    rs.getInt("number_rent_acc"),
                    rs.getInt("number_services_acc"),
                    rs.getString("date_end"),  
                    rs.getInt("id_contract"),
                    createObjectRent(rs),
                    createObjectFine(rs),
                    createObjectTaxLand(rs),
                    createObjectServices(rs),
                    createObjectEquipment(rs),
                    createObjectExtraCost(rs));
        period.setId(rs.getInt("id"));
        
        return period;
    }
}
