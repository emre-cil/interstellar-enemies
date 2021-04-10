package com.example.interstellarenemies;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.google.android.gms.tasks.*;
import com.google.firebase.auth.*;


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
        //Email signUp Text
        TextView signUpGo = findViewById(R.id.goToSignUpFromSignIn);
        signUpGo.setOnClickListener((View v) -> startActivity(new Intent(this, RegisterPage.class)));

        //button redirecting.
        Button signInButton = findViewById(R.id.signInBut);
        signInButton.setOnClickListener((View v) -> {
            login();
        });

        //reset page redirecting.
        TextView resetText = findViewById(R.id.resetUnderLined);
        resetText.setOnClickListener((View v) -> {
            Intent i = new Intent(this, ResetPassPage.class);
            startActivity(i);
        });
    }

    public void login() {
        String email = ((EditText) findViewById(R.id.LoginEmail)).getText().toString();
        String pass = ((EditText) findViewById(R.id.LoginPass)).getText().toString();
        if (email.isEmpty() || pass.isEmpty())
            return;
        Intent mainIntent = new Intent(this, HomePage.class);
        mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    mainIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(mainIntent);
                } else
                    Toast.makeText(SignInPage.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}