/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package economistworkstation.Entity;

import economistworkstation.ContractData;
import economistworkstation.ContractDataParameters;


/**
 *
 * @author fnajer
 */
public class InvoiceDocument extends Document {
    //paths takeway into parameters
    public InvoiceDocument(ContractDataParameters data, String srcPath, String destPath) {
        super(data, srcPath, destPath);
        
        setLogName("'Счет для оплаты'");
    }
    
    @Override
    public Parser createTagParser(ContractDataParameters data) {
        return new InvoiceTagParser(data.getDataSingle());
    }
}
