package org.mhk;

import org.mhk.enums.State;

import java.nio.file.Paths;
import java.util.List;

public class Main {
    static final String FILE_NAME = Paths.get(System.getProperty("user.home"), "TaskManager", "data", "tasks.json").toString();
    static TaskManager taskManager;

    public static void main(String[] args) {

        taskManager = FileManager.loadTasks(FILE_NAME);

        if (args.length == 0 || (args.length == 1 && (args[0].equals("?") || args[0].equals("--help")))) {
            System.out.println("Please provide a command: Add, List, Remove, Modify");
            return;
        }

        String command = args[0].toLowerCase();

        switch (command) {
            case "add" -> handleAdd(args);
            case "list" -> handleList();
            case "remove" -> handleRemove(args);
            case "modify" -> handleModify(args);
            default -> System.out.println("Unknown command: " + command);
        }
    }

    private static void handleAdd(String[] args) {
        if (args.length < 3) {
            System.out.println("Usage: add <title> <description>");
            return;
        }
        String title = args[1];
        String description = args[2];
        Task newTask = new Task(title, description);
        taskManager.addTask(newTask);
        FileManager.saveTasks(taskManager, FILE_NAME);
        System.out.println("Task added successfully!");
    }

    private static void handleList() {
        if (taskManager.getTaskList().isEmpty()){
            System.out.println("There are no tasks");
            return;
        }
        System.out.println("Tasks:\n");
        for (int i = 0; i < taskManager.getTaskList().size(); i++) {
            Task task = taskManager.getTaskList().get(i);
            System.out.printf("%d.\n%s\n", i+1, task.toString());
        }
    }

    private static void handleRemove(String[] args) {
        if (args.length < 2 || args[1].equalsIgnoreCase("--help") || args[1].equals("?")) {
            System.out.println("Usage: remove <index_or_id_or_unique_title | all>");
            System.out.println("- <index>: 1-based task number (use 'list' to view)");
            System.out.println("- <id>: task's unique identifier");
            System.out.println("- <title>: task's title (must be unique)");
            System.out.println("- all: deletes all tasks");
            return;
        }

        String identifier = args[1];

        try {
            if (identifier.equalsIgnoreCase("all")) {
                taskManager.flushTaskList();
                FileManager.saveTasks(taskManager, FILE_NAME);
                System.out.println("All tasks removed successfully!");
                return;
            }

            // Try if identifier is a number (index)
            try {
                int index = Integer.parseInt(identifier) - 1; // Convert to 0-based index
                taskManager.removeTaskByIndex(index);
            } catch (NumberFormatException e) {
                // Not an index; try removing by ID or Title
                try {
                    taskManager.removeTaskById(identifier);
                } catch (IllegalArgumentException idEx) {
                    try {
                        taskManager.removeTaskByTitle(identifier);
                    } catch (IllegalArgumentException titleEx) {
                        System.out.println("No task found with given ID or Title: " + identifier);
                        return;
                    }
                }
            }

            FileManager.saveTasks(taskManager, FILE_NAME);
            System.out.println("Task removed successfully!");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Invalid index. Please make sure the number corresponds to a task.");
        }
    }




    private static void handleModify(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: modify <id_or_title> <property> <new_value>");
            return;
        }

        String idOrTitle = args[1];
        if (idOrTitle.equalsIgnoreCase("--help") || idOrTitle.equalsIgnoreCase("?")) {
            System.out.println("You need to provide the task's ID or Title. Example:");
            System.out.println("modify <id_or_title> <property> <new_value>");
            return;
        }

        if (args.length < 3) {
            System.out.println("Usage: modify <id_or_title> <property> <new_value>");
            return;
        }

        String property = args[2].toLowerCase();
        if (property.equalsIgnoreCase("--help") || property.equalsIgnoreCase("?")) {
            System.out.println("You need to specify which property to modify:");
            System.out.println("- title: Change the task's title");
            System.out.println("- description: Change the task's description");
            System.out.println("- state: Change the task's state (e.g., NEW, IN_PROGRESS, COMPLETE)");
            return;
        }

        if (args.length < 4) {
            System.out.println("Usage: modify <id_or_title> <property> <new_value>");
            return;
        }

        String newValue = args[3];
        if (newValue.equalsIgnoreCase("--help") || newValue.equalsIgnoreCase("?")) {
            switch (property) {
                case "title" -> System.out.println("Provide the new title you want to set for the task.");
                case "description" -> System.out.println("Provide the new description for the task.");
                case "state" -> {
                    System.out.println("Provide the new state. Available states are:");
                    for (State s : State.values()) {
                        System.out.println("- " + s.name());
                    }
                }
                default -> {
                    System.out.println("Unknown property. Available properties are: title, description, state");
                    return;
                }
            }
            return;
        }

        // Determine if the identifier is ID or Title
        boolean isId = taskManager.getTaskList().stream()
                .anyMatch(t -> t.getId().equals(idOrTitle));
        boolean isTitle = !isId && taskManager.getTaskList().stream()
                .anyMatch(t -> t.getTitle().equalsIgnoreCase(idOrTitle));

        if (!isId && !isTitle) {
            System.out.println("Task not found with ID or Title: " + idOrTitle);
            return;
        }

        try {
            switch (property) {
                case "title" -> {
                    if (isId) {
                        taskManager.updateTaskTitleById(idOrTitle, newValue);
                    } else {
                        taskManager.updateTaskTitleByTitle(idOrTitle, newValue);
                    }
                }
                case "description" -> {
                    if (isId) {
                        taskManager.updateTaskDescriptionById(idOrTitle, newValue);
                    } else {
                        taskManager.updateTaskDescriptionByTitle(idOrTitle, newValue);
                    }
                }
                case "state" -> {
                    State newState;
                    try {
                        newState = State.valueOf(newValue.toUpperCase());
                    } catch (IllegalArgumentException e) {
                        System.out.println("Invalid state. Available states are:");
                        for (State s : State.values()) {
                            System.out.println("- " + s.name());
                        }
                        return;
                    }
                    if (isId) {
                        taskManager.updateTaskStateById(idOrTitle, newState);
                    } else {
                        taskManager.updateTaskStateByTitle(idOrTitle, newState);
                    }
                }
                default -> {
                    System.out.println("Unknown property: " + property);
                    return;
                }
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return;
        }

        FileManager.saveTasks(taskManager, FILE_NAME);
        System.out.println("Task updated successfully!");
    }


}
