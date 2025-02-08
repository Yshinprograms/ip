package clod.operations;

import clod.exceptions.ClodException;

class Todo extends Task {
    private static final String TODO_PREFIX = "todo";
    private static final String TYPE_ICON = "T";

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
            throw new ClodException("Bruh, you want me to create a ToDo without anything in it?");
        }
        return processed;
    }
}