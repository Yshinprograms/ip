class Todo extends Task {
    private static final String TODO_PREFIX = "todo";
    private static final String TYPE_ICON = "T";

    public Todo(String input) {
        super(processInput(input));
    }

    @Override
    public String getTypeIcon() {
        return TYPE_ICON;
    }

    private static String processInput(String input) {
        return input.replaceFirst("(?i)^" + TODO_PREFIX + "\\s*", "").trim(); // (?i) for case-insensitive matching
    }
}