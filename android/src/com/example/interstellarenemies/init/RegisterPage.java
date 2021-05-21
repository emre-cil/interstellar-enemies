package com.example.interstellarenemies.init;

import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

import com.example.interstellarenemies.R;
import com.google.android.material.snackbar.Snackbar;
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
            Snackbar.make(this.findViewById(android.R.id.content), "Passwords does not match!", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        } else {
            mAuth.createUserWithEmailAndPassword(email, pass).
                    addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            mainIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            startActivity(mainIntent);
                        }
                        else
                            Snackbar.make(this.findViewById(android.R.id.content), task.getException().getMessage(), Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                    });
        }

    }

}