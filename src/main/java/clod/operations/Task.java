package clod.operations;

/**
 * The abstract base class for all types of tasks.
 * Provides common properties and methods for task management.
 */
abstract public class Task {
    private boolean isDone;
    private String description;
    private String typeIcon;

    /**
     * Creates a new task with the specified description.
     * The task is initially marked as not done.
     *
     * @param description The task description
     */
    public Task(String description) {
        this(false, description);
    }

    /**
     * Creates a new task with the specified description and completion status.
     *
     * @param isDone Whether the task is completed
     * @param description The task description
     */
    public Task(boolean isDone, String description) {
        this.isDone = isDone;
        this.description = description;
        this.typeIcon = " ";
    }

    /**
     * Returns whether the task is marked as done.
     *
     * @return true if the task is completed, false otherwise
     */
    public boolean isDone() {
        return isDone;
    }

    /**
     * Updates the completion status of the task.
     *
     * @param done The new completion status
     */
    public void setDone(boolean done) {
        isDone = done;
    }

    /**
     * Returns a formatted string representation of the task.
     * Includes the task type, completion status, and description.
     *
     * @return A formatted task description
     */
    public String getDescription() {
        return "[" + getTypeIcon() + "] [" + getStatusIcon() + "] " + description;
    }

    /**
     * Returns the icon representing the task type.
     * Each task type has a unique icon.
     *
     * @return A string representing the task type
     */
    public String getTypeIcon() {
        return typeIcon;
    }

    /**
     * Returns the icon representing the task's completion status.
     *
     * @return "X" if the task is completed, a space otherwise
     */
    protected String getStatusIcon() {
        if (isDone) {
            return "X";
        }
        return " ";
    }
}