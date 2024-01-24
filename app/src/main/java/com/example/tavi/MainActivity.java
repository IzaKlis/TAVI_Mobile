package com.example.tavi;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.room.Database;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.ktx.Firebase;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private static final int REQUEST_CODE = 101;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ImageView addImagePost, sendImagePost;
    EditText inputPostDesc;
    Uri imageUri;
    ProgressDialog mLoadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("TravelVista");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.icons8_menu);

        addImagePost = findViewById(R.id.addImagePost);
        sendImagePost = findViewById(R.id.send_post_imageView);
        inputPostDesc = findViewById(R.id.inputAddPost);

        mLoadingBar = new ProgressDialog(this);

        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navView);

        View view = navigationView.inflateHeaderView(R.layout.drawer_header);

        navigationView.setNavigationItemSelectedListener(this);

        sendImagePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddPost();
            }
        });
        addImagePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE && resultCode==RESULT_OK && data!=null){
            imageUri = data.getData();
            addImagePost.setImageURI(imageUri);
        }
    }

    private void AddPost(){
        String postDesc = inputPostDesc.getText().toString();
        if(postDesc.isEmpty() || postDesc.length()<3){
            inputPostDesc.setError("Proszę napisać treść posta.");
        }else if(imageUri == null){
            Toast.makeText(this,"Proszę wybrać zdjęcie.", Toast.LENGTH_SHORT).show();
        } else {
            mLoadingBar.setTitle("Dodawanie posta.");
            mLoadingBar.setCanceledOnTouchOutside(false);
            mLoadingBar.show();
        }
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int itemId = menuItem.getItemId();

        if (itemId == R.id.home) {
            Toast.makeText(this, "Strona główna", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        } else if (itemId == R.id.profile) {
            Toast.makeText(this, "Profil", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, SetupActivity.class);
            startActivity(intent);
            finish();
        } else if (itemId == R.id.blog) {
            Toast.makeText(this, "Blog", Toast.LENGTH_SHORT).show();
        } else if (itemId == R.id.addFriends) {
            Toast.makeText(this, "Znajomi", Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent(MainActivity.this, FindFriendActivity.class);
//            startActivity(intent);
//            finish();
        } else if (itemId == R.id.chat) {
            Toast.makeText(this, "Chat", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, ChatActivity.class);
            startActivity(intent);
            finish();
        } else if (itemId == R.id.logout) {
            Toast.makeText(this, "Wyloguj się", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home)
        {
            drawerLayout.openDrawer(GravityCompat.START);
            return true;
        }
        return true;
    }
}