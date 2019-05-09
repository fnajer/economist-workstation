/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package economistworkstation.Util;

import economistworkstation.ContractData;
import economistworkstation.Entity.BalanceTable;
import economistworkstation.Entity.Building;
import economistworkstation.Entity.Contract;
import economistworkstation.Entity.Equipment;
import economistworkstation.Entity.Fine;
import economistworkstation.Entity.Period;
import economistworkstation.Entity.Rent;
import economistworkstation.Entity.Renter;
import economistworkstation.Entity.Services;
import economistworkstation.Entity.TaxLand;
import economistworkstation.Entity.User;
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
        Period period = data.getPeriod();
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
            
            BalanceTable balanceTable = period.getBalanceTable();
            balanceTable = balanceTable == null ? new BalanceTable() : balanceTable;
            
            Rent rent = (Rent) period.getRentPayment();
            rent = rent == null ? new Rent() : rent;
            Fine fine = (Fine) period.getFinePayment();
            fine = fine == null ? new Fine() : fine;
            TaxLand taxLand = (TaxLand) period.getTaxLandPayment();
            taxLand = taxLand == null ? new TaxLand() : taxLand;
            Equipment equipment = (Equipment) period.getEquipmentPayment();
            equipment = equipment == null ? new Equipment() : equipment;
            Services services = (Services) period.getServicesPayment();
            services = services == null ? new Services() : services;
            
            //rent account
            if ("<date>".equals(foundedTag)) {
                LocalDate date = LocalDate.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                String formattedString = date.format(formatter);
                newValue = formattedString;
            }
            if ("<numRentAcc>".equals(foundedTag)) {
                newValue = Integer.toString(period.getNumberRentAcc());
            }
            if ("<numServicesAcc>".equals(foundedTag)) {
                newValue = Integer.toString(period.getNumberServicesAcc());
            }
            if ("<subject>".equals(foundedTag)) {
                newValue = renter.getSubject();
            }
            if ("<fullName>".equals(foundedTag)) {
                newValue = renter.getFullName();
            }
            if ("<numContract>".equals(foundedTag)) {
                newValue = Integer.toString(period.getIdContract());
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
                LocalDate date = LocalDate.parse(period.getEndPeriod());
                int monthNum = date.getMonth().minus(1).getValue();
                String monthName = period.getMonthName(monthNum, false);
                int monthYear = date.getYear();
                if (monthNum == 12) {
                    monthYear--;
                }
                newValue = monthName + ' ' + Integer.toString(monthYear);
            }
            if ("<rent>".equals(foundedTag)) {
                String sumRent = parseProperty(rent.sumToPay(), Locale.US);
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
                String sumRent = parseProperty(fine.getFine(), Locale.US);
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
                String costTaxLand = parseProperty(taxLand.getTaxLand(), Locale.US);
                newValue = costTaxLand;
                
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
            if ("<equipment>".equals(foundedTag)) {
                String costTaxLand = parseProperty(equipment.getCostEquipment(), Locale.US);
                newValue = costTaxLand;
                
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
                String costWater = Double.toString(services.calcCostWater());
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
                String costElectricity = Double.toString(services.calcCostElectricity());
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
                String costHeading = Double.toString(services.calcCostHeading());
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
                String costGarbage = Double.toString(services.calcCostGarbage());
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
                String costInternet = Double.toString(services.calcCostInternet());
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
                String costTelephone = Double.toString(services.calcCostTelephone());
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
                LocalDate date = LocalDate.parse(period.getEndPeriod());
                int monthNum = date.getMonth().getValue();
                String monthName = period.getMonthName(monthNum, false);
                int monthYear = date.getYear();

                newValue = monthName + ' ' + Integer.toString(monthYear);
            }
            if ("<month>".equals(foundedTag)) {
                LocalDate date = LocalDate.parse(period.getEndPeriod());
                int monthNum = date.getMonth().minus(1).getValue();
                String monthName = period.getMonthName(monthNum, false);

                newValue = monthName;
            }
            if ("<monthGenitive>".equals(foundedTag)) {
                LocalDate date = LocalDate.parse(period.getEndPeriod());
                int monthNum = date.getMonth().minus(1).getValue();
                String monthName = period.getMonthName(monthNum, true);

                newValue = monthName;
            }
            if ("<getCalcSumRent>".equals(foundedTag)) {
                
                String cost = parseProperty(rent.getCost(), Locale.US);
                String index = parseProperty(rent.getIndexCost(), Locale.US);
                newValue = cost + '*' + index + '=';
            }
            if ("<getCalcCostWater>".equals(foundedTag)) {
                String count = parseProperty(services.getCountWater(), Locale.US);
                String tariff = parseProperty(services.getTariffWater(), Locale.US);
                newValue = count + '*' + tariff + '=';
            }
            if ("<getCalcCostElectricity>".equals(foundedTag)) {
                String count = parseProperty(services.getCountElectricity(), Locale.US);
                String tariff = parseProperty(services.getTariffElectricity(), Locale.US);
                newValue = count + '*' + tariff + '=';
            }
            if ("<getCalcCostHeading>".equals(foundedTag)) {
                String count = parseProperty(services.getCostHeading(), Locale.US);
                String tariff = parseProperty(building.getSquare(), Locale.US);
                newValue = count + '*' + tariff + '=';
            }
            if ("<getCalcCostGarbage>".equals(foundedTag)) {
                String count = parseProperty(services.getCostGarbage(), Locale.US);
                String tariff = parseProperty(building.getSquare(), Locale.US);
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
            
            //memorial
            if ("<day>".equals(foundedTag)) {
                LocalDate date = LocalDate.now();
                String formattedString = String.format("\"%d\"", date.getDayOfMonth());
                newValue = formattedString;
            }
            if ("<monthName>".equals(foundedTag)) {
                LocalDate date = LocalDate.now();
                int monthNum = date.getMonth().getValue();
                String formattedString = period.getMonthName(monthNum, true);
                newValue = formattedString;
            }
            if ("<year>".equals(foundedTag)) {
                LocalDate date = LocalDate.now();
                String formattedString = String.format("%d", date.getYear());
                newValue = formattedString;
            }
            if ("<services>".equals(foundedTag)) {
                String sumServices = Double.toString(services.sumToPay());
                newValue = sumServices;
                
                resultString = cellString.replaceAll(foundedTag, newValue);
                double resultDouble = Double.parseDouble(resultString);
     
                cell.setCellValue(resultDouble);
                return;
            }
            if ("<servicesPaid>".equals(foundedTag)) {
                String servicesPaid = Double.toString(services.safeGetPaid());
                newValue = servicesPaid;
                
                resultString = cellString.replaceAll(foundedTag, newValue);
                double resultDouble = Double.parseDouble(resultString);
     
                cell.setCellValue(resultDouble);
                return;
            }
            if ("<equipment>".equals(foundedTag)) {
                String sumEquipment = Double.toString(equipment.sumToPay());
                newValue = sumEquipment;
                
                resultString = cellString.replaceAll(foundedTag, newValue);
                double resultDouble = Double.parseDouble(resultString);
     
                cell.setCellValue(resultDouble);
                return;
            }
            if ("<equipmentPaid>".equals(foundedTag)) {
                String equipmentPaid = Double.toString(equipment.safeGetPaid());
                newValue = equipmentPaid;
                
                resultString = cellString.replaceAll(foundedTag, newValue);
                double resultDouble = Double.parseDouble(resultString);
     
                cell.setCellValue(resultDouble);
                return;
            }
            if ("<rentPaid>".equals(foundedTag)) {
                String rentPaid = Double.toString(rent.safeGetPaid());
                newValue = rentPaid;
                
                resultString = cellString.replaceAll(foundedTag, newValue);
                double resultDouble = Double.parseDouble(resultString);
     
                cell.setCellValue(resultDouble);
                return;
            }
            if ("<finePaid>".equals(foundedTag)) {
                String finePaid = Double.toString(fine.safeGetPaid());
                newValue = finePaid;
                
                resultString = cellString.replaceAll(foundedTag, newValue);
                double resultDouble = Double.parseDouble(resultString);
     
                cell.setCellValue(resultDouble);
                return;
            }
            if ("<taxLandPaid>".equals(foundedTag)) {
                String taxLandPaid = Double.toString(taxLand.safeGetPaid());
                newValue = taxLandPaid;
                
                resultString = cellString.replaceAll(foundedTag, newValue);
                double resultDouble = Double.parseDouble(resultString);
     
                cell.setCellValue(resultDouble);
                return;
            }
            if ("<sumPeriodMem>".equals(foundedTag)) {
                int rowIndex = cell.getRowIndex() + 1;
                cell.setCellFormula("SUM(J" + rowIndex + ":O" + rowIndex + ")");
                return;
            }
            if ("<sumPeriodMemPaid>".equals(foundedTag)) {
                int rowIndex = cell.getRowIndex() + 1;
                cell.setCellFormula("SUM(S" + rowIndex + ":X" + rowIndex + ")");
                return;
            }
            if ("<sumRentMem>".equals(foundedTag)) {
                int rowIndex = cell.getRowIndex();
                cell.setCellFormula("SUM(J" + rowIndex + ":M" + rowIndex + ")");
                return;
            }
            if ("<sumRentPaidMem>".equals(foundedTag)) {
                int rowIndex = cell.getRowIndex();
                cell.setCellFormula("SUM(S" + rowIndex + ":V" + rowIndex + ")");
                return;
            }
            if ("<sumMem>".equals(foundedTag)) {
                int rowIndex = cell.getRowIndex() + 1;
                cell.setCellFormula("SUM(P" + rowIndex + ":Y" + rowIndex + ")");
                return;
            }
            
            if ("<sumDebit>".equals(foundedTag)) {
                String sumDebit = Double.toString(balanceTable.sumDebit());
                newValue = sumDebit;
                
                resultString = cellString.replaceAll(foundedTag, newValue);
                double resultDouble = Double.parseDouble(resultString);
     
                cell.setCellValue(resultDouble);
                return;
            }
            if ("<sumCredit>".equals(foundedTag)) {
                String sumCredit = Double.toString(balanceTable.sumCredit());
                newValue = sumCredit;
                
                resultString = cellString.replaceAll(foundedTag, newValue);
                double resultDouble = Double.parseDouble(resultString);
     
                cell.setCellValue(resultDouble);
                return;
            }
            if ("<nextDebit>".equals(foundedTag)) {
                period.setBalanceTable(balanceTable); // table must be not null
                period.calculateBalance();
                BalanceTable nextBalanceTable = period.getNextBalanceTable();
            
                String sumCredit = Double.toString(nextBalanceTable.sumDebit());
                newValue = sumCredit;
                
                resultString = cellString.replaceAll(foundedTag, newValue);
                double resultDouble = Double.parseDouble(resultString);
     
                cell.setCellValue(resultDouble);
                return;
            }
            if ("<nextCredit>".equals(foundedTag)) {
                period.setBalanceTable(balanceTable); // table must be not null
                period.calculateBalance();
                BalanceTable nextBalanceTable = period.getNextBalanceTable();
                
                String sumCredit = Double.toString(nextBalanceTable.sumCredit());
                newValue = sumCredit;
                
                resultString = cellString.replaceAll(foundedTag, newValue);
                double resultDouble = Double.parseDouble(resultString);
     
                cell.setCellValue(resultDouble);
                return;
            }
            
            //accumulation
            if ("<accountRentNum>".equals(foundedTag)) {
                if (rent.isEmpty()) {
                    cell.setCellValue("");
                    return;
                }
                
                newValue = Integer.toString(period.getNumberRentAcc());
                 
                resultString = cellString.replaceAll(foundedTag, newValue);
                int resultInt = Integer.parseInt(resultString);
                
                cell.setCellValue(resultInt);
                return;
            }
            if ("<accountEquipmentNum>".equals(foundedTag)) {
                if (equipment.isEmpty()) {
                    cell.setCellValue("");
                    return;
                }
                
                newValue = Integer.toString(period.getNumberRentAcc());
                 
                resultString = cellString.replaceAll(foundedTag, newValue);
                int resultInt = Integer.parseInt(resultString);
                
                cell.setCellValue(resultInt);
                return;
            }
            if ("<accountFineNum>".equals(foundedTag)) {
                if (fine.isEmpty()) {
                    cell.setCellValue("");
                    return;
                }
                
                newValue = Integer.toString(period.getNumberRentAcc());
                 
                resultString = cellString.replaceAll(foundedTag, newValue);
                int resultInt = Integer.parseInt(resultString);
                
                cell.setCellValue(resultInt);
                return;
            }
            if ("<accountServicesNum>".equals(foundedTag)) {   
                if (services.isEmpty()) {
                    cell.setCellValue("");
                    return;
                }            
                
                newValue = Integer.toString(period.getNumberServicesAcc());
                
                resultString = cellString.replaceAll(foundedTag, newValue);
                int resultInt = Integer.parseInt(resultString);
                
                cell.setCellValue(resultInt);
                return;
            }
            if ("<accountTaxLandNum>".equals(foundedTag)) {
                if (taxLand.isEmpty()) {
                    cell.setCellValue("");
                    return;
                }
                
                newValue = Integer.toString(period.getNumberRentAcc());
                 
                resultString = cellString.replaceAll(foundedTag, newValue);
                int resultInt = Integer.parseInt(resultString);
                
                cell.setCellValue(resultInt);
                return;
            }
            if ("<creditRent>".equals(foundedTag)) {
                Double safeValue = balanceTable.safeGet(balanceTable.getCreditRent());
                String credit = Double.toString(safeValue);
                newValue = credit;
                
                resultString = cellString.replaceAll(foundedTag, newValue);
                double resultDouble = Double.parseDouble(resultString);
     
                cell.setCellValue(resultDouble);
                return;
            }
            if ("<debitRent>".equals(foundedTag)) {
                Double safeValue = balanceTable.safeGet(balanceTable.getDebitRent());
                String debit = Double.toString(safeValue);
                newValue = debit;
                
                resultString = cellString.replaceAll(foundedTag, newValue);
                double resultDouble = Double.parseDouble(resultString);
     
                cell.setCellValue(resultDouble);
                return;
            }
            if ("<creditFine>".equals(foundedTag)) {
                Double safeValue = balanceTable.safeGet(balanceTable.getCreditFine());
                String credit = Double.toString(safeValue);
                newValue = credit;
                
                resultString = cellString.replaceAll(foundedTag, newValue);
                double resultDouble = Double.parseDouble(resultString);
     
                cell.setCellValue(resultDouble);
                return;
            }
            if ("<debitFine>".equals(foundedTag)) {
                Double safeValue = balanceTable.safeGet(balanceTable.getDebitFine());
                String debit = Double.toString(safeValue);
                newValue = debit;
                
                resultString = cellString.replaceAll(foundedTag, newValue);
                double resultDouble = Double.parseDouble(resultString);
     
                cell.setCellValue(resultDouble);
                return;
            }
            if ("<creditTaxLand>".equals(foundedTag)) {
                Double safeValue = balanceTable.safeGet(balanceTable.getCreditTaxLand());
                String credit = Double.toString(safeValue);
                newValue = credit;
                
                resultString = cellString.replaceAll(foundedTag, newValue);
                double resultDouble = Double.parseDouble(resultString);
     
                cell.setCellValue(resultDouble);
                return;
            }
            if ("<debitTaxLand>".equals(foundedTag)) {
                Double safeValue = balanceTable.safeGet(balanceTable.getDebitTaxLand());
                String debit = Double.toString(safeValue);
                newValue = debit;
                
                resultString = cellString.replaceAll(foundedTag, newValue);
                double resultDouble = Double.parseDouble(resultString);
     
                cell.setCellValue(resultDouble);
                return;
            }
            if ("<creditServices>".equals(foundedTag)) {
                Double safeValue = balanceTable.safeGet(balanceTable.getCreditService());
                String credit = Double.toString(safeValue);
                newValue = credit;
                
                resultString = cellString.replaceAll(foundedTag, newValue);
                double resultDouble = Double.parseDouble(resultString);
     
                cell.setCellValue(resultDouble);
                return;
            }
            if ("<debitServices>".equals(foundedTag)) {
                Double safeValue = balanceTable.safeGet(balanceTable.getDebitService());
                String debit = Double.toString(safeValue);
                newValue = debit;
                
                resultString = cellString.replaceAll(foundedTag, newValue);
                double resultDouble = Double.parseDouble(resultString);
     
                cell.setCellValue(resultDouble);
                return;
            }
            if ("<creditEquipment>".equals(foundedTag)) {
                Double safeValue = balanceTable.safeGet(balanceTable.getCreditEquipment());
                String credit = Double.toString(safeValue);
                newValue = credit;
                
                resultString = cellString.replaceAll(foundedTag, newValue);
                double resultDouble = Double.parseDouble(resultString);
     
                cell.setCellValue(resultDouble);
                return;
            }
            if ("<debitEquipment>".equals(foundedTag)) {
                Double safeValue = balanceTable.safeGet(balanceTable.getDebitEquipment());
                String debit = Double.toString(safeValue);
                newValue = debit;
                
                resultString = cellString.replaceAll(foundedTag, newValue);
                double resultDouble = Double.parseDouble(resultString);
     
                cell.setCellValue(resultDouble);
                return;
            }
            if ("<datePaidRent>".equals(foundedTag)) {
                if (!isExist(rent.getDatePaid())) {
                    cell.setCellValue("");
                    return;
                }
                LocalDate date = LocalDate.parse(rent.getDatePaid());
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                String formattedString = date.format(formatter);
                newValue = formattedString;
            }
            if ("<datePaidFine>".equals(foundedTag)) {
                if (!isExist(fine.getDatePaid())) {
                    cell.setCellValue("");
                    return;
                }
                LocalDate date = LocalDate.parse(fine.getDatePaid());
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                String formattedString = date.format(formatter);
                newValue = formattedString;
            }
            if ("<datePaidTaxLand>".equals(foundedTag)) {
                if (!isExist(taxLand.getDatePaid())) {
                    cell.setCellValue("");
                    return;
                }
                LocalDate date = LocalDate.parse(taxLand.getDatePaid());
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                String formattedString = date.format(formatter);
                newValue = formattedString;
            }
            if ("<datePaidServices>".equals(foundedTag)) {
                if (!isExist(services.getDatePaid())) {
                    cell.setCellValue("");
                    return;
                }
                LocalDate date = LocalDate.parse(services.getDatePaid());
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                String formattedString = date.format(formatter);
                newValue = formattedString;
            }
            if ("<datePaidEquipment>".equals(foundedTag)) {
                if (!isExist(equipment.getDatePaid())) {
                    cell.setCellValue("");
                    return;
                }
                LocalDate date = LocalDate.parse(equipment.getDatePaid());
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                String formattedString = date.format(formatter);
                newValue = formattedString;
            }
            if ("<debitNext>".equals(foundedTag)) {
                int rowIndex = cell.getRowIndex() + 1;
                String formatString = "IF(H%1$d+D%1$d-J%1$d<0,0,H%1$d+D%1$d-J%1$d)";
                cell.setCellFormula(String.format(formatString, rowIndex));
                return;
            }
            if ("<creditNext>".equals(foundedTag)) {
                int rowIndex = cell.getRowIndex() + 1;
                String formatString = "IF(J%1$d+E%1$d-H%1$d<0,0,J%1$d+E%1$d-H%1$d)";
                cell.setCellFormula(String.format(formatString, rowIndex));
                return;
            }
            if ("<totalRent>".equals(foundedTag)) {
                int startIdx = numFirstRow;
                int finishIdx = cell.getRowIndex() - 1; //get values before total rows
                int columnNumber = cell.getColumnIndex();
                String columnLetter = CellReference.convertNumToColString(columnNumber);
                String formula = columnLetter + startIdx;
                for (int i = startIdx + countTypePayments; i < finishIdx; i += countTypePayments) {
                    formula += "+" + columnLetter + i;
                }
                cell.setCellFormula(formula);
                return;
            }
            if ("<totalEquipment>".equals(foundedTag)) {
                int startIdx = numFirstRow + 1;
                int finishIdx = cell.getRowIndex() - 2;
                int columnNumber = cell.getColumnIndex();
                String columnLetter = CellReference.convertNumToColString(columnNumber);
                String formula = columnLetter + startIdx;
                for (int i = startIdx + countTypePayments; i < finishIdx; i += countTypePayments) {
                    formula += "+" + columnLetter + i;
                }
                cell.setCellFormula(formula);
                return;
            }
            if ("<totalFine>".equals(foundedTag)) {
                int startIdx = numFirstRow + 2;
                int finishIdx = cell.getRowIndex() - 4;
                int columnNumber = cell.getColumnIndex();
                String columnLetter = CellReference.convertNumToColString(columnNumber);
                String formula = columnLetter + startIdx;
                for (int i = startIdx + countTypePayments; i < finishIdx; i += countTypePayments) {
                    formula += "+" + columnLetter + i;
                }
                cell.setCellFormula(formula);
                return;
            }
            if ("<totalServices>".equals(foundedTag)) {
                int startIdx = numFirstRow + 3;
                int finishIdx = cell.getRowIndex() - 5;
                int columnNumber = cell.getColumnIndex();
                String columnLetter = CellReference.convertNumToColString(columnNumber);
                String formula = columnLetter + startIdx;
                for (int i = startIdx + countTypePayments; i < finishIdx; i += countTypePayments) {
                    formula += "+" + columnLetter + i;
                }
                cell.setCellFormula(formula);
                return;
            }
            if ("<totalTaxLand>".equals(foundedTag)) {
                int startIdx = numFirstRow + 4;
                int finishIdx = cell.getRowIndex() - 3;
                int columnNumber = cell.getColumnIndex();
                String columnLetter = CellReference.convertNumToColString(columnNumber);
                String formula = columnLetter + startIdx;
                for (int i = startIdx + countTypePayments; i < finishIdx; i += countTypePayments) {
                    formula += "+" + columnLetter + i;
                }
                cell.setCellFormula(formula);
                return;
            }
            if ("<sumTotalRent>".equals(foundedTag)) {
                int firstPaymentCell = cell.getRowIndex() - 4;
                int lastPaymentCell = cell.getRowIndex() - 1;
                int columnNumber = cell.getColumnIndex();
                String columnLetter = CellReference.convertNumToColString(columnNumber);
                cell.setCellFormula("SUM(" + columnLetter + firstPaymentCell
                        + ":" + columnLetter + lastPaymentCell + ")");
                return;
            }
            if ("<sumOneAccum>".equals(foundedTag)) {
                int rowIndex = cell.getRowIndex();
                int columnNumber = cell.getColumnIndex();
                String columnLetter = CellReference.convertNumToColString(columnNumber);
                cell.setCellFormula("SUM(" + columnLetter + indexStartRow + ":" + columnLetter + rowIndex + ")");
                return;
            }
            
            resultString = cellString.replaceAll(foundedTag, newValue);
            cell.setCellValue(resultString);
            cellString = resultString;
        }
    }
    
    private static final int countTypePayments = 5;
    private static final int numFirstRow = 4; // 3 + 1
    
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
    
    public static String parseProperty(Double doubleObject, Locale locale) {
        DecimalFormat df = getDecimalFormat(locale);
        try {
            return df.format(doubleObject);
        } catch(IllegalArgumentException e) {
            System.err.println(String.format(
                    "%s: value '%s' is not correct. Replaced by 0.0",
                    "TagParser",
                    doubleObject));
            return "0.0";
        }
    }
}
