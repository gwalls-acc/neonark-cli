package org.example.neonarkcli;
import java.util.Scanner;



public class NeonarkCliApplication {

    public static void main(String[] args) {


            Scanner scanner = new Scanner(System.in);
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
                        System.out.println("\nViewing list of Wardens");
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
