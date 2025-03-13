package clod.operations;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import clod.exceptions.ClodException;

/**
 * The TimeManager class handles formatting of Strings into LocalDateTime format
 * It provides methods for formatting LocalDateTime for display and storage.
 */
public class TimeManager {
    public static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
    public static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy, h:mm a");

    /**
     * Processes the date and time entered by the user and converts it into LocalDateTime
     *
     * @param dateString The raw input date string from the user
     * @return The date and time in LocalDateTime format
     * @throws ClodException If the input date string is empty
     */
    public static LocalDateTime parseDate(String dateString) throws ClodException {
        if (dateString == null || dateString.trim().isEmpty()) {
            throw new ClodException("Date string cannot be empty");
        }
        try {
            return LocalDateTime.parse(dateString.trim(), INPUT_FORMAT);
        } catch (DateTimeParseException e) {
            throw new ClodException("Invalid date format: " + dateString
                    + "\nValid format is yyyy-MM-dd HHmm\nExample: " + INPUT_FORMAT.format(LocalDateTime.now()));
        }
    }

    /**
     * Converts the saved date and time into a string for display
     *
     * @param dateTime The saved date and time in storage
     * @return The saved date and time of type String
     */
    public static String formatForDisplay(LocalDateTime dateTime) {
        if (dateTime == null) {
            throw new IllegalArgumentException("DateTime cannot be null");
        }
        return dateTime.format(OUTPUT_FORMAT);
    }

    /**
     * Converts entered date and time into type string for storage
     *
     * @param dateTime The raw date and time string entered by user
     * @return The entered date and time string in storage-friendly format
     */
    public static String formatForStorage(LocalDateTime dateTime) {
        if (dateTime == null) {
            throw new IllegalArgumentException("DateTime cannot be null");
        }
        return dateTime.format(INPUT_FORMAT);
    }
}
