import java.util.Scanner;

public class Clod {
    private static final String BOT_PREFIX = "Clod: ";
    private static final String USER_PREFIX = "You: ";
    private static final String LIST_COMMAND = "list";
    private static final String MARK_COMMAND = "mark";
    private static final String UNMARK_COMMAND = "unmark";
    private static final String TODO_COMMAND = "todo";
    private static final String DEADLINE_COMMAND = "deadline";
    private static final String EVENT_COMMAND = "event";
    private static final String BYE_COMMAND = "bye";

    public static void main(String[] args) {
        printStartMessage();
        runClod();
        printExitMessage();
    }

    public static void runClod() {
        TaskList taskList = new TaskList();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String userInput = takeUserInput(scanner);
            if (containsBye(userInput)) {
                break;
            }
            processUserCommand(userInput, taskList);
        }
        scanner.close();
    }

    private static void processUserCommand(String userInput, TaskList taskList) {
        String lowerCaseInput = userInput.toLowerCase();
        String[] words = lowerCaseInput.split("\\s+", 2); // Split input into command and arguments
        String command = words[0];
        String taskDescription;
        if (words.length > 1) {
            taskDescription = words[1];
        } else {
            taskDescription = "";
        }

        switch (command) {
        case LIST_COMMAND:
            taskList.printList();
            break;
        case MARK_COMMAND:
            taskList.markTaskAsCompleted(parseTaskIndex(taskDescription));
            break;
        case UNMARK_COMMAND:
            taskList.unmarkCompletedTask(parseTaskIndex(taskDescription));
            break;
        case TODO_COMMAND:
            taskList.addNewTodoToList(taskDescription);
            break;
        case DEADLINE_COMMAND:
            taskList.addNewDeadlineToList(taskDescription);
            break;
        case EVENT_COMMAND:
            taskList.addNewEventToList(taskDescription);
            break;
        default:
            printMessage("You're gonna want the real Claude for that...");
            break;
        }
    }

    private static int parseTaskIndex(String taskDescription) {
        try {
            return Integer.parseInt(taskDescription.trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public static boolean containsBye(String line) {
        return line.toLowerCase().contains(BYE_COMMAND);
    }

    public static String takeUserInput(Scanner scanner) {
        System.out.println();
        promptUserInputWith("What's that? Did you say something?");
        printUserPrefix();
        return scanner.nextLine();
    }

    private static void promptUserInputWith(String message) {
        printBotPrefix();
        System.out.println(message);
    }

    public static void printStartMessage() {
        printBotPrefix();
        System.out.println("Welcome to Clod");
        printBotPrefix();
        System.out.println("Hi, I'm Clod. Am I a helper bot? Or a... what was that word... a... thingy? I don't know.");
        printBotPrefix();
        System.out.println("Anyway, I'm supposed to help you with your tasks.");
        printBotPrefix();
        System.out.println("list/todo/deadline/event/mark/unmark/bye are really all that I can handle right now.");
    }

    public static void printExitMessage() {
        printBotPrefix();
        System.out.println("(Confused silence) Oh, was that it? ...Hmm. Bye.");
    }

    public static void printUserPrefix() {
        System.out.print(USER_PREFIX);
    }

    public static void printBotPrefix() {
        System.out.print(BOT_PREFIX);
    }

    public static void printMessage(String message) {
        printBotPrefix();
        System.out.println(message);
    }
}