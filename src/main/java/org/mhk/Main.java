package org.mhk;

import java.util.List;

public class Main {
    static final String FILE_NAME = "data/tasks.json";
    static TaskManager taskManager;

    public static void main(String[] args) {

        taskManager = FileManager.loadTasks(FILE_NAME);

        if (args.length == 0) {
            System.out.println("Please provide a command: Add, List, Remove");
            return;
        }

        String command = args[0].toLowerCase();

        switch (command) {
            case "add" -> {
                if (args.length < 3) {
                    System.out.println("Usage: add <title> <description>");
                } else {
                    String title = args[1];
                    String description = args[2];
                    Task newTask = new Task(title, description);
                    taskManager.appendATaskToTheTaskList(newTask);
                    FileManager.saveTasks(taskManager, FILE_NAME);
                    System.out.println("Task added successfully!");
                }
            }
            case "list" -> {
                System.out.println("Tasks:\n");
                for (int i = 0; i < taskManager.getTaskList().size(); i++) {
                    Task task = taskManager.getTaskList().get(i);
                    System.out.printf("%d.\n%s\n", i, task.toString());
                }
            }
            case "remove" -> {
                if (args.length < 2) {
                    System.out.println("Usage: remove <index>");
                } else {
                    int index = Integer.parseInt(args[1]) - 1;
                    if (index >= 0 && index < taskManager.getTaskList().size()) {
                        taskManager.getTaskList().remove(index);
                        FileManager.saveTasks(taskManager, FILE_NAME);
                        System.out.println("Task removed successfully!");
                    } else {
                        System.out.println("Invalid task index.");
                    }
                }
            }
            default -> System.out.println("Unknown command: " + command);
        }
    }
}

