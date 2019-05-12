/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package economistworkstation.Entity;

import economistworkstation.ContractDataParameters;
import economistworkstation.Model.PeriodModel;
import java.time.LocalDate;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import static org.apache.poi.ss.usermodel.CellType.STRING;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;


/**
 *
 * @author fnajer
 */
public abstract class StatementDocument extends Document {
    protected int indexStartRow;
    protected int sizeList;
    
    StatementDocument(ContractDataParameters data, String srcPath, String destPath) {
        super(data, srcPath, destPath);
        
        prepareDocument(data);
    }
    
    private void prepareDocument(ContractDataParameters data) {
        sizeList = data.getDataList().size();
        addTemplateRows(sizeList);
    }
    
    protected int addTemplateRows(int contractsSize) {
        if (indexStartRow != -1)
            for (int i = indexStartRow + 1; i < contractsSize + indexStartRow; i++) {
                copyRow(indexStartRow, indexStartRow + 1);
            }
        return indexStartRow;
    }
    
    protected int findRowByTag(String searchString) {
        boolean isFounded;
        for (Sheet currSheet : workbook) {
            this.sheet = currSheet;
            for (Row row : sheet) {
                for (Cell cell : row) {
                    CellType cellType = cell.getCellType();
                    if (cellType == STRING) {
                        isFounded = Parser.findTag(cell, searchString);
                        if (isFounded)
                            return cell.getRowIndex();
                    }
                }
            }
        }
        return -1;
    }
    
    protected void copyRow(int sourceRowNum, int destinationRowNum) {
        // Get the source / new row
        Row newRow = sheet.getRow(destinationRowNum);
        Row sourceRow = sheet.getRow(sourceRowNum);

        // If the row exist in destination, push down all rows by 1 else create a new row
        if (newRow != null) {
            sheet.shiftRows(destinationRowNum, sheet.getLastRowNum(), 1, true, false);
        } else {
            newRow = sheet.createRow(destinationRowNum);
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
        for (int i = 0; i < sheet.getNumMergedRegions(); i++) {
            CellRangeAddress cellRangeAddress = sheet.getMergedRegion(i);
            if (cellRangeAddress.getFirstRow() == sourceRow.getRowNum()) {
                CellRangeAddress newCellRangeAddress = new CellRangeAddress(newRow.getRowNum(),
                        (newRow.getRowNum() +
                                (cellRangeAddress.getLastRow() - cellRangeAddress.getFirstRow()
                                        )),
                        cellRangeAddress.getFirstColumn(),
                        cellRangeAddress.getLastColumn());
                sheet.addMergedRegion(newCellRangeAddress);
            }
        }
    }
}
