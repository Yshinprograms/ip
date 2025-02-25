# Clod - Your Forgetful Task Manager

Clod is a command-line task management chatbot.  While Clod might not be the *brightest* bulb in the box (and openly admits it), it can help you keep track of your todos, deadlines, and events.  Think of Clod as a very, *very* basic version of more sophisticated AI assistants.  It's got a limited vocabulary, but it gets the job done (sometimes).

## Getting Started

1.  **Prerequisites:** You need to have Java installed on your system.
2.  **Running Clod:**
    *   Compile the Java files.  From the root directory of the project (where `src` is located), you can typically compile using a command like: `javac src/main/java/clod/*.java src/main/java/clod/exceptions/*.java src/main/java/clod/operations/*.java src/main/java/clod/storage/*.java src/main/java/clod/ui/*.java`
    *   Run the `Clod` class: `java -cp src/main/java clod.Clod`
3. **Data Storage:**
    * Clod will automatically create a `data` directory (if it doesn't exist) in the same location you run the command, and in that the file `clod.txt` is created.
    * Clod automatically saves and loads your tasks from `data/clod.txt`.

## Features
*   **Task Management:**
    *   **Add a to-do task:**
        *   Command: `todo DESCRIPTION`
        *   Explanation: Adds a new to-do task with the given description.
        *   Example: `todo read a book`
    *   **Add a deadline task:**
        *   Command: `deadline DESCRIPTION /by DATE_TIME`
        *   Explanation: Adds a new deadline task with the given description and deadline.
        *   Example: `deadline submit report /by tomorrow`
    *   **Add an event task:**
        *   Command: `event DESCRIPTION /from START_TIME /to END_TIME`
        *   Explanation: Adds a new event task with the given description, start time, and end time.
        *   Example: `event attend meeting /from 2pm /to 4pm`
    *   **Delete a task:**
        *   Command: `delete TASK_NUMBER`
        *   Explanation: Deletes the task with the given task number from the list.
        *   Example: `delete 1`
    *   **List tasks:**
        *   Command: `list`
        *   Explanation: Displays all tasks in your list.
        *   Example: `list`
    *   **Mark a task as done:**
        *   Command: `mark TASK_NUMBER`
        *   Explanation: Marks the task with the given task number as completed. The task number corresponds to the number shown when you use the `list` command.
        *   Example: `mark 1`
    *   **Mark a task as undone:**
        *   Command: `unmark TASK_NUMBER`
        *   Explanation: Marks a completed task as *not* completed.
        *   Example: `unmark 1`
    *   **Find tasks by keyword:**
        *   Command: `find KEYWORD`
        *   Explanation: Finds tasks that contain the given keyword in their description.
        *   Example: `find book`
*   **Storage:**
    *   Load tasks: Loads previously saved tasks from the `data/clod.txt` file.
    *   Save tasks: Saves the current list of tasks to the `data/clod.txt` file.
*   **User Interface:**
    *   Text-based interface: Interact with the chatbot through text commands.

## Command Format

Words in `UPPER_CASE` are the parameters to be supplied by the user.
e.g. in `add n/NAME`, `NAME` is a parameter which can be used as `add n/John Doe`.

Items in square brackets are optional.
e.g `n/NAME [t/TAG]` can be used as `n/John Doe t/friend` or as `n/John Doe`.

Items with `…` after them can be used multiple times including zero times.
e.g. `[t/TAG]…` can be used as (i.e. 0 times), `t/friend`, `t/friend t/family` etc.

Parameters can be in any order.
e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.
e.g. if the command specifies `help 123`, it will be interpreted as `help`.

## Error Handling

Clod provides informative error messages for invalid commands and incorrect input.

*   **Invalid command:**
    *   Explanation: If you enter an invalid command, Clod will display a message indicating that it does not understand the command.
    *   Example: `blah` -> `Clod: I'm sorry, but I don't know what that means :-(`
*   **Missing description for todo:**
    *   Explanation: If you enter the `todo` command without a description, Clod will display an error message.
    *   Example: `todo` -> `Clod: Todo description cannot be empty!`
*   **Missing description/time for deadline:**
    *   Explanation: If you enter the `deadline` command without a description or deadline, Clod will display an error message.
    *   Example: `deadline` -> If the description is missing: `Clod: Wow. A deadline for nothing? That's beyond even me.\nCome on, if you're gonna try, at least come up with a reasonable description.` If the time is missing: `Clod: A deadline without a DEADLINE huh? You've truly outdone yourself this time.\nMaybe try setting a REAL deadline this time.`
*   **Missing description/time for event:**
    *   Explanation: If you enter the `event` command without a description, start time, or end time, Clod will display an error message.
    *   Example: `event` -> Clod will prompt for the missing information, e.g., `Clod: Whats the time for the event to start?`
*   **Invalid task number:**
    *   Explanation: If you try to mark, unmark, or delete a task with an invalid task number, Clod will display an error message.
    *   Example: `mark 0` -> `Clod: I may not be the smartest around, but I can still count you know...`
*   **Task not found:**
    *   Explanation: If you enter the `find` command without a keyword, Clod will display an error message.
    *   Example: `find` -> `Clod: What am I supposed to find? Your hopes and dreams?`
*   **Invalid task format:**
    *   Explanation: If the save file (`data/clod.txt`) is corrupted, Clod will display an error message.
    *   Example: Corrupted `data/clod.txt` -> `Clod: Invalid task format: ...`

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