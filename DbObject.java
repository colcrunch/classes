/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testdb;

import java.sql.*;
import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 *
 * @author Robert
 */
public class DbObject {
    protected int id;
    protected String table = getClass().getSimpleName().toLowerCase();
    protected Field[] fields = getClass().getDeclaredFields();
    
    /**
     * Default Constructor
     */
    public DbObject() {
    }
    
    /**
     * Creates a DbObject with an id.
     * This constructor should only be used for objects 
     * created from database queries.
     * @param id the ID of the object in the database. 
     */
    protected DbObject(int id){
        this.id = id;
    }
    
    /**
     * Saves the object to the database.
     */
    public void save() {
        try {
            // Make the connection to the db.
            Connection con = Db.connect();
            
            ArrayList<String> names = new ArrayList<>();
            ArrayList<String> params = new ArrayList<>();
            for (Field f:fields){
                names.add(f.getName());
                params.add("?");
            }
            
            // Build string to represent prepared statement.
            String stmt = "INSERT INTO " + this.table + " (" + String.join(", ", names) + ") VALUES (" + String.join(", ", params) + ")";
            
            PreparedStatement q = con.prepareStatement(stmt);
            
            // Set the values in the prepared statement
            for (int i = 0; i < fields.length; i++){
                Field f = fields[i];
                
                // Prepared statement parameters are NOT 0-indexed.
                int param_index = i + 1;
                
                // Determine the "set" function name to use for each parameter.
                String type = f.getType().getSimpleName();
                
                switch (type){
                    case "int":
                        q.setInt(param_index, (int) f.get(this));
                        break;
                    case "String":
                        q.setString(param_index, (String) f.get(this));
                        break;
                    case "double":
                        q.setDouble(param_index, (double) f.get(this));
                        break;
                    case "LocalDateTime":
                        q.setString(param_index, f.get(this).toString());
                        break;
                    case "boolean":
                        q.setBoolean(param_index, f.getBoolean(this));
                        break;
                    default:
                        q.setString(param_index, (String) f.get(this));
                }
            }
            
            q.execute();
            
            // Get the ID of the DB entity and update the object.
            Statement obj_id = con.createStatement();
            ResultSet r = obj_id.executeQuery("SELECT LAST_INSERT_ID()");
            if (r.next()) {
                this.id = r.getInt(1);
            }
            
            // Close the connection.
            con.close();
            
        } catch (Exception e){
            System.out.println(e);
        }
        
    }
    
    /**
     * Updates a database entity.
     * @throws SQLException 
     */
    public void update() throws SQLException {
        if (this.id < 0) {
            throw new SQLException("The object does not exist in the database.");
        }
        try{
            // Get Database connection
            Connection con = Db.connect();

            ArrayList<String> params = new ArrayList<>();
            for (Field f:fields) {
                params.add(f.getName() + "=?");
            }

            String stmt = "UPDATE " + this.table + " SET " + String.join(", ", params) + " WHERE id = " + this.id;

            PreparedStatement q = con.prepareStatement(stmt);

            // Set the values in the prepared statement
            for (int i = 0; i < fields.length; i++){
                Field f = fields[i];

                // Prepared statement parameters are NOT 0-indexed.
                int param_index = i + 1;

                // Determine the "set" function name to use for each parameter.
                String type = f.getType().getSimpleName();

                switch (type){
                    case "int":
                        q.setInt(param_index, (int) f.get(this));
                        break;
                    case "String":
                        q.setString(param_index, (String) f.get(this));
                        break;
                    case "double":
                        q.setDouble(param_index, (double) f.get(this));
                        break;
                    case "LocalDateTime":
                        q.setString(param_index, f.get(this).toString());
                        break;
                    case "boolean":
                        q.setBoolean(param_index, f.getBoolean(this));
                        break;
                    default:
                        q.setString(param_index, (String) f.get(this));
                }
            }
            
            q.execute();
            con.close();
        }
        catch (Exception e) {
                System.out.println(e);
                }
        
    }
    
    /**
     * Deletes the object from the database. If the object is not in the database 
     * (ID less than 0) an SQLException will be thrown.
     * @throws SQLException 
     */
    public void delete() throws SQLException {
        if (this.id < 0) {
            throw new SQLException("The object does not exist in the database.");
        } else {
            Connection con = Db.connect();
            
            PreparedStatement q = con.prepareStatement("DELETE FROM " + this.table + " WHERE id = ?");
            q.setInt(1, this.id);
            
            q.execute();
            
            // Set the ID to -1 to indicate that the object no longer exists in the DB.
            this.id = -1;
            
            con.close();
        }
    }
    
    public int getId(){
        return this.id;
    }
}