package clod.storage;

import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.FileWriter;
import java.io.FileReader;

import clod.operations.Task;
import clod.operations.TaskList;
import clod.operations.Todo;
import clod.operations.Event;
import clod.operations.Deadline;
import clod.exceptions.ClodException;
import clod.ui.Interactions;
import clod.operations.TimeManager; // Import TimeManager

public class Storage {
    private String filePath;
    private static final String DATA_DIR = "data";
    private static final String DATA_FILE = "clod.txt";

    public static TaskList initialiseStorage () throws ClodException {
        String filePath = DATA_DIR + File.separator + DATA_FILE;
        Storage saveData = new Storage(filePath);
        return new TaskList(saveData);
    }

    public Storage(String path) throws ClodException {
        // Saves data/clod.txt for later use
        this.filePath = path;
        File dataFile = new File(path);
        File dataDirectory = dataFile.getParentFile();

        if (!dataDirectory.exists()) {
            try {
                dataDirectory.mkdirs();
                Interactions.printMessage("Directory created: " + dataDirectory.getAbsolutePath());
            } catch (SecurityException e) {
                throw new ClodException("Error creating data directory: " + e.getMessage());
            }
        }

        if (!dataFile.exists()) {
            try {
                dataFile.createNewFile();
                Interactions.printMessage("Created data file: " + dataFile.getAbsolutePath());
            } catch (IOException e) {
                throw new ClodException("Error creating data file: " + e.getMessage());
            }
        }
    }

    public void saveTasks(TaskList taskList) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Task task : taskList.getTasks()) {
                writer.write(convertTaskToString(task));
                writer.newLine();
            }
        }
    }

    public void loadTasks(TaskList taskList) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    Task task = convertStringToTask(line);
                    taskList.addTaskToList(task);
                } catch (ClodException e) {
                    Interactions.printMessage("Error reading task: " + e.getMessage());
                }
            }
        }
    }

    // Every time we save Task to storage, we call this function
    private String convertTaskToString(Task task) {
        StringBuilder sb = new StringBuilder();
        String typeIcon = task.getTypeIcon();
        String isDone;
        if (task.isDone()) isDone = "1";
        else isDone = "0";

        // Basic task info
        sb.append(typeIcon).append(" | ").append(isDone).append(" | ");

        // We need to initialise the various subclasses again here because we cannot access their methods through the Task class
        if (task instanceof Deadline) {
            Deadline deadline = (Deadline) task;
            sb.append(deadline.getDescriptionWithoutIcons()).append(" | ").append(TimeManager.formatForStorage(deadline.getBy()));
        } else if (task instanceof Event) {
            Event event = (Event) task;
            sb.append(event.getDescriptionWithoutIcons()).append(" | ").append(TimeManager.formatForStorage(event.getFrom())).append(" | ").append(TimeManager.formatForStorage(event.getTo())); // Use getDescriptionWithoutTime()
        } else { // Todo
            sb.append(task.getDescription()); // For Todo, getDescription() is fine
        }

        return sb.toString();
    }

    private Task convertStringToTask(String line) throws ClodException {
        String[] parts = line.split("\\|");
        if (parts.length < 3) {
            throw new ClodException("Invalid task format: " + line);
        }

        // Trim all parts so that there are no whitespaces during processing
        for (int i = 0; i < parts.length; i++) {
            parts[i] = parts[i].trim();
        }

        // Get task type and done status
        String type = parts[0];
        boolean isDone = parts[1].equals("1");
        String description = parts[2];

        Task task;
        switch (type) {
        case "T":
            task = new Todo("todo " + description);
            break;
        case "D":
            if (parts.length < 4) {
                throw new ClodException("Invalid deadline format: " + line);
            }
            String byTimeString = parts[3];
            task = new Deadline("deadline " + description + " /by " + byTimeString);
            ((Deadline) task).setBy(TimeManager.parseDate(byTimeString)); // Parse and set LocalDateTime
            break;
        case "E":
            if (parts.length < 5) {
                throw new ClodException("Invalid event format: " + line);
            }
            String fromTimeString = parts[3];
            String toTimeString = parts[4];
            task = new Event("event " + description + " /from " + fromTimeString + " /to " + toTimeString);
            ((Event) task).setFrom(TimeManager.parseDate(fromTimeString)); // Parse and set LocalDateTime
            ((Event) task).setTo(TimeManager.parseDate(toTimeString));     // Parse and set LocalDateTime
            break;
        default:
            throw new ClodException("Unknown task type: " + type);
        }

        task.setDone(isDone);
        return task;
    }
}