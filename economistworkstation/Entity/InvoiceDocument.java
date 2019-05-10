/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package economistworkstation.Entity;

import economistworkstation.ContractData;


/**
 *
 * @author fnajer
 */
public class InvoiceDocument extends Document {
    
    public InvoiceDocument(String srcPath, String destPath) {
        super(srcPath, destPath);
        
        setLogName("'Счет для оплаты'");
    }
    
    @Override
    public Parser createTagParser(ContractData data) {
        return new InvoiceTagParser(data);
    }
}