package clod;

import java.util.Scanner;

import clod.ui.Parser;
import clod.ui.Interactions;
import clod.exceptions.ClodException;
import clod.operations.TaskList;
import clod.storage.Storage;

/**
 * The main class for the Clod task management application.
 * Clod is a text-based task management system with a distinctive personality.
 */
public class Clod {
    /**
     * The entry point of the application.
     * Initializes storage, displays a welcome message, and starts the main loop.
     *
     * @param args Command line arguments (not used)
     */
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

    /**
     * Runs the Clod application.
     * Continuously accepts user commands until the user exits.
     *
     * @param taskList The TaskList to perform operations on
     */
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
