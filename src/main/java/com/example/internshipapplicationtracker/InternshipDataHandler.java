/**
 * PROGRAM PURPOSE: Program to read and write Internship objects to a specified CSV file, with additional utility related methods
 * @author Mika Collins
 * Date: 5/31/25
 */

package com.example.internshipapplicationtracker;

import java.io.*;
import java.util.*;

public class InternshipDataHandler {

    // CSV file to store all internship application data
    private static final String FILE_NAME = "InternshipApplications.csv";

    /**
     * Method to append an internship to the designated CSV file
     * @param internship the internship object that is to be added to the CSV file
     */
    public static void addInternship(Internship internship) {
        File file = new File(FILE_NAME);

        // Check if the file already exists
        boolean fileExists = file.exists();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
             PrintWriter printWriter = new PrintWriter(writer)) {

            // If the CSV file is new, write the header row first
            if (!fileExists) {
                writer.write("Company Name, Role Title, Status, Date Applied, Location, Compensation, Type, Link, Portal Password\n");
            }

            // Append internship data as a CSV line
            printWriter.println(internship.toCSV());

        } catch (IOException error) {
            System.err.println("Error writing internship: " + error.getMessage());
        }
    }

    /**
     * Method to load all internships from the CSV data file into a list
     * @return a list of internship objects from the CSV file
     */
    public static List<Internship> loadInternships() {
        List<Internship> internships = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;

            while ((line = reader.readLine()) != null) {

                // Skip the header row if detected
                if (line.startsWith("Company Name,")) {
                    continue;
                }

                List<String> fields = parseCSVLine(line);

                // Check to avoid IndexOutOfBoundsException
                if (fields.size() >= 9) {
                    Internship internship = new Internship();

                    internship.setCompanyName(fields.get(0));
                    internship.setRoleTitle(fields.get(1));
                    internship.setStatus(fields.get(2));
                    internship.setDateApplied(fields.get(3));
                    internship.setLocation(fields.get(4));
                    internship.setCompensation(fields.get(5));
                    internship.setType(fields.get(6));
                    internship.setLink(fields.get(7));
                    internship.setPortalPassword(fields.get(8));
                    internships.add(internship);
                }
            }
        } catch (IOException error) {
            System.err.println("Error loading internships: " + error.getMessage());
        }
        return internships;
    }

    /**
     * Method to save the list of internships to the CSV file, overwriting existing contents
     * @param internships the list of internship objects to save
     * @throws IOException exception in case writing to the file fails
     */
    public static void saveToCSV(List<Internship> internships) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {

            // Loop to write each internship to CSV file
            for (Internship internship : internships) {
                writer.write(internship.toCSV());
                writer.write("\n"); // new line
            }
        }
    }

    /**
     * Method to parse a CSV data line into a list of individual fields
     * @param line the specified CSV line to parse
     * @return a list of parsed string values
     */
    private static List<String> parseCSVLine(String line) {
        List<String> result = new ArrayList<>();
        StringBuilder currentField = new StringBuilder();
        boolean inQuotes = false;

        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);

            if (inQuotes) {
                if (c == '\"') {
                    if (i + 1 < line.length() && line.charAt(i + 1) == '\"') {
                        currentField.append('\"');
                        i++; // Skip the escaped quotes
                    } else {
                        inQuotes = false; // End of quoted section
                    }
                } else {
                    currentField.append(c);
                }
            } else {
                if (c == '\"') {
                    inQuotes = true;
                } else if (c == ',') {
                    result.add(currentField.toString());
                    currentField.setLength(0);
                } else {
                    currentField.append(c);
                }
            }
        }
        // Add the last field after the loop ends
        result.add(currentField.toString());
        return result;
    }
}
