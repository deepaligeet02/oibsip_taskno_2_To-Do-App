package com.example.todoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;


import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
public class MainActivity extends AppCompatActivity {
    Button b;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b = findViewById(R.id.button1);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(MainActivity.this, Login.class);
                startActivity(in);

            }
        });
    }
}
