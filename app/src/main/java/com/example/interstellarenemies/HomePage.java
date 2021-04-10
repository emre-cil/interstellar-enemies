package com.example.interstellarenemies;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;

public class HomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent goMain = new Intent(this, MainActivity.class);

        Button signOutBut = findViewById(R.id.signOutBut);
        signOutBut.setOnClickListener((View v)->{

            MainActivity.getmAuth().signOut();
            MainActivity.getmGoogleSignInClient().signOut();
         FirebaseAuth.getInstance().signOut();
            startActivity(goMain);
        });
    }
}