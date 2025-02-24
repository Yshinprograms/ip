package clod.operations;

import java.time.LocalDateTime;
import clod.exceptions.ClodException;

public class Deadline extends Task {
    private static final String DEADLINE_PREFIX = "deadline";
    private static final String TYPE_ICON = "D";
    private static final String BY_DELIMITER = "/by";
    private String description;
    private LocalDateTime by;

    public Deadline(String input) throws ClodException {
        super(extractDescription(input));
        this.by = extractByTime(input);
    }

    public Deadline(String description, LocalDateTime by) {
        super(description);
        if (by == null) {
            throw new IllegalArgumentException("Deadline date cannot be null");
        }
        this.by = by;
    }

    @Override
    public String getTypeIcon() {
        return TYPE_ICON;
    }

    @Override
    public String getDescription() {
        return description + " (by: " + TimeManager.formatForDisplay(by) + ")";
    }

    @Override
    public String toString() {
        return "[" + TYPE_ICON + "]" + "[" + getStatusIcon() + "] " + getDescription();
    }

    @Override
    public String toFileFormat() {
        return TYPE_ICON + " | " + (isDone() ? "1" : "0") + " | " + description + " | " + TimeManager.formatForStorage(by);
    }

    private static String extractDescription(String input) throws ClodException {
        String[] parts = splitInput(input);
        if (parts.length < 1 || parts[0].trim().isEmpty()) {
            throw new ClodException("Wow. A deadline for nothing? That's beyond even me.\n" +
                    "Come on, if you're gonna try, at least come up with a reasonable description.");
        }
        return parts[0].trim();
    }

    private LocalDateTime extractByTime(String input) throws ClodException {
        String[] parts = splitInput(input);
        if (parts.length < 2 || parts[1].trim().isEmpty()) {
            throw new ClodException("A deadline without a DEADLINE huh? " +
                    "You've truly outdone yourself this time.\n" +
                    "Maybe try setting a REAL deadline this time.\n" +
                    "(Try using /by)");
        }

        return TimeManager.parseDate(parts[1].trim());
    }

    private static String[] splitInput(String input) {
        String processedInput = input.replaceFirst("(?i)^" + DEADLINE_PREFIX + "\\s*", "").trim();
        return processedInput.split(BY_DELIMITER, 2);
    }

    public LocalDateTime getBy() {
        return by;
    }
}