/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package economistworkstation.Util;

import java.util.prefs.Preferences;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author fnajer
 */
public class Precedency {
    private final Preferences savePrefs;
    private final Preferences loadPrefs;
    
    private String path;
    public Precedency() {
        savePrefs = Preferences.userRoot().node("economist-save"); 
        loadPrefs = Preferences.userRoot().node("economist-load");
    }
    
    public ObservableList<Statement> getStatements() {
        ObservableList<Statement> items = FXCollections.observableArrayList();
        items.addAll(
                new AccrualStatement(), 
                new MemorialStatement(), 
                new AccumulationStatement());
        return items;
    }
    
    public String getSaveDirectory() {
        return savePrefs.get("saveDirectory", "C:");
    }
    public void setSaveDirectory(String path) {
        savePrefs.put("saveDirectory", path);
    }
    
    public String getSavePath() {
        return path;
    }
    public void setSavePath(String path) {
        this.path = path;
    }
}
