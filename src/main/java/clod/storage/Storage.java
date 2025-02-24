package clod.storage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import clod.operations.Task;
import clod.operations.TaskList;
import clod.operations.Todo;
import clod.operations.Event;
import clod.operations.Deadline;
import clod.exceptions.ClodException;
import clod.ui.Interactions;
import clod.operations.TimeManager;
import java.time.LocalDateTime;

public class Storage {
    private String filePath;
    private static final String DATA_DIR = "data";
    private static final String DATA_FILE = "clod.txt";

    public static TaskList initialiseStorage() throws ClodException {
        String filePath = DATA_DIR + File.separator + DATA_FILE;
        Storage saveData = new Storage(filePath);
        return new TaskList(saveData);
    }

    public Storage(String path) throws ClodException {
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
                writer.write(task.toFileFormat());
                writer.newLine();
            }
        }
    }

    public void loadTasks(TaskList taskList) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                try {
                    Task task = convertStringToTask(line);
                    if (task != null) {
                        taskList.addTaskToList(task);
                    }
                } catch (ClodException e) {
                    Interactions.printMessage("Error reading task at line " + lineNumber + ": " + e.getMessage());
                }
            }
        }
    }

    private Task convertStringToTask(String line) throws ClodException {
        String[] parts = line.split("\\|"); 
        if (parts.length < 3) {
            throw new ClodException("Invalid task format (needs at least type, status, and description): " + line);
        }

        // Trim all parts
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
            task = new Todo(description);
            break;
        case "D":
            task = createDeadline(parts, description);
            break;
        case "E":
            if (parts.length < 5) {
                throw new ClodException("Invalid event format (missing from/to times): " + line);
            }
            task = new Event(description + " /from " + parts[3] + " /to " + parts[4]);
            break;
        default:
            throw new ClodException("Unknown task type: " + type);
        }

        task.setDone(isDone);
        return task;
    }

    private Task createDeadline(String[] parts, String description) throws ClodException {
        if (parts.length < 4) {
            throw new ClodException("Invalid deadline format (missing date): " + String.join(" | ", parts));
        }
        try {
            LocalDateTime by = TimeManager.parseDate(parts[3]);
            return new Deadline(description, by);
        } catch (ClodException e) {
            throw new ClodException("Invalid date format in storage: " + parts[3] + 
                "\nExpected format: yyyy-MM-dd HHmm (e.g., 2024-02-25 1430)");
        }
    }
}
