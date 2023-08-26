package com.example.todoapp;

public class TodoItem {
    private int taskId;
    private String taskTitle;
    private String taskDescription;

    public TodoItem(int id, String title, String description) {
        this.taskId = id;
        this.taskTitle = title;
        this.taskDescription = description;
    }

    public int getTaskId() {
        return taskId;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public String getTaskDescription() {
        return taskDescription;
    }
}
