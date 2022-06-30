package com.example.iconnect.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.iconnect.ChatActivity;
import com.example.iconnect.Models.ModelUser;
import com.example.iconnect.R;
import com.squareup.picasso.Picasso;

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
        String userImage = userList.get(position).getImage();
        String userName = userList.get(position).getName();
        String userEmail = userList.get(position).getEmail();
        String userschool= userList.get(position).getschool();
        String userCountry = userList.get(position).getcountry();
        String userMajor = userList.get(position).getMajor();

        //set data
        holder.mNameTv.setText(userName);
        holder.mEmailTv.setText(userEmail);
        holder.mSchoolTv.setText(userschool);
        holder.mCountryTv.setText(userCountry);
        holder.mMajorTv.setText(userMajor);


        try{
            Picasso.get().load(userImage)
                    .placeholder(R.drawable.ic_default_img)
                    .into(holder.avatarIv);

        }
        catch(Exception e){

        }
        //handle item click
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, ""+ userEmail, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent (context, ChatActivity.class);
                intent.putExtra("hisUid", hisUID);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    //view holder class

    class MyHolder extends RecyclerView.ViewHolder{

        ImageView avatarIv;
        TextView mNameTv;
        TextView mEmailTv;
        TextView mSchoolTv;
        TextView mCountryTv;
        TextView mMajorTv;


        public MyHolder(@NonNull View itemView) {
            super(itemView);

            //init views
            avatarIv = itemView.findViewById(R.id.avatarIv);
            mNameTv = itemView.findViewById(R.id.userPname);
            mEmailTv = itemView.findViewById(R.id.userPemail);
            mSchoolTv = itemView.findViewById(R.id.userPSchoolname);
            mCountryTv = itemView.findViewById(R.id.userPCountryname);
            mMajorTv = itemView.findViewById(R.id.userMajor);

        }
    }
}
