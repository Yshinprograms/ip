package clod.operations;

import clod.exceptions.ClodException;

public class Todo extends Task {
    private static final String TODO_PREFIX = "todo";
    private static final String TYPE_ICON = "T";
    private static final String USAGE_INSTRUCTIONS = "Usage: {description}";

    public Todo(String input) throws ClodException {
        super(processInput(input));
    }

    @Override
    public String getTypeIcon() {
        return TYPE_ICON;
    }

    private static String processInput(String input) throws ClodException {
        String processed = input.replaceFirst("(?i)^" + TODO_PREFIX + "\\s*", "").trim();
        if (processed.isEmpty()) {
            throw new ClodException("Todo description cannot be empty!\n" + USAGE_INSTRUCTIONS);
        }
        return processed;
    }
}