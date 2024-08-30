/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.frb.h1mjr01.calendar.main;

import java.io.FileInputStream;
import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.Description;

/**
 *
 * @author H1MJR01
 */
public class CalendarMain {
    public static final void main(String [] args) throws Exception {
        System.out.printf("Starting CalendarMain%n");
        
        // Search and replace ##Empty.Group##
        {
            // TODO
        }
                
        // Parse   
        System.out.printf("%nStarting parsing%n");
        FileInputStream fin = new FileInputStream(
            "\\\\rb.win.frb.org\\H1\\Accounts\\P-R\\H1MJR01\\Redirected\\Desktop\\Remijan Michael J Calendar.ics"
        );
        CalendarBuilder builder = new CalendarBuilder();
        Calendar calendar = builder.build(fin);
        System.out.printf("Ending parsing%n");
        
        
        // Test getComponents{}
        System.out.printf("%nStrating test \"getComponents()\"%n");
        for (Object o : calendar.getComponents()) {
            Component c = (Component)o;
            System.out.printf(
                "Component name=\"%s\" classname=\"%s\" %n"
                , c.getName(), c.getClass().getName()
            );            
            
        }
        System.out.printf("Ending.%n");
        
        
        // Test getComponents{"VEVENT}
        System.out.printf("%nStrating test \"getComponents(\"VEVENT\")\"%n");
        System.out.printf("-- ORIGINAL --%n");
        for (Object o : calendar.getComponents("VEVENT")) {
            VEvent e = (VEvent)o;
            if (e.getSummary().isEmpty() == false) {
                System.out.printf(
                    "VEvent summary=\"%s\" description=\"%s\"  %n"
                    , e.getSummary()
                    , e.getDescription()
                );  
                e.replace(new Description("Hi Mike!"));
            }
        }
        System.out.printf("-- REPLACED --%n");
        for (Object o : calendar.getComponents("VEVENT")) {
            VEvent e = (VEvent)o;
            if (e.getSummary().isEmpty() == false) {
                System.out.printf(
                    "VEvent summary=\"%s\" description=\"%s\"  %n"
                    , e.getSummary()
                    , e.getDescription()
                );  
            }
        }
        System.out.printf("Ending.%n");
        
        
        System.out.printf("Exiting CalendarMain%n");
    }
}
