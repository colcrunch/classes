/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testdb;

import java.sql.*;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.Month;

/**
 *
 * @author Robert
 */
public class TestDB {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        System.out.println("Loaded Driver");
        
        Patient test = Patient.get("Test", "McTesterson");
        
        LocalDate t = LocalDate.of(2020, 12, 5);
        
        Admission a = Admission.get(test, t);
        System.out.println(a.toString());
        
        System.out.println(test.toString());
        
        System.out.println("\n\n PATIENT APPOINTMENTS \n");
        
        ArrayList<Appointment> appts = test.getAppointments();
        
        for(Appointment a : appts) {
            System.out.println(a.toString() + "\n");
            
            if (a.getId() == 1) {
                a.setChecked(true);
                a.setCheck_time("2020-12-23 00:00:00");
                a.update();
                
                System.out.println("APPOINTMRNT UPDATED \n" + a.toString());
            }   
        }
       
        System.out.println("\n\n PATIENT ADMISSIONS \n");
        
        ArrayList<Admission> adm = test.getAdmissions();
        
        for (Admission a : adm) {
            System.out.println(a.toString() + "\n");
            
            
            Appointment aa = a.getAppointment();
            System.out.println("ADMISSION APPT \n" + aa.toString() + "\n");
        }
        
        System.out.println("\n\n PATIENT MEDICATIONS \n");
        
        ArrayList<Medication> meds = test.getMedication();
        
        for (Medication m : meds) {
            System.out.println(m.toString() + "\n");
        }
    }
    
}
