package clod.operations;

import clod.exceptions.ClodException;

/**
 * Represents a Todo task.
 */
public class Todo extends Task {
    private static final String TODO_PREFIX = "todo";
    private static final String TYPE_ICON = "T";
    private static final String USAGE_INSTRUCTIONS = "Usage: {description}";

    /**
     * Constructs a Todo object with the given input.
     * @param input The input string containing the todo description.
     * @throws ClodException If the todo description is empty.
     */
    public Todo(String input) throws ClodException {
        super(processInput(input));
    }

    @Override
    public String getTypeIcon() {
        return TYPE_ICON;
    }

    /**
     * Processes the input string to extract the todo description.
     * @param input The input string to process.
     * @return The extracted todo description.
     * @throws ClodException If the input is empty.
     */
    private static String processInput(String input) throws ClodException {
        String processed = input.replaceFirst("(?i)^" + TODO_PREFIX + "\\s*", "").trim();
        if (processed.isEmpty()) {
            throw new ClodException("Todo description cannot be empty!\n" + USAGE_INSTRUCTIONS);
        }
        return processed;
    }
}