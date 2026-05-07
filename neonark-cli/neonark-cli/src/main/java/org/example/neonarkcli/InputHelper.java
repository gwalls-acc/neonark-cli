

package org.example.neonarkcli;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class InputHelper {

    private static final List<Integer> VALID_ROLES = Arrays.asList(1, 2, 3, 4, 5);
    private static final List<Integer> VALID_LEVELS = Arrays.asList(1, 2, 3);
    private static final List<Integer> VALID_STATUSES = Arrays.asList(1, 2, 3);

    public static String getRequiredString(Scanner scanner, String prompt) {
        String input = "";
        while (true) {
            System.out.print(prompt);
            input = scanner.nextLine().strip(); // Clean up whitespace

            if (!input.isBlank()) {
                return input; // Success!
            } else {
                System.out.println("Error: This field is required. It cannot be left blank.");
            }
        }
    }

    /**
     * Gets a string from the user.
     * If the user enters whitespace or nothing, it returns an empty string.
     */
    public static String getOptionalString(Scanner scanner, String prompt) {
        System.out.print(prompt + " (Optional - press Enter to skip): ");
        String input = scanner.nextLine();

        // Return a stripped version of the string, or an empty string if it's null
        return (input == null) ? "" : input.strip();
    }

    public static int getValidRole(Scanner scanner) {
        while (true) {
            System.out.println("Select Role [1-5]: ");
            System.out.println("[1] Admin");
            System.out.println("[2] Field");
            System.out.println("[3] Rift");
            System.out.println("[4] Trainer");
            System.out.println("[5] Astral");

            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                scanner.nextLine();

                // Now we use the list check you wanted!
                if (VALID_ROLES.contains(choice)) {
                    return choice;
                }
            } else {
                scanner.nextLine();
            }
            System.out.println("Invalid choice. Try again.");
        }
    }

    public static int getValidLevel(Scanner scanner) {
        while (true) {
            System.out.println("Select Clearance Level [1-3]: ");
            System.out.println("[1] Alpha");
            System.out.println("[2] Omega");
            System.out.println("[3] Eclipse");
            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                scanner.nextLine();

                // Now we use the list check you wanted!
                if (VALID_LEVELS.contains(choice)) {
                    return choice;
                }
            } else {
                scanner.nextLine();
            }
            System.out.println("Invalid choice. Try again.");
        }
    }

    public static int getValidStatus(Scanner scanner) {
        while (true) {
            System.out.println("Select Status [1-3]: ");
            System.out.println("[1] Active");
            System.out.println("[2] OnLeave");
            System.out.println("[3] Terminated");
            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                scanner.nextLine();

                // Now we use the list check you wanted!
                if (VALID_STATUSES.contains(choice)) {
                    return choice;
                }
            } else {
                scanner.nextLine();
            }
            System.out.println("Invalid choice. Try again.");
        }
    }

    public static String getValidEmail(Scanner scanner) {
        // A standard regex for basic email validation
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[a-z]{2,}$";
        Pattern pattern = Pattern.compile(emailRegex);

        while (true) {
            System.out.print("Enter Email Address: ");
            String email = scanner.nextLine().trim(); // trim() removes accidental spaces

            if (pattern.matcher(email).matches()) {
                return email; // Valid email! Exit and return the string.
            } else {
                System.out.println("Invalid format. Example: name@domain.com");
            }
        }
    }

    public static LocalDate getValidDate(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt + " (YYYY-MM-DD): ");
            String input = scanner.nextLine().strip();

            try {
                // LocalDate.parse expects the ISO_LOCAL_DATE format (YYYY-MM-DD) by default
                return LocalDate.parse(input);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid format. Please use the YYYY-MM-DD format (e.g., 2026-05-06).");
            }
        }
    }

    public static boolean getConfirmation(Scanner scanner, String message) {
        while (true) {
            System.out.print(message + " (Y/N): ");
            String input = scanner.nextLine().strip().toLowerCase();

            if (input.equals("y") || input.equals("yes")) return true;
            if (input.equals("n") || input.equals("no")) return false;

            System.out.println("Invalid input. Please enter 'Y' for Yes or 'N' for No.");
        }
    }

}
