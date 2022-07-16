package com.example.iconnect;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class ThereProfileActivity extends AppCompatActivity {

    FirebaseAuth fireebaseAuth;
    String uid;
    FirebaseUser user;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ActionBar actionBar;

    TextView tvName;
    TextView tvEmail;
    TextView tvschool;
    TextView tvcountry;
    TextView tvmajor;
    TextView tvwork;
    Button liveBtn;
    Button mapBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_there_profile);



        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.actionbar)));

        actionBar = getSupportActionBar();
        actionBar.setTitle("Profile");

        //enable back button in action bar
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        fireebaseAuth = FirebaseAuth.getInstance();


        tvName = findViewById(R.id.nameTv);
        tvschool = findViewById(R.id.uniTv);
        tvEmail = findViewById(R.id.emailTv);
        tvcountry = findViewById(R.id.countryTv);
        tvmajor = findViewById(R.id.majorTv);
        tvwork = findViewById(R.id.workTv);

        Intent intent = getIntent();
        uid = intent.getStringExtra("uid");

        Query query = FirebaseDatabase.getInstance().getReference("Users").orderByChild("uid").equalTo(uid);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot ds: snapshot.getChildren()){
                    //get data
                    String name = ""+ ds.child("name").getValue();
                    String email = ""+ ds.child("email").getValue();
                    String uni = ""+ ds.child("school").getValue();
                    String country = ""+ds.child("country").getValue();
                    String major = ""+ds.child("major").getValue();
                    String work = ""+ds.child("work").getValue();


                    //set data
                    tvwork.setText(work);
                    tvmajor.setText(major);
                    tvcountry.setText(country);
                    tvName.setText(name);
                    tvschool.setText(uni);
                    tvEmail.setText(email);


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        checkUserStatus();


    }

    private void checkUserStatus(){

        FirebaseUser user = fireebaseAuth.getCurrentUser();
        if (user!= null){

        }
        else{
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        menu.findItem(R.id.action_add).setVisible(false);
        menu.findItem(R.id.action_logout).setVisible(false);
        menu.findItem(R.id.action_create_group).setVisible(false);
        menu.findItem(R.id.action_add).setVisible(false);
        menu.findItem(R.id.action_add_participant).setVisible(false);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logout){
            fireebaseAuth.signOut();
            checkUserStatus();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed(); //go to previous activity
        return super.onSupportNavigateUp();
    }
}