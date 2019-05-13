/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package economistworkstation.Util;

import economistworkstation.ContractDataParameters;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author fnajer
 */
public abstract class Pattern {
    private String pathToSave;
    private String patternPath;
    private final String patternDirPath = "src/config/patterns";        
    public Pattern() {
        
    }
    
    protected String constructPatternPath(String fileName) {
        return Paths.get(patternDirPath).resolve(fileName).toString();
    }
    
    public static ObservableList<Pattern> getStatements() {
        ObservableList<Pattern> items = FXCollections.observableArrayList();
        items.addAll(
                new AccrualPattern(), 
                new MemorialPattern(), 
                new AccumulationPattern());
        return items;
    }
    
    public static ObservableList<Pattern> getAccounts() {
        ObservableList<Pattern> items = FXCollections.observableArrayList();
        items.addAll(
                new RentalPattern(),
                new ServicePattern(),
                new CalculationPattern());
        return items;
    }
    
    public static ObservableList<Pattern> getPatterns() {
        ObservableList<Pattern> items = FXCollections.observableArrayList();
        items.addAll(getAccounts());
        items.addAll(getStatements());
        return items;
    }

    
    public String getPathToSave() {
        return pathToSave;
    }
    public void setPathToSave(String pathToSave) {
        this.pathToSave = pathToSave;
    }
    
    public String getPatternPath() {
        return patternPath;
    }
    public void setPatternPath(String patternPath) {
        this.patternPath = constructPatternPath(patternPath);
    }
    
    public abstract void print(ContractDataParameters data);
}
