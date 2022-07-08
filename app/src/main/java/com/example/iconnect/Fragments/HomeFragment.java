package com.example.iconnect.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.iconnect.Adapters.AdapterPosts;
import com.example.iconnect.AddPostActivity;
import com.example.iconnect.MainActivity;
import com.example.iconnect.Models.ModelPost;
import com.example.iconnect.R;
import com.example.iconnect.SettingsActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {
    FirebaseAuth firebaseAuth;


    RecyclerView recyclerView;
    List<ModelPost> postList;
    AdapterPosts adapterPosts;


    public HomeFragment() {
        // Required empty public constructor
    }

//    public static HomeFragment newInstance(String param1, String param2) {
//
//        return null;
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_home, container, false);

        firebaseAuth = FirebaseAuth.getInstance();

        //recycler view and its properties
        recyclerView = view.findViewById(R.id.postRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        //show newest post first, for this load from last
        layoutManager.setStackFromEnd(true);
        layoutManager.setReverseLayout(true);

        //set layout to recyclerview
        recyclerView.setLayoutManager(layoutManager);

        //init post list

        postList = new ArrayList<>();

        loadposts();
        return view;
    }

    private void loadposts() {
        //path of all posts
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Posts");
        //get all data from this ref
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                postList.clear();
                for(DataSnapshot ds: snapshot.getChildren()){
                    ModelPost modelPost = ds.getValue(ModelPost.class);
                    postList.add(modelPost);

                    //adapter
                    adapterPosts = new AdapterPosts(getActivity(), postList);

                    //set adapter to recycler view
                    recyclerView.setAdapter(adapterPosts);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //incase of error
                Toast.makeText(getActivity(), ""+ error.getMessage(), Toast.LENGTH_SHORT ).show();

            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true); //to show menu options in fragment
        super.onCreate(savedInstanceState);
    }
    //inflate options menu

    @Override
    public void onCreateOptionsMenu( @NonNull Menu menu,  @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);

        //searchview to search posts by post title/description
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        //search listener

        //search listener
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //called when user press any search button from keyboard
                //if query is not empty then search
                if(!TextUtils.isEmpty(query.trim())){
                    //search text contains text.
                    searchPosts(query);

                }
                else{
                    //search all text, get all users
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                //called whenever user press any single letter

                if(!TextUtils.isEmpty(query.trim())){
                    //search text contains text.
                    searchPosts(query);

                }
                else{
                    //search all text, get all users
                }
                return false;
            }
        });


        super.onCreateOptionsMenu(menu, inflater);

    }

    private void searchPosts(String query) {

        //get current user
        FirebaseUser fUser = FirebaseAuth.getInstance().getCurrentUser();
        //get path of database named "users" containing users info
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Posts");
        //get all data from path
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                postList.clear();
                for(DataSnapshot ds: snapshot.getChildren()){
                    ModelPost modelPost= ds.getValue(ModelPost.class);

                    //search by condition: name, email
                    //get all searched users except currently signed in user
                    if(!modelPost.getuid().equals(fUser.getUid())){
                        if(modelPost.getpTitle().toLowerCase().contains(query.toLowerCase()) || modelPost.getpdDescr().toLowerCase().contains(query.toLowerCase())){
                            postList.add(modelPost);

                        }
                    }
                    //adapter
                    adapterPosts = new AdapterPosts(getActivity(), postList);
                    //refresh adapter
                    adapterPosts.notifyDataSetChanged();
                    //set adapter to recycler view
                    recyclerView.setAdapter(adapterPosts);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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
        if(id == R.id.settings){
            startActivity(new Intent(getActivity(), SettingsActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }


    private void checkUserStatus() {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if(user != null){

        }
        else{
            startActivity(new Intent(getActivity(), MainActivity.class));
            getActivity().finish();

        }

    }
}