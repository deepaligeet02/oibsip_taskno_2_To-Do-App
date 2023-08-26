package com.example.todoapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class signup extends AppCompatActivity {

    EditText pname, pemail, ppass, pphone;
    Button registerb;
    //TextView loginbutton;
    TodoDbHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        pname = findViewById(R.id.name);
        pemail = findViewById(R.id.email);
        ppass = findViewById(R.id.pass);
        pphone = findViewById(R.id.phone);
        registerb = findViewById(R.id.register);
        //loginbutton = findViewById(R.id.loghere);
        db = new TodoDbHelper(signup.this);

        registerb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uname = pname.getText().toString().trim();
                String uemail = pemail.getText().toString().trim();
                String upass = ppass.getText().toString().trim();
                String uphone = pphone.getText().toString().trim();
                long result = db.insertdata(uname, uemail, upass, uphone);//calling insert logic

                if (uname.equals("") || uemail.equals("") || upass.equals("") || uphone.equals("")) {
                    Toast.makeText(signup.this, "please Enters All Fields", Toast.LENGTH_SHORT).show();
                }
                if (result > 0) {


                    boolean check = validateinfo(uname, uemail, upass, uphone);
                    if (check == true) {
                        Toast.makeText(signup.this, "Saved Sucessfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), Login.class));
                    } else {
                        Toast.makeText(signup.this, "please enter valid info", Toast.LENGTH_SHORT).show();
                    }

                }}
        });


        }

    private boolean validateinfo(String uname, String uemail, String upass, String uphone) {
        if (!uname.matches("[a-zA-Z]+")) {
            pname.requestFocus();
            pname.setError("Enters Only alphabates");
            return false;
        }
        if (!uemail.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
            pemail.requestFocus();
            pemail.setError("Enters valid email");
            return false;
        }
        if (upass.length() <= 5) {
            ppass.requestFocus();
            ppass.setError("Minimum 6 char required");
            return false;
        }
        if (uphone.length() < 10 || uphone.length() > 10) {
            pphone.requestFocus();
            pphone.setError("Invalid mobile Number");
            return false;
        }

        return true;
    }

}








