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
    Properties props = null;
    Connection conn = null;
    Statement stmt = null;
    
    public void connect () {
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

//            stmt.executeUpdate("INSERT INTO RENTER VALUES(NULL, '" + name + "')");
//            System.out.println("Добавлено: " + name);

        } catch (IOException | ClassNotFoundException | SQLException ex) {
            Logger.getLogger(EconomistWorkstation.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
}
