package com.example.iconnect;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class GroupCreateActivity extends AppCompatActivity {

    private ActionBar actionBar;

    private FirebaseAuth firebaseAuth;

    ImageView groupIconIv;
    EditText groupTitleEt;
    EditText groupDescr;
    Button createGroupBtn;
    ProgressDialog progressDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_create);

        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle("Create Group");

        groupIconIv = findViewById(R.id.groupIconIv);
        groupTitleEt = findViewById(R.id.groupName);
        groupDescr = findViewById(R.id.groupDescription);
        createGroupBtn = findViewById(R.id.createGroupBtn);

        firebaseAuth = FirebaseAuth.getInstance();
        checkUser();

        //handle click event
        createGroupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCreatingGroup();
            }
        });


    }

    private void startCreatingGroup() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Creating Group");

        //input title, description
        String groupTitle = groupTitleEt.getText().toString().trim();
        String groupDescription = groupDescr.getText().toString().trim();

        if(TextUtils.isEmpty(groupTitle)){
            Toast.makeText(this, "Please enter group title", Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog.show();


        String g_timestamp = ""+ System.currentTimeMillis();
        createGroup(""+ g_timestamp, ""+groupTitle, ""+groupDescription);

    }

    private void createGroup(String g_timestamp, String groupTitle, String groupDescription) {
        //set up info of group
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("groupId",""+g_timestamp);
        hashMap.put("groupTitle", ""+ groupTitle);
        hashMap.put("groupDescription", ""+ groupDescription);
        hashMap.put("timeStamp", ""+ g_timestamp);
        hashMap.put("createdBy", ""+firebaseAuth.getUid());

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Groups");
        ref.child(g_timestamp).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                //created successfully
                //adding current user in group's participants list
                HashMap<String, String> hashMap1 = new HashMap<>();
                hashMap1.put("uid", firebaseAuth.getUid());
                hashMap1.put("role", "creator");
                hashMap1.put("timestamp", g_timestamp);

                DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference("Groups");
                ref1.child(g_timestamp).child("Participants").child(firebaseAuth.getUid())
                        .setValue(hashMap1)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {

                                progressDialog.dismiss();
                                Toast.makeText(GroupCreateActivity.this, "Group created",Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                                progressDialog.dismiss();
                                Toast.makeText(GroupCreateActivity.this, ""+e.getMessage(),Toast.LENGTH_SHORT).show();

                            }
                        });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(GroupCreateActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void checkUser() {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null){
            actionBar.setSubtitle(user.getEmail());
        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}