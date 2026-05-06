/*
  Name: Gloria Walls
  Instructor: Professor Jon-Mikel Pearson
  Assignment: Project 4
  Due Date: 05/14/2026
  Course/Section: COSC 4301 – Section 1
  File Name: NeonarkCliApplication .java
  Purpose: This is the main file for the CLI
  for the Neonark application
*/


package org.example.neonarkcli;

import java.util.Scanner;


public class NeonarkCliApplication {

    public static void main(String[] args) {
        String csvPath = "wardens.csv";

        Scanner scanner = new Scanner(System.in);

        try {
            Menu mainmenu = new Menu(scanner, csvPath);
            mainmenu.showMenu();
        } finally {
             scanner.close();
        }

           
    }

}
