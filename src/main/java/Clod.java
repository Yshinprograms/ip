import java.util.Scanner;
import java.util.ArrayList;

// Clod as a play on Claude with a Confused Purpose & Identity
public class Clod {
    public static final int LISTLOWERBOUND = 1;

    public static void main(String[] args) {
        printStartMessage();
        runClod();
        printExitMessage();
    }

    public static void runClod() {
        String userInput = takeUserInput();
        ArrayList<Task> listOfUserInputs = new ArrayList<Task>();

        while (!containsBye(userInput)) {
            processUserCommand(userInput, listOfUserInputs);
            userInput = takeUserInput();
        }
    }

    private static void processUserCommand(String userInput, ArrayList<Task> listOfUserInputs) {
        String lowerCaseUserInput = userInput.toLowerCase();
        String userCommand = parseCommand(lowerCaseUserInput);

        switch (userCommand) {
        case "list":
            printList(listOfUserInputs);
            break;
        case "mark":
            int taskNumber = getTaskNumber(userInput);
            printCompletedTask(taskNumber, listOfUserInputs);
            break;
        case "unmark":
            int taskNumber2 = getTaskNumber(userInput);
            removeCompletedTask(taskNumber2, listOfUserInputs);
            break;
        default:
            addNewTaskToList(userInput, listOfUserInputs);
            break;
        }
    }

    private static String parseCommand(String lowerCaseUserInput) {
        if (lowerCaseUserInput.contains("list")) {
            return "list";
        } else if (lowerCaseUserInput.contains("mark")) {
            return "mark";
        } else if (lowerCaseUserInput.contains("unmark")) {
            return "unmark";
        }
        return "add";
    }

    public static void removeCompletedTask(Integer taskNumber, ArrayList<Task> userInputList) {
        if (outOfListBounds(taskNumber, userInputList)) {
            System.out.println("Come on, even I know that's not a valid task number.");
        }
        Task completedTask = userInputList.get(taskNumber - 1);
        completedTask.setDone(false);
        System.out.println("I guess you didn't finish this after all. Well, that's life I guess.");
        System.out.println(completedTask.getDescription());
    }

    private static boolean outOfListBounds(Integer taskNumber, ArrayList<Task> userInputList) {
        // Task numbers are 1 to list size
        return taskNumber < LISTLOWERBOUND || taskNumber > userInputList.size();
    }

    public static void printCompletedTask(Integer taskNumber, ArrayList<Task> userInputList) {
        if (outOfListBounds(taskNumber, userInputList)) {
            System.out.println("Come on, even I know that's not a valid task number.");
        } else {
            Task completedTask = userInputList.get(taskNumber - 1);
            completedTask.setDone(true);
            System.out.println("Congrats for finishing this... I think?" + '\n' + completedTask.getDescription());
        }
    }

    public static int getTaskNumber(String line) {
        String[] words = line.split(" ");
        for (String word : words) {
            try {
                return Integer.parseInt(word);
            } catch (NumberFormatException ignored) {
            }
        }
        return -1;
    }

    public static void printList(ArrayList<Task> userInputList) {
        System.out.println("Here's what you've said so far:");
        int count = 1;
        for (Task addedTask : userInputList) {
            System.out.print(count + ". " + addedTask.getDescription() + '\n');
            count++;
        }
    }

    public static void addNewTaskToList(String line, ArrayList<Task> userInputList) {
        Task newTask = new Task(false, line);
        userInputList.add(newTask);
        System.out.print("Added to list: " + newTask.getDescription() + '\n');
    }

    public static boolean containsBye(String line) {
        return line.toLowerCase().contains("bye");
    }

    public static String takeUserInput() {
        // Start new prompt on next line
        System.out.println();
        Scanner in = new Scanner(System.in);
        promptUserInputWith("What's that? Did you say something?");
        printUserPrefix(true);
        return in.nextLine();
    }

    private static void promptUserInputWith(String x) {
        printUserPrefix(false);
        System.out.println(x);
    }

    public static void printStartMessage() {
        String logo = "┏┓┓ ┏┓┳┓\n"
                + "┃ ┃ ┃┃┃┃\n"
                + "┗┛┗┛┗┛┻┛\n";
        System.out.println("Welcome to\n" + logo);
        printUserPrefix(false);
        System.out.println("Hi, I'm Clod. Am I a helper bot? Or a... what was that word... a... thingy? I don't know.");
    }

    public static void printExitMessage() {
        printUserPrefix(false);
        System.out.println("(Confused silence) Oh, was that it? ...Hmm. Bye.");
    }

    public static void printUserPrefix(boolean isUser) {
        if (isUser) {
            System.out.print("You: ");
        } else {
            System.out.print("Clod: ");
        }
    }
}

