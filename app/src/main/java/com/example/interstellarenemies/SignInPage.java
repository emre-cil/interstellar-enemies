package com.example.interstellarenemies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignInPage extends AppCompatActivity {
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_page);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Button signInButton = findViewById(R.id.signInBut);
        signInButton.setOnClickListener((View v) -> {
           login();
        });
    }

//    public void navigateUser(View view) {
//        Intent intent = new Intent(this,MainActivity.class);
//        startActivity(intent);
//    }

    public void login() {
        String email = ((EditText) findViewById(R.id.LoginEmail)).getText().toString();
        String pass = ((EditText) findViewById(R.id.LoginPass)).getText().toString();
        Intent mainIntent = new Intent(this, HomePage.class);
        mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    startActivity(mainIntent);
                }else{
                    Toast.makeText(SignInPage.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}