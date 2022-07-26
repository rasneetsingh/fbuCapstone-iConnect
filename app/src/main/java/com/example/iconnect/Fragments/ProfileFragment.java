package com.example.iconnect.Fragments;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.iconnect.AddPostActivity;
import com.example.iconnect.MainActivity;
import com.example.iconnect.MapActivity;
import com.example.iconnect.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;


public class ProfileFragment extends Fragment {
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    TextView tvName;
    TextView tvEmail;
    TextView tvschool;
    TextView tvcountry;
    TextView tvmajor;
    TextView tvwork;
    Button mapBtn;
    ImageView editprofile;

    ProgressDialog pd;

    public ProfileFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        mapBtn = view.findViewById(R.id.mapBtn);
        mapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goMapActivity();
            }
        });


        editprofile = view.findViewById(R.id.edit);
        editprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditProfileDialog();
            }
        });

        //init firebase
       firebaseAuth = FirebaseAuth.getInstance();
       user = firebaseAuth.getCurrentUser();
       firebaseDatabase = FirebaseDatabase.getInstance();
       databaseReference = firebaseDatabase.getReference("Users");

        tvName = view.findViewById(R.id.nameTv);
        tvschool = view.findViewById(R.id.uniTv);
        tvEmail = view.findViewById(R.id.emailTv);
        tvcountry = view.findViewById(R.id.countryTv);
        tvmajor = view.findViewById(R.id.majorTv);
        tvwork = view.findViewById(R.id.workTv);


        //init progress dialog
        pd = new ProgressDialog(getActivity());

        Query query = databaseReference.orderByChild("email").equalTo(user.getEmail());

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                //checks until required data get
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    //get data
                    String name = ""+ ds.child("name").getValue();
                    String email = ""+ ds.child("email").getValue();
                    String uni = ""+ ds.child("school").getValue();
                    String country = ""+ds.child("country").getValue();
                    String major = ""+ds.child("major").getValue();
                    String work = ""+ds.child("work").getValue();

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

        return view;
    }

    private void showEditProfileDialog() {
        String options [] = {"Edit Name", "Edit School", "Edit Country", "Edit Major","Edit Work"};

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Choose Action");

        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //handle dialog item clicks
                if (which == 0){
                    //edit name clicked
                    pd.setMessage("Updating Name");
                    showProfileUpdateDialog("name");
                }
                else if (which == 1){
                    //edit school clicked
                    pd.setMessage("Updating School");
                    showProfileUpdateDialog("school");


                }
                else if(which == 2){
                    //edit country clicked
                    pd.setMessage("Updating Country");
                    showProfileUpdateDialog("country");

                }
                else if(which == 3){
                    //edit major clicked
                    pd.setMessage("Updating Major");
                    showProfileUpdateDialog("major");

                }
                else if(which == 4){
                    //edit work clicked
                    pd.setMessage("Updating Work");
                    showProfileUpdateDialog("work");

                }
            }
        });
        //create and show dialog
        builder.create().show();
    }

    private void showProfileUpdateDialog(String key) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Update "+ key);

        LinearLayout linearLayout = new LinearLayout(getActivity());
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setPadding(10, 10, 10, 10);

        //add edit text
        EditText editText = new EditText(getActivity());
        editText.setHint("Enter " + key);
        linearLayout.addView(editText);

        builder.setView(linearLayout);

        //add buttons in dialog to update
        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //input text from edit text
                String value = editText.getText().toString().trim();
                //validate if user has entered something or not
                if (!TextUtils.isEmpty(value)){
                    pd.show();
                    HashMap<String, Object> result = new HashMap<>();
                    result.put(key, value);

                    databaseReference.child(user.getUid()).updateChildren(result)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    pd.dismiss();
                                    Toast.makeText(getActivity(), "Updated...", Toast.LENGTH_SHORT).show();

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    pd.dismiss();
                                    Toast.makeText(getActivity(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();

                                }
                            });



                }
                else{
                    Toast.makeText(getActivity(),"Enter "+ key, Toast.LENGTH_SHORT).show();

                }


            }
        });

        //add buttons in dialog to cancel

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        //create and show dialog
        builder.create().show();
    }

    private void goMapActivity() {
        Intent i = new Intent(getActivity(), MapActivity.class);
        startActivity(i);
        getActivity().finish();

    }


    private void checkUserStatus(){
        //get current user
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if(user != null){

        }
        else{
            startActivity(new Intent(getActivity(), MainActivity.class));
            getActivity().finish();

        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
       setHasOptionsMenu(true); //to show menu options in fragment
        super.onCreate(savedInstanceState);
    }
    //infllate options menu

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //get item id
        int id = item.getItemId();
        if(id==R.id.action_logout){
            firebaseAuth.signOut();
            checkUserStatus();
        }
        if(id == R.id.action_add){
            startActivity(new Intent(getActivity(), AddPostActivity.class));

        }
       return super.onOptionsItemSelected(item);
    }
}