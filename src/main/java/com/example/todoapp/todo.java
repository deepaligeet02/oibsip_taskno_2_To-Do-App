package com.example.todoapp;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class todo extends AppCompatActivity {
    private ListView taskListView;
    private ArrayAdapter<String> taskAdapter;
    private List<String> taskList;
    private TodoDbHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todo_activity);

        databaseHelper = new TodoDbHelper(this);

        // Initialize views and variables
        taskListView = findViewById(R.id.taskListView); // Assuming you have a ListView with this ID in your layout
        taskList = new ArrayList<>();
        taskAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, taskList);
        taskListView.setAdapter(taskAdapter);
        loadTasksFromDatabase();

        Button logoutButton = findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(todo.this, Login.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });
        taskListView.setOnItemClickListener(null); // Disable the onItemClick listener

        taskAdapter = new ArrayAdapter<>(this, R.layout.list_item_todo, R.id.listItemTextView, taskList);
        taskListView.setAdapter(taskAdapter);

        // Set the click listener for the delete buttons within each list item
        taskAdapter.notifyDataSetChanged();
        taskAdapter = new ArrayAdapter<>(this, R.layout.list_item_todo, R.id.listItemTextView, taskList) {
            @SuppressLint("ViewHolder")
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                convertView = getLayoutInflater().inflate(R.layout.list_item_todo, parent, false);

                TextView taskTextView = convertView.findViewById(R.id.listItemTextView);
                Button deleteButton = convertView.findViewById(R.id.deleteButton);

                String task = taskList.get(position);
                taskTextView.setText(task);

                // Set click listener for delete button
                deleteButton.setOnClickListener(v -> {
                    // Delete the task from the database and update the UI
                    deleteTaskFromDatabase(task);
                    taskList.remove(position);
                    notifyDataSetChanged();
                });

                return convertView;
            }
        };
        taskListView.setAdapter(taskAdapter);

        // @SuppressLint("WrongViewCast")
         Button addTaskButton = findViewById(R.id.addb); // Assuming you have a FloatingActionButton for adding tasks
        addTaskButton.setOnClickListener(view -> {
            // Show a dialog to input the new task
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Add Task");
            final EditText input = new EditText(this);
            builder.setView(input);
            builder.setPositiveButton("Add", (dialog, which) -> {
                String task = input.getText().toString().trim();
                if (!task.isEmpty()) {
                    addTaskToDatabase(task); // Add the task to the database
                    taskList.add(task);
                    taskAdapter.notifyDataSetChanged();
                }
            });
            builder.setNegativeButton("Cancel", null);
            builder.show();
        });

        // Example: Delete a task on item click
        taskListView.setOnItemClickListener((parent, view, position, id) -> {
            String task = taskList.get(position);
            deleteTaskFromDatabase(task); // Delete the task from the database
            taskList.remove(position);

            taskAdapter.notifyDataSetChanged();
        });
    }

    // Method to load tasks from the SQLite database
    private void loadTasksFromDatabase() {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + DatabaseContract.TaskEntry.TABLE_NAME, null);


        while (c.moveToNext()) {
            String task = c.getString(c.getColumnIndexOrThrow(DatabaseContract.TaskEntry.COLUMN_TODO_TITLE));
            taskList.add(task);
        }

        c.close();
        db.close();
        taskAdapter.notifyDataSetChanged();
        taskAdapter = new ArrayAdapter<>(this, R.layout.list_item_todo, R.id.listItemTextView, taskList);
        taskListView.setAdapter(taskAdapter);
    }





    // Method to add a task to the SQLite database
    private void addTaskToDatabase(String task) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.TaskEntry.COLUMN_TODO_TITLE, task);

        db.insert(DatabaseContract.TaskEntry.TABLE_NAME, null, values);
        db.close();
    }

    // Method to delete a task from the SQLite database
    private void deleteTaskFromDatabase(String task) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        String selection = DatabaseContract.TaskEntry.COLUMN_TODO_TITLE  + " = ?";
        String[] selectionArgs = {task};
        db.delete(DatabaseContract.TaskEntry.TABLE_NAME, selection, selectionArgs);
        db.close();
    }
}





