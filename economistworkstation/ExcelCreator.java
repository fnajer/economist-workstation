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
import economistworkstation.Model.RenterModel;
import economistworkstation.Util.Money;
import economistworkstation.Util.TagParser;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
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
        for (Sheet sheet : workbook) {
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
    }

    
    public static void printAccountPayment(Contract contract, Month month) throws IOException {
       
        File file = new File("C:\\Users\\fnajer\\Desktop\\workbook.xls");

        HSSFWorkbook workbook;
        try (FileInputStream inputStream = new FileInputStream(file)) {
            workbook = new HSSFWorkbook(inputStream);
            
            Renter renter = RenterModel.getRenter(contract.getIdRenter());
            Building building = BuildingModel.getBuilding(contract.getIdBuilding());
            ContractData data = new ContractData(null, month, building, renter, contract, workbook);
            
            iterateCells(workbook, TagParser::convertTags, data);
//            HSSFSheet sheet = workbook.getSheetAt(0);
//            HSSFCell cell = sheet.getRow(1).getCell(2);
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
            
            iterateCells(workbook, TagParser::convertTags, data);
        }

        try (OutputStream out = new FileOutputStream("C:\\Users\\fnajer\\Desktop\\workbookCalcNew.xls")) {
            workbook.write(out);
        }
        System.out.println("Обновлен документ 'Счет для оплаты'");
    }
}