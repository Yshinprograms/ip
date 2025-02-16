package clod.storage;

import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.FileWriter;
import java.io.FileReader;

import clod.Clod;
import clod.operations.Task;
import clod.operations.TaskList;
import clod.operations.Todo;
import clod.operations.Event;
import clod.operations.Deadline;
import clod.exceptions.ClodException;

public class Storage {
    private String filePath;

    public Storage(String path) throws ClodException {
        // Saves data/clod.txt for later use
        this.filePath = path;
        File dataFile = new File(path);
        File dataDirectory = dataFile.getParentFile();

        if (!dataDirectory.exists()) {
            try {
                dataDirectory.mkdirs();
                Clod.printMessage("Directory created: " + dataDirectory.getAbsolutePath());
            } catch (SecurityException e) {
                throw new ClodException("Error creating data directory: " + e.getMessage());
            }
        }

        if (!dataFile.exists()) {
            try {
                dataFile.createNewFile();
                Clod.printMessage("Created data file: " + dataFile.getAbsolutePath());
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
                    if (task != null) {
                        taskList.addTaskToList(task);
                    }
                } catch (ClodException e) {
                    Clod.printMessage("Error reading task: " + e.getMessage());
                }
            }
        }
    }

    private String convertTaskToString(Task task) {
        StringBuilder sb = new StringBuilder();
        String typeIcon = task.getTypeIcon();
        String isDone;
        if (task.isDone()) isDone = "1";
        else isDone = "0";

        // Split the description to handle the different parts
        String description = task.getDescription();

        // Basic task info
        sb.append(typeIcon).append(" | ").append(isDone).append(" | ");

        // Extract the main description (remove type and status icons)
        String mainDesc = description.substring(description.indexOf("]") + 1); // Skip first ]
        mainDesc = mainDesc.substring(mainDesc.indexOf("]") + 1).trim(); // Skip second ]

        // Handle different task types
        if (typeIcon.equals("D")) {
            // For Deadline, split at (by:
            String[] parts = mainDesc.split("\\(by:");
            sb.append(parts[0].trim());
            if (parts.length > 1) {
                sb.append(" | ").append(parts[1].replace(")", "").trim());
            }
        } else if (typeIcon.equals("E")) {
            // For Event, split at (from:
            String[] parts = mainDesc.split("\\(from:");
            sb.append(parts[0].trim());
            if (parts.length > 1) {
                String[] timeParts = parts[1].split("to:");
                sb.append(" | ").append(timeParts[0].trim());
                if (timeParts.length > 1) {
                    sb.append(" | ").append(timeParts[1].replace(")", "").trim());
                }
            }
        } else {
            // For Todo, just use the description
            sb.append(mainDesc);
        }

        return sb.toString();
    }

    private Task convertStringToTask(String line) throws ClodException {
        String[] parts = line.split("\\|");
        if (parts.length < 3) {
            throw new ClodException("Invalid task format: " + line);
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
            task = new Todo("todo " + description);
            break;
        case "D":
            if (parts.length < 4) {
                throw new ClodException("Invalid deadline format: " + line);
            }
            task = new Deadline("deadline " + description + " /by " + parts[3]);
            break;
        case "E":
            if (parts.length < 5) {
                throw new ClodException("Invalid event format: " + line);
            }
            task = new Event("event " + description + " /from " + parts[3] + " /to " + parts[4]);
            break;
        default:
            throw new ClodException("Unknown task type: " + type);
        }

        task.setDone(isDone);
        return task;
    }
}
