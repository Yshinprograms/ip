package clod.operations;

import clod.ui.Interactions;
import clod.exceptions.ClodException;
import clod.storage.Storage;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

/**
 * The TaskList class manages the collection of tasks and operations on them.
 * It handles adding, removing, finding, and updating tasks, as well as
 * persisting changes to the storage system.
 */
public class TaskList {
    private static final int LIST_LOWER_BOUND = 1;
    private static final String LIST_SEPARATOR = "==============================================";
    private final java.util.List<Task> tasks;
    private final Storage storage;

    /**
     * Creates a new TaskList with the specified storage system.
     * Attempts to load any previously saved tasks during initialization.
     *
     * @param previouslySavedStorage The storage system to use for persistence
     */
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

    /**
     * Returns the list of all tasks currently managed by this TaskList.
     *
     * @return An unmodifiable List of Task objects
     */
    public List<Task> getTasks() {
        return tasks;
    }

    /**
     * Adds a task to the list and persists it to storage.
     * Displays a confirmation message to the user.
     *
     * @param task The Task to add to the list
     */
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

    /**
     * Creates a new task of the specified type and adds it to the list.
     * Handles exceptions and provides feedback to the user.
     *
     * @param command The type of task to create (todo, deadline, event)
     * @param line    The description and other details for the task
     */
    public void addNewTaskToListByType(String command, String line) {
        try {
            Task newTask = createTaskByType(command, line);
            addTaskToList(newTask);
        } catch (ClodException e) {
            Interactions.printMessage(e.getMessage());
        }
    }

    /**
     * Deletes a task at the specified index from the list.
     * Updates the storage after deletion.
     *
     * @param taskIndex The 1-based index of the task to delete
     */
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

    /**
     * Marks a task as completed at the specified index.
     *
     * @param taskIndex The 1-based index of the task to mark as completed
     */
    public void markTaskAsCompleted(int taskIndex) {
        updateTaskStatus(taskIndex, true);
    }

    /**
     * Removes the completed status from a task at the specified index.
     *
     * @param taskIndex The 1-based index of the task to unmark
     */
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

    /**
     * Displays all tasks in the list to the user.
     * If the list is empty, provides appropriate feedback.
     */
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

    /**
     * Searches for tasks containing the specified keyword in their descriptions.
     * Displays a list of matching tasks to the user.
     *
     * @param keyword The search term to look for in task descriptions
     */
    public void findTasksByKeyword(String keyword) {
        if (tasks.isEmpty()) {
            Interactions.printMessage("There's nothing to search through. The list is emptier than my... well, everything.");
            return;
        }

        List<Task> matchingTasks = new ArrayList<>();
        for (Task task : tasks) {
            String description = task.getDescription().toLowerCase();
            if (description.contains(keyword.toLowerCase())) {
                matchingTasks.add(task);
            }
        }

        if (matchingTasks.isEmpty()) {
            Interactions.printMessage("Searched high and low but couldn't find '" + keyword + "'. " +
                    "Are you sure it exists? I'm not exactly known for my memory, you know.");
            return;
        }

        // Print the matching tasks
        System.out.println(LIST_SEPARATOR);
        Interactions.printMessage("Here are the matching tasks in your list:");
        for (int i = 0; i < matchingTasks.size(); i++) {
            System.out.println((i + 1) + ". " + matchingTasks.get(i).getDescription());
        }
        System.out.println(LIST_SEPARATOR);
    }
}