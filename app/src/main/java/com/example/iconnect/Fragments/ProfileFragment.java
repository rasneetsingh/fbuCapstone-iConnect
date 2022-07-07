package com.example.iconnect.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.iconnect.AddPostActivity;
import com.example.iconnect.MainActivity;
import com.example.iconnect.MapActivity;
import com.example.iconnect.R;
import com.example.iconnect.VideoActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class ProfileFragment extends Fragment {

    //firebase
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;


    //views from xml
    //ImageView profileimg;
    TextView tvName;
    TextView tvEmail;
    TextView tvschool;
    TextView tvcountry;
    TextView tvmajor;
    TextView tvwork;
    Button liveBtn;
    Button mapBtn;



    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);




//        liveBtn = view.findViewById(R.id.livevid);
//        liveBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                goVideoActivity();
//            }
//        });

        mapBtn = view.findViewById(R.id.mapBtn);
        mapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goMapActivity();
            }
        });
//
//

       //init firebase
       firebaseAuth = FirebaseAuth.getInstance();
       user = firebaseAuth.getCurrentUser();
       firebaseDatabase = FirebaseDatabase.getInstance();
       databaseReference = firebaseDatabase.getReference("Users");

       //init views
        //profileimg = view.findViewById(R.id.profileimg);
        tvName = view.findViewById(R.id.nameTv);
        tvschool = view.findViewById(R.id.uniTv);
        tvEmail = view.findViewById(R.id.emailTv);
        tvcountry = view.findViewById(R.id.countryTv);
        tvmajor = view.findViewById(R.id.majorTv);
        tvwork = view.findViewById(R.id.workTv);

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
                    //String image = ""+ ds.child("image").getValue();
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
//                    try{
//                        //if image is received then set
//                        Picasso.get().load(image).into(profileimg);
//
//                    }
//                    catch(Exception e){
//                        //if there is any exception while getting image then set default.
//                        Picasso.get().load(R.drawable.ic_default_img).into(profileimg);
//
//
//                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;
    }

    private void goMapActivity() {
        Intent i = new Intent(getActivity(), MapActivity.class);
        startActivity(i);
        getActivity().finish();

    }

    private void goVideoActivity() {
        Intent i = new Intent(getActivity(), VideoActivity.class);
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
//        inflater.inflate(R.menu.menu_main, menu);
//        super.onCreateOptionsMenu(menu, inflater);
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