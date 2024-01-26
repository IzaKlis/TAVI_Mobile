package com.example.tavi;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tavi.data.models.Post;
import com.example.tavi.data.viewModels.PostViewModel;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final int REQUEST_CODE = 101;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;

    FirebaseAuth mAuth;
    FirebaseUser mUser;
    DatabaseReference mUserRef;
    String profileImageUrlV, usernameV;
    CircleImageView profileImageHeader;
    TextView usernameHeader;
    ImageView addImagePost, sendImagePost;
    EditText inputPostDesc;
    Uri imageUri;
    ProgressDialog mLoadingBar;
    PostViewModel postViewModel;
    RecyclerView recyclerView;
    private PostAdapter adapter;

    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        postViewModel = new ViewModelProvider(this).get(PostViewModel.class);

        toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("TravelVista");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.icons8_menu);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mUserRef = FirebaseDatabase.getInstance("https://tavi-8c1c2-default-rtdb.europe-west1.firebasedatabase.app/").getReference().child("Users");

        addImagePost = findViewById(R.id.addImagePost);
        sendImagePost = findViewById(R.id.send_post_imageView);
        inputPostDesc = findViewById(R.id.inputAddPost);
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mLoadingBar = new ProgressDialog(this);

        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navView);

        View view = navigationView.inflateHeaderView(R.layout.drawer_header);
        profileImageHeader = view.findViewById(R.id.profileImage_header);
        usernameHeader = view.findViewById(R.id.username_header);

        navigationView.setNavigationItemSelectedListener(this);

        sendImagePost.setOnClickListener(v -> AddPost());
        addImagePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        LiveData<List<Post>> postList = postViewModel.findAllPosts();
        adapter = new PostAdapter();
        recyclerView.setAdapter(adapter);

        postList.observe(this, posts -> {
            Log.d("MainActivity", "Number of posts: " + (posts != null ? posts.size() : 0));
            adapter.setPostList(posts);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            addImagePost.setImageURI(imageUri);
        }
    }

    private void AddPost() {
        String postDesc = inputPostDesc.getText().toString();
        if (postDesc.isEmpty() || postDesc.length() < 3) {
            inputPostDesc.setError("Proszę napisać treść posta.");
        } else if (imageUri == null) {
            Toast.makeText(this, "Proszę wybrać zdjęcie.", Toast.LENGTH_SHORT).show();
        } else {
            Post post = new Post();
            post.setDateCreated(new Date());
            post.setContent(postDesc);
            post.setUserId(1);
            int initialPostCount = adapter.getItemCount();
            postViewModel.insert(post);
            mLoadingBar.setTitle("Dodawanie posta.");
            mLoadingBar.setCanceledOnTouchOutside(false);
            mLoadingBar.show();

            postViewModel.findAllPosts().observe(this, posts -> {
                if (posts != null && posts.size() > initialPostCount) {
                    mLoadingBar.dismiss();
                    Toast.makeText(MainActivity.this, "Dodano nowy post!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    protected void onStart(){
        super.onStart();
        if(mUser == null){
            SendUserToLoginActivity();
        }else{
            mUserRef.child(mUser.getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()){
                        profileImageUrlV = dataSnapshot.child("profileImage").getValue().toString();
                        usernameV = dataSnapshot.child("username").getValue().toString();
                        Picasso.get().load(profileImageUrlV).into(profileImageHeader);
                        usernameHeader.setText(usernameV);
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(MainActivity.this, "Ups!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void SendUserToLoginActivity() {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
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
            Intent intent = new Intent(MainActivity.this, FindFriendActivity.class);
            startActivity(intent);
            finish();
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
        if (item.getItemId() == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START);
            return true;
        }
        return true;
    }

    public class PostHolder extends RecyclerView.ViewHolder {

        Post post;
        private final TextView profileUsername;
        private final ImageView profileImage;
        private final TextView timesAgo;
        private final TextView postDesc;
        private final ImageView imageView2;
        private final ImageView postImage;
        private final TextView textView4;
        private final ImageView imageView4;
        private final TextView commentCounter;


        public PostHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.single_view_post, parent, false));
            profileUsername = itemView.findViewById(R.id.profileUsernamePost);
            profileImage = itemView.findViewById(R.id.profileImagePost);
            timesAgo = itemView.findViewById(R.id.timesAgo);
            postDesc = itemView.findViewById(R.id.postDesc);
            postImage = itemView.findViewById(R.id.postImage);
            imageView2 = itemView.findViewById(R.id.imageView2);
            textView4 = itemView.findViewById(R.id.textView4);
            imageView4 = itemView.findViewById(R.id.imageView4);
            commentCounter = itemView.findViewById(R.id.commentCounter);
        }

        public void bind(Post post) {
            this.post = post;
            profileUsername.setText(String.valueOf(post.getUserId()));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            timesAgo.setText(sdf.format(post.getDateCreated()));
            postDesc.setText(post.getContent());
        }
    }

    public class PostAdapter extends RecyclerView.Adapter<PostHolder> {
        private List<Post> postList;

        public PostAdapter() {
            this.postList = new ArrayList<>();
        }

        @NonNull
        @Override
        public PostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflate = LayoutInflater.from(parent.getContext());
            return new PostHolder(inflate, parent);
        }
        public void setPostList(List<Post> posts) {
            this.postList = posts;
            notifyDataSetChanged();
        }

        @Override
        public void onBindViewHolder(@NonNull PostHolder holder, int position) {
            if (postList != null && position < postList.size()) {
                Post currentPost = postList.get(position);
                holder.bind(currentPost);
            }
        }

        @Override
        public int getItemCount() {
            return postList != null ? postList.size() : 0;
        }
    }
}