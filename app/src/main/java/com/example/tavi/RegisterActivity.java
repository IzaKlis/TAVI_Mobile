package com.example.tavi;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
    TextView alreadyHaveAnAccount;
    private EditText inputPassword, inputEmail, inputConfirmPassword;
    Button btnRegister;
    FirebaseAuth mAuth;
    ProgressDialog mLoadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        inputEmail = findViewById(R.id.inputEmail);
        inputPassword = findViewById(R.id.inputPassword);
        inputConfirmPassword = findViewById(R.id.inputConfirmPassword);
        alreadyHaveAnAccount = findViewById(R.id.alreadyHaveAccount);

        mAuth = FirebaseAuth.getInstance();
        mLoadingBar = new ProgressDialog(this);

        btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(v -> checkRegistration());

        alreadyHaveAnAccount.setOnClickListener(v -> startActivity(new Intent(RegisterActivity.this, LoginActivity.class)));
    }

    private void checkRegistration() {
        String email = inputEmail.getText().toString();
        String password = inputPassword.getText().toString();
        String confirmPassword = inputConfirmPassword.getText().toString();

        if (email.isEmpty() || !email.contains("@")) {
            showError(inputEmail, "Email jest nieprawidłowy!");
        } else if (password.isEmpty() || password.length() < 5) {
            showError(inputPassword, "Hasło musi zawierać 5 znaków!");
        } else if (confirmPassword.isEmpty() || !confirmPassword.equals(password)) {
            showError(inputConfirmPassword, "Hasła muszą być takie same!");
        } else {
            mLoadingBar.setTitle("Rejestracja");
            mLoadingBar.setMessage("Proszę czekać, trwa rejestracja użytkownika.");
            mLoadingBar.setCanceledOnTouchOutside(false);
            mLoadingBar.show();
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
//                    FirebaseUser firebaseUser = task.getResult().getUser();
//                    User user = new User();
//                    user.setEmail(firebaseUser.getEmail());
//                    userViewModel.insert(user);
                    mLoadingBar.dismiss();
                    Toast.makeText(RegisterActivity.this, "Rejestracja udana", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this, SetupActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                } else {
                    mLoadingBar.dismiss();
                    Toast.makeText(RegisterActivity.this, "Rejestracja nie udana.", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void showError(EditText input, String s) {
        input.setError(s);
        input.requestFocus();
    }
}