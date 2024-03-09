package org.javarush.view;

import java.util.Scanner;

public class ConsoleViewProvider {
    private final Scanner scanner;

    public ConsoleViewProvider() {
        this.scanner = new Scanner(System.in);
    }

    public String readInput() {
        return scanner.nextLine();
    }

    public void printOutput(String output) {
        System.out.println(output);
    }

    public int readInt() {
        return scanner.nextInt();
    }

    public void closeScanner() {
        scanner.close();
    }
}
