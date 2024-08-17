package com.tasktracker.cli;

import com.tasktracker.service.TaskService;

public class TaskCLI {

    private static TaskService taskService = new TaskService();

    public static void main(String[] args) {
        if (args.length == 0) {
            printUsage();
            return;
        }

        String command = args[0];

        switch (command) {
            case "add":
                if (args.length >= 2) {
                    String description = String.join(" ", args).substring(4); // Se asume que "add " tiene 4 caracteres
                    taskService.addTask(description);
                } else {
                    System.out.println("Please provide a description for the new task.");
                }
                break;

            case "update":
                if (args.length >= 3) {
                    int id = Integer.parseInt(args[1]);
                    String description = String.join(" ", args).substring(args[0].length() + args[1].length() + 2);
                    taskService.updateTask(id, description);
                } else {
                    System.out.println("Please provide an ID and a new description for the task.");
                }
                break;

            case "delete":
                if (args.length == 2) {
                    int id = Integer.parseInt(args[1]);
                    taskService.deleteTask(id);
                } else {
                    System.out.println("Please provide an ID of the task to delete.");
                }
                break;

            case "mark-in-progress":
                if (args.length == 2) {
                    int id = Integer.parseInt(args[1]);
                    taskService.markTaskInProgress(id);
                } else {
                    System.out.println("Please provide an ID of the task to mark as in-progress.");
                }
                break;

            case "mark-done":
                if (args.length == 2) {
                    int id = Integer.parseInt(args[1]);
                    taskService.markTaskDone(id);
                } else {
                    System.out.println("Please provide an ID of the task to mark as done.");
                }
                break;

            case "list":
                if (args.length == 1) {
                    taskService.listTasks(null);
                } else if (args.length == 2) {
                    String status = args[1];
                    taskService.listTasks(status);
                } else {
                    System.out.println("Usage: list [status]");
                }
                break;

            default:
                printUsage();
        }
    }

    private static void printUsage() {
        System.out.println("Usage:");
        System.out.println("task-cli add \"Task Description\"");
        System.out.println("task-cli update <ID> \"New Task Description\"");
        System.out.println("task-cli delete <ID>");
        System.out.println("task-cli mark-in-progress <ID>");
        System.out.println("task-cli mark-done <ID>");
        System.out.println("task-cli list [status]");
        System.out.println("status options: done, todo, in-progress");
    }

}
