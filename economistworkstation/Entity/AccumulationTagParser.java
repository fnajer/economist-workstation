/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package economistworkstation.Entity;

import economistworkstation.ContractData;
import static economistworkstation.Util.Util.isExist;
import java.time.LocalDate;
import javafx.collections.ObservableList;

/**
 *
 * @author fnajer
 */
public class AccumulationTagParser extends StatementTagParser {
    private final int countTypePayments = 5;
    
    public AccumulationTagParser(ObservableList<ContractData> dataList, int indexStartRow) {
        super(dataList, indexStartRow);
    }
   
    @Override
    protected String parse(String foundedTag) {
        String newValue = "<Tag not founded>";
  
        //statement
        if ("<num>".equals(foundedTag)) {
            useContractData(dataList.get(num - 1)); // to go to the next renter
            cell.setCellValue(num++);
            return null;
        }
        if ("<sumOne>".equals(foundedTag)) {
            int rowIndex = cell.getRowIndex() - 1;
            String columnLetter = getColumnLetter(cell);
            cell.setCellFormula("SUM(" + columnLetter + startRow + 
                    ":" + columnLetter + rowIndex + ")");
            return null;
        }
        
        if ("<services>".equals(foundedTag)) {
            double result = getDoubleValue(services.sumToPay());
            
            cell.setCellValue(result);
            return null;
        }
        if ("<servicesPaid>".equals(foundedTag)) {
            double result = getDoubleValue(services.safeGetPaid());;

            cell.setCellValue(result);
            return null;
        }
        if ("<equipment>".equals(foundedTag)) {
            double result = getDoubleValue(equipment.sumToPay());
            
            cell.setCellValue(result);
            return null;
        }
        if ("<equipmentPaid>".equals(foundedTag)) {
            double result = getDoubleValue(equipment.safeGetPaid());

            cell.setCellValue(result);
            return null;
        }
        if ("<rent>".equals(foundedTag)) {
            double result = getDoubleValue(rent.sumToPay());

            cell.setCellValue(result);
            return null;
        }
        if ("<rentPaid>".equals(foundedTag)) {
            double result = getDoubleValue(rent.safeGetPaid());

            cell.setCellValue(result);
            return null;
        }
        if ("<fine>".equals(foundedTag)) {
            double result = getDoubleValue(fine.getFine());
            
            cell.setCellValue(result);
            return null;
        }
        if ("<finePaid>".equals(foundedTag)) {
            double result = getDoubleValue(fine.safeGetPaid());

            cell.setCellValue(result);
            return null;
        }
        if ("<taxLand>".equals(foundedTag)) {
            double result = getDoubleValue(taxLand.getTaxLand());
            
            cell.setCellValue(result);
            return null;
        }
        if ("<taxLandPaid>".equals(foundedTag)) {
            double result = getDoubleValue(taxLand.safeGetPaid());

            cell.setCellValue(result);
            return null;
        }
        //same||
        
        if ("<accountRentNum>".equals(foundedTag)) {
            if (rent.isEmpty()) {
                cell.setCellValue("");
                return null;
            }
            int result = period.getNumberRentAcc();
            
            cell.setCellValue(result);
            return null;
        }
        if ("<accountEquipmentNum>".equals(foundedTag)) {
            if (equipment.isEmpty()) {
                cell.setCellValue("");
                return null;
            }
            int result = period.getNumberRentAcc();
            
            cell.setCellValue(result);
            return null;
        }
        if ("<accountFineNum>".equals(foundedTag)) {
            if (fine.isEmpty()) {
                cell.setCellValue("");
                return null;
            }
            int result = period.getNumberRentAcc();

            cell.setCellValue(result);
            return null;
        }
        if ("<accountServicesNum>".equals(foundedTag)) {   
            if (services.isEmpty()) {
                cell.setCellValue("");
                return null;
            }            
            int result = period.getNumberServicesAcc();
            
            cell.setCellValue(result);
            return null;
        }
        if ("<accountTaxLandNum>".equals(foundedTag)) {
            if (taxLand.isEmpty()) {
                cell.setCellValue("");
                return null;
            }
            int result = period.getNumberRentAcc();

            cell.setCellValue(result);
            return null;
        }
        if ("<creditRent>".equals(foundedTag)) {
            Double result = getDoubleValue(balanceTable.getCreditRent());
            
            cell.setCellValue(result);
            return null;
        }
        if ("<debitRent>".equals(foundedTag)) {
            Double result = getDoubleValue(balanceTable.getDebitRent());
            
            cell.setCellValue(result);
            return null;
        }
        if ("<creditFine>".equals(foundedTag)) {
            Double result = getDoubleValue(balanceTable.getCreditFine());
            
            cell.setCellValue(result);
            return null;
        }
        if ("<debitFine>".equals(foundedTag)) {
            Double result = getDoubleValue(balanceTable.getDebitFine());
            
            cell.setCellValue(result);
            return null;
        }
        if ("<creditTaxLand>".equals(foundedTag)) {
            Double result = getDoubleValue(balanceTable.getCreditTaxLand());
            
            cell.setCellValue(result);
            return null;
        }
        if ("<debitTaxLand>".equals(foundedTag)) {
            Double result = getDoubleValue(balanceTable.getDebitTaxLand());
            
            cell.setCellValue(result);
            return null;
        }
        if ("<creditServices>".equals(foundedTag)) {
            Double result = getDoubleValue(balanceTable.getCreditService());
            
            cell.setCellValue(result);
            return null;
        }
        if ("<debitServices>".equals(foundedTag)) {
            Double result = getDoubleValue(balanceTable.getDebitService());
      
            cell.setCellValue(result);
            return null;
        }
        if ("<creditEquipment>".equals(foundedTag)) {
            Double result = getDoubleValue(balanceTable.getCreditEquipment());
            
            cell.setCellValue(result);
            return null;
        }
        if ("<debitEquipment>".equals(foundedTag)) {
            Double result = getDoubleValue(balanceTable.getDebitEquipment());
            
            cell.setCellValue(result);
            return null;
        }
        if ("<datePaidRent>".equals(foundedTag)) {
            if (!isExist(rent.getDatePaid())) {
                cell.setCellValue("");
                return null;
            }
            LocalDate date = LocalDate.parse(rent.getDatePaid());
            newValue = formatDate(date);
        }
        if ("<datePaidFine>".equals(foundedTag)) {
            if (!isExist(fine.getDatePaid())) {
                cell.setCellValue("");
                return null;
            }
            LocalDate date = LocalDate.parse(fine.getDatePaid());
            newValue = formatDate(date);
        }
        if ("<datePaidTaxLand>".equals(foundedTag)) {
            if (!isExist(taxLand.getDatePaid())) {
                cell.setCellValue("");
                return null;
            }
            LocalDate date = LocalDate.parse(taxLand.getDatePaid());
            newValue = formatDate(date);
        }
        if ("<datePaidServices>".equals(foundedTag)) {
            if (!isExist(services.getDatePaid())) {
                cell.setCellValue("");
                return null;
            }
            LocalDate date = LocalDate.parse(services.getDatePaid());
            newValue = formatDate(date);
        }
        if ("<datePaidEquipment>".equals(foundedTag)) {
            if (!isExist(equipment.getDatePaid())) {
                cell.setCellValue("");
                return null;
            }
            LocalDate date = LocalDate.parse(equipment.getDatePaid());
            newValue = formatDate(date);
        }
        if ("<debitNext>".equals(foundedTag)) {
            int rowIndex = cell.getRowIndex() + 1;
            String formatString = "IF(H%1$d+D%1$d-J%1$d<0,0,H%1$d+D%1$d-J%1$d)";
            cell.setCellFormula(String.format(formatString, rowIndex));
            return null;
        }
        if ("<creditNext>".equals(foundedTag)) {
            int rowIndex = cell.getRowIndex() + 1;
            String formatString = "IF(J%1$d+E%1$d-H%1$d<0,0,J%1$d+E%1$d-H%1$d)";
            cell.setCellFormula(String.format(formatString, rowIndex));
            return null;
        }
        if ("<totalRent>".equals(foundedTag)) {
            int startIdx = startRow;
            int finishIdx = cell.getRowIndex() - 1; //get values before total rows
            String columnLetter = getColumnLetter(cell);
            String formula = columnLetter + startIdx;
            for (int i = startIdx + countTypePayments; i < finishIdx; i += countTypePayments) {
                formula += "+" + columnLetter + i;
            }
            cell.setCellFormula(formula);
            return null;
        }
        if ("<totalEquipment>".equals(foundedTag)) {
            int startIdx = startRow + 1;
            int finishIdx = cell.getRowIndex() - 2;
            String columnLetter = getColumnLetter(cell);
            String formula = columnLetter + startIdx;
            for (int i = startIdx + countTypePayments; i < finishIdx; i += countTypePayments) {
                formula += "+" + columnLetter + i;
            }
            cell.setCellFormula(formula);
            return null;
        }
        if ("<totalFine>".equals(foundedTag)) {
            int startIdx = startRow + 2;
            int finishIdx = cell.getRowIndex() - 4;
            String columnLetter = getColumnLetter(cell);
            String formula = columnLetter + startIdx;
            for (int i = startIdx + countTypePayments; i < finishIdx; i += countTypePayments) {
                formula += "+" + columnLetter + i;
            }
            cell.setCellFormula(formula);
            return null;
        }
        if ("<totalServices>".equals(foundedTag)) {
            int startIdx = startRow + 3;
            int finishIdx = cell.getRowIndex() - 5;
            String columnLetter = getColumnLetter(cell);
            String formula = columnLetter + startIdx;
            for (int i = startIdx + countTypePayments; i < finishIdx; i += countTypePayments) {
                formula += "+" + columnLetter + i;
            }
            cell.setCellFormula(formula);
            return null;
        }
        if ("<totalTaxLand>".equals(foundedTag)) {
            int startIdx = startRow + 4;
            int finishIdx = cell.getRowIndex() - 3;
            String columnLetter = getColumnLetter(cell);
            String formula = columnLetter + startIdx;
            for (int i = startIdx + countTypePayments; i < finishIdx; i += countTypePayments) {
                formula += "+" + columnLetter + i;
            }
            cell.setCellFormula(formula);
            return null;
        }
        if ("<sumTotalRent>".equals(foundedTag)) {
            int firstPaymentCell = cell.getRowIndex() - 4;
            int lastPaymentCell = cell.getRowIndex() - 1;
            String columnLetter = getColumnLetter(cell);
            cell.setCellFormula("SUM(" + columnLetter + firstPaymentCell
                    + ":" + columnLetter + lastPaymentCell + ")");
            return null;
        }
        if ("<sumOneAccum>".equals(foundedTag)) {
            int rowIndex = cell.getRowIndex();
            String columnLetter = getColumnLetter(cell);
            cell.setCellFormula("SUM(" + columnLetter + startRow + 
                    ":" + columnLetter + rowIndex + ")");
            return null;
        }
        
        if ("<Tag not founded>".equals(newValue))
            newValue = super.parse(foundedTag);
        return newValue;
    }
}
