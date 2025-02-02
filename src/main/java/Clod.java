import java.util.Scanner;

public class Clod {
    public static void main(String[] args) {
        printStartMessage();
        runClod();
        printExitMessage();
    }

    public static void runClod() {
        String userInput = takeUserInput();
        TaskList listOfUserInputs = new TaskList();

        while (!containsBye(userInput)) {
            processUserCommand(userInput, listOfUserInputs);
            userInput = takeUserInput();
        }
    }

    private static void processUserCommand(String userInput, TaskList listOfUserInputs) {
        String lowerCaseUserInput = userInput.toLowerCase();
        String userCommand = parseCommand(lowerCaseUserInput);

        switch (userCommand) {
        case "list":
            listOfUserInputs.printList();
            break;
        case "mark":
            int taskNumber = listOfUserInputs.getUserTaskNumber(userInput);
            listOfUserInputs.markTaskAsCompleted(taskNumber);
            break;
        case "unmark":
            int taskNumber2 = listOfUserInputs.getUserTaskNumber(userInput);
            listOfUserInputs.unmarkCompletedTask(taskNumber2);
            break;
        case "todo":
            listOfUserInputs.addNewTodoToList(userInput);
            break;
        case "deadline":
            listOfUserInputs.addNewDeadlineToList(userInput);
            break;
        default:
            System.out.println("I'm not sure what you're asking me to do.");
            break;
        }
    }

    private static String parseCommand(String lowerCaseUserInput) {
        if (lowerCaseUserInput.contains("list")) {
            return "list";
        } else if (lowerCaseUserInput.contains("unmark")) {
            return "unmark";
        } else if (lowerCaseUserInput.contains("mark")) {
            return "mark";
        } else if (lowerCaseUserInput.contains("todo")) {
            return "todo";
        } else if (lowerCaseUserInput.contains("deadline")) {
            return "deadline";
        }
        return "add";
    }

    public static boolean containsBye(String line) {
        return line.toLowerCase().contains("bye");
    }

    public static String takeUserInput() {
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