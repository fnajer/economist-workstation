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
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
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
import org.apache.poi.ss.util.CellRangeAddress;

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
    
    public static void printAccountCalculation(Contract contract, Month month) throws IOException {
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
        
        try (OutputStream out = new FileOutputStream("C:\\Users\\fnajer\\Desktop\\workbookCalcNew.xls")) {
            workbook.write(out);
        }
        
        System.out.println("Обновлен документ 'Расчет для оплаты'");
    }
    
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
    
    public static int findRow(Workbook workbook, String searchString) {
        boolean isFounded;
        for (Sheet sheet : workbook) {
            for (Row row : sheet) {
                for (Cell cell : row) {
                    CellType cellType = cell.getCellType();
                    if (cellType == STRING) {
                        isFounded = TagParser.findTag(cell, searchString);
                        if (isFounded)
                            return cell.getRowIndex();
                    }
                }
            }
        }
        return -1;
    }
    
    private static void addTemplateRows(Workbook workbook, int contractsSize) {
        int indexStartRow = findRow(workbook, "<num>");
        if (indexStartRow != -1)
            for (int i = indexStartRow + 1; i < contractsSize + indexStartRow; i++) {
                copyRow(workbook, sheet, indexStartRow, indexStartRow+1);
            }
    }
    
    public static void buildAcr(Workbook workbook, Consumer<ContractData> method,
            ObservableList<ContractData> data) {
        for (Sheet sheet : workbook) {
            ExcelCreator.sheet = sheet;
            addTemplateRows(workbook, data.size());
            for (Row row : sheet) {
                for (Cell cell : row) {
                    CellType cellType = cell.getCellType();
                    if (cellType == STRING) {
                        //data.setCell(cell);
                        //method.accept(data);
                    }
                }
            }
        }
    }
    
    public static void printAcruals() throws IOException {
        File file = new File("C:\\Users\\fnajer\\Desktop\\workbookAcr.xls");

        Workbook workbook;
        ObservableList<ContractData> fullContracts;
        try (FileInputStream inputStream = new FileInputStream(file)) {
            workbook = new HSSFWorkbook(inputStream);
            
            LocalDate data = LocalDate.parse("2019-06-01");
            fullContracts = MonthModel.getContractData(data);
            
            TagParser.typeDoc = "acrual";
            buildAcr(workbook, TagParser::convertTags, fullContracts);
        }
        
        try (OutputStream out = new FileOutputStream("C:\\Users\\fnajer\\Desktop\\workbookAcrNew.xls")) {
            workbook.write(out);
        }
        
        System.out.println("Создан документ 'Ведомость начислений'");
    }
    
    private static void copyRow(Workbook workbook, Sheet worksheet, 
            int sourceRowNum, int destinationRowNum) {
        // Get the source / new row
        Row newRow = worksheet.getRow(destinationRowNum);
        Row sourceRow = worksheet.getRow(sourceRowNum);

        // If the row exist in destination, push down all rows by 1 else create a new row
        if (newRow != null) {
            worksheet.shiftRows(destinationRowNum, worksheet.getLastRowNum(), 1, true, false);
        } else {
            newRow = worksheet.createRow(destinationRowNum);
        }

        // Loop through source columns to add to new row
        for (int i = 0; i < sourceRow.getLastCellNum(); i++) {
            // Grab a copy of the old/new cell
            Cell oldCell = sourceRow.getCell(i);
            Cell newCell = newRow.createCell(i);

            // If the old cell is null jump to next cell
            if (oldCell == null) {
                newCell = null;
                continue;
            }

            // Copy style from old cell and apply to new cell
            CellStyle newCellStyle = oldCell.getCellStyle();
            newCell.setCellStyle(newCellStyle);

            // If there is a cell comment, copy
            if (oldCell.getCellComment() != null) {
                newCell.setCellComment(oldCell.getCellComment());
            }

            // If there is a cell hyperlink, copy
            if (oldCell.getHyperlink() != null) {
                newCell.setHyperlink(oldCell.getHyperlink());
            }

            // Set the cell data type
            newCell.setCellType(oldCell.getCellType());

            // Set the cell data value
            switch (oldCell.getCellType()) {
                case BLANK:
                    newCell.setCellValue(oldCell.getStringCellValue());
                    break;
                case BOOLEAN:
                    newCell.setCellValue(oldCell.getBooleanCellValue());
                    break;
                case ERROR:
                    newCell.setCellErrorValue(oldCell.getErrorCellValue());
                    break;
                case FORMULA:
                    newCell.setCellFormula(oldCell.getCellFormula());
                    break;
                case NUMERIC:
                    newCell.setCellValue(oldCell.getNumericCellValue());
                    break;
                case STRING:
                    newCell.setCellValue(oldCell.getRichStringCellValue());
                    break;
            }
        }

        // If there are are any merged regions in the source row, copy to new row
        for (int i = 0; i < worksheet.getNumMergedRegions(); i++) {
            CellRangeAddress cellRangeAddress = worksheet.getMergedRegion(i);
            if (cellRangeAddress.getFirstRow() == sourceRow.getRowNum()) {
                CellRangeAddress newCellRangeAddress = new CellRangeAddress(newRow.getRowNum(),
                        (newRow.getRowNum() +
                                (cellRangeAddress.getLastRow() - cellRangeAddress.getFirstRow()
                                        )),
                        cellRangeAddress.getFirstColumn(),
                        cellRangeAddress.getLastColumn());
                worksheet.addMergedRegion(newCellRangeAddress);
            }
        }
    }
}
