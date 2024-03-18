package org.javarush.app;

import org.javarush.command.Command;
import org.javarush.view.ConsoleViewProvider;

import java.util.Scanner;

import static java.util.Objects.isNull;

public class Runner {
    public void run() {
        final ConsoleViewProvider console = new ConsoleViewProvider(new Scanner(System.in));
        String userInput;
        Command command;

        boolean isRunning = true;
        while (isRunning) {
            console.printCommands();

            try {
                userInput = console.readInput();
                command = AppConfig.getCommand(userInput.toUpperCase());

                if (!isNull(command)) {
                    command.execute();
                    isRunning = false;
                }
            } catch (IllegalArgumentException e) {
                console.printOutput("Invalid command");
            }
        }

        console.printOutput("Exiting the app.");
        console.closeScanner();
    }
}
