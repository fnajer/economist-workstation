/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package economistworkstation.Entity;

import economistworkstation.ContractData;
import javafx.collections.ObservableList;
import org.apache.poi.ss.usermodel.Cell;

/**
 *
 * @author fnajer
 */
public class AccrualTagParser extends StatementTagParser {
   
    public AccrualTagParser(ObservableList<ContractData> dataList, int indexStartRow) {
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
        
        if ("<rent>".equals(foundedTag)) {
            double result = getDoubleValue(rent.sumToPay());

            if (cellWillClear(result, cell))
                return null;
            
            cell.setCellValue(result);
            return null;
        }
        if ("<fine>".equals(foundedTag)) {
            double result = getDoubleValue(fine.getFine());
            
            if (cellWillClear(result, cell))
                return null;
            
            cell.setCellValue(result);
            return null;
        }
        if ("<taxLand>".equals(foundedTag)) {
            double result = getDoubleValue(taxLand.getTaxLand());
            
            if (cellWillClear(result, cell))
                return null;
            
            cell.setCellValue(result);
            return null;
        }
        if ("<equipment>".equals(foundedTag)) {
            double result = getDoubleValue(equipment.getCostEquipment());
            
            if (cellWillClear(result, cell))
                return null;

            cell.setCellValue(result);
            return null;
        }
        if ("<costWater>".equals(foundedTag)) {
            double result = getDoubleValue(services.calcCostWater());

            if (cellWillClear(result, cell))
                return null;
            
            cell.setCellValue(result);
            return null;
        }
        if ("<costElectricity>".equals(foundedTag)) {
            double result = getDoubleValue(services.calcCostElectricity());

            if (cellWillClear(result, cell))
                return null;
            
            cell.setCellValue(result);
            return null;
        }
        if ("<costHeading>".equals(foundedTag)) {
            double result = getDoubleValue(services.calcCostHeading());
            
            if (cellWillClear(result, cell))
                return null;

            cell.setCellValue(result);
            return null;
        }
        if ("<costGarbage>".equals(foundedTag)) {
            double result = getDoubleValue(services.calcCostGarbage());
          
            if (cellWillClear(result, cell))
                return null;
            
            cell.setCellValue(result);
            return null;
        }
        if ("<costInternet>".equals(foundedTag)) {
            double result = getDoubleValue(services.calcCostInternet());
            
            if (cellWillClear(result, cell))
                return null;

            cell.setCellValue(result);
            return null;
        }
        if ("<costTelephone>".equals(foundedTag)) {
            double result = getDoubleValue(services.calcCostTelephone());
           
            if (cellWillClear(result, cell))
                return null;

            cell.setCellValue(result);
            return null;
        }
        
        if ("<sumMonth>".equals(foundedTag)) {
            int rowIndex = cell.getRowIndex() + 1;
            cell.setCellFormula("SUM(D" + rowIndex + ":N" + rowIndex + ")");
            return null;
        }
        if ("<sumRentalAccr>".equals(foundedTag)) {
            int rowIndex = cell.getRowIndex();
            cell.setCellFormula("SUM(D" + rowIndex + ":G" + rowIndex + ")");
            return null;
        }
        if ("<sumServicesAccr>".equals(foundedTag)) {
            int rowIndex = cell.getRowIndex();
            cell.setCellFormula("SUM(I" + rowIndex + ":N" + rowIndex + ")");
            return null;
        }
        if ("<sumAccr>".equals(foundedTag)) {
            int rowIndex = cell.getRowIndex() + 1;
            cell.setCellFormula("SUM(D" + rowIndex + ":N" + rowIndex + ")");
            return null;
        }
        if ("<squareInNum>".equals(foundedTag)) {
            double result = getDoubleValue(building.getSquare());
            
            if (cellWillClear(result, cell))
                return null;

            cell.setCellValue(result);
            return null;
        }
        
        if ("<Tag not founded>".equals(newValue))
            newValue = super.parse(foundedTag);
        return newValue;
    }
    
    private static boolean cellWillClear(double value, Cell cell) {
        if (value <= 0) {
            cell.setCellValue("");
            return true;
        }
        return false;
    }
}
