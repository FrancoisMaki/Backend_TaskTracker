package com.tasktracker.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tasktracker.model.Task;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class JSONHandler {
    private static final String FILE_NAME = "tasks.json";
    private ObjectMapper objectMapper;

    public JSONHandler() {
        this.objectMapper = new ObjectMapper();
    }

    public List<Task> readTasks() throws IOException {
        if (!Files.exists(Paths.get(FILE_NAME))) {
            return new ArrayList<>();
        }
        return objectMapper.readValue(new File(FILE_NAME), new TypeReference<List<Task>>() {});
    }

    public void writeTasks(List<Task> tasks) throws IOException {
        objectMapper.writeValue(new File(FILE_NAME), tasks);
    }
}
