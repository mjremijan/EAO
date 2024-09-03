/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.frb.h1mjr01.calendar.main;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.Description;

/**
 *
 * @author H1MJR01
 */
public class CalendarMain {
    public static final void main(String [] args) throws Exception {
        System.out.printf("Starting CalendarMain%n");
        
        Path path 
            = Paths.get("\\\\rb.win.frb.org\\H1\\Accounts\\P-R\\H1MJR01\\Redirected\\Desktop\\Remijan Michael J Calendar.ics");
        
        // Search and replace ##Empty.Group##
        {
            String content = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
            content = content.replaceAll("##Empty.Group##", "frbgroup");
            for (int i=1; i<=10; i++) {
                try {
                    System.out.printf("Try writing (%d)%n",i);
                    Files.write(path, content.getBytes(StandardCharsets.UTF_8));
                    i=11;
                } catch (IOException e) {
                    if (i==10) { 
                        throw e;
                    } else {
                        Thread.sleep(10);
                    }
                }
            }
        }
                
        // Parse   
        System.out.printf("%nStarting parsing%n");
        FileInputStream fin = new FileInputStream(path.toFile());
        CalendarBuilder builder = new CalendarBuilder();
        Calendar calendar = builder.build(fin);
        System.out.printf("Ending parsing%n");
        
        
        // Mike meeting
        System.out.printf("%nFinding \"Mike meeting\"%n");
        VEvent mikeMeeting = findMikeMeeting(calendar);
        System.out.printf(
            "Mike Meeting%n -summary=\"%s\"%n -description=\"%s\"  %n"
            , mikeMeeting.getSummary().get().getValue()
            , mikeMeeting.getDescription().orElse(new Description("")).getValue()
        );
        System.out.printf("Ending.%n");
        
        
        System.out.printf("%nExiting CalendarMain%n");
    }

    private static VEvent findMikeMeeting(Calendar calendar) {
        List<VEvent> found 
            = calendar.getComponents("VEVENT").stream()
                .map (e -> (VEvent)e)
                .filter(e -> e.getSummary().isEmpty() == false)
                .filter(f -> f.getSummary().get().getValue().equals("Mike meeting"))
                .collect(Collectors.toList())
        ;
        if (found.isEmpty()) {
            throw new RuntimeException("\"Mike meeting\" not found");
        }
        else
        if (found.size() != 1) {
            throw new RuntimeException("More than 1 \"Mike meeting\" found");
        } 
        else {
            return found.getFirst();
        }
    }
}
