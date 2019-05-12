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
public class MemorialDocument extends StatementDocument {
    
    public MemorialDocument(ContractDataParameters data, String srcPath, String destPath) {
        super(data, srcPath, destPath);
        
        setLogName("'Меморальный ордер'");
    }
    
    @Override
    public Parser createTagParser(ContractDataParameters data) {
        indexStartRow = findRowByTag("<num>");
        return new MemorialTagParser(data.getDataList(), indexStartRow);
    }
}
