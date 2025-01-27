import java.util.Scanner;

// Clod as a play on Claude with a Confused Purpose & Identity
public class Clod {
    public static void main(String[] args) {
        String logo = "┏┓┓ ┏┓┳┓\n"
                + "┃ ┃ ┃┃┃┃\n"
                + "┗┛┗┛┗┛┻┛\n";
        System.out.println("Welcome to\n" + logo);
        printStartMessage();

        String line = takeInput();

        while (!containsBye(line)){
            echoUserInput(line);
            line = takeInput();
        }

        printExitMessage();
    }

    public static boolean containsBye(String line){
        return line.toLowerCase().contains("bye");
    }

    public static String takeInput(){
        Scanner in = new Scanner(System.in);
        printUserPrefix(false);
        System.out.println("What's that? Did you say something?");
        printUserPrefix(true);
        return in.nextLine();
    }

    public static void printStartMessage(){
        printUserPrefix(false);
        System.out.println("Hi, I'm Clod. Am I a helper bot? Or a... what was that word... a... thingy? I don't know.");
    }

    public static void echoUserInput(String line){
        printUserPrefix(false);
        System.out.println(line);
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

