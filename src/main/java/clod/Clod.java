package clod;

import java.util.Scanner;

import clod.ui.Parser;
import clod.ui.Interactions;
import clod.exceptions.ClodException;
import clod.operations.TaskList;
import clod.storage.Storage;

public class Clod {
    public static void main(String[] args) {
        try {
            TaskList taskList = Storage.initialiseStorage();
            Interactions.printStartMessage();
            runClod(taskList);
            Interactions.printExitMessage();
        } catch (ClodException e) {
            Interactions.printMessage("Error starting Clod: " + e.getMessage());
        }
    }

    public static void runClod(TaskList taskList) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            try {
                String userInput = Interactions.takeUserInput(scanner);
                if (Interactions.containsBye(userInput)) {
                    break;
                }
                Parser.processUserCommand(userInput, taskList);
            } catch (ClodException e) {
                Interactions.printMessage(e.getMessage());
            }
        }
        scanner.close();
    }
}