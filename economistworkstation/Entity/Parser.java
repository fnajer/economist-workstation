/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package economistworkstation.Entity;

import economistworkstation.ContractData;
import static economistworkstation.Util.Util.isExist;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellReference;

/**
 *
 * @author fnajer
 */
public abstract class Parser {
    protected Period period;
    protected Renter renter;
    protected Building building;
    protected Contract contract;
    
    protected Cell cell;
    protected String cellString;
    
    protected Rent rent;
    protected Fine fine;
    protected TaxLand taxLand;
    protected Equipment equipment;
    protected Services services;
    Parser(ContractData data) {
        period = data.getPeriod();
        renter = data.getRenter();
        building = data.getBuilding();
        contract = data.getContract();
        
//        BalanceTable balanceTable = period.getBalanceTable();
//        balanceTable = balanceTable == null ? new BalanceTable() : balanceTable;

        rent = isExist(period.getRentPayment()) 
                ? period.getRentPayment() : new Rent();
        fine = isExist(period.getFinePayment()) 
                ? period.getFinePayment() : new Fine();
        taxLand = isExist(period.getTaxLandPayment()) 
                ? period.getTaxLandPayment() : new TaxLand();
        equipment = isExist(period.getEquipmentPayment()) 
                ? period.getEquipmentPayment() : new Equipment();
        services = isExist(period.getServicesPayment()) 
                ? period.getServicesPayment() : new Services();
    }
    
    public void convertTags(ContractData data) {
        cell = data.getCell();
        cellString = cell.getStringCellValue();
        
        Pattern pattern = Pattern.compile("<\\w+>");
        Matcher matcher = pattern.matcher(cellString);
        while(matcher.find()) {
            String foundedTag = matcher.group();
            System.out.println(foundedTag);
            String newValue = parse(foundedTag);
            if (!isExist(newValue)) return;
            
            String result = cellString.replaceAll(foundedTag, newValue);
            cell.setCellValue(result);
            cellString = result;
        }
    }
    
    protected String formatDate(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return date.format(formatter);
    }
    
    protected double getDoubleValue(Double value) {
        String decValue = safeDecFormat(value, Locale.US);
        
        return Double.parseDouble(decValue);
    }
    protected String getStringValue(Double value) {
        String decValue = safeDecFormat(value, Locale.US);
        
        return decValue;
    }
    
    protected DecimalFormat decFormat(Locale locale) {
        DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(locale);
        DecimalFormat df = new DecimalFormat("#.##", otherSymbols);
        df.setRoundingMode(RoundingMode.HALF_UP);
        
        return df;
    }
    
    protected String getColumnLetter(Cell cell) {
        int columnNumber = cell.getColumnIndex();
        return CellReference.convertNumToColString(columnNumber);
    }
    
    protected String safeDecFormat(Double doubleObject, Locale locale) {
        DecimalFormat df = decFormat(locale);
        try {
            return df.format(doubleObject);
        } catch(IllegalArgumentException e) {
            System.err.println(String.format(
                    "%s: value '%s' is not correct. Replaced by 0.0",
                    this.getClass().getSimpleName(),
                    doubleObject));
            return "0.0";
        }
    }
    
    private final ArrayList<Integer> rowsForClear = new ArrayList();
    private final ArrayList<Integer> rowsForDelete = new ArrayList();
    
    public ArrayList<Integer> getRowsForClear() {
        return rowsForClear;
    }
    public ArrayList<Integer> getRowsForDelete() {
        return rowsForDelete;
    }
    
    protected boolean rowWillClear(double value, Cell cell) {
        if (value <= 0) {
            Row row = cell.getRow();
            int rowIndex = row.getRowNum();
            
            if (!rowsForClear.isEmpty() && rowsForClear.contains(rowIndex)) 
                return true;
                
            rowsForClear.add(rowIndex);
            return true;
        }
        return false;
    }
    protected boolean rowWillDelete(double value, Cell cell) {
        if (value <= 0) {
            Row row = cell.getRow();
            int rowIndex = row.getRowNum();
            
            if (!rowsForDelete.isEmpty() && rowsForDelete.contains(rowIndex)) 
                return true;
                
            rowsForDelete.add(rowIndex);
            return true;
        }
        return false;
    }
    
    protected abstract String parse(String foundedTag);
}
