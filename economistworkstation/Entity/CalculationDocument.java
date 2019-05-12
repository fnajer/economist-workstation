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
public class CalculationDocument extends Document {
    
    public CalculationDocument(ContractDataParameters data, String srcPath, String destPath) {
        super(data, srcPath, destPath);
        
        setLogName("'Расчет для оплаты'");
    }
    
    @Override
    public Parser createTagParser(ContractDataParameters data) {
        return new CalculationTagParser(data.getDataSingle());
    }
}
