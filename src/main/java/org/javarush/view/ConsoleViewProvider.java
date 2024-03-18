package org.javarush.view;

import lombok.AllArgsConstructor;
import org.javarush.app.AppConfig;
import org.javarush.command.Command;

import java.util.Scanner;

@AllArgsConstructor
public class ConsoleViewProvider {
    private final Scanner scanner;

    public String readInput() {
        return scanner.nextLine();
    }

    public void printOutput(String output) {
        System.out.println(output);
    }

    public void printCommands() {
        printOutput("Available commands:");
        for (Command command : AppConfig.getCommands().values()) {
            printOutput("'" + command.getTarget() + "'" + " - " + command.getDescription());
        }
    }

    public void closeScanner() {
        scanner.close();
    }
}
