package com.example.iconnect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.window.SplashScreen;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashScreenActivity extends AppCompatActivity {

    Handler handler;
    private final int SPLASH_DISPLAY_LENGTH = 1000;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(user != null){
                    Intent intent=new Intent(SplashScreenActivity.this,DashboardActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Intent intent = new Intent(SplashScreenActivity.this , GetStartedActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        },SPLASH_DISPLAY_LENGTH);
    }



}