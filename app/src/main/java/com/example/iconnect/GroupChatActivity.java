package com.example.iconnect;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.example.iconnect.Adapters.AdapterGroupChat;
import com.example.iconnect.Models.ModelGroupChat;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class GroupChatActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference usersDbRef;

    String groupId, myGroupRole="";

    Toolbar toolbar;
    TextView groupTitleTv;
    ImageButton attachBtn;
    ImageButton sendBtn;
    EditText messageEt;
    private RecyclerView chatRv;
    private ArrayList<ModelGroupChat> groupChatList;
    private AdapterGroupChat adapterGroupChat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_chat);

        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.actionbar)));

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Group Chat");

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        toolbar = findViewById(R.id.toolbar);
        groupTitleTv = findViewById(R.id.groupTitleTv);
        attachBtn = findViewById(R.id.attachBtn);
        sendBtn = findViewById(R.id.sendBtn);
        messageEt = findViewById(R.id.messageEt);
        chatRv = findViewById(R.id.chatRv);

        firebaseAuth = FirebaseAuth.getInstance();

        Intent intent = getIntent();
        groupId = intent.getStringExtra("groupId");

        loadGroupInfo();
        loadGroupMessages();
        loadMyGroupRole();

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = messageEt.getText().toString().trim();
                if(TextUtils.isEmpty(message)){
                    Toast.makeText(GroupChatActivity.this, "Cant send empty message", Toast.LENGTH_SHORT).show();
                }
                else{
                    //send message
                    sendMessage(message);
                }
            }
        });
    }

    private void loadMyGroupRole() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Groups");
        ref.child(groupId).child("Participants")
                .orderByChild("uid").equalTo(firebaseAuth.getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot ds: snapshot.getChildren()){
                            myGroupRole = ""+ds.child("role").getValue();
                            //refresh menu items
                            invalidateOptionsMenu();

                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

    }

    private void loadGroupMessages() {

        //init list
        groupChatList = new ArrayList<>();

        DatabaseReference ref =  FirebaseDatabase.getInstance().getReference("Groups");
        ref.child(groupId).child("Messages").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                groupChatList.clear();
                for(DataSnapshot ds: snapshot.getChildren()){
                    ModelGroupChat model = ds.getValue(ModelGroupChat.class);
                    groupChatList.add(model);
                }
                //adapter
                adapterGroupChat = new AdapterGroupChat(GroupChatActivity.this, groupChatList);
                chatRv.setAdapter(adapterGroupChat);



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void sendMessage(String message) {
        String timestamp = ""+System.currentTimeMillis();

        //set up message data
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sender", ""+ firebaseAuth.getUid());
        hashMap.put("message", ""+ message);
        hashMap.put("timestamp", ""+timestamp);
        hashMap.put("type", ""+"text");

        //add in database
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Groups");
        reference.child(groupId).child("Messages").child(timestamp).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                //message sent
                //clear messageEt
                messageEt.setText("");

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(GroupChatActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void loadGroupInfo() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Groups");
        ref.orderByChild("groupId").equalTo(groupId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds: snapshot.getChildren()){
                    String groupTitle = ""+ ds.child("groupTitle").getValue();
                    String groupDescription = ""+ds.child("groupDescription").getValue();
                    String timestamp = ""+ds.child("timestamp").getValue();
                    String createdBy = ""+ds.child("createdBy").getValue();

                    groupTitleTv.setText(groupTitle);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        menu.findItem(R.id.action_logout).setVisible(false);
        menu.findItem(R.id.action_create_group).setVisible(false);
        menu.findItem(R.id.action_add).setVisible(false);

        if (myGroupRole.equals("creator") || myGroupRole.equals("admin")){
            //i am admin creator, show add person
            menu.findItem(R.id.action_add_participant).setVisible(true);
        }
        else{
            menu.findItem(R.id.action_add_participant).setVisible(false);
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_add_participant){
            Intent intent = new Intent(this, GroupParticipantsAddActivity.class);
            intent.putExtra("groupId", groupId);
            startActivity(intent);

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed(); //go previous activity
        return super.onSupportNavigateUp();
    }
}