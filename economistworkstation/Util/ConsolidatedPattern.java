/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package economistworkstation.Util;

import economistworkstation.ContractDataParameters;
import economistworkstation.Entity.ConsolidatedDocument;
import economistworkstation.Entity.Document;

/**
 *
 * @author fnajer
 */
public class ConsolidatedPattern extends Pattern {
    public ConsolidatedPattern() {
        super();
        
        setPatternPath("consolidatedStatement.xls");
    }
    
    @Override
    public void print(ContractDataParameters data) {
        Document doc = new ConsolidatedDocument(
                data,
                getPatternPath(),
                getPathToSave());
        doc.print();
    }
    
    @Override
    public String toString() {
        return String.format("Сводная ведомость");
    }
}
