package org.mhk;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import org.mhk.enums.State;

public class Task {

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

    @Override
    public String toString(){
        return """
                \tid: %s
                \tTitle: %s
                \tDescription: %s
                \tState: %s
                """.formatted(this.getId(), this.getTitle(), this.getDescription(), this.getState().getName());
    }

    public String getId() {
        return id;
    }

    public String generateID(){
        return NanoIdUtils.randomNanoId();
    }
}
