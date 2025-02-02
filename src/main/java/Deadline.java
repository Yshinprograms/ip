public class Deadline extends Task {
    private static final String DEADLINE_PREFIX = "deadline";
    private static final String TYPE_ICON = "D";
    private static final String BY_DELIMITER = "/by";
    private String by;

    public Deadline() {
        super();
        setBy("");
    }

    public Deadline(String input) {
        this(false, input);
    }

    public Deadline(boolean isDone, String input) {
        super(isDone, extractDescription(input));
        setBy(extractDeadlineTime(input));
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
        String[] parts = input.split(BY_DELIMITER, 2);
        return parts[0].trim();
    }

    private static String extractDeadlineTime(String input) {
        input = processInput(input);
        String[] parts = input.split(BY_DELIMITER, 2);

        if (missingByDelimiter(parts)) {
            Clod.printUserPrefix(false);
            handleMissingByDelimiter();
            return "";
        }

        String deadlinePart = parts[1];
        return deadlinePart.trim();
    }

    private static boolean missingByDelimiter(String[] parts) {
        return parts.length < 2;
    }

    private static void handleMissingByDelimiter() {
        System.out.println("Error: Missing '/by' delimiter in deadline description.");
    }

    private static String processInput(String input) {
        return input.replaceFirst("^" + DEADLINE_PREFIX + "\\s*", "").trim();
    }
}