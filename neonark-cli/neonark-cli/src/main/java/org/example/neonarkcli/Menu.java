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
import java.time.LocalDate;
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

        //load data from csv file into database arraylist
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
                    updateSubmenu();
                    break;

                case 4:
                    manageCertificationsSubmenu();
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

    // submenu 2.1 view entire warden's table
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


  //submenu 2.2 display all wardens for a specific ID
    private void searchById() {
        System.out.println("\n\n" + "-".repeat(150));
        System.out.println("[ 2.2 ] View Warden by ID");
        System.out.println( "-".repeat(150));
        System.out.println("User would be prompted for ID");
        System.out.println("HTTP Method: GET");
        System.out.println("Endpoint: /api/wardensByID/{id}");
        System.out.println("200 OK (success, returns warden JSON for one warden");
        System.out.println("404 Not Found (no warden with that ID)");
        System.out.println("400 Bad Request (ID is not a valid number\n");
        System.out.println("Sample request:");
        System.out.println("GET /api/wardensById/123456");
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


    //submenu 2.3 view all wardens wi4th specified status
    private void viewByStatus() {
        System.out.println("\n\n" + "-".repeat(150));
        System.out.println("[ 2.3 ] View Warden by Employment Status");
        System.out.println( "-".repeat(150));
        System.out.println("User would be prompted for status");
        System.out.println("HTTP Method: GET");
        System.out.println("Endpoint: /api/wardensByStatus/{status}");
        System.out.println("HTTP Status Codes:");
        System.out.println("404 Not Found (status not found)");
        System.out.println("200 OK (success, returns warden JSON for wardens of specified status");
        System.out.println("Response Body:");
        System.out.println("Sample request:");
        System.out.println("GET /api/wardensByStatus/OnLeave");
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

    // submenu 2.4 view wardesn of specified role
    private void viewByRole() {
        System.out.println("\n\n" + "-".repeat(150));
        System.out.println("[ 2.4 ] View Warden by Role");
        System.out.println( "-".repeat(150));
        System.out.println("User would be prompted for role");
        System.out.println("HTTP Method: GET");
        System.out.println("Endpoint: /api/wardensByRole/{role}");
        System.out.println("HTTP Status Codes:");
        System.out.println("404 Not Found (role not found)");
        System.out.println("200 OK (success, returns warden JSON for wardens of specified role");
        System.out.println("Response Body:");
        System.out.println("Sample request:");
        System.out.println("GET /api/wardensByRole/Astral");
        System.out.println("Sample Response");
        System.out.println("HTTP/1.1 200 OK");
        System.out.println("Content-Type: application/json");
        System.out.println("\n[");
        System.out.println("  {");
        System.out.println("    \"pid\": \"102\",");
        System.out.println("    \"firstName\": \"Aris\",");
        System.out.println("    \"lastName\": \"Thorne\",");
        System.out.println("    \"email\": \"athorne@astral.org\",");
        System.out.println("    \"role\": \"Astral\",");
        System.out.println("    \"status\": \"Active\",");
        System.out.println("    \"startDate\": \"2026-01-15\",");
        System.out.println("    \"endDate\": null");
        System.out.println("  },");
        System.out.println("  {");
        System.out.println("    \"pid\": \"205\",");
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

    //Submenu  3 to update Warden Record
    private void updateSubmenu(){
        boolean backToMain = false;

        while (!backToMain) {
            System.out.println("\n\n" + "-".repeat(150));
            System.out.println("[ 3 ] Update Warden");
            System.out.println("-".repeat(150));
            System.out.println("Choose an option");
            System.out.println("1. Update Role");
            System.out.println("2. Update Clearance Level");
            System.out.println("3. Update Employment Status");
            System.out.println("4. Update Start Date");
            System.out.println("5. Update End Date (Termination Date)");
            System.out.println("6. Update Email");
            System.out.println("7. Return to Main Menu ");

            int choice = InputHelper.getValidInput(scanner, 1, 7);

            switch (choice) {
                case 1 -> viewUpdateRole();
                case 2 -> viewUpdateLevel();
                case 3 -> viewUpdateStatus();
                case 4 -> viewUpdateStart();
                case 5 -> viewUpdateEnd();
                case 6 -> viewUpdateEmail();
                case 7 -> backToMain = true; // Breaks the submenu loop
            }
        }
    }

    //submenu 3.1 to update role
    private void viewUpdateRole() {
        boolean backToMenu = false;
         while (!backToMenu) {
            System.out.println("[ 3.1 ] Update Role");
            System.out.println("-".repeat(150));
            int roleChoice = InputHelper.getValidRole(scanner);
            String role = WardenManager.mapRoleChoice(roleChoice);
            System.out.println("Role will be changed to "+ role );
            System.out.println("User will be asked to confirm change");
            System.out.println("If user confirms change the following API will be called");
            System.out.println("HTTP Method: POST");
            System.out.println("Endpoint: /api/{id}/wardenUpdate");
            System.out.println("Example:");
            System.out.println("POST /api/45778/wardenUpdate");
            System.out.println("PAYLOAD (JSON)");
            System.out.println("{");
            System.out.println("\"role\": \""+ role + "\"");
            System.out.println("}");
            System.out.println();
            System.out.println("-----------Return to Previous Menu--------------");
            backToMenu = InputHelper.getConfirmation(scanner,"Return to Update Warden Menu?");

      }
    }

    //submenu  3.2 to update role
    private void viewUpdateLevel() {
        boolean backToMenu = false;
        while (!backToMenu) {
            System.out.println("[ 3.2 ] Update Level");
            System.out.println("-".repeat(150));
            int levelChoice = InputHelper.getValidLevel(scanner);
            String level = WardenManager.mapLevelChoice(levelChoice);
            System.out.println("Level will be changed to " + level);
            System.out.println("User will be asked to confirm change");
            System.out.println("If user confirms change the following API will be called");
            System.out.println("HTTP Method: POST");
            System.out.println("Endpoint: /api/{id}/wardenUpdate");
            System.out.println("Example:");
            System.out.println("POST /api/45778/wardenUpdate");
            System.out.println("PAYLOAD (JSON)");
            System.out.println("{");
            System.out.println("\"level\" : \"" + level +"\"");
            System.out.println("}");
            System.out.println();
            System.out.println("-----------Return to Previous Menu--------------");
            backToMenu = InputHelper.getConfirmation(scanner, "Return to UpdateWarden Menu");
        }
    }

    //submenu  3.3 to update status
        private void viewUpdateStatus() {
        boolean backToMenu = false;
        while (!backToMenu) {
            System.out.println("[ 3.3 ] Update Status");
            System.out.println("-".repeat(150));
            int statusChoice = InputHelper.getValidStatus(scanner);
            String status = WardenManager.mapStatusChoice(statusChoice);
            System.out.println("Status will be changed to "+ status );
            System.out.println("User will be asked to confirm change");
            System.out.println("If user confirms change the following API will be called");
            System.out.println("HTTP Method: POST");
            System.out.println("Endpoint: /api/{id}/wardenUpdate");
            System.out.println("Example:");
            System.out.println("POST /api/12452/wardenUpdate");
            System.out.println("PAYLOAD (JSON)");
            System.out.println("{");
            System.out.println("\"status\" :\" " + status + "\"");
            System.out.println("}");
            System.out.println();
            System.out.println("-----------Return to Previous Menu--------------");
            backToMenu = InputHelper.getConfirmation(scanner,"Return to Update Warden Menu?");
        }
    }

    //submenu 3.4 to update stasrt date
    private void viewUpdateStart() {
        boolean backToMenu = false;
        while (!backToMenu) {
            System.out.println("[ 3.4 ] Update Start");
            System.out.println("-".repeat(150));
            LocalDate startDate = InputHelper.getValidDate(scanner,"Enter new start date: ");
            System.out.println("Start date  will be changed to "+ startDate );
            System.out.println("User will be asked to confirm change");
            System.out.println("If user confirms change the following API will be called");
            System.out.println("HTTP Method: POST");
            System.out.println("Endpoint: /api/{id}/wardenUpdate");
            System.out.println("Example:");
            System.out.println("POST /api/136258/wardenUpdate");
            System.out.println("PAYLOAD (JSON)");
            System.out.println("{");
            System.out.println("\"startDate\": \"" + startDate + "\"");
            System.out.println("}");
            System.out.println();
            System.out.println("-----------Return to Previous Menu--------------");
            backToMenu = InputHelper.getConfirmation(scanner,"Return to Update Warden Menu ?");
        }
    }

    //submenu 3.5 to update end date
    private void viewUpdateEnd() {
        boolean backToMenu = false;
        LocalDate startDate = LocalDate.parse("2020-10-31"); //sample startDate for demo.  Needed for validate of end date
        while (!backToMenu) {
            System.out.println("[ 3.5 ] Update End");
            System.out.println("-".repeat(150));
            System.out.println("Validation code requires start date for comparison.");
            System.out.println("This data will be extracted from Warden record based  the  warden ID provided by the user");
            System.out.println("For demonstration purposes, I am providing a value for start date");
            LocalDate endDate = InputHelper.getOptionalDate(scanner,"Enter new end date: ",startDate);
            System.out.println("End date  will be changed to "+ endDate );
            System.out.println("User will be asked to confirm change");
            System.out.println("If user confirms change the following API will be called");
            System.out.println("HTTP Method: POST");
            System.out.println("Endpoint: /api/{ID}/wardenUpdate");
            System.out.println("Example:");
            System.out.println("POST /api/136258/wardenUpdate");
            System.out.println("PAYLOAD (JSON)");
            System.out.println("{");
            System.out.println("\"endDate\" : \"" + endDate + "\"");
            System.out.println("}");
            System.out.println();
            System.out.println("-----------Return to Previous Menu--------------");
            backToMenu = InputHelper.getConfirmation(scanner,"Return to Update Warden Menu ?");
        }
    }


   //submenu 3.6 to update email
    private void viewUpdateEmail() {
        boolean backToMenu = false;
        while (!backToMenu) {
            System.out.println("[ 3.6 ] Update Email");
            System.out.println("-".repeat(150));
            String email = InputHelper.getValidEmail(scanner);
            System.out.println("Email will be changed to " + email);
            System.out.println("User will be asked to confirm change");
            System.out.println("If user confirms change the following API will be called");
            System.out.println("HTTP Method: POST");
            System.out.println("Endpoint: /api/74874/wardenUpdate");
            System.out.println("Example:");
            System.out.println("POST /api/wardenUpdate");
            System.out.println("PAYLOAD (JSON)");
            System.out.println("{");
            System.out.println("\"email\" : \"" + email + "\"");
            System.out.println("}");
            System.out.println();
            System.out.println("-----------Return to Previous Menu--------------");
            backToMenu = InputHelper.getConfirmation(scanner, "Return to Update Warden Menu");
        }

    }



        
      // Main Menu item 4 - Manager Certifications
      private void  manageCertificationsSubmenu(){
          boolean backToMain = false;
          while (!backToMain) {
              System.out.println("\n\n" + "-".repeat(150));
              System.out.println("[ 4 ] Manage Certifications");
              System.out.println("-".repeat(150));
              System.out.println("Choose an option");
              System.out.println("1. Add certification");
              System.out.println("2. View Certifications");
              System.out.println("3. Mark Certifications Expired");
              System.out.println("4. Remove Certification");             
              System.out.println("5. Return to Main Menu ");

              int choice = InputHelper.getValidInput(scanner, 1, 7);

              switch (choice) {
                  case 1 -> viewaddCertification();
                  case 2 -> viewCertifications();
                  case 3 -> viewCertificationExpired();
                  case 4 -> viewRemoveCertification();                 
                  case 5 -> backToMain = true; // Breaks the submenu loop
              }
          }

      }

    // submenu 4.1  from Manage Certifications - Add  Certification
    private void viewaddCertification() {

        boolean backToMenu = false;
        while (!backToMenu) {

        System.out.println("[ 4.1 ] Add Certification");
        System.out.println("-".repeat(150));
        System.out.println("User is prompted for Warden's ID ");
        System.out.println("User is prompted for Certification Name ");
        System.out.println("User is prompted for Expiration date.  This is optional in case Certification does not have one ");
        System.out.println("Date is validated for valid date format" );
        System.out.println("HTTP Method: POST");
        System.out.println("DESCRIPTION: Create a new certification record and associate it with an existing Warden.");
        System.out.println("Endpoint: /api/{id}/addCertification");
        System.out.println("Example:");
        System.out.println("POST /api/74874/addCertification");
        System.out.println("PAYLOAD (JSON)");
        System.out.println("{");
        System.out.println("\"name\" : \" Cosmic Care and Feeding \"");
        System.out.println("\"earnedDate\": \"2026-02-03\",");
        System.out.println("\"expirationDate\": \"2027-02-03\",");
        System.out.println("}");
        System.out.println("HTTP Status Codes:");
        System.out.println("200 OK (success - certification was successfully added )");
        System.out.println("-----------Return to Previous Menu--------------");
        backToMenu = InputHelper.getConfirmation(scanner, "Return to Manage Certifications Menu");
        }
    }

    // submenu 4.2  from  Manage Certifications - View  Certifications
    private void viewCertifications() {
        boolean backToMenu = false;
        while (!backToMenu) {

            System.out.println("[ 4.2 ] View Certifications");
            System.out.println("-".repeat(150));
            System.out.println("User is prompted for Warden's ID ");
            System.out.println("HTTP Method: GET");
            System.out.println("DESCRIPTION: View all certifications held by existing Warden.");
            System.out.println("Endpoint: /api/{id}/viewCertifications");
            System.out.println("Example:");
            System.out.println("GET /api/{id}/viewCertifications");
            System.out.println("PAYLOAD: None");
            System.out.println("HTTP Status Codes:");
            System.out.println("200 OK (success - list of certifications will follow )");
            System.out.println("{");
            System.out.println("\"name\" : \" Cosmic Care and Feeding \"");
            System.out.println("\"earnedDate\": \"2026-02-03\",");
            System.out.println("\"expirationDate\": \"2027-02-03\",");
            System.out.println("}");
            System.out.println("HTTP Status Codes:");
            System.out.println("200 OK (success -  ID was matched and any certifications for this warden will be listed)");
            System.out.println("{");
            System.out.println("  [");
            System.out.println("     \"name\" : \" Cosmic Care and Feeding \"");
            System.out.println("     \"earnedDate\": \"2026-02-03\",");
            System.out.println("     \"expirationDate\": \"2027-02-03\",");
            System.out.println("  ],");
            System.out.println("  [");
            System.out.println("     \"name\" : \" Intergalactic Veterinary Specialist I \"");
            System.out.println("     \"earnedDate\": \"2026-01-01\",");
            System.out.println("     \"expirationDate\": \"2029-01-31\",");
            System.out.println("  ],");
            System.out.println("}");
            System.out.println("-----------Return to Previous Menu--------------");
            backToMenu = InputHelper.getConfirmation(scanner, "Return to Manage Certifications Menu");
        }
    }

    // submenu 4.3  from Manage Certifications - Remove  Mark Expired  Certification
    private void viewCertificationExpired() {
        boolean backToMenu = false;
        while (!backToMenu) {

            System.out.println("[ 4.3 ] View  Expired Certifications");
            System.out.println("-".repeat(150));
            System.out.println("User is prompted for Warden's ID ");
            System.out.println("HTTP Method: GET");
            System.out.println("DESCRIPTION: View all certifications held by existing Warden.");
            System.out.println("Endpoint: /api/{id}/viewCertifications");
            System.out.println("Example:");
            System.out.println("GET /api/{id}/viewExpCertifications");
            System.out.println("PAYLOAD: None");
            System.out.println("HTTP Status Codes:");
            System.out.println("200 OK (success -  ID was matched and any certifications for this warden will be listed)");
            System.out.println("{");
            System.out.println("  [");
            System.out.println("     \"name\" : \" Cosmic Care and Feeding \"");
            System.out.println("     \"earnedDate\": \"2025-02-03\",");
            System.out.println("     \"expirationDate\": \"2026-02-03\",");
            System.out.println("  ],");
            System.out.println("  [");
            System.out.println("     \"name\" : \" Intergalactic Veterinary Specialist I \"");
            System.out.println("     \"earnedDate\": \"2025-01-01\",");
            System.out.println("     \"expirationDate\": \"2026-01-31\",");
            System.out.println("  ],");
            System.out.println("}");
            System.out.println("-----------Return to Previous Menu--------------");
            backToMenu = InputHelper.getConfirmation(scanner, "Return to Manage Certifications Menu");
        }
    }

    // submenu 4.4  from Manage Certifications - Remove Certifications
    private void viewRemoveCertification() {

        boolean backToMenu = false;
        while (!backToMenu) {

            System.out.println("[ 4.4 ] Remove Certification");
            System.out.println("-".repeat(150));
            System.out.println("User is prompted for Warden's ID ");
            System.out.println("User is prompted for Certification name ");
            System.out.println("HTTP Method: POST");
            System.out.println("DESCRIPTION: Remove specified certification for specified warden");
            System.out.println("Endpoint: /api/{id}/removeCertification");
            System.out.println("Example:");
            System.out.println("POST /api/74874/removeCertification");
            System.out.println("PAYLOAD (JSON)");
            System.out.println("{");
            System.out.println("\"name\" : \" Cosmic Care and Feeding \"");
            System.out.println("}");
            System.out.println("HTTP Status Codes:");
            System.out.println("200 OK (success - certification was successfully removed )");
            System.out.println("-----------Return to Previous Menu--------------");
            backToMenu = InputHelper.getConfirmation(scanner, "Return to Manage Certifications Menu");
        }
    }


    //Menu Item 5 Deactivate Warden
    private void deactivateWarden() {
        boolean backToMain = false;
        while (!backToMain) {
            System.out.println("\n\n" + "-".repeat(150));
            System.out.println("[ 5 ] Deactivate Warden");
            System.out.println("-".repeat(150));
            System.out.println("User would be prompted for ID of warden to be deactivated");
            System.out.println("Deactivation submenu would be displayed and look like this");
            System.out.println("User would be prompted to choose deactivation type : OnLeave or Terminated");
            System.out.println("HTTP Method: POST");
            System.out.println("ENDPOINT /api/deactivateWarden/type");
            System.out.println("PAYLOAD (JSON)");
            System.out.println("{");
            System.out.println("\"deactivateType\": \"Terminated\"");
            System.out.println("}");
            System.out.println("HTTP Status Codes:");
            System.out.println("200 OK (success warden was marked inactive");
            System.out.println("404 Not Found (id  not found)");
            System.out.println("-----------Return to Main Menu--------------");
            backToMain = InputHelper.getConfirmation(scanner, "Return to Main Menu?");

        }
    }

}
