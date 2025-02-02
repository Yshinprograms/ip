import java.util.ArrayList;

class TaskList {
    public static final int LISTLOWERBOUND = 1;
    private ArrayList<Task> tasks;

    public TaskList() {
        tasks = new ArrayList<Task>();
    }

    public void addNewTaskToList(String line) {
        Task newTask = new Task(line);
        tasks.add(newTask);
        System.out.print("Added to list: " + newTask.getDescription() + '\n');
    }

    public void removeCompletedTask(Integer taskNumber) {
        if (outOfListBounds(taskNumber)) {
            System.out.println("Come on, even I know that's not a valid task number.");
            return;
        }
        Task task = tasks.get(taskNumber - 1);
        task.setDone(false);
        System.out.println("I guess you didn't finish this after all. Well, that's life I guess.");
        System.out.println(task.getDescription());
    }

    public void markTaskAsCompleted(Integer taskNumber) {
        if (outOfListBounds(taskNumber)) {
            System.out.println("Come on, even I know that's not a valid task number.");
            return;
        }
        Task task = tasks.get(taskNumber - 1);
        task.setDone(true);
        System.out.println("Congrats for finishing this... I think?");
        System.out.println(task.getDescription());
    }

    private boolean outOfListBounds(Integer taskNumber) {
        return taskNumber < LISTLOWERBOUND || taskNumber > tasks.size();
    }

    public int getTaskNumber(String line) {
        String[] words = line.split(" ");
        for (String word : words) {
            try {
                return Integer.parseInt(word);
            } catch (NumberFormatException ignored) {
            }
        }
        return -1;
    }

    public void printList() {
        if (tasks.isEmpty()) {
            System.out.println("Hmm... The list seems empty. Did you say anything yet?");
            return;
        }
        System.out.println("Here's what you've said so far:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ". " + tasks.get(i).getDescription());
        }
    }
}