package com.example.tavi;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.ktx.Firebase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class SetupActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 101;
    CircleImageView profileImageView;
    EditText inputUsername, inputCity, inputCountry, inputAbout;
    Button btnSave, btnBack;
    Uri imageUri;
    ProgressDialog mLoadingBar;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    DatabaseReference mRef;
    StorageReference StorageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mRef = FirebaseDatabase.getInstance("https://tavi-8c1c2-default-rtdb.europe-west1.firebasedatabase.app/").getReference().child("Users");
        StorageRef = FirebaseStorage.getInstance().getReference().child("ProfileImages");

        mLoadingBar = new ProgressDialog(this);
        profileImageView = findViewById(R.id.profile_image);
        inputUsername = findViewById(R.id.inputUsername);
        inputCity = findViewById(R.id.inputCity);
        inputCountry = findViewById(R.id.inputCountry);
        inputAbout = findViewById(R.id.inputHobby);
        btnSave = findViewById(R.id.btnSave);
        btnBack = findViewById(R.id.btnBack);

        btnBack.setVisibility(View.GONE);

        mRef.child(mUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String username = dataSnapshot.child("username").getValue().toString();
                    inputUsername.setText(username);

                    String city = dataSnapshot.child("city").getValue().toString();
                    inputCity.setText(city);

                    String country = dataSnapshot.child("country").getValue().toString();
                    inputCountry.setText(country);

                    String about = dataSnapshot.child("hobby").getValue().toString();
                    inputAbout.setText(about);

                    String profileImageUri = dataSnapshot.child("profileImage").getValue().toString();
                    Glide.with(SetupActivity.this).load(profileImageUri).into(profileImageView);

                    btnSave.setText("Update Profile");
                    btnBack.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        profileImageView.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(intent, REQUEST_CODE);
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveData();
            }
        });
    }

    private void SaveData(){
        String username = inputUsername.getText().toString();
        String city = inputCity.getText().toString();
        String country = inputCountry.getText().toString();
        String about = inputAbout.getText().toString();

        if(username.isEmpty() || username.length()<3){
            showError(inputUsername,"Imię jest za krótkie");
        }else if(!Character.isUpperCase(username.charAt(0))){
            showError(inputUsername,"Imię musi zaczynać się z wielkiej litery");
        }else if(city.isEmpty() || city.length()<3) {
            showError(inputCity, "Nazwa miasta jest za krótka");
        }else if(!Character.isUpperCase(city.charAt(0))){
                showError(inputCity,"Nazwa miasta musi zaczynać się z wielkiej litery");
        }else if(country.isEmpty() || country.length()<3){
            showError(inputCountry,"Nazwa kraju jest za krótka");
        }else if(!Character.isUpperCase(country.charAt(0))){
            showError(inputCountry,"Nazwa kraju musi zaczynać się z wielkiej litery");
        }else if(about.isEmpty() || about.length()<3){
            showError(inputAbout,"Zainteresowania są za krótkie");
        }else if(imageUri==null){
            Toast.makeText(this, "Proszę wybrać zdjęcie profilowe", Toast.LENGTH_SHORT).show();
        } else{
            mLoadingBar.setTitle("Uzupełnianie profilu.");
            mLoadingBar.setCanceledOnTouchOutside(false);
            mLoadingBar.show();
            StorageRef.child(mUser.getUid()).putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    if(task.isSuccessful()){
                        StorageRef.child(mUser.getUid()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                HashMap hashMap = new HashMap();
                                hashMap.put("username", username);
                                hashMap.put("city", city);
                                hashMap.put("country", country);
                                hashMap.put("hobby", about);
                                hashMap.put("profileImage", uri.toString());
                                hashMap.put("status", "offline");

                                mRef.child(mUser.getUid()).setValue(hashMap).addOnSuccessListener(new OnSuccessListener() {
                                    @Override
                                    public void onSuccess(Object o) {
                                        mLoadingBar.dismiss();
                                        Toast.makeText(SetupActivity.this, "Uzupełnianie profilu zakończone sukcesem", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(SetupActivity.this, MainActivity.class);
                                        startActivity(intent);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        mLoadingBar.dismiss();
                                        Toast.makeText(SetupActivity.this, "Uzupełnianie profilu nieukończone", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        });
                    }else{
                        Log.e("TAG", "Błąd podczas przesyłania pliku: " + task.getException().getMessage());
                    }
                }
            });
        }
    }

    private void showError(EditText input, String s){
        input.setError(s);
        input.requestFocus();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE && resultCode==RESULT_OK && data!=null){
            imageUri = data.getData();
            profileImageView.setImageURI(imageUri);
        }
    }
}