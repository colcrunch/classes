/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testdb;

import java.sql.*;
import java.util.ArrayList;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author dante
 */
public class Admission extends DbObject {
    protected int
            patient_id = -1,
            appointment_id = -1,
            room = -1,
            bed = -1;
    protected String doctor = "",
            dsc_notes = "";
    protected LocalDateTime adm_date = LocalDateTime.of(1970, 01, 01, 0, 0, 0),
            dsc_date = LocalDateTime.of(1970, 01, 01, 0, 0, 0);
    protected boolean discharged = false;
    
    public Admission(){
        
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

    public int getAppointment_id() {
        return appointment_id;
    }

    public void setAppointment_id(int appointment_id) {
        this.appointment_id = appointment_id;
    }

    public int getRoom() {
        return room;
    }

    public void setRoom(int room) {
        this.room = room;
    }

    public int getBed() {
        return bed;
    }

    public void setBed(int bed) {
        this.bed = bed;
    }

    public LocalDateTime getAdm_date() {
        return adm_date;
    }

    public void setAdm_date(String adm_date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.adm_date = LocalDateTime.parse(adm_date, formatter);
    }
    
    public void setAdm_date(LocalDateTime adm_date) {
        this.adm_date = adm_date;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public String getDsc_notes() {
        return dsc_notes;
    }

    public void setDsc_notes(String dsc_notes) {
        this.dsc_notes = dsc_notes;
    }

    public LocalDateTime getDsc_date() {
        return dsc_date;
    }

    public void setDsc_date(String dsc_date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.dsc_date = LocalDateTime.parse(dsc_date, formatter);
    }
    
    public void setDsc_date(LocalDateTime dsc_date) {
        this.dsc_date = dsc_date;
    }

    public boolean isDischarged() {
        return discharged;
    }

    public void setDischarged(boolean discharged) {
        this.discharged = discharged;
    }
    
    @Override
    public String toString() {
        String ret = "";
        
        if(this.discharged && this.dsc_date != null) {
            ret = "ADMISSION ID: " + this.id + "\n"
                    + "PATIENT ID: " + this.patient_id + "\n"
                    + "APPOINTMENT_ID: " + this.appointment_id + "\n"
                    + "DOCTOR: " + this.doctor + "\n"
                    + "ROOM/BED: " + this.room + "/" + this.bed + "\n"
                    + "ADMIT DATE: " + this.adm_date.toString() + "\n"
                    + "DESC DATE: " + this.dsc_date.toString() + "\n"
                    + "DESC NOTES: " + this.dsc_notes + "\n";
        }
        else {
            ret = "ADMISSION ID: " + this.id + "\n"
                    + "PATIENT ID: " + this.patient_id + "\n"
                    + "APPOINTMENT_ID: " + this.appointment_id + "\n"
                    + "DOCTOR: " + this.doctor + "\n"
                    + "ROOM/BED: " + this.room + "/" + this.bed + "\n"
                    + "ADMIT DATE: " + this.adm_date.toString() + "\n"
                    + "DESC NOTES: " + this.dsc_notes + "\n";
        }
        
        return ret;
    }
    
    public static ArrayList<Admission> get(Patient patient) {
    	try {
            Connection con = Db.connect();
            
            Statement stmt = con.createStatement();
            
            ResultSet res = stmt.executeQuery("SELECT * FROM admission WHERE patient_id = " + patient.getId());
            
            ArrayList<Admission> ret = new ArrayList<>();
            
            while(res.next()){
                Admission a = new Admission();
                a.setId(res.getInt("id"));
                a.setPatient_id(patient.getId());
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
        catch (Exception e) {
            System.out.println(e);
            return new ArrayList<>();
        }
    }
    
    public static Admission get(Patient patient, LocalDate date) {
        try {
            Connection con = Db.connect();

            String stmt = "SELECT * FROM admission WHERE patient_id = ? AND adm_date LIKE ?";
            PreparedStatement q = con.prepareStatement(stmt);

            q.setInt(1, patient.getId());
            q.setString(2, "%" + date.toString() + "%");

            ResultSet res = q.executeQuery();

            Admission ret = new Admission();
            // This should only return 1 result.
            if(res.next()) {
                ret.setId(res.getInt("id"));
                ret.setPatient_id(patient.getId());
                ret.setAdm_date(res.getString("adm_date"));
                ret.setAppointment_id(res.getInt("appointment_id"));
                ret.setBed(res.getInt("bed"));
                ret.setRoom(res.getInt("room"));
                ret.setDoctor(res.getString("Doctor"));
                ret.setDischarged(res.getBoolean("discharged"));
                String dsc_d = res.getString("dsc_date");
                if (dsc_d != null) {
                    ret.setDsc_date(dsc_d);
                }
                String dsc_n = res.getString("dsc_notes");
                if (dsc_n == null) {
                    ret.setDsc_notes("");
                }
                else {
                    ret.setDsc_notes(dsc_n);
                }
            }

            res.close();
            q.close();
            con.close();    
            
            return ret;
        }
        catch (SQLException e) {
            System.out.println(e);
            return new Admission();
        }
    }
    
    public Appointment getAppointment() {
    	try {
            Connection con = Db.connect();

            String stmt = "SELECT * FROM appointment WHERE id = ?";
            PreparedStatement q = con.prepareStatement(stmt);

            q.setInt(1, this.appointment_id);
            ResultSet res = q.executeQuery();

            Appointment ret = new Appointment();
            // This should only return 1 result.
            if(res.next()) {
                ret.setId(res.getInt("id"));
                ret.setPatient_id(this.id);
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
