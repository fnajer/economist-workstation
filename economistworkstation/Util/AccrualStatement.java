/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package economistworkstation.Util;

import economistworkstation.ContractDataParameters;
import economistworkstation.Entity.AccrualDocument;
import economistworkstation.Entity.Document;

/**
 *
 * @author fnajer
 */
public class AccrualStatement extends Statement {
    public AccrualStatement() {
        
    }
    
    @Override
    public void print(ContractDataParameters data) {
        Document doc = new AccrualDocument(
                data,
                "C:\\Users\\fnajer\\Desktop\\workbookAcr.xls",
                data.getSavePath());
        doc.print();
    }
    
    @Override
    public String toString() {
        return String.format("Ведомость начисления");
    }
}
