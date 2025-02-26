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

## Command Format

Words in `UPPER_CASE` are the parameters to be supplied by the user.
e.g. in `todo DESCRIPTION`, `DESCRIPTION` is a parameter which can be used as `todo Eat Lunch`.

Time inputs must be indicated using a '/' to mark the start of the time input.
e.g. `deadline DESCRIPTION /by DATE_TIME` can be used as `deadline Eat Lunch /by 2025-11-11 1400`.

Time inputs must be provided in yyyy-MM-dd HHmm format.
e.g. `yyyy-MM-dd HHmm` can be used as `2025-11-11 1400`.

## Features
*   **Task Management:**
    *   **Add a to-do task:**
        *   Command: `todo DESCRIPTION`
        *   Explanation: Adds a new to-do task with the given description.
        *   Example: `todo read a book`
    *   **Add a deadline task:**
        *   Command: `deadline DESCRIPTION /by DATE_TIME`
        *   Explanation: Adds a new deadline task with the given description and deadline.
        *   Example: `deadline submit report /by 2023-12-31 1800`
        *   Date Format: Use `yyyy-MM-dd HHmm` format for deadlines (e.g., `2023-12-31 1800` for Dec 31, 2023, 6:00 PM)
    *   **Add an event task:**
        *   Command: `event DESCRIPTION /from START_TIME /to END_TIME`
        *   Explanation: Adds a new event task with the given description, start time, and end time.
        *   Example: `event team meeting /from 2023-10-15 1400 /to 2023-10-15 1600`
        *   Date Format: Use `yyyy-MM-dd HHmm` format for event times
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
    *   **Unmark a completed task:**
        *   Command: `unmark TASK_NUMBER`
        *   Explanation: Removes the completed status from a task that was previously marked as done.
        *   Example: `unmark 2`
    *   **Find tasks by keyword:**
        *   Command: `find KEYWORD`
        *   Explanation: Searches through all tasks for the specified keyword and displays matching tasks.
        *   Example: `find meeting`

*   **Persistent Storage:**
    *   Tasks are automatically saved to `data/clod.txt` when added, deleted, or their status is changed.
    *   Tasks are automatically loaded from storage when Clod starts.
    *   Storage format preserves all task details including completion status, descriptions, and time information.

*   **Time Management:**
    *   Supports date and time input in `yyyy-MM-dd HHmm` format (e.g., `2023-12-31 1800`)
    *   Displays dates in a user-friendly format (e.g., `Dec 31 2023, 6:00 PM`)
    *   Handles both event time ranges (start and end times) and deadlines

*   **User Experience:**
    *   **Personality:** Clod has a mix of forgetfulness, sarcasm and nonchalance that adds character to interactions.
    *   **Error Handling:** Provides clear, conversational error messages when commands are improperly formatted.
    *   **Input Validation:** Validates user input and provides guidance when inputs are malformed.
    *   **Exit Command:** Use `bye` to exit the application.

## Error Handling

Clod provides informative error messages for invalid commands and incorrect input.

*   **Invalid command:**
    *   Explanation: If you enter an invalid command, Clod will display a message indicating that it does not understand the command.
    *   Example:  `help`  →  `Clod: You're gonna want the real Claude for that... Do I look like I've got 175 billion parameters under the hood?`

*   **Missing description for todo:**
    *   Explanation: If you enter the `todo` command without a description, Clod will display an error message.
    *   Example:  `todo`  →  `Clod: Todo description cannot be empty! Usage: {description}`

*   **Missing description for deadline:**
    *   Explanation: If you enter the `deadline` command without a description, Clod will display an error message.
    *   Example:  `deadline`  →  `Clod: Wow. A deadline for nothing? That's beyond even me. Come on, if you're gonna try, at least come up with a reasonable description. Usage: {description} /by {yyyy/MM/dd hh:mm}`

*   **Missing time for deadline:**
    *   Explanation: If you enter the `deadline` command with a description but without a deadline, Clod will display an error message.
    *   Example:  `deadline submit report`  →  `Clod: A deadline without a DEADLINE huh? You've truly outdone yourself this time. Maybe try setting a REAL deadline this time. Usage: {description} /by {yyyy/MM/dd hh:mm}`

*   **Missing description for event:**
    *   Explanation: If you enter the `event` command without any description, Clod will display an error message.
    *   Example:  `event`  →  `Clod: Lazing around all day with no specific event descriptions sounds like my kinda thing... Usage: {description} /from {yyyy/MM/dd hh:mm} /to {yyyy/MM/dd hh:mm}`

*   **Missing start time for event:**
    *   Explanation: If you enter the `event` command with a description but without a start time, Clod will display an error message.
    *   Example:  `event team meeting`  → `Clod: I may not be the best bot to host events, but we're still gonna need to get it started. Usage: {description} /from {yyyy/MM/dd hh:mm} /to {yyyy/MM/dd hh:mm}`

*   **Missing end time for event:**
    *   Explanation: If you enter the `event` command with a description and start time but without an end time, Clod will display an error message.
    *   Example:  `event team meeting /from 2023-10-15 1400`  →  `Clod: And when is this thing ever gonna end? Usage: {description} /from {yyyy/MM/dd hh:mm} /to {yyyy/MM/dd hh:mm}`

*   **Invalid task number format:**
    *   Explanation: If you try to `mark`, `unmark`, or `delete` a task with a non-numeric task number, Clod will display an error message.
    *   Example:  `mark abc`  →  `Clod: I may not be the smartest around, but I can still count you know... Maybe try using task numbers that are actually valid this time.`

*   **Invalid task index:**
    *   Explanation: If you try to `mark`, `unmark`, or `delete` a task with a task number that doesn't exist in the list, Clod will display an error message.
    *   Example:  `mark 999` (when there are fewer tasks)  →  `Clod: Come on, even I know that's not a valid task number.`

*   **Empty search keyword:**
    *   Explanation: If you enter the `find` command without a keyword, Clod will display an error message.
    *   Example: `find` → `Clod: What am I supposed to find? Your hopes and dreams? Try giving me a keyword to search for instead.`

*   **Empty task list when searching:**
    *   Explanation: If you try to search when the task list is empty, Clod will let you know.
    *   Example: `find meeting` (when list is empty) → `Clod: There's nothing to search through. The list is emptier than my... well, everything.`

* **No matching tasks found:**
    * Explanation: If no tasks match your search keyword, Clod will display a message.
    * Example: `find xyz` (when no tasks match) → `Clod: Searched high and low but couldn't find 'xyz'. Are you sure it exists? I'm not exactly known for my memory, you know.`

*   **Empty task list when listing:**
    *   Explanation: If you try to list tasks when the list is empty, Clod will display a message.
    *   Example:  `list` (when no tasks have been added)  →  `Clod: Hmm... The list seems empty. Did you say anything yet?`

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
