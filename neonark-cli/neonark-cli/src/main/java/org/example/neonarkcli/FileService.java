/*
  Name: Gloria Walls
  Instructor: Professor Jon-Mikel Pearson
  Assignment: Project 4
  Due Date: 05/14/2026
  Course/Section: COSC 4301 – Section 1
  File Name: FileSErvice.java
  Purpose: This is a helper class for processing CSV files
*/

package org.example.neonarkcli;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;


public class FileService {
    // Adjust the pattern to match your CSV date format (e.g., "yyyy-MM-dd")
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public List<Warden> loadWardensFromCSV(String filePath) {
        List<Warden> wardens = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String headerLine = br.readLine();
            if (headerLine == null) return wardens;

            // Step 1: Learn where every column is based on the header row
            Map<String, Integer> columnMap = createColumnMap(headerLine);
            String line;

            while ((line = br.readLine()) != null) {
                // The -1 limit ensures trailing empty commas (like empty endDate) are preserved
                String[] v = line.split(",", -1);

                // Step 2: Use the map to grab data by column name, not index number
                String firstName = getVal(v, columnMap, "firstName");
                String lastName  = getVal(v, columnMap, "lastName");
                String pidType   = getVal(v, columnMap, "pidType");
                String pid       = getVal(v, columnMap, "pid");
                String email     = getVal(v, columnMap, "email");
                String role      = getVal(v, columnMap, "role");
                String level     = getVal(v, columnMap, "level");
                String status    = getVal(v, columnMap, "status");

                // Step 3: Parse dates (ISO format: yyyy-MM-dd)
                LocalDate start = parseDate(getVal(v, columnMap, "startDate"));
                LocalDate end   = parseDate(getVal(v, columnMap, "endDate"));

                wardens.add(new Warden(firstName, lastName, pidType, pid, email, role, level, status, start, end));
            }
        } catch (IOException e) {
            System.out.println("Error reading CSV: " + e.getMessage());
        }
        return wardens;
    }

    private Map<String, Integer> createColumnMap(String headerLine) {
        Map<String, Integer> map = new HashMap<>();
        String[] headers = headerLine.split(",");
        for (int i = 0; i < headers.length; i++) {
            // Trim to handle hidden spaces in the header
            map.put(headers[i].trim(), i);
        }
        return map;
    }

    private String getVal(String[] row, Map<String, Integer> map, String colName) {
        Integer index = map.get(colName);
        if (index != null && index < row.length) {
            return row[index].trim();
        }
        return ""; // Safe fallback for empty columns or missing headers
    }

    private LocalDate parseDate(String dateStr) {
        if (dateStr == null || dateStr.isEmpty()) return null;
        try {
            // No formatter needed for 2026-05-05!
            return LocalDate.parse(dateStr);
        } catch (Exception e) {
            return null;
        }
    }
}
