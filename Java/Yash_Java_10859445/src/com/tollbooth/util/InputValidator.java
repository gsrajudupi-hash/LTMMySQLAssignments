package com.tollbooth.util;

import java.util.Scanner;

public final class InputValidator {

    private InputValidator() {
    }

    public static String readRequiredString(
            Scanner scanner,
            String message
    ) {
        while (true) {
            System.out.print(message);

            String input = scanner.nextLine().trim();

            if (!input.isBlank()) {
                return input;
            }

            System.out.println(
                    "Input cannot be empty. Please try again."
            );
        }
    }

    public static int readPositiveInteger(
            Scanner scanner,
            String message
    ) {
        while (true) {
            System.out.print(message);

            String input = scanner.nextLine().trim();

            try {
                int value = Integer.parseInt(input);

                if (value > 0) {
                    return value;
                }

                System.out.println(
                        "Value must be greater than zero."
                );

            } catch (NumberFormatException exception) {
                System.out.println(
                        "Invalid input. Enter a valid whole number."
                );
            }
        }
    }

    public static int readMenuChoice(
            Scanner scanner,
            String message,
            int minimum,
            int maximum
    ) {
        while (true) {
            System.out.print(message);

            String input = scanner.nextLine().trim();

            try {
                int choice = Integer.parseInt(input);

                if (choice >= minimum && choice <= maximum) {
                    return choice;
                }

                System.out.printf(
                        "Enter a number between %d and %d.%n",
                        minimum,
                        maximum
                );

            } catch (NumberFormatException exception) {
                System.out.println(
                        "Invalid choice. Enter a valid number."
                );
            }
        }
    }
}