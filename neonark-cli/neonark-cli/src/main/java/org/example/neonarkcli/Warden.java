package org.example.neonarkcli;
import java.time.LocalDate;
import java.util.Scanner;



public class Warden {
        private  String firstName;
        private  String lastName;
        private String pidType;
        private String  pid;
        private String email;
        private String role;
        private String status;
        private String level;
        private LocalDate startDate;
        private LocalDate endDate;


        public Warden(String firstName, String lastName, String pidType, String pid,
                      String email, String role, String level, String status,
                      LocalDate startDate, LocalDate endDate) {
            this.firstName = firstName;
            this.lastName = (lastName == null || lastName.isEmpty()) ? "" : lastName;
            this.pidType = pidType;
            this.pid = pid;
            this.email = email;
            this.role = role;
            this.level = level;
            this.status = status;
            this.startDate = startDate;
            this.endDate = endDate;
        }

        // need to finish toString method for Warden Values
        @Override
        public String toString() {
            return String.format(
                    "%-12s | %-12s | %-5s: %-10s | %-25s | %-10s | %-8s | %-10s | Start: %-10s | End: %-10s",
                    firstName,
                    (lastName.isEmpty() ? "---" : lastName), // Display dashes if last name is missing
                    pidType,
                    pid,
                    email,
                    role,
                    level,
                    status,
                    (startDate != null ? startDate.toString() : "N/A"),
                    (endDate != null ? endDate.toString() : "OPEN")
            );
        }
        public String getId() {
            return pid;
        }


}
