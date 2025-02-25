package clod.operations;

abstract public class Task {
    private boolean isDone;
    private String description;
    private String typeIcon; // Added typeIcon to Task

    public Task(String description) {
        this(false, description);
    }

    public Task(boolean isDone, String description) {
        this.isDone = isDone;
        this.description = description;
        this.typeIcon = " ";
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public String getDescription() {
        return "[" + getTypeIcon() + "] [" + getStatusIcon() + "] " + description;
    }

    public String getDescriptionWithoutIcons(){
        return description;
    }

    public String getTypeIcon() {
        return typeIcon;
    }

    protected String getStatusIcon() {
        if (isDone) {
            return "X";
        }
        return " ";
    }
}