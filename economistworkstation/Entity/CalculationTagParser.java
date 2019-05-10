/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package economistworkstation.Entity;

import economistworkstation.ContractData;
import economistworkstation.Util.Money;
import java.time.LocalDate;
import java.util.Locale;

/**
 *
 * @author fnajer
 */
public class CalculationTagParser extends Parser {
    
    public CalculationTagParser(ContractData data) {
        super(data);
    }
    
    @Override
    protected String parse(String foundedTag) {
        String newValue = "<Tag not founded>";
  
        //|same with invoice
        if ("<monthNameAndYear>".equals(foundedTag)) {
            LocalDate date = LocalDate.parse(period.getEndPeriod());
            int monthNum = date.getMonth().minus(1).getValue();
            String monthName = period.getMonthName(monthNum, false);
            int monthYear = date.getYear();
            if (monthNum == 12) {
                monthYear--;
            }
            newValue = monthName + ' ' + Integer.toString(monthYear);
        }
        if ("<subject>".equals(foundedTag)) {
            newValue = renter.getSubject();
        }
        if ("<fullName>".equals(foundedTag)) {
            newValue = renter.getFullName();
        }
        if ("<square>".equals(foundedTag)) {
            newValue = safeDecFormat(building.getSquare(), Locale.getDefault());
        }
        //same||
        if ("<user>".equals(foundedTag)) {
            User user = new User();
            newValue = user.getFullName();
        }
        //endsame|
        if ("<rent>".equals(foundedTag)) {
            double result = getDoubleValue(rent.sumToPay());

            cell.setCellValue(result);
            return null;
        }
        if ("<fine>".equals(foundedTag)) {
            double result = getDoubleValue(fine.getFine());
            
            cell.setCellValue(result);
            return null;
        }
        if ("<taxLand>".equals(foundedTag)) {
            double result = getDoubleValue(taxLand.getTaxLand());
            
            cell.setCellValue(result);
            return null;
        }
        if ("<equipment>".equals(foundedTag)) {
            double result = getDoubleValue(equipment.getCostEquipment());
            
            if (rowWillDelete(result, cell))
                return null;

            cell.setCellValue(result);
            return null;
        }
        if ("<costWater>".equals(foundedTag)) {
            double result = getDoubleValue(services.calcCostWater());

            cell.setCellValue(result);
            return null;
        }
        if ("<costElectricity>".equals(foundedTag)) {
            double result = getDoubleValue(services.calcCostElectricity());

            cell.setCellValue(result);
            return null;
        }
        if ("<costHeading>".equals(foundedTag)) {
            double result = getDoubleValue(services.calcCostHeading());
            
            if (rowWillClear(result, cell))
                return null;

            cell.setCellValue(result);
            return null;
        }
        if ("<costGarbage>".equals(foundedTag)) {
            double result = getDoubleValue(services.calcCostGarbage());
          
            cell.setCellValue(result);
            return null;
        }
        if ("<costInternet>".equals(foundedTag)) {
            double result = getDoubleValue(services.calcCostInternet());
            
            if (rowWillClear(result, cell))
                return null;

            cell.setCellValue(result);
            return null;
        }
        if ("<costTelephone>".equals(foundedTag)) {
            double result = getDoubleValue(services.calcCostTelephone());
           
            if (rowWillClear(result, cell))
                return null;

            cell.setCellValue(result);
            return null;
        }
        if ("<sumCalculation>".equals(foundedTag)) {
            int firstCell = 11;
            int lastCell = cell.getRowIndex() - 1;
            String columnLetter = getColumnLetter(cell);
            cell.setCellFormula("SUM(" + columnLetter + firstCell
                + ":" + columnLetter + lastCell + ")");
            return null;
        }
        if ("<type>".equals(foundedTag)) {
            newValue = building.getType();
        }
        if ("<currentMonthNameAndYear>".equals(foundedTag)) {
            LocalDate date = LocalDate.parse(period.getEndPeriod());
            int monthNum = date.getMonth().getValue();
            String monthName = period.getMonthName(monthNum, false);
            int monthYear = date.getYear();

            newValue = monthName + ' ' + Integer.toString(monthYear);
        }
        if ("<month>".equals(foundedTag)) {
            LocalDate date = LocalDate.parse(period.getEndPeriod());
            int monthNum = date.getMonth().minus(1).getValue();

            newValue = period.getMonthName(monthNum, false);
        }
        if ("<monthGenitive>".equals(foundedTag)) {
            LocalDate date = LocalDate.parse(period.getEndPeriod());
            int monthNum = date.getMonth().minus(1).getValue();
            String monthName = period.getMonthName(monthNum, true);

            newValue = monthName;
        }
        if ("<getCalcRent>".equals(foundedTag)) {
            String cost = getStringValue(rent.getCost());
            String index = getStringValue(rent.getIndexCost());
            newValue = cost + '*' + index + '=';
        }
        if ("<getCalcCostWater>".equals(foundedTag)) {
            String count = getStringValue(services.getCountWater());
            String tariff = getStringValue(services.getTariffWater());
            newValue = count + '*' + tariff + '=';
        }
        if ("<getCalcCostElectricity>".equals(foundedTag)) {
            String count = getStringValue(services.getCountElectricity());
            String tariff = getStringValue(services.getTariffElectricity());
            newValue = count + '*' + tariff + '=';
        }
        if ("<getCalcCostHeading>".equals(foundedTag)) {
            String count = getStringValue(services.getCostHeading());
            String tariff = getStringValue(building.getSquare());
            newValue = count + '*' + tariff + '=';
        }
        if ("<getCalcCostGarbage>".equals(foundedTag)) {
            String count = getStringValue(services.getCostGarbage());
            String tariff = getStringValue(building.getSquare());
            newValue = count + '*' + tariff + '=';
        }
        
        return newValue;
    }
}
