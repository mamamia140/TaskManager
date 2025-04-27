package org.mhk;

import java.util.ArrayList;
import java.util.List;

public class TaskManager {

    private List<Task> taskList;

    public TaskManager() {
        taskList = new ArrayList<Task>();
    }
    public TaskManager(List<Task> tasks){
        taskList = tasks;
    }
    public Task getTaskLocatedAt(int index){
        return taskList.get(index);
    }
    public void appendATaskToTheTaskList(Task task){
        taskList.addLast(task);
    }
    public void removeTask(Task task){
        taskList.removeIf(t -> t.equals(task));
    }
    public void removeTaskAt(int index){
        taskList.remove(index);
    }
    public void flushTaskList(){
        taskList.clear();
    }
    public List<Task> getTaskList(){
        return List.copyOf(taskList);
    }

    public void setTaskList(List<Task> taskList){
        this.taskList = taskList;
    }
}
