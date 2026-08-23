package com.trainsystem.ui.utils;

import java.util.Scanner;

public class ConsoleUtils {

    private static final int WIDTH = 70;
    private static final String BLUE = "\u001B[34m";
    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";
    private static final String RED = "\u001B[31m";
    private static final String RESET = "\u001B[0m";

    // title
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

    // print message
    public static void printInfo(String message) {
        System.out.println(BLUE + "\n[INFO] " + message + RESET);
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

    // pause the screen before go to other screen
    public static void pause(Scanner scanner) {
        System.out.println();
        System.out.print("Press Enter to continue...");
        scanner.nextLine();
    }

    /**
     * convert minutes duration to hours and minutes form
     * @param minutes duration in minutes
     * @return duration in hours and minutes in String format
     */
    public static String formatDuration(int minutes) {

        // calculate the hours and minutes
        int hours = minutes / 60;
        int remainingMinutes = minutes % 60;

        if (hours == 0) {
            return remainingMinutes + " min";
        }

        if (remainingMinutes == 0) {
            return hours + "h";
        }

        return hours + "h " + remainingMinutes + "min";
    }
}
