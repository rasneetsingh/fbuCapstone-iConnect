package com.example.iconnect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btnRegister;
    Button btnLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnRegister = findViewById(R.id.register_btn);
        btnLogin = findViewById(R.id.login_btn);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //start RegisterActivity
                startActivity(new Intent(MainActivity.this, RegisterActivity.class));

            }
        });

        //handle login button click
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //start loginactivity
                startActivity(new Intent(MainActivity.this, LoginActivity.class));


            }
        });




    }
}