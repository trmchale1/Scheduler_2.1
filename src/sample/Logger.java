package sample;

import java.io.IOException;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.ZonedDateTime;

/**
 * Opens file login_activity.txt and writes to the file the status and timestamp
 */
public class Logger {
        private static final String FILENAME = "login_activity.txt";

        public static void logger (String username, boolean success) {
            try (FileWriter fw = new FileWriter(FILENAME, true);
                 BufferedWriter bw = new BufferedWriter(fw);
                 PrintWriter pw = new PrintWriter(bw)) {
                pw.println(ZonedDateTime.now() + " " + username + (success ? " Success" : " Failure"));
            } catch (IOException e) {
                System.out.println("Log Error: " + e.getMessage());
            }
        }
    }


