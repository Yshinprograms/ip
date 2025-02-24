package clod.ui;

import java.util.Scanner;

public class Interactions {
    private static final String BOT_PREFIX = "Clod: ";
    private static final String USER_PREFIX = "You: ";
    private static final String BYE_COMMAND = "bye";

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
        System.out.println("Let's just say... my vocabulary is a little... focused right now. " +
                "\nThink list, todo, deadline, event, mark, unmark, and bye. " +
                "\nThat's about the extent of my genius, I'm not about to debug your code like... " +
                "\nWell, you know who.");
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
