package clod.operations;

import java.time.LocalDateTime;

import clod.exceptions.ClodException;

/**
 * Represents an Event task.
 */
public class Event extends Task {
    private static final String EVENT_PREFIX = "event";
    private static final String TYPE_ICON = "E";
    private static final String FROM_DELIMITER = "/from";
    private static final String TO_DELIMITER = "/to";
    private static final String USAGE_INSTRUCTIONS = "Usage: {description} /from {yyyy/MM/dd hh:mm} /to {yyyy/MM/dd hh:mm}";
    private LocalDateTime from; // Changed to LocalDateTime
    private LocalDateTime to;   // Changed to LocalDateTime

    /**
     * Constructs an Event object with the given input.
     * @param input The input string containing the event description, start time, and end time.
     * @throws ClodException If the input is invalid.
     */
    public Event(String input) throws ClodException {
        super(extractEventDescription(input));
        LocalDateTime[] startAndEndTime = extractStartAndEndTimes(input);
        this.from = startAndEndTime[0];
        this.to = startAndEndTime[1];
    }

    @Override
    public String getTypeIcon() {
        return TYPE_ICON;
    }

    @Override
    public String getDescription() {
        return super.getDescription() + " (from: " + TimeManager.formatForDisplay(from) + " to: " + TimeManager.formatForDisplay(to) + ")";
    }

    // Added for Storage to get description without time
    public String getDescriptionWithoutTime() {
        return super.getDescription();
    }

    public LocalDateTime getFrom() {
        return from;
    }

    public LocalDateTime getTo() {
        return to;
    }

    public void setFrom(LocalDateTime from) {
        this.from = from;
    }

    public void setTo(LocalDateTime to) {
        this.to = to;
    }

    private static String extractEventDescription(String input) throws ClodException {
        String[] descriptionParts = splitEventInput(input, FROM_DELIMITER);
        if (descriptionParts.length == 0 || descriptionParts[0].trim().isEmpty()) {
            throw new ClodException("If you're not gonna tell me what the event is about," +
                    " the probability of me remembering it for you goes from 37% down to 0%." +
                    "\nMaybe if you bother telling me about them it would be higher?\n" +
                    USAGE_INSTRUCTIONS);
        }
        return descriptionParts[0].trim();
    }

    private LocalDateTime[] extractStartAndEndTimes(String input) throws ClodException {
        String[] fromParts = splitEventInput(input, FROM_DELIMITER);
        if (isDelimiterMissingOrEmpty(fromParts)) {
            throw new ClodException("I may not be the best bot to host events, " +
                    "but we're still gonna need to get it started.\n" +
                    USAGE_INSTRUCTIONS);
        }

        String fromToSection = fromParts[1];
        String[] toParts = splitEventInputSection(fromToSection, TO_DELIMITER);
        String startString = toParts[0].trim();
        String endString;
        LocalDateTime start;
        LocalDateTime end;


        try {
            start = TimeManager.parseDate(startString);
        } catch (ClodException e) {
            throw new ClodException("Whats the time for the event to start? \n" + e.getMessage() +
                    "\n" + USAGE_INSTRUCTIONS);
        }


        if (toParts.length < 2 || toParts[1].trim().isEmpty()) {
            throw new ClodException("And when is this thing ever gonna end?\n" +
                    USAGE_INSTRUCTIONS);
        } else {
            endString = toParts[1].trim();
            try {
                end = TimeManager.parseDate(endString);
            } catch (ClodException e) {
                throw new ClodException("Whats the time for the event to end? \n" + e.getMessage() +
                        "\n" + USAGE_INSTRUCTIONS);
            }
        }

        return new LocalDateTime[]{start, end};
    }

    private static boolean isDelimiterMissingOrEmpty(String[] parts) {
        return parts.length < 2 || parts[1].trim().isEmpty();
    }

    private static String[] splitEventInput(String input, String delimiter) throws ClodException {
        String processedInput = input.replaceFirst("(?i)^" + EVENT_PREFIX + "\\s*", "").trim(); // (?i) for case-insensitive matching
        if (processedInput.isEmpty()) {
            throw new ClodException("Lazing around all day with no " +
                    "specific event descriptions sounds like my kinda thing...\n" +
                    USAGE_INSTRUCTIONS);
        }
        return processedInput.split(delimiter, 2);
    }

    private static String[] splitEventInputSection(String inputSection, String delimiter) {
        return inputSection.split(delimiter, 2);
    }
}