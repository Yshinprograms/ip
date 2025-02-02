public class Todo extends Task {
    private static final String TODO_PREFIX = "todo";
    private static final String TYPE_ICON = "T";

    public Todo() {
        super();
    }

    public Todo(String input) {
        super(processInput(input));
    }

    public Todo(boolean isDone, String input) {
        super(isDone, processInput(input));
    }

    @Override
    public String getDescription() {
        return "[" + TYPE_ICON + "]" + super.getDescription();
    }

    private static String processInput(String input) {
        return input.replaceFirst("^" + TODO_PREFIX + "\\s*", "").trim();
    }
}
