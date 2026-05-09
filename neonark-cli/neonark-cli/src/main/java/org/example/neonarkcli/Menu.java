/*
  Name: Gloria Walls
  Instructor: Professor Jon-Mikel Pearson
  Assignment: Project 4
  Due Date: 05/14/2026
  Course/Section: COSC 4301 – Section 1
  File Name: Menu.java
  Purpose: This is the  main menu for the CLI
  for the Neonark application
*/

package org.example.neonarkcli;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Menu
{
    private final Scanner scanner;
    private final List<Warden> database = new ArrayList<>();
    private final FileService fileService = new FileService();

    public Menu(Scanner scanner, String csvPath) {
        this.scanner = scanner;
        // 2. THIS IS WHERE IT GETS FILLED
        // We load the CSV data into that list immediately.
        database.addAll(fileService.loadWardensFromCSV("wardens.csv"));
    }

    private Warden findWardenById(String id) {
        for (Warden w : database) {
            if (w.getId().equalsIgnoreCase(id)) {
                return w;
            }
        }
        return null;
    }

    //Show application main menu
    public void showMenu()
    {

        boolean running = true;

        while (running) {
            System.out.println("\n=========================================================");
            System.out.println("       NEON ARK — ADMIN WARDEN ONBOARDING CONSOLE");
            System.out.println("=========================================================");
            System.out.println("\n[ MAIN MENU ]");
            System.out.println("---------------------------------------------------------");
            System.out.println("1. Add New Warden");
            System.out.println("2. View Wardens");
            System.out.println("3. Update Warden");
            System.out.println("4. Manage Certifications");
            System.out.println("5. Deactivate / Terminate Warden");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            WardenManager wardenManager = new WardenManager();

            switch (choice) {
                case 1:
                    showAddWardenSubmenu();

                    break;
                case 2:
                    showViewSubmenu();
                    break;
                case 3:
                    System.out.println("\nUpdating Warden");
                    break;

                case 4:
                    System.out.println("\nGo to Manage Certifications Submenu");
                    break;

                case 5:
                    deactivateWarden();
                    break;

                case 6:
                    running = false; // Terminates the loop
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
        scanner.close(); // Best practice to close the scanner
    }

    //Submenu for option 1 Add New Warden
    private void showAddWardenSubmenu(){
        WardenManager wardenManager = new WardenManager();

        System.out.println("\n\n" + "-".repeat(150));
        System.out.println("[ 1 ] Add Warden");
        System.out.println( "-".repeat(150));
        wardenManager.addWarden(scanner,database);

    }

    //Submenu for option 2 View Wardens
    private void showViewSubmenu() {
        boolean backToMain = false;

        while (!backToMain) {
            System.out.println("\n\n" + "-".repeat(150));
            System.out.println("[ 2 ] View Wardens");
            System.out.println( "-".repeat(150));
            System.out.println("1. View All Wardens");
            System.out.println("2. View Warden by ID");
            System.out.println("3. View Wardens by Employment Status");
            System.out.println("4. View Wardens by Role");
            System.out.println("5. Return to MAIN MENU");

            int choice = InputHelper.getValidInput(scanner, 1, 5);

            switch (choice) {
                case 1 -> viewAllWardens();
                case 2 -> searchById();
                case 3 -> viewByStatus();
                case 4 -> viewByRole();
                case 5 -> backToMain = true; // Breaks the submenu loop
            }
        }
    }


    private void viewAllWardens() {

            System.out.println("\n\n" + "-".repeat(160));
            System.out.println("[ 2.1 ] View All Wardens");
            System.out.println("-".repeat(160));

            // Header - Added PID TYPE column
            String format = "%-12s | %-12s | %-12s | %-15s | %-25s | %-12s | %-6s | %-10s | %-12s | %-12s%n";

            System.out.printf(format,
                    "FIRST NAME", "LAST NAME", "PID TYPE", "PID", "EMAIL", "ROLE", "LVL", "STATUS", "START DATE", "END DATE"
            );
            System.out.println("=".repeat(160));

            // Data Rows - Use the same 'format' string!
            for (Warden w : database) {
                System.out.printf(format,
                        w.getFirstName(),
                        w.getLastName(),
                        w.getPidType(),
                        w.getId(),
                        w.getEmail(),
                        w.getRole(),
                        w.getLevel(),
                        w.getStatus(),
                        w.getStartDate(),
                        (w.getEndDate() == null ? "N/A" : w.getEndDate())
                );
            }
            System.out.println("-".repeat(160));
        }



    private void searchById() {
        System.out.println("\n\n" + "-".repeat(150));
        System.out.println("[ 2.2 ] View Warden by ID");
        System.out.println( "-".repeat(150));
        System.out.println("User would be prompted for ID");
        System.out.println("HTTP Method: GET");
        System.out.println("Endpoint: /api/wardens?id={id}");
        System.out.println("200 OK (success, returns warden JSON for one warden");
        System.out.println("404 Not Found (no warden with that ID)");
        System.out.println("400 Bad Request (ID is not a valid number\n");
        System.out.println("Sample request:");
        System.out.println("GET /api/wardens?id=123456");
        System.out.println("Sample Response");
        System.out.println("HTTP/1.1 200 OK");
        System.out.println("Content-Type: application/json");
        System.out.println("\n[");
        System.out.println("  {");
        System.out.println("    \"pid\": \"123456\",");
        System.out.println("    \"firstName\": \"Artos\",");
        System.out.println("    \"lastName\": \"Silmaril\",");
        System.out.println("    \"email\": \"asilmaril@neoanark.org\",");
        System.out.println("    \"role\": \"Admin\",");
        System.out.println("    \"status\": \"Active\",");
        System.out.println("    \"startDate\": \"2025-11-12\",");
        System.out.println("    \"endDate\": null");
        System.out.println("  },");
        System.out.println("]");


    }

    private void viewByStatus() {
        System.out.println("\n\n" + "-".repeat(150));
        System.out.println("[ 2.3 ] View Warden by Employment Status");
        System.out.println( "-".repeat(150));
        System.out.println("User would be prompted for status");
        System.out.println("HTTP Method: GET");
        System.out.println("Endpoint: /api/wardens?status={status}");
        System.out.println("HTTP Status Codes:");
        System.out.println("404 Not Found (status not found)");
        System.out.println("200 OK (success, returns warden JSON for wardens of specified status");
        System.out.println("Response Body:");
        System.out.println("Sample request:");
        System.out.println("GET /api/wardens?status=OnLeave");
        System.out.println("Sample Response");
        System.out.println("HTTP/1.1 200 OK");
        System.out.println("Content-Type: application/json");
        System.out.println("\n[");
        System.out.println("  {");
        System.out.println("    \"pid\": \"W-102\",");
        System.out.println("    \"firstName\": \"Aris\",");
        System.out.println("    \"lastName\": \"Thorne\",");
        System.out.println("    \"email\": \"athorne@astral.org\",");
        System.out.println("    \"role\": \"Astral\",");
        System.out.println("    \"status\": \"OnLeave\",");
        System.out.println("    \"startDate\": \"2026-01-15\",");
        System.out.println("    \"endDate\": null");
        System.out.println("  },");
        System.out.println("  {");
        System.out.println("    \"pid\": \"W-205\",");
        System.out.println("    \"firstName\": \"Lyra\",");
        System.out.println("    \"lastName\": \"Vane\",");
        System.out.println("    \"email\": \"lvane@astral.org\",");
        System.out.println("    \"role\": \"Field\",");
        System.out.println("    \"status\": \"OnLeave\",");
        System.out.println("    \"startDate\": \"2025-11-20\",");
        System.out.println("    \"endDate\": \"2026-06-01\"");
        System.out.println("  }");
        System.out.println("]");

    }

    private void viewByRole() {
        System.out.println("\n\n" + "-".repeat(150));
        System.out.println("[ 2.4 ] View Warden by Role");
        System.out.println( "-".repeat(150));
        System.out.println("User would be prompted for role");
        System.out.println("HTTP Method: GET");
        System.out.println("Endpoint: /api/wardens?role={role}");
        System.out.println("HTTP Status Codes:");
        System.out.println("404 Not Found (role not found)");
        System.out.println("200 OK (success, returns warden JSON for wardens of specified role");
        System.out.println("Response Body:");
        System.out.println("Sample request:");
        System.out.println("GET /api/wardens?role=Astral");
        System.out.println("Sample Response");
        System.out.println("HTTP/1.1 200 OK");
        System.out.println("Content-Type: application/json");
        System.out.println("\n[");
        System.out.println("  {");
        System.out.println("    \"pid\": \"W-102\",");
        System.out.println("    \"firstName\": \"Aris\",");
        System.out.println("    \"lastName\": \"Thorne\",");
        System.out.println("    \"email\": \"athorne@astral.org\",");
        System.out.println("    \"role\": \"Astral\",");
        System.out.println("    \"status\": \"Active\",");
        System.out.println("    \"startDate\": \"2026-01-15\",");
        System.out.println("    \"endDate\": null");
        System.out.println("  },");
        System.out.println("  {");
        System.out.println("    \"pid\": \"W-205\",");
        System.out.println("    \"firstName\": \"Lyra\",");
        System.out.println("    \"lastName\": \"Vane\",");
        System.out.println("    \"email\": \"lvane@astral.org\",");
        System.out.println("    \"role\": \"Astral\",");
        System.out.println("    \"status\": \"OnLeave\",");
        System.out.println("    \"startDate\": \"2025-11-20\",");
        System.out.println("    \"endDate\": \"2026-06-01\"");
        System.out.println("  }");
        System.out.println("]");
    }

    //Submenu to update Warden Record
    private void updateSubmenu(){
        System.out.println("\n\n" + "-".repeat(150));
        System.out.println("[ 3 ] Update Warden");
        System.out.println( "-".repeat(150));
        System.out.println("Choose field to update");
        System.out.println("[1] FirstName");
        System.out.println("[2] LastName");
        System.out.println("[3] Email");
        System.out.println("[4] Role");
        System.out.println("[5] Level");
        System.out.println( "-".repeat(150));
        System.out.println("[6] Reactivate Inactive Warden");
        System.out.println("[7] Return to Main Menu ");

    }

    private void deactivateWarden(){
        System.out.println("\n\n" + "-".repeat(150));
        System.out.println("[ 5 ] Deactivate Warden");
        System.out.println( "-".repeat(150));
        System.out.println("User would be prompted for ID of warden to be deactivated");
        System.out.println("User would be prompted to choose deactivation type : OnLeave or Terminated");
        System.out.println("Would send: POST /api/deactivateWarden/{id}");
        System.out.println("HTTP Status Codes:");
        System.out.println("200 OK (success warden was marked inactive");
        System.out.println("404 Not Found (id  not found)");
    }

 }
