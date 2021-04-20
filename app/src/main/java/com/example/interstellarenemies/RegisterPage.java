package com.example.interstellarenemies;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class RegisterPage extends AppCompatActivity {
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Button signUpButton = findViewById(R.id.signUpBut);
        signUpButton.setOnClickListener((View v) -> createUser());
    }

    public void createUser() {
        String email = ((EditText) findViewById(R.id.RegisterEmail)).getText().toString();
        String pass = ((EditText) findViewById(R.id.RegisterEmailPass)).getText().toString();
        String passConf = ((EditText) findViewById(R.id.RegisterEmailPassConfirm)).getText().toString();

        //if user does not enter anything -> do nothing.
        if (email.isEmpty() || pass.isEmpty() || passConf.isEmpty())
            return;
        Intent mainIntent = new Intent(this, HomePage.class);

        if (!pass.equals(passConf)) {
            Toast.makeText(RegisterPage.this, "Passwords does not match!", Toast.LENGTH_SHORT).show();
        } else {
            mAuth.createUserWithEmailAndPassword(email, pass).
                    addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            mainIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            startActivity(mainIntent);
                        }
                        else
                            Toast.makeText(RegisterPage.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    });
        }

    }

}