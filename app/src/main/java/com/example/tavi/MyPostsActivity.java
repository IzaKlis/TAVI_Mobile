package com.example.tavi;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tavi.data.models.Post;
import com.example.tavi.data.viewModels.PostViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MyPostsActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    DrawerLayout drawerLayout;
    PostViewModel postViewModel;

    FirebaseAuth mAuth;
    FirebaseUser currentUser;
    String userId;
    String newImagePath;
    private MyPostAdapter myPostAdapter;
    private static final int PICK_IMAGE_REQUEST = 1;

    Button returnBtnBlog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_blog);
        recyclerView = findViewById(R.id.recyclerview1);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        drawerLayout = findViewById(R.id.drawerLayout1);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            userId = currentUser.getUid();
        }

        myPostAdapter = new MyPostAdapter();
        recyclerView.setAdapter(myPostAdapter);

        postViewModel = new ViewModelProvider(this).get(PostViewModel.class);
        LiveData<List<Post>> postList = postViewModel.findPostsByUserId(userId);
        Log.d("MyPostsActivity", " postList.getValue()");
        postList.observe(this, posts -> {
            if (posts != null) {
                myPostAdapter.setPostList(posts);
            } else {
                Log.d("MyPostsActivity", "Posts list is null");
            }
        });

        returnBtnBlog = findViewById(R.id.returnBtnBlog);

        returnBtnBlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyPostsActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            newImagePath = imageUri.toString();
            Toast.makeText(MyPostsActivity.this, "Dodano zdjęcie", Toast.LENGTH_SHORT).show();
        }
    }

    public class MyPostHolder extends RecyclerView.ViewHolder {

        Post post;
        private final TextView profileUsername;
        private final ImageView profileImage;
        private final ImageView postImage;
        private final TextView timesAgo;
        private final TextView postDesc;
        private final ImageView deletePostImage;
        private final ImageView editPostImage;
        DatabaseReference mUserRef;


        public MyPostHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.single_view_my_post, parent, false));
            profileUsername = itemView.findViewById(R.id.profileUsernamePost);
            profileImage = itemView.findViewById(R.id.profileImagePost);
            timesAgo = itemView.findViewById(R.id.timesAgo);
            postDesc = itemView.findViewById(R.id.postDesc);
            postImage = itemView.findViewById(R.id.postImage);
            deletePostImage = itemView.findViewById(R.id.deletePostIcon);
            editPostImage = itemView.findViewById(R.id.editPostIcon);
            mUserRef = FirebaseDatabase.getInstance("https://tavi-8c1c2-default-rtdb.europe-west1.firebasedatabase.app/").getReference().child("Users");


            deletePostImage.setOnClickListener(v -> {
                if (post != null) {
                    Log.d("PostViewModel", "Deleting post: " + post.getId());
                    postViewModel.delete(post);
                }
            });

            editPostImage.setOnClickListener(v -> {
                if (post != null) {
                    handleEditPost(post);
                }
            });
        }

        private void handleEditPost(Post post) {
            AlertDialog.Builder builder = new AlertDialog.Builder(itemView.getContext());
            builder.setTitle("Edytuj post");

            View dialogView = LayoutInflater.from(itemView.getContext()).inflate(R.layout.edit_post, null);
            builder.setView(dialogView);

            final EditText inputContent = dialogView.findViewById(R.id.editTextContent);
            final Button btnPickImage = dialogView.findViewById(R.id.btnPickImage);

            inputContent.setInputType(InputType.TYPE_CLASS_TEXT);
            inputContent.setText(post.getContent());


            btnPickImage.setOnClickListener(view -> {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
            });

            builder.setPositiveButton("Zapisz", (dialog, which) -> {
                String newContent = inputContent.getText().toString();
                postViewModel.updatePostContent(post, newContent, newImagePath);
            });

            builder.setNegativeButton("Anuluj", (dialog, which) -> dialog.cancel());
            builder.show();

        }


        public void bind(Post post) {
            this.post = post;
            fetchUsernameAndPicture(post.getUserId());
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault());
            timesAgo.setText(sdf.format(post.getDateCreated()));
            postDesc.setText(post.getContent());
            if (post.getPicture() != null && !post.getPicture().isEmpty()) {
                loadImage(post.getPicture(), postImage);
                postImage.setVisibility(View.VISIBLE);
            } else {
                postImage.setVisibility(View.GONE);
            }
        }

        private void loadImage(String imageUrl, ImageView imageView) {
            Glide.with(itemView.getContext())
                    .load(Uri.parse(imageUrl))
                    .into(imageView);
        }
        private void fetchUsernameAndPicture(String userId) {
            mUserRef.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        String username = dataSnapshot.child("username").getValue().toString();
                        profileUsername.setText(username);
                        String profileImageUrl = dataSnapshot.child("profileImage").getValue().toString();
                        Picasso.get().load(profileImageUrl).into(profileImage);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    profileUsername.setText("Użytkownik nieznany");
                }
            });
        }
    }

    public class MyPostAdapter extends RecyclerView.Adapter<MyPostHolder> {
        private List<Post> postList;

        private Uri selectedImageUri;


        public void setSelectedImageUri(Uri imageUri) {
            this.selectedImageUri = imageUri;
        }

        public MyPostAdapter() {
            this.postList = new ArrayList<>();
        }

        @NonNull
        @Override
        public MyPostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            return new MyPostHolder(inflater, parent);
        }

        public void setPostList(List<Post> posts) {
            this.postList = posts;
            notifyDataSetChanged();
        }

        @Override
        public void onBindViewHolder(@NonNull MyPostHolder holder, int position) {
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
