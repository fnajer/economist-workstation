/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package economistworkstation.Entity;

import economistworkstation.ContractData;
import java.time.LocalDate;
import javafx.collections.ObservableList;

/**
 *
 * @author fnajer
 */
public class ConsolidatedTagParser extends StatementTagParser {
    
    public ConsolidatedTagParser(ObservableList<ContractData> dataList, int indexStartRow) {
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
        //|same with
        if ("<sumOne>".equals(foundedTag)) {
            int rowIndex = cell.getRowIndex() - 1;
            String columnLetter = getColumnLetter(cell);
            cell.setCellFormula("SUM(" + columnLetter + startRow + 
                    ":" + columnLetter + rowIndex + ")");
            return null;
        }
        
        if ("<day>".equals(foundedTag)) {
            LocalDate date = LocalDate.now();
            newValue = String.format("\"%d\"", date.getDayOfMonth());
        }
        if ("<monthName>".equals(foundedTag)) {
            LocalDate date = LocalDate.now();
            int monthNum = date.getMonth().getValue();
            newValue = period.getMonthName(monthNum, true);
        }
        if ("<year>".equals(foundedTag)) {
            LocalDate date = LocalDate.now();
            newValue = String.format("%d", date.getYear());
        }
        if ("<services>".equals(foundedTag)) {
            double result = getDoubleValue(services.sumToPay());
            
            cell.setCellValue(result);
            return null;
        }
        if ("<servicesPaid>".equals(foundedTag)) {
            double result = getDoubleValue(services.safeGetPaid());

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
        if ("<sumPeriodMem>".equals(foundedTag)) {
            int rowIndex = cell.getRowIndex() + 1;
            cell.setCellFormula("SUM(J" + rowIndex + ":O" + rowIndex + ")");
            return null;
        }
        if ("<sumPeriodMemPaid>".equals(foundedTag)) {
            int rowIndex = cell.getRowIndex() + 1;
            cell.setCellFormula("SUM(S" + rowIndex + ":X" + rowIndex + ")");
            return null;
        }
        if ("<sumRentMem>".equals(foundedTag)) {
            int rowIndex = cell.getRowIndex();
            cell.setCellFormula("SUM(J" + rowIndex + ":M" + rowIndex + ")");
            return null;
        }
        if ("<sumRentPaidMem>".equals(foundedTag)) {
            int rowIndex = cell.getRowIndex();
            cell.setCellFormula("SUM(S" + rowIndex + ":V" + rowIndex + ")");
            return null;
        }
        if ("<sumMem>".equals(foundedTag)) {
            int rowIndex = cell.getRowIndex() - 1;
            cell.setCellFormula("P" + rowIndex + "+Y" + rowIndex);
            return null;
        }

        if ("<sumDebit>".equals(foundedTag)) {
            double result = getDoubleValue(balanceTable.sumDebit());
            
            cell.setCellValue(result);
            return null;
        }
        if ("<sumCredit>".equals(foundedTag)) {
            double result = getDoubleValue(balanceTable.sumCredit());
            
            cell.setCellValue(result);
            return null;
        }
        if ("<nextDebit>".equals(foundedTag)) {
            period.setBalanceTable(balanceTable); // table must be not null
            period.calculateBalance();
            BalanceTable nextBalanceTable = period.getNextBalanceTable();

            double result = getDoubleValue(nextBalanceTable.sumDebit());

            cell.setCellValue(result);
            return null;
        }
        if ("<nextCredit>".equals(foundedTag)) {
            period.setBalanceTable(balanceTable); // table must be not null
            period.calculateBalance();
            BalanceTable nextBalanceTable = period.getNextBalanceTable();

            double result = getDoubleValue(nextBalanceTable.sumCredit());

            cell.setCellValue(result);
            return null;
        }
        
        if ("<Tag not founded>".equals(newValue))
            newValue = super.parse(foundedTag);
        return newValue;
    }
}
