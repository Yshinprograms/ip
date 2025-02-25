# Clod - Your Forgetful Task Manager

Clod is a command-line task management chatbot.  While Clod might not be the *brightest* bulb in the box (and openly admits it), it can help you keep track of your todos, deadlines, and events.  Think of Clod as a very, *very* basic version of more sophisticated AI assistants.  It's got a limited vocabulary, but it gets the job done (sometimes).

## Features

Clod supports the following commands:

*   **`list`**: Displays all tasks in your list.
*   **`todo <description>`**: Adds a new todo task.
*   **`deadline <description> /by <date/time>`**: Adds a new deadline task.
*   **`event <description> /from <start time> /to <end time>`**: Adds a new event task.
*   **`mark <task number>`**: Marks a task as completed.  The task number corresponds to the number shown when you use the `list` command.
*   **`unmark <task number>`**: Marks a completed task as *not* completed.
*   **`delete <task number>`**: Deletes a task from the list.
*   **`bye`**: Exits Clod.

## Getting Started

1.  **Prerequisites:** You need to have Java installed on your system.
2.  **Running Clod:**
    *   Compile the Java files.  From the root directory of the project (where `src` is located), you can typically compile using a command like: `javac src/main/java/clod/*.java src/main/java/clod/exceptions/*.java src/main/java/clod/operations/*.java src/main/java/clod/storage/*.java src/main/java/clod/ui/*.java`
    *   Run the `Clod` class: `java -cp src/main/java clod.Clod`
3. **Data Storage:**
    * Clod will automatically create a `data` directory (if it doesn't exist) in the same location you run the command, and in that the file `clod.txt` is created.
    * Clod automatically saves and loads your tasks from `data/clod.txt`.

## Usage Examples

Here's how you might interact with Clod:

```
Clod: Welcome to Clod
Clod: Hi, I'm Clod. Am I a helper bot? Or a... what was that word... a... thingy? I don't know.
Clod: Anyway, I'm supposed to help you with your tasks.
Clod: Let's just say... my vocabulary is a little... focused right now.
Think list, todo, deadline, event, mark, unmark, and bye.
That's about the extent of my genius, I'm not about to debug your code like...
Well, you know who.

What's that? Did you say something?
You: todo read a book

Clod: Added to list: [T] [ ] read a book

What's that? Did you say something?
You: deadline submit report /by tomorrow

Clod: Added to list: [D] [ ] submit report (by: tomorrow)

What's that? Did you say something?
You: event team meeting /from 2pm /to 4pm

Clod: Added to list: [E] [ ] team meeting (from: 2pm to: 4pm)

What's that? Did you say something?
You: list

==============================================
Clod: Here's what you've said so far:
1. [T] [ ] read a book
2. [D] [ ] submit report (by: tomorrow)
3. [E] [ ] team meeting (from: 2pm to: 4pm)
   ==============================================

What's that? Did you say something?
You: mark 1

Clod: Congrats for finishing this... I think?
[T] [X] read a book

What's that? Did you say something?
You: delete 2

Clod: Deleted task: [D] [ ] submit report (by: tomorrow)

What's that? Did you say something?
You: bye

Clod: (Confused silence) Oh, was that it? ...Hmm. Bye.
```

## Important Notes and Error Handling

*   **Case-Insensitivity:**  Commands (`todo`, `deadline`, `event`, etc.) are case-insensitive.  You can type `TODO` or `Todo` and it will work the same.
*   **Command Structure:**  Pay close attention to the required format for each command, especially `deadline` and `event`, which need `/by`, `/from`, and `/to`.
*   **Task Numbers:** When using `mark`, `unmark`, or `delete`, make sure you use a valid task number from the list. Clod will complain if you don't.
*   **Empty Descriptions:**
    *   For `todo`, you *must* provide a description.  Clod won't let you add an empty todo.
    *   For `deadline`, you must provide a description *and* a deadline using `/by`.
    *   For `event`, you need a description, a start time (`/from`), and an end time (`/to`).
* **Error Messages:** Clod provides (somewhat sarcastic) error messages if you make a mistake.  Read these messages; they'll tell you what went wrong.  Examples include:
    *   **Invalid command:**  If you type something Clod doesn't understand.
    *   **Invalid task number:** If you try to `mark`, `unmark`, or `delete` a task that doesn't exist.
    *   **Missing description/time:**  If you don't provide the necessary information for `todo`, `deadline`, or `event`.
    *   **Invalid task format:** If the save file (`data/clod.txt`) is corrupted.

## Troubleshooting
*   **Tasks not saving:** Make sure the program has write permissions to the directory where you're running it.  The `data` directory needs to be created, and `clod.txt` needs to be written to.
* **"Error loading tasks":** if the error "Error loading previous task list. This may be empty if its your first time seeing me..." prints.
     This will occur if it is your first time running the program or if the `clod.txt` file is deleted.