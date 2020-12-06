/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testdb;

import java.sql.PreparedStatement;
import java.sql.*;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author dante
 */
public class Patient extends DbObject {
    
    protected int age = 0;
            
    
    protected double
            height = 0.0,
            weight = 0.0,
            temp = 0.0;
           
    protected String first_name = "",
            last_name = "",
            gender = "",
            email = "",
            phone_number = "",
            bp = "";
    
    
    public Patient(){
        
    }
    
    public Patient(int id, int age, String firstName, String lastName, String gender, String email){
        this.id = id;
        this.age = age;
        this.first_name = firstName;
        this.last_name = lastName;
        this.gender = gender;
        this.email = email;
    }
    
    
    public void setID(int id){
        this.id = id;
    }
    
    public int getID(){
        return id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getBp() {
        return bp;
    }

    public void setBp(String bp) {
        this.bp = bp;
    }
    
    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }
    
    
    public String toString(){
        return "Patient Data\n"+
               "ID:" + id + 
               "\nFirst Name: " + first_name + 
               "\nLast Name: " + last_name + 
               "\nAge: " + age + 
               "\nGender: " + gender + 
               "\nEmail: " + email;
    }
    
    public static Patient get(String firstName, String lastName) {
        try {
            Connection con = Db.connect();

            String stmt = "SELECT * FROM patient WHERE first_name = ? AND last_name = ?";
            PreparedStatement q = con.prepareStatement(stmt);

            q.setString(1, firstName);
            q.setString(2, lastName);

            ResultSet r = q.executeQuery();

            Patient ret = new Patient();
            // This should only return 1 result.
            if(r.next()) {
                    ret.setID(r.getInt(1));
                    ret.setFirst_name(r.getString(2));
                    ret.setLast_name(r.getString(3));
                    ret.setGender(r.getString(4));
                    ret.setAge(r.getInt(5));
                    ret.setEmail(r.getString(6));
                    ret.setPhone_number(r.getString(7));
                    ret.setHeight(r.getDouble(8));
                    ret.setWeight(r.getDouble(9));
                    ret.setTemp(r.getDouble(10));
                    ret.setBp(r.getString(11));
            }

            r.close();
            q.close();
            con.close();    
            
            return ret;
        }
        catch (SQLException e) {
            System.out.println(e);
            return new Patient();
        }
    }
    
    public static Patient get(int patient_id) {
        try {
            Connection con = Db.connect();

            String stmt = "SELECT * FROM patient WHERE first_name = ? AND last_name = ?";
            PreparedStatement q = con.prepareStatement(stmt);

            q.setInt(1, patient_id);

            ResultSet r = q.executeQuery();

            Patient ret = new Patient();
            // This should only return 1 result.
            if(r.next()) {
                    ret.setID(r.getInt(1));
                    ret.setFirst_name(r.getString(2));
                    ret.setLast_name(r.getString(3));
                    ret.setGender(r.getString(4));
                    ret.setAge(r.getInt(5));
                    ret.setEmail(r.getString(6));
                    ret.setPhone_number(r.getString(7));
                    ret.setHeight(r.getDouble(8));
                    ret.setWeight(r.getDouble(9));
                    ret.setTemp(r.getDouble(10));
                    ret.setBp(r.getString(11));
            }

            r.close();
            q.close();
            con.close();    
            
            return ret;
        }
        catch (SQLException e) {
            System.out.println(e);
            return new Patient();
        }
    }
    
    public ArrayList<Appointment> getAppointments() {
    	try {
            Connection con = Db.connect();
            
            Statement stmt = con.createStatement();
            
            ResultSet res = stmt.executeQuery("SELECT * FROM appointment WHERE patient_id = " + this.id);
            
            ArrayList<Appointment> ret = new ArrayList<>();
            
            while(res.next()){
                Appointment a = new Appointment();
                a.setId(res.getInt("id"));
                a.setPatient_id(this.id);
                a.setDoctor(res.getString("doctor"));
                a.setDate_time(res.getString("date_time"));
                a.setChecked(res.getBoolean("checked"));
                
                String ct = res.getString("check_time");
                if (ct != null) {
                    a.setCheck_time(ct);
                }
                String note = res.getString("note");
                if (note == null) {
                    a.setNote("");
                }
                else {
                    a.setNote(res.getString("note"));
                }
                
                ret.add(a);
            }
            
            return ret;   
        }
        catch (Exception e) {
            System.out.println(e);
            return new ArrayList<>();
        }
    }
    
    public ArrayList<Admission> getAdmissions() {
    	try {
            Connection con = Db.connect();
            
            Statement stmt = con.createStatement();
            
            ResultSet res = stmt.executeQuery("SELECT * FROM admission WHERE patient_id = " + this.id);
            
            ArrayList<Admission> ret = new ArrayList<>();
            
            while(res.next()){
                Admission a = new Admission();
                a.setId(res.getInt("id"));
                a.setPatient_id(this.id);
                a.setAdm_date(res.getString("adm_date"));
                a.setAppointment_id(res.getInt("appointment_id"));
                a.setBed(res.getInt("bed"));
                a.setRoom(res.getInt("room"));
                a.setDoctor(res.getString("Doctor"));
                a.setDischarged(res.getBoolean("discharged"));
                String dsc_d = res.getString("dsc_date");
                if (dsc_d != null) {
                    a.setDsc_date(dsc_d);
                }
                String dsc_n = res.getString("dsc_notes");
                if (dsc_n == null) {
                    a.setDsc_notes("");
                }
                else {
                    a.setDsc_notes(dsc_n);
                }
                
                
                ret.add(a);
            }
            
            return ret;   
        }
        catch (SQLException e) {
            System.out.println(e);
            return new ArrayList<>();
        }
    }
    
    public ArrayList<Medication> getMedication() {
    	try {
            Connection con = Db.connect();
            
            Statement stmt = con.createStatement();
            
            ResultSet res = stmt.executeQuery("SELECT * FROM medication WHERE patient_id = " + this.id);
            
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
