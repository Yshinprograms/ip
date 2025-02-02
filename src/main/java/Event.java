public class Event extends Task {
    private static final String EVENT_PREFIX = "event";
    private static final String TYPE_ICON = "E";
    private static final String FROM_DELIMITER = "/from";
    private static final String TO_DELIMITER = "/to";
    private String start;
    private String end;

    private void extractStartAndEnd(String input) {
        input = processInput(input);
        String[] userInput = input.split(FROM_DELIMITER, 2);
        String descriptionOnly = userInput[0].trim();

        if (missingFrom(userInput)) {
            Clod.printUserPrefix(false);
            handleMissingFrom(descriptionOnly);
            return;
        }

        String fromAndTo = userInput[1];
        String[] fromAndToSplit = fromAndTo.split(TO_DELIMITER, 2);

        this.setDescription(descriptionOnly);

        if (missingTo(fromAndToSplit)) {
            handleMissingTo(fromAndToSplit);
        } else {
            setFromAndTo(fromAndToSplit);
        }
    }

    private void setFromAndTo(String[] fromAndToSplit) {
        setStart(fromAndToSplit[0].trim());
        setEnd(fromAndToSplit[1].trim());
    }

    private void handleMissingTo(String[] fromAndToSplit) {
        Clod.printUserPrefix(false);
        System.out.println("Error: Missing '/to' delimiter after '/from' in event description.");
        String from = fromAndToSplit[0].trim();
        setStart(from);
        setEnd("");
    }

    private boolean missingTo(String[] fromAndToSplit) {
        return fromAndToSplit.length < 2;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    private void handleMissingFrom(String descriptionOnly) {
        System.out.println("Error: Missing '/from' delimiter in event description.");
        setStartAndEnd("", "");
        setDescription(descriptionOnly);
    }

    private boolean missingFrom(String[] parts) {
        return parts.length < 2;
    }


    public Event() {
        super();
        setStartAndEnd("", "");
    }

    public Event(String input) {
        super("");
        extractStartAndEnd(input);
    }

    public Event(boolean isDone, String input) {
        super(isDone, "");
        extractStartAndEnd(input);
    }

    @Override
    public String getDescription() {
        return "[" + TYPE_ICON + "]" + super.getDescription() + " (from: " + start + " to: " + end + ")";
    }

    public void setStartAndEnd(String start, String end) {
        this.start = start;
        this.end = end;
    }

    private static String processInput(String input) {
        return input.replaceFirst("^" + EVENT_PREFIX + "\\s*", "").trim();
    }
}