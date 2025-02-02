public class Deadline extends Task {
    private static final String DEADLINE_PREFIX = "deadline";
    private static final String TYPE_ICON = "D";
    private static final String BY_DELIMITER = "by";

    private String by;

    public Deadline() {
        super();
        this.by = "";
    }

    public Deadline(String input) {
        this(false, input);
    }

    public Deadline(boolean isDone, String input) {
        super(isDone, extractDescription(input));
        this.by = extractDeadline(input);
    }

    public String getBy() {
        return by;
    }

    public void setBy(String by) {
        this.by = by;
    }

    @Override
    public String getDescription() {
        return "[" + TYPE_ICON + "]" + super.getDescription() + " (by: " + by + ")";
    }

    private static String extractDescription(String input) {
        input = processInput(input);
        String[] parts = input.split(BY_DELIMITER);
        return parts[0].trim();
    }

    private static String extractDeadline(String input) {
        input = processInput(input);
        String[] parts = input.split(BY_DELIMITER);
        return parts.length > 1 ? parts[1].trim() : "";
    }

    private static String processInput(String input) {
        return input.replaceFirst("^" + DEADLINE_PREFIX + "\\s*", "").trim();
    }
}