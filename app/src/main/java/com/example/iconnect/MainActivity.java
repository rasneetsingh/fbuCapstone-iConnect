package com.example.iconnect;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    Button startedbtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.actionbar)));

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user!= null){
            goDashboardActivity();
        }



        startedbtn = findViewById(R.id.started_btn);

        startedbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //start RegisterActivity
                startActivity(new Intent(MainActivity.this, GetStartedActivity.class));

            }
        });

    }

    private void goDashboardActivity() {
        Intent i = new Intent(this,DashboardActivity.class);
        startActivity(i);
        finish();
    }


}