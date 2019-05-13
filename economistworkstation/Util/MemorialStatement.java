/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package economistworkstation.Util;

import economistworkstation.ContractDataParameters;
import economistworkstation.Entity.Document;
import economistworkstation.Entity.MemorialDocument;

/**
 *
 * @author fnajer
 */
public class MemorialStatement extends Statement {
    public MemorialStatement() {
        super();
        
        path = getPatternsPath("memorialOrder.xls");
    }
    
    @Override
    public void print(ContractDataParameters data) {
        Document doc = new MemorialDocument(
                data,
                path,
                data.getSavePath());
        doc.print();
    }
    
    @Override
    public String toString() {
        return String.format("Мемориальный ордер");
    }
}
