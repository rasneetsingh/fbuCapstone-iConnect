package com.example.iconnect;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {

    //views
    EditText uname;
    EditText countryet;
    EditText schoolEt;
    EditText majorEt;
    EditText etEmail;
    EditText etPassword;
    EditText workEt;
    Button registerbtn;
    TextView haveaccount;

    //progressbar to display while registering user
    ProgressDialog progressDialog;

    //declare an instance of firebaseauth
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.actionbar)));


        //Actionbar and its title
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Create Account");

        //enable back button
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        //init
        uname = findViewById(R.id.Username);
        countryet = findViewById(R.id.Countryname);
        schoolEt = findViewById(R.id.Schoolname);
        etEmail = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        registerbtn = findViewById(R.id.btnRegister);
        haveaccount = findViewById(R.id.haveaccount);
        majorEt = findViewById(R.id.major);
        workEt = findViewById(R.id.work);


        //in the onCreate() method, initilaze the FirebaseAuth instance.
        mAuth = FirebaseAuth.getInstance();


        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Registering User....");

        //handle register btn click;
        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //input email, password
                String name = uname.getText().toString().trim();
                String country = countryet.getText().toString().trim();
                String school = schoolEt.getText().toString().trim();
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                String major = majorEt.getText().toString().trim();
                String work = workEt.getText().toString().trim();
                //validate

                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    //set error and focus to email edittext
                    etEmail.setError("Invalid Email");
                    etEmail.setFocusable(true);
                }else if (name.isEmpty() || country.isEmpty() || school.isEmpty() || major.isEmpty() || work.isEmpty()){
                    uname.setError("name is required");
                    countryet.setError("country is required");
                    schoolEt.setError("school is required");
                    majorEt.setError("major is required");
                    workEt.setError("work is required");
                    etPassword.setFocusable(true);
                }
                else if(password.length()<6){
                    //set error and focus to password edittext
                    etPassword.setError("Password length at least 6 characters");
                    etPassword.setFocusable(true);
                }
                else{
                    registerUser(email, password, name, country, school, major, work);
                }


            }
        });

        //handle login textview click listener
        haveaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class ));

            }
        });


    }

    private void registerUser(String email, String password, String name, String country, String school, String major, String work) {
        //email and password pattern is valid, show progress dialog and start registering user.
        progressDialog.show();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, dismiss dialog and start register activity
                            progressDialog.dismiss();

                            FirebaseUser user = mAuth.getCurrentUser();
                            //Get user email and uid from auth
                            String email = user.getEmail();
                            String uid = user.getUid();
                            //when user is registered store user info in firebase realtime database using hashmap
                            HashMap<Object, String> hashMap = new HashMap<>();
                            //put info in hashmap
                            hashMap.put("email", email);
                            hashMap.put("uid", uid);
                            hashMap.put("name", name );
                            hashMap.put("school", school);
                            hashMap.put("image", "");
                            hashMap.put("country", country);
                            hashMap.put("major", major);
                            hashMap.put("work", work);
                            hashMap.put("pLikes", "0");

                            //firebase database instance
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            //path to store user data named "users"
                            DatabaseReference reference = database.getReference("Users");
                            //put data within hashmap in database
                            reference.child(uid).setValue(hashMap);


                            Toast.makeText(RegisterActivity.this,"Registered... \n"+user.getEmail(), Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(RegisterActivity.this, DashboardActivity.class));
                            finish();

                        } else {
                            // If sign in fails, display a message to the user.
                            progressDialog.dismiss();

                            Toast.makeText(RegisterActivity.this, "Authentication failed.",Toast.LENGTH_SHORT).show();


                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //error, dismiss progress dialog and get and show the error message
                        progressDialog.dismiss();
                        Toast.makeText(RegisterActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });


    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed(); //go previous activity
        return super.onSupportNavigateUp();
    }
}