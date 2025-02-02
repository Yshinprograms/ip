public class Task {
    protected boolean isDone;
    protected String description;

    public Task() {
        this("");
    }

    public Task(String description) {
        this(false, description);
    }

    public Task(boolean isDone, String description) {
        this.isDone = isDone;
        this.description = description;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public String getDescription() {
        return "[" + getStatusIcon() + "] " + description;
    }

    public String getBaseDescription() {
        return description;
    }

    protected String getStatusIcon() {
        if (isDone) {
            return "X";
        } else {
            return " ";
        }
    }

    protected void setDescription(String description) {
        this.description = description;
    }
}