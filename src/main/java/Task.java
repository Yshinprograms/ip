public class Task {
    private boolean isDone = false;
    private String description;

    public Task() {
        setDone(false);
        setDescription("");
    }

    public Task(boolean isDone, String description) {
        setDone(isDone);
        setDescription(description);
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
        setDescription(description);
    }

    public String getDescription() {
        String checkMark;
        if (isDone) {
            checkMark = "X";
        }
        else{
            checkMark = " ";
        }
        return "[" + checkMark + "] " + description;
    }

    public void setDescription(String newDescription) {
        description = newDescription;
    }
}
