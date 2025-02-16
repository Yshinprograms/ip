package clod.operations;

import clod.exceptions.ClodException;

public class Event extends Task {
    private static final String EVENT_PREFIX = "event";
    private static final String TYPE_ICON = "E";
    private static final String FROM_DELIMITER = "/from";
    private static final String TO_DELIMITER = "/to";
    private String startTime;
    private String endTime;

    public Event(String input) throws ClodException {
        super(extractEventDescription(input));
        String[] startAndEndTime = extractStartAndEndTimes(input);
        this.startTime = startAndEndTime[0];
        this.endTime = startAndEndTime[1];
    }

    @Override
    public String getTypeIcon() {
        return TYPE_ICON;
    }

    @Override
    public String getDescription() {
        return super.getDescription() + " (from: " + startTime + " to: " + endTime + ")";
    }

    private static String extractEventDescription(String input) throws ClodException {
        String[] descriptionParts = splitEventInput(input, FROM_DELIMITER);
        if (descriptionParts.length == 0 || descriptionParts[0].trim().isEmpty()) {
            throw new ClodException("If you're not gonna tell me what the event is about," +
                    " the probability of me remembering it for you goes from 37% down to 0%." +
                    "\nMaybe if you bother telling me about them it would even grow higher?");
        }
        return descriptionParts[0].trim();
    }

    private String[] extractStartAndEndTimes(String input) throws ClodException {
        String[] fromParts = splitEventInput(input, FROM_DELIMITER);
        if (isDelimiterMissingOrEmpty(fromParts)) {
            throw new ClodException("I may not be the best bot to host events, " +
                    "but we're still gonna need to get it started.  " +
                    "\n(Missing '/from' time)");
        }

        String fromToSection = fromParts[1];
        String[] toParts = splitEventInputSection(fromToSection, TO_DELIMITER);
        String start = toParts[0].trim();
        String end;

        if (toParts.length < 2 || toParts[1].trim().isEmpty()) {
            throw new ClodException("And when is this thing ever gonna end? " +
                    "\n(Missing '/to' time)");
        } else {
            end = toParts[1].trim();
        }

        return new String[]{start, end};
    }

    private static boolean isDelimiterMissingOrEmpty(String[] parts) {
        return parts.length < 2 || parts[1].trim().isEmpty();
    }

    private static String[] splitEventInput(String input, String delimiter) throws ClodException {
        String processedInput = input.replaceFirst("(?i)^" + EVENT_PREFIX + "\\s*", "").trim(); // (?i) for case-insensitive matching
        if(processedInput.isEmpty()) {
            throw new ClodException("Lazing around all day with no " +
                    "specific event descriptions sounds like my kinda thing...");
        }
        return processedInput.split(delimiter, 2);
    }

    private static String[] splitEventInputSection(String inputSection, String delimiter) {
        return inputSection.split(delimiter, 2);
    }
}