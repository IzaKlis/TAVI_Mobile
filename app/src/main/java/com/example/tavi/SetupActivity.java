package com.example.tavi;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import de.hdodenhof.circleimageview.CircleImageView;

public class SetupActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 101;
    CircleImageView profileImageView;
    EditText inputUsername, inputCity, inputCountry, inputHobby;
    Button btnSave;
    Uri imageUri;
    ProgressDialog mLoadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        profileImageView = findViewById(R.id.profile_image);
        inputUsername = findViewById(R.id.inputUsername);
        inputCity = findViewById(R.id.inputCity);
        inputCountry = findViewById(R.id.inputCountry);
        inputHobby = findViewById(R.id.inputHobby);
        btnSave = findViewById(R.id.btnSave);
        mLoadingBar = new ProgressDialog(this);


        profileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE);
            }
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
        String hobby = inputHobby.getText().toString();

        if(username.isEmpty() || username.length()<3){
            showError(inputUsername,"Imię jest za krótkie");
        }else if(city.isEmpty() || city.length()<3){
            showError(inputCity,"Nazwa miasta jest za krótka");
        }else if(country.isEmpty() || country.length()<3){
            showError(inputCountry,"Nazwa kraju jest za krótka");
        }else if(hobby.isEmpty() || hobby.length()<3){
            showError(inputHobby,"Zainteresowania są za krótkie");
        }else if(imageUri==null){
            Toast.makeText(this, "Proszę wybrać zdjęcie profilowe", Toast.LENGTH_SHORT).show();
        }else{
            mLoadingBar.setTitle("Uzupełnianie profilu.");
            mLoadingBar.setCanceledOnTouchOutside(false);
            mLoadingBar.show();
            Intent intent = new Intent(SetupActivity.this, MainActivity.class);
            startActivity(intent);
            mLoadingBar.dismiss();
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