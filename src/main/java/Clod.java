import java.util.Scanner;
import java.util.ArrayList;

// Clod as a play on Claude with a Confused Purpose & Identity
public class Clod {
    public static void main(String[] args) {
        printStartMessage();
        runClod();
        printExitMessage();
    }

    public static void runClod(){
        String line = takeInput();
        ArrayList<Task> userInputList = new ArrayList<Task>();
        while (!containsBye(line)){
            if (line.toLowerCase().contains("list")){
                printList(userInputList);
            }
            else if (line.toLowerCase().contains("mark")){
                int taskNumber = getTaskNumber(line);
                printCompletedTask(taskNumber, userInputList);
            }
            else if (line.toLowerCase().contains("unmark")){
                int taskNumber = getTaskNumber(line);
                removeCompletedTask(taskNumber, userInputList);
            }
            else {
                addNewTaskToList(line, userInputList);
            }
            line = takeInput();
        }
    }

    public static void removeCompletedTask(Integer taskNumber, ArrayList<Task> userInputList){
        if (taskNumber < 1 || taskNumber > userInputList.size()){
            System.out.println("Come on, even I know that's not a valid task number.");
        }
        else {
            Task completedTask = userInputList.get(taskNumber - 1);
            completedTask.setDone(false);
            System.out.println("I guess you didn't finish this after all. Well, that's life I guess.");
            System.out.println(completedTask.getDescription());
        }
    }

    public static void printCompletedTask(Integer taskNumber, ArrayList<Task> userInputList){
        if (taskNumber < 1 || taskNumber > userInputList.size()){
            System.out.println("Come on, even I know that's not a valid task number.");
        }
        else {
            Task completedTask = userInputList.get(taskNumber - 1);
            completedTask.setDone(true);
            System.out.println("Congrats for finishing this... I think?");
            System.out.println(completedTask.getDescription());
        }
    }

    public static int getTaskNumber(String line){
        String[] words = line.split(" ");
        for (String word : words){
            try {
                return Integer.parseInt(word);
            }
            catch (NumberFormatException ignored) {}
        }
        return -1;
    }

    public static void printList(ArrayList<Task> userInputList){
        System.out.println("Here's what you've said so far:");
        int count = 1;
        for (Task addedTask : userInputList){
            System.out.print(count + ". ");
            System.out.println(addedTask.getDescription());
            count++;
        }
    }

    public static void addNewTaskToList(String line, ArrayList<Task> userInputList){
        Task newTask = new Task(false, line);
        userInputList.add(newTask);
        System.out.print("Added to list: ");
        System.out.println(newTask.getDescription());
    }

    public static boolean containsBye(String line){
        return line.toLowerCase().contains("bye");
    }

    public static String takeInput(){
        // Start new prompt on next line
        System.out.println();
        Scanner in = new Scanner(System.in);

        // Clod prompts user for input
        printUserPrefix(false);
        System.out.println("What's that? Did you say something?");

        // Save user input
        printUserPrefix(true);
        return in.nextLine();
    }

    public static void printStartMessage(){
        String logo = "┏┓┓ ┏┓┳┓\n"
                + "┃ ┃ ┃┃┃┃\n"
                + "┗┛┗┛┗┛┻┛\n";
        System.out.println("Welcome to\n" + logo);
        printUserPrefix(false);
        System.out.println("Hi, I'm Clod. Am I a helper bot? Or a... what was that word... a... thingy? I don't know.");
    }

    public static void printExitMessage(){
        printUserPrefix(false);
        System.out.println("(Confused silence) Oh, was that it? ...Hmm. Bye.");
    }

    public static void printUserPrefix(boolean isUser){
        if (isUser){
            System.out.print("You: ");
        } else {
            System.out.print("Clod: ");
        }
    }
}

