package com.example.tavi;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.widget.Toolbar;

import com.google.firebase.database.DatabaseReference;

public class FindFriendActivity extends AppCompatActivity {
    Toolbar toolbar;
    Button returnBtn;

    DatabaseReference mUsersRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_friend);

        toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Znajdź znajomego");

        returnBtn = findViewById(R.id.returnBtn);

        returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FindFriendActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //LoadUsers("");
    }

    private void LoadUsers(String s){
        //DownloadManager.Query query*
    }
}