package com.example.iconnect;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 100;
    private static final String TAG = "FACEBOOK";
    private CallbackManager mCallbackManager;


    GoogleSignInClient mGoogleSignInClient;

    EditText loginusername;
    EditText loginpassword;
    Button loginbtn;
    TextView noaccount;
    ProgressDialog pd;
    SignInButton mGoogleLogin;
    LoginButton facebookLogin;

    private FirebaseAuth mAuth;
    FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.actionbar)));

        //Actionbar and its title;
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Log in");


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        //enable back button
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);



        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


        mAuth = FirebaseAuth.getInstance();

        //init
        loginusername = findViewById(R.id.loginuser);
        loginpassword = findViewById(R.id.loginPassword);
        loginbtn = findViewById(R.id.btnlogin);
        noaccount = findViewById(R.id.nothaveaccount);
        mGoogleLogin = findViewById(R.id.googleLogin);


        //loginbtnclick
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //input data
                String email = loginusername.getText().toString();
                String password = loginpassword.getText().toString().trim();
                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    //invalid email pattern set error
                    loginusername.setError("Invalid Email");
                    loginusername.setFocusable(true);
                }
                else{
                    //valid email pattern
                    loginUser(email, password);

                }

            }
        });

        //not have account click

        noaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                finish();

            }
        });



        //handle google login btn click

        mGoogleLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //begin login process

                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
                
            }
        });

        mCallbackManager = CallbackManager.Factory.create();
        facebookLogin = findViewById(R.id.facebookLogin);
        facebookLogin.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFacebookAccessToken(loginResult.getAccessToken());


            }

            @Override
            public void onCancel() {
                Toast.makeText(LoginActivity.this, "Login Cancelled", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onError(@NonNull FacebookException e) {

            }
        });


        //init progress dialog
        pd= new ProgressDialog(this);
        pd.setMessage("Loggingin...");


    }


    private void handleFacebookAccessToken(AccessToken accessToken) {
        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
        mAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(LoginActivity.this, "Log in Successful", Toast.LENGTH_SHORT).show();
                    FirebaseUser user = mAuth.getCurrentUser();

                    Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                }
                else{
                    Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }



    private void loginUser(String email, String password) {
        //show progress dialog
        pd.show();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            //Get user email and uid from auth
                            FirebaseUser user = mAuth.getCurrentUser();

                            //if user is sign in first time then get and show user info
                            if(task.getResult().getAdditionalUserInfo().isNewUser()){
                                String email = user.getEmail();
                                String uid = user.getUid();
                                //when user is registered store user info in firebase realtime database using hashmap
                                HashMap<Object, String> hashMap = new HashMap<>();
                                //put info in hashmap
                                hashMap.put("email", email);
                                hashMap.put("uid", uid);
                                hashMap.put("name", "" );
                                hashMap.put("bio", "" );
                                hashMap.put("school", "");
                                hashMap.put("image", "");

                                //firebase database instance
                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                //path to store user data named "users"
                                DatabaseReference reference = database.getReference("Users");
                                //put data within hashmap in database
                                reference.child(uid).setValue(hashMap);

                            }

                            //dismiss progress dialog
                            pd.dismiss();
                            // Sign in success, update UI with the signed-in user's information


                            startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
                            finish();

                        } else {
                            //dismiss progress dialog
                            pd.dismiss();
                            // If sign in fails, display a message to the user.

                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //error, show error message.
                        Toast.makeText(LoginActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed(); //go previous activity
        return super.onSupportNavigateUp();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        mCallbackManager.onActivityResult(requestCode, resultCode, data);

        //result returned from launching the intent from googlesignin api

        if(requestCode == RC_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try{
                //google sign in was successful, authenticate with firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);

            } catch(ApiException e) {
                //google sign in failed, update UI.
                Log.w(TAG, "Google sign in failed", e);
                Toast.makeText(this, ""+ e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        }
    }


    private void firebaseAuthWithGoogle(GoogleSignInAccount acct){


        AuthCredential firebaseCredential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(firebaseCredential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            // Sign in success, update UI with the signed-in user's information
                            //show user email in toast
                            Toast.makeText(LoginActivity.this, ""+ user.getEmail(), Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "signInWithCredential:success");

                            startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
                            finish();

                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.

                            Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();

                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //get and show error message
                        Toast.makeText(LoginActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }

}