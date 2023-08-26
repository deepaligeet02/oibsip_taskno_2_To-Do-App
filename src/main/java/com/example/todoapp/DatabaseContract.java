package com.example.todoapp;
import android.provider.BaseColumns;



public final class DatabaseContract {

    private DatabaseContract() {}

    public static class TaskEntry implements BaseColumns {
        public static final String TABLE_NAME = "todos";
        public static final String COLUMN_TODO_TITLE ="title" ;
        // public static final String COLUMN_TASK = "task";
    }
}
