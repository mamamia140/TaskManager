package org.mhk;

import org.mhk.enums.State;

import java.util.ArrayList;
import java.util.List;

public class TaskManager {

    private List<Task> taskList;

    public TaskManager() {
        taskList = new ArrayList<Task>();
    }
    public TaskManager(List<Task> tasks){
        this.taskList = new ArrayList<>(tasks); // ensures it's mutable
    }

    public void addTask(Task task){
        taskList.addLast(task);
    }
    public void removeTaskByIndex(int index){
        taskList.remove(index);
    }
    public void removeTaskById(String id){
        Task task = getTaskById(id);
        taskList.remove(task);
    }
    public void removeTaskByTitle(String title){
        Task task = getTaskByTitle(title);
        taskList.remove(task);
    }
    public void flushTaskList(){
        taskList.clear();
    }
    public void updateTaskStateByIndex(int index, State state){
        Task task = taskList.get(index);
        task.setState(state);
    }

    public void updateTaskStateById(String id, State state){
        Task task = getTaskById(id);
        task.setState(state);
    }

    public void updateTaskStateByTitle(String title, State state){
        Task task = getTaskByTitle(title);
        task.setState(state);
    }
    public void updateTaskDescriptionByIndex(int index, String description){
        Task task = taskList.get(index);
        task.setDescription(description);
    }

    public void updateTaskDescriptionById(String id, String description){
        Task task = getTaskById(id);
        task.setDescription(description);
    }

    public void updateTaskDescriptionByTitle(String title, String description){
        Task task = getTaskByTitle(title);
        task.setDescription(description);
    }
    public void updateTaskTitleByIndex(int index, String title){
        Task task = taskList.get(index);
        task.setTitle(title);
    }
    public void updateTaskTitleById(String id, String title){
        Task task = getTaskById(id);
        task.setTitle(title);
    }
    public void updateTaskTitleByTitle(String oldTitle, String newTitle){
        Task task = getTaskByTitle(oldTitle);
        task.setTitle(newTitle);
    }
    public Task getTaskByIndex(int index){
        return taskList.get(index).clone();
    }
    private Task getTaskById(String id) {
        return taskList.stream()
                .filter(task -> task.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No task found with ID: " + id));
    }
    private Task getTaskByTitle(String title) {
        return taskList.stream()
                .filter(task -> task.getTitle().equals(title))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No task found with title: " + title));
    }
    public List<Task> getTaskList(){
        return taskList.stream().map(Task::clone).toList();
    }

    public void setTaskList(List<Task> taskList){
        this.taskList = taskList;
    }
}
