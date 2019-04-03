/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package economistworkstation;

import economistworkstation.Entity.Building;
import economistworkstation.Entity.Contract;
import economistworkstation.Entity.Month;
import economistworkstation.Entity.Renter;
import economistworkstation.Model.BuildingModel;
import economistworkstation.Model.ContractModel;
import economistworkstation.Model.MonthModel;
import economistworkstation.Model.RenterModel;
import economistworkstation.Util.Money;
import economistworkstation.Util.TagParser;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.collections.ObservableList;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import static org.apache.poi.ss.usermodel.CellType.STRING;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**
 *
 * @author fnajer
 */
public class ExcelCreator {
    public static void iterateCells(HSSFWorkbook workbook, Consumer<ContractData> method, ContractData data) {
        TagParser.sumForWords = 0;
        TagParser.rowsForClear.clear();
        TagParser.rowsForDelete.clear();
        for (Sheet sheet : workbook) {
            ExcelCreator.sheet = sheet;
            for (Row row : sheet) {
                for (Cell cell : row) {
                    CellType cellType = cell.getCellType();
                    if (cellType == STRING) {
                        data.setCell(cell);
                        method.accept(data);
                    }
                }
            }
        }
        
        for (int row : TagParser.rowsForClear) {
            clearRow(row);
        }
        
        int deletion = 0;
        for (int row : TagParser.rowsForDelete) {
            removeRow(row - deletion);
            deletion++;
        }
    }
    
    private static Sheet sheet;

    public static void clearRow(int rowIndex) {
        Row row = sheet.getRow(rowIndex);
        for (Cell cell : row) {
            cell.setCellValue("");
        }
    }
    
    public static void removeRow(int rowIndex) {
        int lastRowNum=sheet.getLastRowNum();
        if(rowIndex>=0&&rowIndex<lastRowNum){
            sheet.shiftRows(rowIndex+1,lastRowNum, -1);
        }
        if(rowIndex==lastRowNum){
            Row removingRow=sheet.getRow(rowIndex);
            if(removingRow!=null){
                sheet.removeRow(removingRow);
            }
        }
    }
    
    public static void printAccountPayment(Contract contract, Month month) throws IOException {
       
        File file = new File("C:\\Users\\fnajer\\Desktop\\workbook.xls");

        HSSFWorkbook workbook;
        try (FileInputStream inputStream = new FileInputStream(file)) {
            workbook = new HSSFWorkbook(inputStream);
            
            Renter renter = RenterModel.getRenter(contract.getIdRenter());
            Building building = BuildingModel.getBuilding(contract.getIdBuilding());
            ContractData data = new ContractData(null, month, building, renter, contract, workbook);
            
            TagParser.typeDoc = "account";
            iterateCells(workbook, TagParser::convertTags, data);
        }

        try (OutputStream out = new FileOutputStream("C:\\Users\\fnajer\\Desktop\\workbookNew.xls")) {
            workbook.write(out);
        }
        System.out.println("Обновлен документ 'Счет для оплаты'");
    }
    
    public static HSSFWorkbook printAccountCalculation(Contract contract, Month month) throws IOException {
       
        File file = new File("C:\\Users\\fnajer\\Desktop\\workbookCalc.xls");

        HSSFWorkbook workbook;
        try (FileInputStream inputStream = new FileInputStream(file)) {
            workbook = new HSSFWorkbook(inputStream);
            
            Renter renter = RenterModel.getRenter(contract.getIdRenter());
            Building building = BuildingModel.getBuilding(contract.getIdBuilding());
            ContractData data = new ContractData(null, month, building, renter, contract, workbook);
            
            TagParser.typeDoc = "calculation";
            iterateCells(workbook, TagParser::convertTags, data);
        }
        
        if ("printAll".equals(ExcelCreator.process))
            return workbook;

        try (OutputStream out = new FileOutputStream("C:\\Users\\fnajer\\Desktop\\workbookCalcNew.xls")) {
            workbook.write(out);
        }
        
        System.out.println("Обновлен документ 'Расчет для оплаты'");
        return workbook;
    }
    
    private static String process;
    private static int indexRow;
    private static int indexColumn;
    
    private static void rewriteWb(HSSFWorkbook workbook, HSSFWorkbook oneCalcWb) {
        sheet = workbook.createSheet("new sheet");
        for (Sheet sheet1 : oneCalcWb) {
            for (Row row : sheet1) {
                indexRow = row.getRowNum();
//                CellStyle cs = row.getRowStyle();
                short width = row.getHeight();
                Row newRow = sheet.createRow(indexRow);
                newRow.setHeight(width);
                
//                workbook.getFontAt(indexRow)
//                CellStyle ns = workbook.createCellStyle();
//                ns.cloneStyleFrom(cs);
//                newRow.setRowStyle(ns);
                for (Cell cell : row) {
                    CellType cellType = cell.getCellType();
                    CellStyle cellStyle = cell.getCellStyle();
                    CellStyle newStyle = workbook.createCellStyle();
                   
                    newStyle.cloneStyleFrom(cellStyle);
                    indexColumn = cell.getColumnIndex();
                    System.out.println(indexColumn);
                    Cell newCell = newRow.createCell(indexColumn, STRING);
                    newCell.setCellValue("");
                    newCell.setCellStyle(newStyle);
                    
                    int i = sheet1.getColumnWidth(indexColumn);
//                   sheet.setColumnWidth(i);
                    if (cellType == STRING) {
                        //System.out.println(newCell);
                        newCell.setCellValue(cell.getStringCellValue());
                    }
                    
                }
            }
        }
    }
    
    public static void printCalculationAll() throws IOException {
        OutputStream out;
        HSSFWorkbook workbook = new HSSFWorkbook();
                
        Contract contract = ContractModel.getContract(51);
        ObservableList<Month> months = MonthModel.getMonths(contract.getId());

        ExcelCreator.process = "printAll";
        HSSFWorkbook oneCalcWb = printAccountCalculation(contract, months.get(0));
        rewriteWb(workbook, oneCalcWb);

        out = new FileOutputStream("C:\\Users\\fnajer\\Desktop\\workbookCalcAllNew.xls");
        workbook.write(out);

//        id++;
//        
//        workbook = printAccountCalculation(contract, months.get(1));
//        workbook.write(out);
        out.close();

        System.out.println("Обновлен документ 'Общие расчеты'");
    }
}
