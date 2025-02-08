class Deadline extends Task {
    private static final String DEADLINE_PREFIX = "deadline";
    private static final String TYPE_ICON = "D";
    private static final String BY_DELIMITER = "/by";
    private String by;

    public Deadline(String input) {
        super(extractDescription(input));
        this.by = extractByTime(input);
    }

    @Override
    public String getTypeIcon() {
        return TYPE_ICON;
    }

    @Override
    public String getDescription() {
        return super.getDescription() + " (by: " + by + ")";
    }

    private static String extractDescription(String input) {
        String[] parts = splitInput(input);
        return parts[0].trim();
    }

    private String extractByTime(String input) {
        String[] parts = splitInput(input);
        if (parts.length < 2 || parts[1].trim().isEmpty()) {
            Clod.printMessage("Error: Missing or empty '/by' delimiter in deadline description.");
            return "";
        }
        return parts[1].trim();
    }


    private static String[] splitInput(String input) {
        String processedInput = input.replaceFirst("(?i)^" + DEADLINE_PREFIX + "\\s*", "").trim(); // (?i) for case-insensitive matching
        return processedInput.split(BY_DELIMITER, 2);
    }
}