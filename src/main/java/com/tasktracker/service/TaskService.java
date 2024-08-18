package com.tasktracker.service;

import com.tasktracker.model.Task;
import com.tasktracker.repository.JSONHandler;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class TaskService {

    private JSONHandler jsonHandler;

    public TaskService() {
        this.jsonHandler = new JSONHandler();
    }

    public void addTask(String description) {
        try {
            List<Task> tasks = jsonHandler.readTasks();
            int newId = tasks.isEmpty() ? 1 : tasks.get(tasks.size() - 1).getId() + 1;
            LocalDateTime now = LocalDateTime.now();
            Task newTask = new Task(newId, description, "to-do", now, now);
            tasks.add(newTask);
            jsonHandler.writeTasks(tasks);
            System.out.println("Task added successfully (ID: " + newId + ")");
        } catch (IOException e) {
            System.err.println("Error adding task: " + e.getMessage());
        }
    }

    public void updateTask(int id, String description) {
        try {
            List<Task> tasks = jsonHandler.readTasks();
            Optional<Task> taskOpt = tasks.stream().filter(t -> t.getId() == id).findFirst();

            if (taskOpt.isPresent()) {
                Task task = taskOpt.get();
                task.setDescription(description);
                task.setUpdatedAt(java.time.LocalDateTime.now());
                jsonHandler.writeTasks(tasks);
                System.out.println("Task updated successfully (ID: " + id + ")");
            } else {
                System.out.println("Task not found (ID: " + id + ")");
            }
        } catch (IOException e) {
            System.err.println("Error updating task: " + e.getMessage());
        }
    }

    public void deleteTask(int id) {
        try {
            List<Task> tasks = jsonHandler.readTasks();
            if (tasks.removeIf(t -> t.getId() == id)) {
                jsonHandler.writeTasks(tasks);
                System.out.println("Task deleted successfully (ID: " + id + ")");
            } else {
                System.out.println("Task not found (ID: " + id + ")");
            }
        } catch (IOException e) {
            System.err.println("Error deleting task: " + e.getMessage());
        }
    }

    public void markTaskInProgress(int id) {
        updateTaskStatus(id, "in-progress");
    }

    public void markTaskDone(int id) {
        updateTaskStatus(id, "done");
    }

    private void updateTaskStatus(int id, String status) {
        try {
            List<Task> tasks = jsonHandler.readTasks();
            Optional<Task> taskOpt = tasks.stream().filter(t -> t.getId() == id).findFirst();

            if (taskOpt.isPresent()) {
                Task task = taskOpt.get();
                task.setStatus(status);
                task.setUpdatedAt(java.time.LocalDateTime.now());
                jsonHandler.writeTasks(tasks);
                System.out.println("Task status updated to '" + status + "' (ID: " + id + ")");
            } else {
                System.out.println("Task not found (ID: " + id + ")");
            }
        } catch (IOException e) {
            System.err.println("Error updating task status: " + e.getMessage());
        }
    }

    public void listTasks(String status) {
        try {
            List<Task> tasks = jsonHandler.readTasks();
            tasks.stream()
                    .filter(t -> status == null || t.getStatus().equalsIgnoreCase(status))
                    .forEach(t -> System.out.println(t.toString()));
        } catch (IOException e) {
            System.err.println("Error listing tasks: " + e.getMessage());
        }
    }

}
