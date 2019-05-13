/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package economistworkstation.Util;

import economistworkstation.ContractDataParameters;
import economistworkstation.Entity.Document;
import economistworkstation.Entity.InvoiceDocument;

/**
 *
 * @author fnajer
 */
public class RentalPattern extends Pattern {
    public RentalPattern() {
        super();
        
        setPatternPath("rentalAccount.xls");
    }
    
    @Override
    public void print(ContractDataParameters data) {
        Document doc = new InvoiceDocument(
                data,
                getPatternPath(),
                getPathToSave());
        doc.print();
    }
    
    @Override
    public String toString() {
        return String.format("Счет аренды");
    }
}
