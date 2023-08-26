package com.example.todoapp;


import android.annotation.SuppressLint;
        import android.content.ContentValues;
        import android.content.Context;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import android.database.sqlite.SQLiteOpenHelper;


        import java.util.ArrayList;
        import java.util.List;

public class TodoDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "TODO.db";
    private static final int DATABASE_VERSION = 2;
    private static final String TABLE_TODOS = "todos";
    private static final String TABLE_USERS = "user";
    private static final String COLUMN_TODO_ID = "id";
    private static final String COLUMN_TODO_TITLE = "title";
    private static final String COLUMN_TODO_DESCRIPTION = "description";

    private SQLiteDatabase db;

    public TodoDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;
        db.execSQL("create Table TABLE_USERS (username varchar(20)  ,Email varchar(20),Password varchar(8),Phone varchar(10))");
//CREATE TABLE IN SQLITE
        db.execSQL("CREATE TABLE " + TABLE_TODOS + " (" +
                COLUMN_TODO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TODO_TITLE + " TEXT, " +
                COLUMN_TODO_DESCRIPTION + " TEXT)");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+  TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TODOS);
        onCreate(db);
    }

    public long insertdata(String username, String Email, String Password, String Phone) {
        SQLiteDatabase db = this.getWritableDatabase();//getwritable fun use for storing data into database
        ContentValues contents = new ContentValues();//contentvalues is nothing but collectionits a formate
        contents.put("username", username);
        contents.put("Email", Email);
        contents.put("Password", Password);
        contents.put("Phone", Phone);
        long result = db.insert("users", null, contents);
        return result;
    }

    public boolean chekusernamepassword(String username, String password) {
        SQLiteDatabase mydb = this.getWritableDatabase();
        Cursor cursor = mydb.rawQuery("select * from users where username= ? and password = ?", new String[]{username, String.valueOf(password)});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    public long insertTodo(String title, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_TODO_TITLE, title);
        contentValues.put(COLUMN_TODO_DESCRIPTION, description);
        return db.insert(TABLE_TODOS, null, contentValues);
    }

    public List<TodoItem> getAllTodos() {
        List<TodoItem> todoList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_TODOS, null);

        if (cursor.moveToFirst()) {

            do {
                 int id = cursor.getInt(cursor.getColumnIndex(COLUMN_TODO_ID));
                String title = cursor.getString(cursor.getColumnIndex(COLUMN_TODO_TITLE));
                String description = cursor.getString(cursor.getColumnIndex(COLUMN_TODO_DESCRIPTION));
                TodoItem todoItem = new TodoItem(id, title, description);
                todoList.add(todoItem);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return todoList;
    }
}








