package com.example.tavi;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    TextView createNewAccount, forgotPassword;
    EditText inputEmail,inputPassword;
    Button btnLogin;
    FirebaseAuth mAuth;
    ProgressDialog mLoadingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inputEmail = findViewById(R.id.inputEmail);
        inputPassword = findViewById(R.id.inputPassword);
        btnLogin = findViewById(R.id.btnLogin);
        createNewAccount = findViewById(R.id.textViewSignUp);
        mAuth = FirebaseAuth.getInstance();
        mLoadingBar = new ProgressDialog(this);

        btnLogin.setOnClickListener(v -> checkLogin());
        createNewAccount.setOnClickListener(v -> startActivity(new Intent(LoginActivity.this, RegisterActivity.class)));
        forgotPassword = findViewById(R.id.forgotPassword);
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class));
            }
        });

    }
    private void checkLogin(){
        String email=inputEmail.getText().toString();
        String password=inputPassword.getText().toString();

        if(email.isEmpty() || !email.contains("@")){
            showError(inputEmail, "E-mail jest nieprawidłowy!");
        }
        else if(password.isEmpty() || password.length()<5){
            showError(inputPassword, "Hasło musi zawierać 5 zanków!");
        }
        else{
            mLoadingBar.setTitle("Logowanie");
            mLoadingBar.setMessage("Proszę czekać, trwa logowanie użytkownika.");
            mLoadingBar.setCanceledOnTouchOutside(false);
            mLoadingBar.show();
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                if(task.isSuccessful()){
                    mLoadingBar.dismiss();
                    Toast.makeText(LoginActivity.this, "Logowanie udane.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }else{
                    mLoadingBar.dismiss();
                    Toast.makeText(LoginActivity.this,"Logowanie nieudane.",Toast.LENGTH_SHORT).show();
                }
            });

        }
    }
    private  void showError(EditText input, String s){
        input.setError(s);
        input.requestFocus();
    }

}