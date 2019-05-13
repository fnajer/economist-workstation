/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package economistworkstation.Util;

import java.util.prefs.Preferences;

/**
 *
 * @author fnajer
 */
public class Precedency {
    private final Preferences prefs;
    
    public Precedency() {
        prefs = Preferences.userRoot().node("economist-workstation"); 
    }

    public String getSaveDirectory() {
        return prefs.get("saveDirectory", "C:");
    }
    public void setSaveDirectory(String path) {
        prefs.put("saveDirectory", path);
    }
    
    public String getLoadDirectory() {
        return prefs.get("loadDirectory", "C:");
    }
    public void setLoadDirectory(String path) {
        prefs.put("loadDirectory", path);
    }
}
