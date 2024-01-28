package com.example.tavi;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import de.hdodenhof.circleimageview.CircleImageView;

public class FriendMyViewHolder extends RecyclerView.ViewHolder {
    CircleImageView profileImageUrl;
    TextView username, hobby;
    public FriendMyViewHolder(@NonNull View itemView){
        super(itemView);
        profileImageUrl = itemView.findViewById(R.id.profileImage);
        username = itemView.findViewById(R.id.username);
        hobby = itemView.findViewById(R.id.hobby);
    }
}
