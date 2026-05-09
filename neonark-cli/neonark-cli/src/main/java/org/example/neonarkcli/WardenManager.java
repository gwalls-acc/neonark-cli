/*
  Name: Gloria Walls
  Instructor: Professor Jon-Mikel Pearson
  Assignment: Project 4
  Due Date: 05/14/2026
  Course/Section: COSC 4301 – Section 1
  File Name: WardenManager.java
  Purpose: This is a helper class for adding new Wardens
*/

package org.example.neonarkcli;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class WardenManager {
    //map chosen menu int  to name for level
    private String mapLevelChoice(int choice) {
        return switch (choice) {
            case 1 -> "Alpha";
            case 2 -> "Omega";
            case 3 -> "Eclipse";
            default -> "";
        };
    }

    //map chosen menu int  to name for status
    private String mapStatusChoice(int choice) {
        return switch (choice) {
            case 1 -> "Active";
            case 2 -> "OnLeave";
            case 3 -> "Terminated";
            default -> "Active";
        };
    }

    //map chosen menu int  to name for role
    private String mapRoleChoice(int choice) {
        return switch (choice) {
            case 1 -> "Admin";
            case 2 -> "Field";
            case 3 -> "Rift";
            case 4 -> "Trainer";
            case 5 -> "Astral";
            default -> "";
        };
    }

    //find a warden record given a pid
    public Warden findWardenById(List<Warden> database, String targetId) {
        for (Warden warden : database) {
            // Use .equals() for String comparison, not ==
            if (warden.getId().equals(targetId)) {
                return warden; // Found them! Exit immediately.
            }
        }
        return null; // Checked the whole list and found nothing.
    }

    //check to see if warden exists to prevent duplicate records
    public boolean wardenExists(List<Warden> database, String pid) {
        // If findWardenById doesn't return null, it means the ID is taken
        return findWardenById(database, pid) != null;
    }

   //get inputs for warden record and create record
    public boolean addWarden(Scanner scanner, List<Warden> database) {
        //clear the buffer
        scanner.nextLine();


        System.out.println("--- New Warden Entry ---");

        String pid = "";

        while (true) {
            System.out.print("Enter Unique ID (or type 'q' to cancel): ");
            pid = scanner.nextLine().strip();

            // 1. Check for escape command
            if (pid.equalsIgnoreCase("q")) {
                System.out.println("Operation cancelled.");
                return false;
            }

            // 2. Check for empty
            if (pid.isBlank()) {
                System.out.println("Error: ID cannot be blank.");
                continue;
            }

            // 3. Check for duplicates
            if (wardenExists(database, pid)) {
                System.out.println("Error: ID '" + pid + "' is already in use.");
            } else {
                break; // Success! The ID is unique.
            }


        // Now proceed with the rest of the form...
        System.out.println("ID accepted: " + pid);
        // ...
        return true;
    }

        String pidType = InputHelper.getRequiredString(scanner, "Enter ID Type: ");
        String firstName = InputHelper.getRequiredString(scanner, "Enter First Name: ");
        String lastName = InputHelper.getOptionalString(scanner, "Enter Last Name: ");
        String email = InputHelper.getValidEmail(scanner);
        int roleChoice = InputHelper.getValidRole(scanner);
        int levelChoice = InputHelper.getValidLevel(scanner);
        int statusChoice = InputHelper.getValidStatus(scanner);

        String level = mapLevelChoice(levelChoice);
        String status = mapStatusChoice(statusChoice);
        String role = mapRoleChoice(roleChoice);


        LocalDate start = InputHelper.getValidDate(scanner, "Enter Start Date");

        // End date is optional but if entered must not occur befoe start date
        LocalDate end = InputHelper.getOptionalDate(scanner, "Enter End Date", start);

        //create new warden record
        Warden newWarden = new Warden(firstName, lastName, pidType, pid, email, role, level, status, start, end);

        //prompt user to save warden record
        if (InputHelper.getConfirmation(scanner, "Save this entry to the session?")) {
            database.add(newWarden);
            System.out.println("Warden added to the current session.");
            simulateApiRequest(newWarden);
            return true;
        }
        // record discarded
        System.out.println("Entry discarded.");
        return false;

    }

    //API request for saving new warden record
    private void simulateApiRequest(Warden warden) {
        System.out.println("\n--- [SIMULATED API CALL] ---");
        System.out.println("HTTP Method: POST");
        System.out.println("Endpoint: https://api.warden-registry.sys/v1/wardens");
        System.out.println("Description: Registering new warden into the global database.");

        // Constructing a "Fake" JSON payload for display
        String endStr = (warden.getEndDate() == null) ? "N/A" : warden.getEndDate().toString();
        String jsonPayload = String.format(
                "{\n" +
                        "  \"pid\": \"%s\",\n" +
                        "  \"type\": \"%s\",\n" +
                        "  \"name\": \"%s %s\",\n" +
                        "  \"email\": \"%s\",\n" +
                        "  \"role\": \"%s\",\n" +
                        "  \"status\": \"%s\",\n" +
                        "  \"startDate\": \"%s\",\n" +
                        "  \"endDate\": \"%s\"\n" +
                        "}",
                warden.getId(), warden.getPidType(), warden.getFirstName(), warden.getLastName(),
                warden.getEmail(), warden.getRole(), warden.getStatus(), warden.getStartDate(),endStr
        );

        //simulated JSON
        System.out.println("Payload:\n" + jsonPayload);
        System.out.println("----------------------------");

        // Simulated Result
        // You could add logic here: if email contains "blocked.com", return BLOCKED
        if (warden.getEmail().endsWith("@blocked.com")) {
            System.out.println("RESULT: [403 FORBIDDEN] - Entry Blocked: Email domain blacklisted.");
        } else {
            System.out.println("RESULT: [201 CREATED] - Entry Verified: Success.");
        }
        System.out.println("----------------------------\n");
    }

}

