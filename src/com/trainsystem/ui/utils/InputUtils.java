package com.trainsystem.ui.utils;

import java.util.Scanner;

public class InputUtils {

    // get user choice, include validation checking
    public static int getMenuChoice(Scanner scanner, int max) {
        while (true) {
            System.out.print("Enter choice: ");

            if (scanner.hasNextInt()) {

                int choice = scanner.nextInt();
                // clear newline
                scanner.nextLine();

                // 0 will always be the min choice of the menu
                if (choice >= 0 && choice <= max) {
                    return choice;
                }
            } else {
                // clear invalid input
                scanner.nextLine();
            }

            System.out.println(
                    "Invalid choice. Please enter "
                            + 0 + " to " + max + "."
            );
        }
    }

    public static boolean getConfirmation(Scanner scanner, String message) {

        while (true) {
            System.out.print(message + " [Y/N] : ");

            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("Y")) {
                return true;
            }

            if (input.equalsIgnoreCase("N")) {
                return false;
            }

            System.out.println("Please enter Y or N.");
        }
    }

    public static int getPositiveInteger(Scanner scanner, String message) {
        while (true) {
            System.out.print(message);

            // get the input as a String
            String input = scanner.nextLine().trim();

            try {
                // convert to integer
                int duration = Integer.parseInt(input);

                if (duration > 0) {
                    // if non-negative
                    return duration;
                }

                // if negative
                ConsoleUtils.printError("Duration must be greater than 0.");

            } catch (NumberFormatException e) {
                // the duration entered contain non-digit character
                ConsoleUtils.printError("Please enter a valid whole number.");
            }
        }
    }

    public static double getPositiveDouble(Scanner scanner, String message) {
        while (true) {
            System.out.print(message);

            // get input as a String
            String input = scanner.nextLine().trim();

            try {
                // convert to double
                double fare = Double.parseDouble(input);

                // if non-negative value return it
                if (fare > 0) {
                    return fare;
                }

                // if is a negative value
                ConsoleUtils.printError("Ticket fare must be greater than RM 0.");

            } catch (NumberFormatException e) {
                // if the ticket fare contains non-digit character
                ConsoleUtils.printError("Please enter a valid ticket fare.");
            }
        }
    }
}
