package org.mhk;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class FileManager {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static void saveTasks(TaskManager taskManager, String filename) {
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(filename), taskManager);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static TaskManager loadTasks(String filename) {
        try {
            return mapper.readValue(new File(filename), TaskManager.class);
        } catch (IOException e) {
            return new TaskManager(); // return empty TaskManager if loading fails
        }
    }
}

