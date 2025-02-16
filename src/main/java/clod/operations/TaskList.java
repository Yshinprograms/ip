package clod.operations;

import clod.Clod;
import clod.exceptions.ClodException;

public class TaskList {
    private static final int LIST_LOWER_BOUND = 1;
    private static final String LIST_SEPARATOR = "==============================================";
    private java.util.List<Task> tasks;

    public TaskList() {
        tasks = new java.util.ArrayList<>();
    }

    public void addTaskToList(Task task) {
        tasks.add(task);
        Clod.printMessage("Added to list: " + task.getDescription());
    }

    public void addNewTaskToListByType(String command, String line) {
        try {
            Task newTask = createTaskByType(command, line);
            addTaskToList(newTask);
        } catch (ClodException e) {
            Clod.printMessage(e.getMessage());
        }
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