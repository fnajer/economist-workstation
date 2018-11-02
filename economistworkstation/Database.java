/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package economistworkstation;

import java.sql.*;
import java.nio.file.*;
import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author fnajer
 */
public class Database {
    private static Database db;
    
    Properties props = null;
    public Connection conn = null;
    public Statement stmt = null;
    
    private void connect () {
        try {
            Class.forName("org.h2.Driver");

            props = new Properties();

            try(InputStream in = Files.newInputStream(Paths.get("src/config/database.properties"))){
                props.load(in);
            }
            String url = props.getProperty("url");
            String username = props.getProperty("username");
            String password = props.getProperty("password");

            conn = DriverManager.getConnection(url, username, password);
            stmt = conn.createStatement();

        } catch (IOException | ClassNotFoundException | SQLException ex) {
            Logger.getLogger(EconomistWorkstation.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    public static Database getInstance() {
        if (db == null) {
            db = new Database();
            db.connect();
        }
        return db;
    }
}
