package com.example.todoapp;



import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {
    Button log;
    EditText name, pass;
    TodoDbHelper DB;
    TextView signupbutton;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        log = findViewById(R.id.login);
        name = findViewById(R.id.uname);
        pass = findViewById(R.id.editTextTextPassword);
        signupbutton = findViewById(R.id.loghere);
        DB = new TodoDbHelper(Login.this);
        signupbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), signup.class));
            }
        });
        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = name.getText().toString();
                String userpass = pass.getText().toString();
                if (username.equals("") || userpass.equals("")) { // Change "pass" to "userpass"
                    Toast.makeText(Login.this, "Please Enter all the fields", Toast.LENGTH_SHORT).show();
                } else {
                    boolean checkuserpass = DB.chekusernamepassword(username, userpass);
                    if (checkuserpass) {
                        Toast.makeText(Login.this, "Sign In Successfully", Toast.LENGTH_SHORT).show();

                        // Pass user's name to the todo activity
                        Intent intent = new Intent(Login.this, todo.class);
                        intent.putExtra("user_name", username);
                        startActivity(intent);
                    } else {
                        Toast.makeText(Login.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
