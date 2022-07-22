package com.example.iconnect.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.iconnect.ChatActivity;
import com.example.iconnect.Models.ModelUser;
import com.example.iconnect.R;
import com.example.iconnect.ThereProfileActivity;

import java.util.List;

public class Users extends RecyclerView.Adapter<Users.MyHolder>  {

    Context context;
    List<ModelUser> userList;

    public Users(Context context, List<ModelUser> userList) {
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //inflate layout(row_user.xml)
        View view = LayoutInflater.from(context).inflate(R.layout.row_users, parent, false);

        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        //get data

        String hisUID = userList.get(position).getUid();
        String userName = userList.get(position).getName();

        String userschool= userList.get(position).getSchool();
        String userCountry = userList.get(position).getCountry();


        //set data
        holder.mNameTv.setText(userName);
        holder.mSchoolTv.setText(userschool);
        holder.mCountryTv.setText(userCountry);


        //handle item click
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                holder.chatBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        Intent intent = new Intent (context, ChatActivity.class);
                        intent.putExtra("hisUid", hisUID);
                        context.startActivity(intent);

                    }
                });

                holder.profileBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, ThereProfileActivity.class);
                        intent.putExtra("uid", hisUID);
                        context.startActivity(intent);

                    }
                });

            }
        });

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    //view holder class

    class MyHolder extends RecyclerView.ViewHolder{

        TextView mNameTv;
        TextView mSchoolTv;
        TextView mCountryTv;
        Button  profileBtn;
        ImageView chatBtn;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            mNameTv = itemView.findViewById(R.id.userPname);
            mSchoolTv = itemView.findViewById(R.id.userPSchoolname);
            mCountryTv = itemView.findViewById(R.id.userPCountryname);
            profileBtn  = itemView.findViewById(R.id.viewProfile);
            chatBtn = itemView.findViewById(R.id.userChat);
        }
    }
}
