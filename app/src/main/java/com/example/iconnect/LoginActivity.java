package com.example.iconnect;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    EditText loginusername;
    EditText loginpassword;
    Button loginbtn;
    TextView noaccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Actionbar and its title;
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Log in");

        //enable back button
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        //init
        loginusername = findViewById(R.id.loginuser);
        loginpassword = findViewById(R.id.loginPassword);
        loginbtn = findViewById(R.id.btnlogin);
        noaccount = findViewById(R.id.nothaveaccount);

        //loginbtnclick


    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed(); //go previous activity
        return super.onSupportNavigateUp();
    }
}