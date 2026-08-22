package com.trainsystem.ui;

import java.util.Scanner;

public class UIHelper {

    private static final int WIDTH = 70;
    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";
    private static final String RED = "\u001B[31m";
    private static final String RESET = "\u001B[0m";

    public static void printTitle(String title) {
        System.out.println();
        // print 50 '-'
        printLine();

        int padding = (WIDTH - title.length()) / 2;
        // align the title at the center
        System.out.println(" ".repeat(padding) + title);
        printLine();
        System.out.println();
    }

    public static void printLine() {
        System.out.println("-".repeat(WIDTH));
    }

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

    public static void printSuccess(String message) {
        System.out.println(GREEN + "\n[SUCCESS] " + message + RESET);
    }

    public static void printWarning(String message) {
        System.out.println(YELLOW + "\n[WARNING]");
        System.out.println(message + RESET);
    }

    public static void printError(String message) {
        System.out.println(RED + "\n[ERROR] " + message + RESET);
    }

    public static void pause(Scanner scanner) {
        System.out.println();
        System.out.print("Press Enter to continue...");
        scanner.nextLine();
    }
}
