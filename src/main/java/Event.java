class Event extends Task {
    private static final String EVENT_PREFIX = "event";
    private static final String TYPE_ICON = "E";
    private static final String FROM_DELIMITER = "/from";
    private static final String TO_DELIMITER = "/to";
    private String startTime;
    private String endTime;

    public Event(String input) {
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

    private static String extractEventDescription(String input) {
        String[] descriptionParts = splitEventInput(input, FROM_DELIMITER);
        return descriptionParts[0].trim();
    }

    private String[] extractStartAndEndTimes(String input) {
        String[] fromParts = splitEventInput(input, FROM_DELIMITER);
        if (isDelimiterMissingOrEmpty(fromParts)) {
            Clod.printMessage("Error: Missing or empty '/from' delimiter in event description.");
            return new String[]{"", ""}; // Default empty start and end
        }

        String fromToSection = fromParts[1];
        String[] toParts = splitEventInputSection(fromToSection, TO_DELIMITER);
        String start = toParts[0].trim();
        String end;
        if (toParts.length > 1) {
            end = toParts[1].trim();
        } else {
            end = "";
        }


        if (end.isEmpty()) {
            Clod.printMessage("Warning: Missing or empty '/to' delimiter, assuming event has no end time.");
        }
        return new String[]{start, end};
    }

    private static boolean isDelimiterMissingOrEmpty(String[] parts) {
        return parts.length < 2 || parts[1].trim().isEmpty();
    }

    private static String[] splitEventInput(String input, String delimiter) {
        String processedInput = input.replaceFirst("(?i)^" + EVENT_PREFIX + "\\s*", "").trim(); // (?i) for case-insensitive matching
        return processedInput.split(delimiter, 2);
    }

    private static String[] splitEventInputSection(String inputSection, String delimiter) {
        return inputSection.split(delimiter, 2);
    }
}