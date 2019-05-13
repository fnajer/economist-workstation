/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package economistworkstation.Util;

import economistworkstation.ContractDataParameters;
import java.nio.file.Paths;

/**
 *
 * @author fnajer
 */
public abstract class Statement {
    protected String path;
            
    public Statement() {
        
    }
    
    protected String getPatternsPath(String fileName) {
        return Paths.get("src/config/patterns").resolve(fileName).toString();
    }
    
    public abstract void print(ContractDataParameters data);
}
