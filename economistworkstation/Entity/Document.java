/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package economistworkstation.Entity;

import economistworkstation.ContractData;
import economistworkstation.Model.BuildingModel;
import economistworkstation.Model.PeriodModel;
import economistworkstation.Model.RenterModel;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
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
public abstract class Document {
    private final File srcFile;
    private final File destFile;
    
    private OutputStream out;
    private InputStream input;
            
    protected Workbook workbook;
    protected Sheet sheet;
    protected Parser parser;
    private String logName;
    
    Document(String srcPath, String destPath) {
        srcFile = new File(srcPath);
        destFile = new File(destPath);
        
        prepareStream();
    }
    
    private void prepareStream() {
        try {
            out = new FileOutputStream(destFile);
            input = new FileInputStream(srcFile);
            
            workbook = new HSSFWorkbook(input);
        } catch (IOException ex) {
            Logger.getLogger(Document.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void write() {
        try {
            workbook.write(out);
            out.close();
            System.out.println(String.format("Обновлен документ %s", getLogName()));
        } catch (IOException ex) {
            Logger.getLogger(Document.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void print(Contract contract, Period period) {
        ContractDataParameters data = constructDataObject(contract, period);
        this.parser = createTagParser(data);
        
        iterateCells();

        write();
    }
    public void print() {
        ContractDataParameters data = constructDataObject();
        this.parser = createTagParser(data);
        
        iterateCells();

        write();
    }
    
    private ContractDataParameters constructDataObject(Contract contract, Period period) {
        Renter renter = RenterModel.getRenter(contract.getIdRenter());
        Building building = BuildingModel.getBuilding(contract.getIdBuilding());
        ContractDataParameters data = new ContractDataParameters();
        data.setDataSingle(new ContractData(null, period, 
                            building, renter, contract, workbook));
        return data;
    }
    private ContractDataParameters constructDataObject() {
        LocalDate date = LocalDate.parse("2019-09-01");
        ContractDataParameters data = new ContractDataParameters();
        data.setDataList(PeriodModel.getContractData(date));
        return data;
    }
    
    private void iterateCells() {
        for (Sheet currSheet : workbook) {
            this.sheet = currSheet;
            for (Row row : sheet) {
                iterateCells(row);
            }
            clearRows();
            removeRows();
        }
    }
    
    private void iterateCells(Row row) {
        for (Cell cell : row) {
            CellType cellType = cell.getCellType();
            if (cellType == STRING) {
                parser.convertTags(cell);
            }
        }
    }
    
    private void clearRows() {
        ArrayList<Integer> rowsForClear = parser.getRowsForClear();
        for (int numRow : rowsForClear) {
            Row row = sheet.getRow(numRow);
            for (Cell cell : row) {
                cell.setCellValue("");
            }
        }
    }
    
    private void removeRows() {
        ArrayList<Integer> rowsForDelete = parser.getRowsForDelete();
        int deletion = 0;
        for (int row : rowsForDelete) {
            removeRow(row - deletion);
            deletion++;
        }
    }
    
    private void removeRow(int rowIndex) {
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
    
    protected String getLogName() {
        return logName;
    }
    protected void setLogName(String logName) {
        this.logName = logName;
    }
    
    public abstract Parser createTagParser(ContractDataParameters data);
    
    protected class ContractDataParameters {
        private ContractData dataSingle;
        private ObservableList<ContractData> dataList;
        
        public ContractData getDataSingle() {
            return dataSingle;
        }
        public void setDataSingle(ContractData dataSingle) {
            this.dataSingle = dataSingle;
        }
        
        public ObservableList<ContractData> getDataList() {
            return dataList;
        }
        public void setDataList(ObservableList<ContractData> dataList) {
            this.dataList = dataList;
        }
    }
}
