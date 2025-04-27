package org.mhk.enums;

public enum State {
    INCOMPLETE("Incomplete", "Task is not started."),
    INPROGRESS("In progress", "Task is in progress."),
    COMPLETE("Complete", "Task is complete."),
    WONTDO("Won't do", "Task will not be done.");


    private final String name;
    private final String description;

    State(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return name;
    }
}
