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

public class Menu {
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

            switch (choice) {
                case 1:
                    System.out.println("\nNew Warden Added");
                    break;
                case 2:
                    System.out.println("\n" + "=".repeat(150));
                    System.out.printf(
                            "%-12s | %-12s | %-17s | %-25s | %-10s | %-8s | %-10s | %-16s | %-10s%n",
                            "FIRST NAME", "LAST NAME", "IDENTITY (PID)", "EMAIL", "ROLE", "LEVEL", "STATUS", "START DATE", "END DATE"
                    );
                    System.out.println("-".repeat(150));

                    database.forEach(System.out::println);
                    break;
                case 3:
                    System.out.println("\nUpdating Warden");
                    break;

                case 4:
                    System.out.println("\nGo to Manage Certifications Submenu");
                    break;

                case 5:
                    System.out.println("\nDeactivating Warden");
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

}

