package com.example.iconnect;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.iconnect.Fragments.ChatListFragment;
import com.example.iconnect.Fragments.HomeFragment;
import com.example.iconnect.Fragments.ProfileFragment;
import com.example.iconnect.Fragments.UsersFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class DashboardActivity extends AppCompatActivity {


    //firebase auth
    FirebaseAuth firebaseAuth;

    ActionBar actionBar;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        //Actionbar and its title;

        actionBar = getSupportActionBar();
        actionBar.setTitle("Profile");

        //init
        firebaseAuth = FirebaseAuth.getInstance();

        actionBar.setTitle("Home"); //change actionbar title
        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.iConnect)));
        HomeFragment fragment1 = new HomeFragment();
        FragmentTransaction ft1 = getSupportFragmentManager().beginTransaction();
        ft1.replace(R.id.container, fragment1, "");
        ft1.commit();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment;
                switch (item.getItemId()) {
                    case R.id.home:
                        //home fragment transaction
                        actionBar.setTitle("Home"); //change actionbar title
                        HomeFragment fragment1 = new HomeFragment();
                        FragmentTransaction ft1 = getSupportFragmentManager().beginTransaction();
                        ft1.replace(R.id.container, fragment1, "");
                        ft1.commit();
                        return true;
                    case R.id.users:
                        actionBar.setTitle("Users"); //change actionbar title
                        UsersFragment fragment2 = new UsersFragment();
                        FragmentTransaction ft2 = getSupportFragmentManager().beginTransaction();
                        ft2.replace(R.id.container, fragment2, "");
                        ft2.commit();
                        return true;


                    case R.id.profile:
                        actionBar.setTitle("Profile"); //change actionbar title
                        ProfileFragment fragment3 = new ProfileFragment();
                        FragmentTransaction ft3 = getSupportFragmentManager().beginTransaction();
                        ft3.replace(R.id.container, fragment3, "");
                        ft3.commit();
                        return true;

//                    case R.id.chat:
//                        actionBar.setTitle("Chat"); //change actionbar title
//                        ChatListFragment fragment4 = new ChatListFragment();
//                        FragmentTransaction ft4 = getSupportFragmentManager().beginTransaction();
//                        ft4.replace(R.id.container, fragment4, "");
//                        ft4.commit();
//                        return true;
                }

                return false;
            }
        });


    }
    private void checkUserStatus(){
        //get current user

        FirebaseUser user = firebaseAuth.getCurrentUser();
        if(user != null){
            // user is signed in stay here
            //mProfileTv.setText(user.getEmail());
        }
        else{
            //user not signed in, go to main activity
            startActivity(new Intent(DashboardActivity.this, MainActivity.class));
        }

    }

    @Override
    protected void onStart() {
        //check on start of app
        checkUserStatus();
        super.onStart();
    }

    //inflate option menu


}