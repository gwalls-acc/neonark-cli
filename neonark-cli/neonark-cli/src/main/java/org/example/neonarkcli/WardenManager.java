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
import java.util.Scanner;

public class WardenManager {

    private String mapLevelChoice(int choice) {
        return switch (choice) {
            case 1 -> "Alpha";
            case 2 -> "Omega";
            case 3 -> "Eclipse";
            default -> "";
        };
    }

    private String mapStatusChoice(int choice) {
        return switch (choice) {
            case 1 -> "Active";
            case 2 -> "OnLeave";
            case 3 -> "Terminated";
            default -> "Active";
        };
    }

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


    public boolean  addWarden(Scanner scanner){
        boolean retval = true;


        System.out.println("--- New Warden Entry ---");

        String pidType    = InputHelper.getRequiredString(scanner, "Enter ID Type: ");
        String pid        = InputHelper.getRequiredString(scanner, "Enter ID: ");
        String firstName  = InputHelper.getRequiredString(scanner, "Enter First Name: ");
        String lastName   = InputHelper.getOptionalString(scanner,"Enter Last Name: ");
        String email      = InputHelper.getValidEmail(scanner);
        int roleChoice          = InputHelper.getValidRole(scanner);
        int levelChoice   = InputHelper.getValidLevel(scanner);
        int statusChoice  = InputHelper.getValidStatus(scanner);

        String level = mapLevelChoice(levelChoice);
        String status = mapStatusChoice(statusChoice);
        String role = mapRoleChoice(roleChoice);


        LocalDate start = InputHelper.getValidDate(scanner, "Enter Start Date");

        LocalDate end;
        while (true) {
            end = InputHelper.getValidDate(scanner, "Enter End Date");

            if (end.isBefore(start)) {
                System.out.println("Error: End date cannot be earlier than the start date (" + start + ").");
            } else {
                break; // Dates are valid relative to each other
            }
        }

        Warden newWarden = new Warden(firstName, lastName, pidType, pid, email, role, level, status, start, end);


        return retval;
    }

}
