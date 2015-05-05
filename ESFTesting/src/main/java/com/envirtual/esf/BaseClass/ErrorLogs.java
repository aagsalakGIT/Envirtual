package com.envirtual.esf.BaseClass;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ErrorLogs {

    private String reportPage;
    private String errorMessage;

    public ErrorLogs() {

    }

    public void LogError(String errorMessage, String reportPage){

        reportPage = reportPage;
        errorMessage = errorMessage;

        // get Date
        String dateTime = getDateTime();

        // The name of the file to open.
        String fileName = "log.txt";

        try {
            // Assume default encoding.
            FileWriter fileWriter = new FileWriter(fileName, true);

            // Always wrap FileWriter in BufferedWriter.
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            // Note that write() does not automatically
            // append a newline character.
            bufferedWriter.newLine();
            bufferedWriter.write(dateTime + " ");
            bufferedWriter.write(reportPage.toUpperCase());
            bufferedWriter.write(" : -");
            bufferedWriter.write(errorMessage);
            bufferedWriter.write(" here is some text.");
            bufferedWriter.newLine();
            bufferedWriter.write("We are writing");
            bufferedWriter.write(" the text to the file.");

            // Always close files.
            bufferedWriter.close();
        }
        catch(IOException ex) {
            System.out.println(
                    "Error writing to file '"
                            + fileName + "'");
            // Or we could just do this:
            // ex.printStackTrace();
        }
    }

    public String getDateTime(){
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            return dateFormat.format(date);
    }
}