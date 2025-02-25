package clod.operations;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import clod.exceptions.ClodException;

// LocalDateTime represents yyyy-mm-dd HH:mm
// LocalDate represents yyyy-mm-dd
// LocalTime represents HH:mm
// DateTimeFormatter allows us to define how we want to interpret date/time strings
// parse() converts a string into date/time
// format converts date/time back into a string for display

public class TimeManager {
    public static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
    public static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy, h:mm a");

    public static LocalDateTime parseDate(String dateString) throws ClodException {
        if (dateString == null || dateString.trim().isEmpty()) {
            throw new ClodException("Date string cannot be empty");
        }
        try {
            return LocalDateTime.parse(dateString.trim(), INPUT_FORMAT);
        } catch (DateTimeParseException e) {
            throw new ClodException("Invalid date format! Please use 'yyyy-mm-dd hhmm'\n" +
                    "For example: 2024-02-25 1430 for Feb 25, 2024, 2:30 PM");
        }
    }

    public static String formatForDisplay(LocalDateTime dateTime) {
        if (dateTime == null) {
            throw new IllegalArgumentException("DateTime cannot be null");
        }
        return dateTime.format(OUTPUT_FORMAT);
    }

    public static String formatForStorage(LocalDateTime dateTime) {
        if (dateTime == null) {
            throw new IllegalArgumentException("DateTime cannot be null");
        }
        return dateTime.format(INPUT_FORMAT);
    }
}
