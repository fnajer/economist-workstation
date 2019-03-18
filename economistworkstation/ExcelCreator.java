/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package economistworkstation;

import economistworkstation.Entity.Month;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
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
    public static void printAccountPayment(Month month) throws IOException {
       
        File file = new File("C:\\Users\\fnajer\\Desktop\\workbook.xls");

        HSSFWorkbook workbook;
        try (FileInputStream inputStream = new FileInputStream(file)) {
            workbook = new HSSFWorkbook(inputStream);
        }

        try (OutputStream out = new FileOutputStream("C:\\Users\\fnajer\\Desktop\\workbookNew.xls")) {
            workbook.write(out);
        }
        System.out.println("Обновлен документ 'Счет для оплаты'");
        
        
//        Workbook wb = new HSSFWorkbook ();
    
        //Sheet sheet = wb.createSheet("Счёт");
        
//        // Создать строку и поместить в нее несколько ячеек. Строки основаны на 0.
//        Row row = sheet.createRow(0);
//        // Создаем ячейку и помещаем в нее значение.
//        Cell cell = row.createCell(0);
//        cell.setCellValue(1);
//
//        // Или сделать это в одной строке.
//        row.createCell(2).setCellValue(1.2);
//        row.createCell(3).setCellValue(true);
        
//        try (OutputStream fileOut = new FileOutputStream ("C:\\Users\\fnajer\\Desktop\\workbook.xls")) {
//            wb.write (fileOut);
//            System.out.println("Создан документ 'Счет для оплаты'");
//        }
//        wb.close ();
    }
}
