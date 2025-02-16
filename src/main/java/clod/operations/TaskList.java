package clod.operations;

import clod.Clod;
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
            Clod.printMessage("Error loading previous task list. " +
                    "This may be empty if its your first time seeing me...");
        }
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void addTaskToList(Task task) {
        tasks.add(task);
        Clod.printMessage("Added to list: " + task.getDescription());
        saveTaskToStorage();
    }

    private void saveTaskToStorage() {
        try {
            storage.saveTasks(this);
        } catch (IOException e) {
            Clod.printMessage("Error saving task list");
        }
    }

    public void addNewTodoToList(String line) {
        try {
            addTaskToList(new Todo(line));
        } catch (ClodException e) {
            Clod.printMessage(e.getMessage());
        }
    }

    public void addNewDeadlineToList(String line) {
        try {
            addTaskToList(new Deadline(line));
        } catch (Exception e) {
            Clod.printMessage(e.getMessage());
        }
    }

    public void addNewEventToList(String line) {
        try {
            addTaskToList(new Event(line));
        } catch (Exception e) {
            Clod.printMessage(e.getMessage());
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
            Clod.printMessage("Come on, even I know that's not a valid task number.");
            return;
        }
        Task task = tasks.get(taskIndex - 1);
        task.setDone(isDone);

        if (isDone) {
            Clod.printMessage("Congrats for finishing this... I think?" + "\n" + task.getDescription());
        } else
            Clod.printMessage("I guess you didn't finish this after all. Well, that's life I guess." + "\n" + task.getDescription());
    }


    private boolean isValidTaskIndex(int taskIndex) {
        return taskIndex >= LIST_LOWER_BOUND && taskIndex <= tasks.size();
    }


    public void printList() {
        if (tasks.isEmpty()) {
            Clod.printMessage("Hmm... The list seems empty. Did you say anything yet?");
            return;
        }
        System.out.println(LIST_SEPARATOR);
        Clod.printMessage("Here's what you've said so far:");
        printTasksInOrder();
        System.out.println(LIST_SEPARATOR);
    }

    private void printTasksInOrder() {
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ". " + tasks.get(i).getDescription());
        }
    }
}