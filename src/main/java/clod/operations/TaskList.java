package clod.operations;

import clod.ui.Interactions;
import clod.exceptions.ClodException;
import clod.storage.Storage;

import java.io.IOException;
import java.util.List;

public class TaskList {
    private static final int LIST_LOWER_BOUND = 1;
    private static final String LIST_SEPARATOR = "==============================================";
    private final java.util.List<Task> tasks;
    private final Storage storage;

    public TaskList(Storage previouslySavedStorage) {
        this.tasks = new java.util.ArrayList<>();
        this.storage = previouslySavedStorage;
        try {
            storage.loadTasks(this);
        } catch (IOException e) {
            Interactions.printMessage("Error loading previous task list. " +
                    "This may be empty if its your first time seeing me...");
        }
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void addTaskToList(Task task) {
        tasks.add(task);
        Interactions.printMessage("Added to list: " + task.getDescription());
        saveTaskToStorage();
    }

    private void saveTaskToStorage() {
        try {
            storage.saveTasks(this);
        } catch (IOException e) {
            Interactions.printMessage("Error saving task list");
        }
    }

    public void addNewTaskToListByType(String command, String line) {
        try {
            Task newTask = createTaskByType(command, line);
            addTaskToList(newTask);
        } catch (ClodException e) {
            Interactions.printMessage(e.getMessage());
        }
    }

    public void deleteTaskFromList(int taskIndex) {
        Interactions.printMessage("Deleted task: " + tasks.get(taskIndex - 1).getDescription());
        tasks.remove(taskIndex - 1);
        saveTaskToStorage();
    }

    private Task createTaskByType(String command, String line) throws ClodException {
        switch (command) {
        case "todo":
            return new Todo(line);
        case "deadline":
            return new Deadline(line);
        case "event":
            return new Event(line);
        default:
            throw new ClodException("Unknown task type: " + command);
        }
    }

    public void markTaskAsCompleted(int taskIndex) {
        updateTaskStatus(taskIndex, true);
    }

    public void unmarkCompletedTask(int taskIndex) {
        updateTaskStatus(taskIndex, false);
    }

    private void updateTaskStatus(int taskIndex, boolean isDone) {
        if (!isValidTaskIndex(taskIndex)) {
            Interactions.printMessage("Come on, even I know that's not a valid task number.");
            return;
        }
        Task task = tasks.get(taskIndex - 1);
        task.setDone(isDone);

        if (isDone) {
            Interactions.printMessage("Congrats for finishing this... I think?" + "\n" + task.getDescription());
        } else
            Interactions.printMessage("I guess you didn't finish this after all. Well, that's life I guess." + "\n" + task.getDescription());
    }


    private boolean isValidTaskIndex(int taskIndex) {
        return taskIndex >= LIST_LOWER_BOUND && taskIndex <= tasks.size();
    }


    public void printList() {
        if (tasks.isEmpty()) {
            Interactions.printMessage("Hmm... The list seems empty. Did you say anything yet?");
            return;
        }
        System.out.println(LIST_SEPARATOR);
        Interactions.printMessage("Here's what you've said so far:");
        printTasksInOrder();
        System.out.println(LIST_SEPARATOR);
    }

    private void printTasksInOrder() {
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ". " + tasks.get(i).getDescription());
        }
    }
}