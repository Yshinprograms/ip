package clod.operations;

import clod.exceptions.ClodException;

class Deadline extends Task {
    private static final String DEADLINE_PREFIX = "deadline";
    private static final String TYPE_ICON = "D";
    private static final String BY_DELIMITER = "/by";
    private String by;

    public Deadline(String input) throws ClodException {
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

    private static String extractDescription(String input) throws ClodException {
        String[] parts = splitInput(input);
        if (parts.length < 1 || parts[0].trim().isEmpty()) {
            throw new ClodException("Wow. A deadline for nothing? That's beyond even me." +
                    "\nCome on, if you're gonna try, at least come up with a reasonable description.");
        }
        return parts[0].trim();
    }

    private String extractByTime(String input) throws ClodException {
        String[] parts = splitInput(input);
        if (parts.length < 2 || parts[1].trim().isEmpty()) {
            throw new ClodException("A deadline without a DEADLINE huh? " +
                    "You've truly outdone yourself this time." +
                    "\nMaybe try setting a REAL deadline this time." +
                    "\n(Try using /by)");
        }
        return parts[1].trim();
    }


    private static String[] splitInput(String input) {
        String processedInput = input.replaceFirst("(?i)^" + DEADLINE_PREFIX + "\\s*", "").trim(); // (?i) for case-insensitive matching
        return processedInput.split(BY_DELIMITER, 2);
    }
}