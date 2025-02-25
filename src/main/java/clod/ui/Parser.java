package clod.ui;

import clod.exceptions.ClodException;
import clod.operations.TaskList;

/**
 * The Parser class handles the processing of user input commands.
 * It interprets commands and delegates the actions to the appropriate methods in TaskList.
 */
public class Parser {
    private static final String LIST_COMMAND = "list";
    private static final String MARK_COMMAND = "mark";
    private static final String UNMARK_COMMAND = "unmark";
    private static final String TODO_COMMAND = "todo";
    private static final String DEADLINE_COMMAND = "deadline";
    private static final String EVENT_COMMAND = "event";
    private static final String DELETE_COMMAND = "delete";
    private static final String FIND_COMMAND = "find";

    /**
     * Processes the user input command and performs the associated action.
     * Splits the input into a command and its arguments, then routes to the
     * appropriate method in TaskList.
     *
     * @param userInput The raw input string from the user
     * @param taskList The TaskList to perform operations on
     * @throws ClodException If the command is invalid or if there are issues with the operation
     */
    public static void processUserCommand(String userInput, TaskList taskList) throws ClodException {
        String lowerCaseInput = userInput.toLowerCase();
        String[] words = lowerCaseInput.split("\\s+", 2); // Split input into command and arguments
        String command = words[0];
        String taskDescription;
        if (words.length > 1) {
            taskDescription = words[1];
        } else {
            taskDescription = "";
        }

        switch (command) {
        case LIST_COMMAND:
            taskList.printList();
            break;
        case MARK_COMMAND:
            taskList.markTaskAsCompleted(parseTaskIndex(taskDescription));
            break;
        case UNMARK_COMMAND:
            taskList.unmarkCompletedTask(parseTaskIndex(taskDescription));
            break;
        case TODO_COMMAND:
        case DEADLINE_COMMAND:
        case EVENT_COMMAND:
            taskList.addNewTaskToListByType(command, taskDescription);
            break;
        case DELETE_COMMAND:
            taskList.deleteTaskFromList(parseTaskIndex(taskDescription));
            break;
        case FIND_COMMAND:
            if (taskDescription.trim().isEmpty()) {
                throw new ClodException("What am I supposed to find? Your hopes and dreams?" +
                        "\nTry giving me a keyword to search for instead.");
            }
            taskList.findTasksByKeyword(taskDescription.trim());
            break;
        default:
            throw new ClodException("You're gonna want the real Claude for that..." +
                    "\nDo I look like I've got 175 billion parameters under the hood?");
        }
    }

    /**
     * Parses a string into a valid task index.
     *
     * @param taskDescription String containing the task index
     * @return The parsed integer index
     * @throws ClodException If the string cannot be parsed as a valid integer
     */
    private static int parseTaskIndex(String taskDescription) throws ClodException {
        try {
            return Integer.parseInt(taskDescription.trim());
        } catch (NumberFormatException e) {
            throw new ClodException("I may not be the smartest around, but I can still count you know..." +
                    "\nMaybe try using task numbers that are actually valid this time.");
        }
    }
}