package clod.operations;

import java.time.LocalDateTime;

import clod.exceptions.ClodException;

/**
 * Represents a Deadline task.
 */
public class Deadline extends Task {
    private static final String DEADLINE_PREFIX = "deadline";
    private static final String TYPE_ICON = "D";
    private static final String BY_DELIMITER = "/by";
    private static final String USAGE_INSTRUCTIONS = "Usage: {description} /by {yyyy/MM/dd hh:mm}";
    private LocalDateTime by;

    /**
     * Constructs a Deadline object with the given input.
     * @param input The input string containing the deadline description and time.
     * @throws ClodException If the input is invalid.
     */
    public Deadline(String input) throws ClodException {
        super(extractDescription(input));
        this.by = extractByTime(input);
    }

    public void setBy(LocalDateTime by) {
        this.by = by;
    }

    public LocalDateTime getBy() {
        return by;
    }

    /**
     * Returns a description of the saved deadline task only, without time included
     *
     * @return A formatted deadline description without time
     */
    public String getDescriptionWithoutTime(){
        return super.getDescription();
    }

    /**
     * Returns type icon for deadline
     *
     * @return 'D' for the deadline icon
     */
    @Override
    public String getTypeIcon() {
        return TYPE_ICON;
    }

    /**
     * Returns a description of the saved deadline task
     *
     * @return A formatted deadline description with time
     */
    @Override
    public String getDescription() {
        return super.getDescription() + " (by: " + TimeManager.formatForDisplay(by) + ")";
    }

    private static String extractDescription(String input) throws ClodException {
        String[] parts = splitInput(input);
        if (parts.length < 1 || parts[0].trim().isEmpty()) {
            throw new ClodException("Wow. A deadline for nothing? That's beyond even me." +
                    "\nCome on, if you're gonna try, at least come up with a reasonable description.\n" +
                    USAGE_INSTRUCTIONS);
        }
        return parts[0].trim();
    }

    private LocalDateTime extractByTime(String input) throws ClodException {
        String[] parts = splitInput(input);
        if (parts.length < 2 || parts[1].trim().isEmpty()) {
            throw new ClodException("A deadline without a DEADLINE huh? " +
                    "You've truly outdone yourself this time." +
                    "\nMaybe try setting a REAL deadline this time.\n" +
                    USAGE_INSTRUCTIONS);
        }

        return TimeManager.parseDate(parts[1].trim());
    }


    private static String[] splitInput(String input) {
        String processedInput = input.replaceFirst("(?i)^" + DEADLINE_PREFIX + "\\s*", "").trim(); // (?i) for case-insensitive matching
        return processedInput.split(BY_DELIMITER, 2);
    }
}