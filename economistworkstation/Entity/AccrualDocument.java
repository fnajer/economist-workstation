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
public class AccrualDocument extends StatementDocument {
    
    public AccrualDocument(ContractDataParameters data, String srcPath, String destPath) {
        super(data, srcPath, destPath);
        
        setLogName("'Ведомость начислений'");
    }
    
    @Override
    public Parser createTagParser(ContractDataParameters data) {
        indexStartRow = findRowByTag("<num>");
        return new AccrualTagParser(data.getDataList(), indexStartRow);
    }
}
