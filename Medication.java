/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testdb;

import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author dante
 */
public class Medication extends DbObject{
    protected int patient_id;
    protected String name;
    protected double dosage;
    
    public Medication(){
        
    }
    
    public Medication(int ID){
        this.id = ID;
    }
    
    public Medication(int id, int patient_id, String name, double dosage) {
    	super(id);
    	this.patient_id = patient_id;
    	this.dosage = dosage;
    	this.name = name;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(int patient_id) {
        this.patient_id = patient_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getDosage() {
        return dosage;
    }

    public void setDosage(double dosage) {
        this.dosage = dosage;
    }
    
    
    public String toString(){
        return
                "ID: " + id
                + "\nPrescription: " + name
                + "\nDosage: " + dosage
                + "\nPatient ID: " + patient_id;
    }
    
    public static ArrayList<Medication> get(Patient patient) {
    	try {
            Connection con = Db.connect();
            
            Statement stmt = con.createStatement();
            
            ResultSet res = stmt.executeQuery("SELECT * FROM medication WHERE patient_id = " + patient.getId());
            
            ArrayList<Medication> ret = new ArrayList<>();
            
            while(res.next()){
                Medication m = new Medication();
                m.setId(res.getInt("id"));
                m.setName(res.getString("name"));
                m.setPatient_id(res.getInt("patient_id"));
                m.setDosage(res.getDouble("dosage"));
                
                
                ret.add(m);
            }
            
            return ret;   
        }
        catch (SQLException e) {
            System.out.println(e);
            return new ArrayList<>();
        }
    }
}
