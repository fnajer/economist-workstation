/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package economistworkstation.Entity;

import economistworkstation.ContractDataParameters;


/**
 *
 * @author fnajer
 */
public class AccumulationDocument extends StatementDocument {
    
    public AccumulationDocument(ContractDataParameters data, String srcPath, String destPath) {
        super(data, srcPath, destPath);
        
        setLogName("'Накопительная ведомость'");
    }
    
    @Override
    public Parser createTagParser(ContractDataParameters data) {
        indexStartRow = findRowByTag("<num>");
//        prepareDocument(data);
        return new AccumulationTagParser(data.getDataList(), indexStartRow);
    }
    
    @Override
    protected int addTemplateRows(int contractsSize) {
        if (indexStartRow != -1)
            for (int i = indexStartRow + 1; i < contractsSize + indexStartRow; i++) {
                copyRow(indexStartRow, indexStartRow+5);
                copyRow(indexStartRow+1, indexStartRow+6);
                copyRow(indexStartRow+2, indexStartRow+7);
                copyRow(indexStartRow+3, indexStartRow+8);
                copyRow(indexStartRow+4, indexStartRow+9);
//                int n = 4; // 4 - iterate count. count rows, that will be copy
//                for (int ii = 0, j = n + indexStartRow; ii < n; ii++, j++) {
//                    copyRow(workbook, sheet, indexStartRow + ii, j);
//                }
            }
        return indexStartRow;
    }
}
