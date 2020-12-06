/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testdb;

import java.sql.*;

/**
 *
 * @author Robert
 */
public class Db{
    public static Connection connect() throws SQLException {
        String url = "jdbc:mysql://localhost:3336/aa";
        String username = "java"; 
        String password = "password";
        
        return DriverManager.getConnection(url, username, password);
    }
}
