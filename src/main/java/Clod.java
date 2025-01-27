import java.util.Scanner;
import java.util.ArrayList;

// Clod as a play on Claude with a Confused Purpose & Identity
public class Clod {
    public static void main(String[] args) {
        String logo = "┏┓┓ ┏┓┳┓\n"
                + "┃ ┃ ┃┃┃┃\n"
                + "┗┛┗┛┗┛┻┛\n";
        System.out.println("Welcome to\n" + logo);
        printStartMessage();

        String line = takeInput();
        ArrayList<String> userInputList = new ArrayList<String>();
        while (!containsBye(line)){
            //echoUserInput(line);
            if (line.toLowerCase().contains("list")){
                printList(userInputList);
            }
            else {
                addUserInputToList(line, userInputList);
            }
            line = takeInput();
        }

        printExitMessage();
    }

    public static void printList(ArrayList<String> userInputList){
        System.out.println("Here's what you've said so far:");
        int count = 1;
        for (String addedLine : userInputList){
            System.out.print(count + ". ");
            echoUserInput(addedLine);
            count++;
        }
    }

    public static void addUserInputToList(String line, ArrayList userInputList){
        userInputList.add(line);
        System.out.print("Added to list: ");
        echoUserInput(line);
    }

    public static boolean containsBye(String line){
        return line.toLowerCase().contains("bye");
    }

    public static String takeInput(){
        // Start on the next line
        System.out.println();
        Scanner in = new Scanner(System.in);
        // Clod prompts user for input
        printUserPrefix(false);
        System.out.println("What's that? Did you say something?");
        //System.out.println();
        // User input
        printUserPrefix(true);
        return in.nextLine();
    }

    public static void echoUserInput(String line){
        System.out.println(line);
    }

    public static void printStartMessage(){
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

