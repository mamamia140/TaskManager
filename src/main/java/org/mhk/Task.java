package org.mhk;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import org.mhk.enums.State;

public class Task implements Cloneable{

    private final String id;
    private String title;
    private String description;

    private State state;

    public Task() {
        this.id = generateID();
    }
    public Task(String title, String description) {
        this.id = generateID();
        this.title = title;
        this.description = description;
        this.state = State.NEW;
    }
    private Task(Task task) {
        this.id = task.getId();
        this.title = task.getTitle();
        this.description = task.getDescription();
        this.state = task.getState();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if(title == null || title.isEmpty()){
            throw new IllegalArgumentException("title can't be empty or null");
        }
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public String getId() {
        return id;
    }

    public String generateID(){
        return NanoIdUtils.randomNanoId();
    }

    @Override
    public String toString(){
        return """
                \tid: %s
                \tTitle: %s
                \tDescription: %s
                \tState: %s
                """.formatted(this.getId(), this.getTitle(), this.getDescription(), this.getState().getName());
    }

    @Override
    public Task clone() {
        return new Task(this); // uses copy constructor
    }

}
