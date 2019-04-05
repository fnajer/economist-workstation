/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package economistworkstation.Util;

import economistworkstation.ContractData;
import economistworkstation.Entity.Building;
import economistworkstation.Entity.Contract;
import economistworkstation.Entity.Month;
import economistworkstation.Entity.Renter;
import economistworkstation.Entity.User;
import economistworkstation.ExcelCreator;
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
public class TagParser {
    public static double sumForWords;
    public static String typeDoc;
    public static int num;
    public static int indexStartRow;
    
    public static boolean findTag(Cell cell, String srcPattern) {
        String cellString = cell.getStringCellValue();
        Pattern pattern = Pattern.compile(srcPattern);
        Matcher matcher = pattern.matcher(cellString);
        while(matcher.find()) {
            return true;
        }
        return false;
    }
    
    public static void convertTags (ContractData data) {
        Cell cell = data.getCell();
        Month month = data.getMonth();
        Renter renter = data.getRenter();
        Building building = data.getBuilding();
        Contract contract = data.getContract();
        String cellString = cell.getStringCellValue();
        
        Pattern pattern = Pattern.compile("<\\w+>");
        Matcher matcher = pattern.matcher(cellString);
        while(matcher.find()) {
            String resultString;
            String newValue = "<Tag not founded>";
            String foundedTag = matcher.group();
            System.out.println(foundedTag);
            
            //rent account
            if ("<date>".equals(foundedTag)) {
                LocalDate date = LocalDate.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                String formattedString = date.format(formatter);
                newValue = formattedString;
            }
            if ("<numRentAcc>".equals(foundedTag)) {
                newValue = Integer.toString(month.getNumberRentAcc());
            }
            if ("<numCommunalAcc>".equals(foundedTag)) {
                newValue = Integer.toString(month.getNumberCommunalAcc());
            }
            if ("<subject>".equals(foundedTag)) {
                newValue = renter.getSubject();
            }
            if ("<fullName>".equals(foundedTag)) {
                newValue = renter.getFullName();
            }
            if ("<numContract>".equals(foundedTag)) {
                System.out.println(contract.getId());
                newValue = Integer.toString(month.getIdContract());
            }
            if ("<dateStartContract>".equals(foundedTag)) {
                LocalDate date = LocalDate.parse(contract.getDateStart());
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                String formattedString = date.format(formatter);
                newValue = formattedString;
            }
            if ("<square>".equals(foundedTag)) {
                newValue = getDecimalFormat(Locale.getDefault()).format(building.getSquare());
            }
            if ("<monthNameAndYear>".equals(foundedTag)) {
                LocalDate date = LocalDate.parse(month.getDate());
                int monthNum = date.getMonth().minus(1).getValue();
                String monthName = Month.getMonthName(monthNum, false);
                int monthYear = date.getYear();
                if (monthNum == 12) {
                    monthYear--;
                }
                newValue = monthName + ' ' + Integer.toString(monthYear);
            }
            if ("<sumRent>".equals(foundedTag)) {
                String sumRent = getDecimalFormat(Locale.US).format(Month.calcSumRent(month));
                newValue = sumRent;
                
                resultString = cellString.replaceAll(foundedTag, newValue);
                double resultDouble = Double.parseDouble(resultString);
                
                if ("acrual".equals(TagParser.typeDoc)) {
                    if (cellWillClear(resultDouble, cell))
                        return;
                }
                
                cell.setCellValue(resultDouble);
                
                TagParser.sumForWords += resultDouble;
                return;
            }
            if ("<fine>".equals(foundedTag)) {
                String sumRent = getDecimalFormat(Locale.US).format(month.getFine());
                newValue = sumRent;
                
                resultString = cellString.replaceAll(foundedTag, newValue);
                double resultDouble = Double.parseDouble(resultString);
                
                if ("account".equals(TagParser.typeDoc)) {
                    if (rowWillClear(resultDouble, cell))
                        return;
                }
                
                if ("acrual".equals(TagParser.typeDoc)) {
                    if (cellWillClear(resultDouble, cell))
                        return;
                }
                
                cell.setCellValue(resultDouble);
                
                TagParser.sumForWords += resultDouble;
                return;
            }
            if ("<taxLand>".equals(foundedTag)) {
                String taxLand = getDecimalFormat(Locale.US).format(month.getTaxLand());
                newValue = taxLand;
                
                resultString = cellString.replaceAll(foundedTag, newValue);
                double resultDouble = Double.parseDouble(resultString);
                
                if ("account".equals(TagParser.typeDoc)) {
                    if (rowWillClear(resultDouble, cell))
                        return;
                }
                
                if ("acrual".equals(TagParser.typeDoc)) {
                    if (cellWillClear(resultDouble, cell))
                        return;
                }
                
                cell.setCellValue(resultDouble);
                
                TagParser.sumForWords += resultDouble;
                return;
            }
            if ("<sumRentAcc>".equals(foundedTag)) {
                cell.setCellFormula("SUM(D15:D17)");
                return;
            }
            if ("<sumInWords>".equals(foundedTag)) {
                double sum = TagParser.sumForWords;
                String sumInWords = Money.digits2Text(sum);
                newValue = sumInWords;
            }
            //communal account
            if ("<sumCommunalAcc>".equals(foundedTag)) {
                cell.setCellFormula("SUM(D16:D19)");
                return;
            }
            if ("<costWater>".equals(foundedTag)) {
                String costWater = Double.toString(Month.calcCostWater(month));
                newValue = costWater;
                
                resultString = cellString.replaceAll(foundedTag, newValue);
                double resultDouble = Double.parseDouble(resultString);
                
                if ("account".equals(TagParser.typeDoc))
                    if (rowWillDelete(resultDouble, cell))
                        return;
                
                if ("acrual".equals(TagParser.typeDoc))
                    if (cellWillClear(resultDouble, cell))
                        return;
                
                cell.setCellValue(resultDouble);
                
                TagParser.sumForWords += resultDouble;
                return;
            }
            if ("<costElectricity>".equals(foundedTag)) {
                String costElectricity = Double.toString(Month.calcCostElectricity(month));
                newValue = costElectricity;
                
                resultString = cellString.replaceAll(foundedTag, newValue);
                double resultDouble = Double.parseDouble(resultString);
                
                if ("account".equals(TagParser.typeDoc))
                    if (rowWillDelete(resultDouble, cell))
                        return; 
                
                if ("acrual".equals(TagParser.typeDoc))
                    if (cellWillClear(resultDouble, cell))
                        return;
                
                cell.setCellValue(resultDouble);
                
                TagParser.sumForWords += resultDouble;
                return;
            }
            if ("<costHeading>".equals(foundedTag)) {
                String costHeading = Double.toString(Month.calcCostHeading(month));
                newValue = costHeading;
                
                resultString = cellString.replaceAll(foundedTag, newValue);
                double resultDouble = Double.parseDouble(resultString);
                
                if ("account".equals(TagParser.typeDoc))
                    if (rowWillDelete(resultDouble, cell))
                        return;
                
                if ("calculation".equals(TagParser.typeDoc)) 
                    if (rowWillClear(resultDouble, cell))
                        return; 
                
                if ("acrual".equals(TagParser.typeDoc))
                    if (cellWillClear(resultDouble, cell))
                        return;
                
                cell.setCellValue(resultDouble);
                
                TagParser.sumForWords += resultDouble;
                return;
            }
            if ("<costGarbage>".equals(foundedTag)) {
                String costGarbage = Double.toString(Month.calcCostGarbage(month));
                newValue = costGarbage;
                
                resultString = cellString.replaceAll(foundedTag, newValue);
                double resultDouble = Double.parseDouble(resultString);
                
                if ("account".equals(TagParser.typeDoc))
                    if (rowWillDelete(resultDouble, cell))
                        return;
                
                if ("acrual".equals(TagParser.typeDoc))
                    if (cellWillClear(resultDouble, cell))
                        return;
                
                cell.setCellValue(resultDouble);
                
                TagParser.sumForWords += resultDouble;
                return;
            }
            if ("<costInternet>".equals(foundedTag)) {
                String costInternet = Double.toString(Month.calcCostInternet(month));
                newValue = costInternet;
                
                resultString = cellString.replaceAll(foundedTag, newValue);
                double resultDouble = Double.parseDouble(resultString);
                
                if ("account".equals(TagParser.typeDoc))
                    if (rowWillDelete(resultDouble, cell))
                        return;
                
                if ("calculation".equals(TagParser.typeDoc)) 
                    if (rowWillClear(resultDouble, cell))
                        return; 
                
                if ("acrual".equals(TagParser.typeDoc))
                    if (cellWillClear(resultDouble, cell))
                        return;
                
                cell.setCellValue(resultDouble);
                
                TagParser.sumForWords += resultDouble;
                return;
            }
            if ("<costTelephone>".equals(foundedTag)) {
                String costTelephone = Double.toString(Month.calcCostTelephone(month));
                newValue = costTelephone;
                
                resultString = cellString.replaceAll(foundedTag, newValue);
                double resultDouble = Double.parseDouble(resultString);
                
                if ("account".equals(TagParser.typeDoc))
                    if (rowWillDelete(resultDouble, cell))
                        return;
                
                if ("calculation".equals(TagParser.typeDoc)) 
                    if (rowWillClear(resultDouble, cell))
                        return; 
                
                if ("acrual".equals(TagParser.typeDoc))
                    if (cellWillClear(resultDouble, cell))
                        return;
                
                cell.setCellValue(resultDouble);
                
                TagParser.sumForWords += resultDouble;
                return;
            }
            // calculation account
            if ("<sumCalcAcc>".equals(foundedTag)) {
                cell.setCellFormula("SUM(B11:B26)");
                return;
            }
            if ("<type>".equals(foundedTag)) {
                newValue = building.getType();
            }
            if ("<currentMonthNameAndYear>".equals(foundedTag)) {
                LocalDate date = LocalDate.parse(month.getDate());
                int monthNum = date.getMonth().getValue();
                String monthName = Month.getMonthName(monthNum, false);
                int monthYear = date.getYear();

                newValue = monthName + ' ' + Integer.toString(monthYear);
            }
            if ("<month>".equals(foundedTag)) {
                LocalDate date = LocalDate.parse(month.getDate());
                int monthNum = date.getMonth().minus(1).getValue();
                String monthName = Month.getMonthName(monthNum, false);

                newValue = monthName;
            }
            if ("<monthGenitive>".equals(foundedTag)) {
                LocalDate date = LocalDate.parse(month.getDate());
                int monthNum = date.getMonth().minus(1).getValue();
                String monthName = Month.getMonthName(monthNum, true);

                newValue = monthName;
            }
            if ("<getCalcSumRent>".equals(foundedTag)) {
                String cost = getDecimalFormat(Locale.getDefault()).format(month.getCost());
                String index = getDecimalFormat(Locale.getDefault()).format(month.getIndexCost());
                newValue = cost + '*' + index + '=';
            }
            if ("<getCalcCostWater>".equals(foundedTag)) {
                String count = getDecimalFormat(Locale.getDefault()).format(month.getCountWater());
                String tariff = getDecimalFormat(Locale.getDefault()).format(month.getTariffWater());
                newValue = count + '*' + tariff + '=';
            }
            if ("<getCalcCostElectricity>".equals(foundedTag)) {
                String count = getDecimalFormat(Locale.getDefault()).format(month.getCountElectricity());
                String tariff = getDecimalFormat(Locale.getDefault()).format(month.getTariffElectricity());
                newValue = count + '*' + tariff + '=';
            }
            if ("<getCalcCostHeading>".equals(foundedTag)) {
                String count = getDecimalFormat(Locale.getDefault()).format(month.getCountHeading());
                String tariff = getDecimalFormat(Locale.getDefault()).format(month.getTariffHeading());
                newValue = count + '*' + tariff + '=';
            }
            if ("<getCalcCostGarbage>".equals(foundedTag)) {
                String count = getDecimalFormat(Locale.getDefault()).format(month.getCountGarbage());
                String tariff = getDecimalFormat(Locale.getDefault()).format(month.getTariffGarbage());
                newValue = count + '*' + tariff + '=';
            }
            if ("<user>".equals(foundedTag)) {
                User user = new User();
                newValue = user.getFullName();
            }
            //acruals statement
            if ("<num>".equals(foundedTag)) {
                cell.setCellValue(num++);
                return;
            }
            if ("<sumMonth>".equals(foundedTag)) {
                int rowIndex = cell.getRowIndex() + 1;
                cell.setCellFormula("SUM(D" + rowIndex + ":N" + rowIndex + ")");
                return;
            }
            if ("<sumOne>".equals(foundedTag)) {
                int rowIndex = cell.getRowIndex() - 1;
                int columnNumber = cell.getColumnIndex();
                String columnLetter = CellReference.convertNumToColString(columnNumber);
                cell.setCellFormula("SUM(" + columnLetter + indexStartRow + ":" + columnLetter + rowIndex + ")");
                return;
            }
            if ("<sumRentAcr>".equals(foundedTag)) {
                int rowIndex = cell.getRowIndex();
                cell.setCellFormula("SUM(D" + rowIndex + ":G" + rowIndex + ")");
                return;
            }
            if ("<sumCommunalAcr>".equals(foundedTag)) {
                int rowIndex = cell.getRowIndex();
                cell.setCellFormula("SUM(I" + rowIndex + ":N" + rowIndex + ")");
                return;
            }
            if ("<sumAcr>".equals(foundedTag)) {
                int rowIndex = cell.getRowIndex() + 1;
                cell.setCellFormula("SUM(D" + rowIndex + ":N" + rowIndex + ")");
                return;
            }
            if ("<squareInNum>".equals(foundedTag)) {
                String square = getDecimalFormat(Locale.US).format(building.getSquare());
                newValue = square;
                
                resultString = cellString.replaceAll(foundedTag, newValue);
                double resultDouble = Double.parseDouble(resultString);
                
                if ("acrual".equals(TagParser.typeDoc)) {
                    if (cellWillClear(resultDouble, cell))
                        return;
                }
                
                cell.setCellValue(resultDouble);
                
                return;
            }
            
            resultString = cellString.replaceAll(foundedTag, newValue);
            cell.setCellValue(resultString);
            cellString = resultString;
        }
    }
    
    public static ArrayList<Integer> rowsForClear = new ArrayList<Integer>();
    public static ArrayList<Integer> rowsForDelete = new ArrayList<Integer>();
    
    private static boolean rowWillClear(double value, Cell cell) {
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
    private static boolean rowWillDelete(double value, Cell cell) {
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
    private static boolean cellWillClear(double value, Cell cell) {
        if (value <= 0) {
            cell.setCellValue("");
            return true;
        }
        return false;
    }
    
    public static DecimalFormat getDecimalFormat(Locale locale) {
        DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(locale);
        DecimalFormat df = new DecimalFormat("#.##", otherSymbols);
        df.setRoundingMode(RoundingMode.HALF_UP);
        
        return df;
    }
}
