/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testdb;

import java.sql.*;
import java.util.ArrayList;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author dante
 */
class Appointment extends DbObject {
    protected int patient_id;
    
    protected String doctor,
            note;
    
    protected LocalDateTime date_time,
            check_time;
    
    protected boolean checked;
    
    
    public Appointment(){
        
    }
    
    public Appointment(int patientId){
        this.patient_id = patientId;
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

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public LocalDateTime getDate_time() {
        return date_time;
    }

    public void setDate_time(String date_time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.date_time = LocalDateTime.parse(date_time, formatter);
    }
    
    public void setDate_time(LocalDateTime date_time) {
        this.date_time = date_time;
    }

    public LocalDateTime getCheck_time() {
        return check_time;
    }

    public void setCheck_time(String check_time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.check_time = LocalDateTime.parse(check_time, formatter);
    }
    
    public void setCheck_time(LocalDateTime check_time) {
        this.check_time = check_time;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
    
    @Override
    public String toString(){
        
        String ret = "";
        
        if (checked == false || check_time == null) {
            ret = "APPOINTMENT ID: " + this.id + "\n"
                    + "Patient ID: " + this.patient_id + "\n"
                    + "CHECKED IN: " + this.checked + "\n"
                    + "DATE_TIME: " + this.date_time.toString() + "\n"
                    + "NOTE: " + this.note;
        }
        else {
            ret = "APPOINTMENT ID: " + this.id + "\n"
                    + "Patient ID: " + this.patient_id + "\n"
                    + "CHECKED IN: " + this.checked + "\n"
                    + "CHECKIN TIME: " + this.check_time.toString() + "\n"
                    + "DATE_TIME: " + this.date_time.toString() + "\n"
                    + "NOTE: " + this.note + "\n";
        }
        
        return ret;
    }
    
    public static ArrayList<Appointment> get(Patient patient) {
    	try {
            Connection con = Db.connect();
            
            Statement stmt = con.createStatement();
            
            ResultSet res = stmt.executeQuery("SELECT * FROM appointment WHERE patient_id = " + patient.getId());
            
            ArrayList<Appointment> ret = new ArrayList<>();
            
            while(res.next()){
                Appointment a = new Appointment();
                a.setId(res.getInt("id"));
                a.setPatient_id(res.getInt("patient_id"));
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
        catch (SQLException e) {
            System.out.println(e);
            return new ArrayList<>();
        }
    }
    
    public static Appointment get(Admission admission) {
    	try {
            Connection con = Db.connect();

            String stmt = "SELECT * FROM appointment WHERE id = ?";
            PreparedStatement q = con.prepareStatement(stmt);

            q.setInt(1, admission.appointment_id);
            ResultSet res = q.executeQuery();

            Appointment ret = new Appointment();
            // This should only return 1 result.
            if(res.next()) {
                ret.setId(res.getInt("id"));
                ret.setPatient_id(res.getInt("patient_id"));
                ret.setDoctor(res.getString("doctor"));
                ret.setDate_time(res.getString("date_time"));
                ret.setChecked(res.getBoolean("checked"));
                System.out.println(ret.isChecked());
                
                String ct = res.getString("check_time");
                if (ct != null) {
                    ret.setCheck_time(ct);
                }
                String note = res.getString("note");
                if (note == null) {
                    ret.setNote("");
                }
                else {
                    ret.setNote(res.getString("note"));
                }
            } 
            
            res.close();
            q.close();
            con.close(); 
            
            return ret;
        }
        catch (SQLException e) {
            System.out.println(e);
            return new Appointment();
        }
    }
    
    public static Appointment get(Patient patient, LocalDateTime date) {
    	try {
            Connection con = Db.connect();

            String stmt = "SELECT * FROM appointment WHERE patient_id = ? AND date_time LIKE ?";
            PreparedStatement q = con.prepareStatement(stmt);

            q.setInt(1, patient.getId());
            q.setString(2, date.toString());

            ResultSet res = q.executeQuery();

            Appointment ret = new Appointment();
            // This should only return 1 result.
            if(res.next()) {
                ret.setId(res.getInt("id"));
                ret.setPatient_id(res.getInt("patient_id"));
                ret.setDoctor(res.getString("doctor"));
                ret.setDate_time(res.getString("date_time"));
                ret.setChecked(res.getBoolean("checked"));
                System.out.println(ret.isChecked());
                
                String ct = res.getString("check_time");
                if (ct != null) {
                    ret.setCheck_time(ct);
                }
                String note = res.getString("note");
                if (note == null) {
                    ret.setNote("");
                }
                else {
                    ret.setNote(res.getString("note"));
                }
                
            }

            res.close();
            q.close();
            con.close();    
            
            return ret;
        }
        catch (SQLException e) {
            System.out.println(e);
            return new Appointment();
        }
    }
    
}
