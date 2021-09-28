package com.techelevator.view;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class VendingMachineLog {

    public static void log(String message) {
        String pattern = "| MM-dd-yyyy | hh:mm:ss a |";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        String date = simpleDateFormat.format(new Date());

        File logFile = new File("logs/vendingMachine.log");

        try (PrintWriter writeToLog = new PrintWriter(new FileOutputStream(logFile, true), true)) {
            writeToLog.print(date + " " +  message + "\n");
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        }

    }
}
