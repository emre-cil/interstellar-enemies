package com.example.interstellarenemies.init.mail;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.*;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

import com.example.interstellarenemies.etc.FirebaseRealtimeUserIntegration;
import com.example.interstellarenemies.R;
import com.example.interstellarenemies.init.home.HomePage;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterPage extends AppCompatActivity {
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            getWindow().getAttributes().layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
        }
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Button signUpButton = findViewById(R.id.signUpBut);
        signUpButton.setOnClickListener((View v) -> createUser());
    }

    @SuppressLint("ResourceType")
    public void createUser() {
        String email = ((EditText) findViewById(R.id.RegisterEmail)).getText().toString();
        String pass = ((EditText) findViewById(R.id.RegisterEmailPass)).getText().toString();
        String passConf = ((EditText) findViewById(R.id.RegisterEmailPassConfirm)).getText().toString();

        //if user does not enter anything -> do nothing.
        if (email.isEmpty() || pass.isEmpty() || passConf.isEmpty())
            return;
        Intent mainIntent = new Intent(this, HomePage.class);

        if (!pass.equals(passConf)) {
            Snackbar.make(this.findViewById(android.R.id.content), getString(R.string.passwordNotMatch), Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        } else {
            mAuth.createUserWithEmailAndPassword(email, pass).
                    addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            FirebaseRealtimeUserIntegration.userAdd();
                            mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            mainIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            startActivity(mainIntent);
                        } else
                            Snackbar.make(this.findViewById(android.R.id.content), task.getException().getMessage(), Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                    });
        }

    }

}