package com.example.interstellarenemies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseUser;

public class HomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent i  = getIntent();
        TextView text = findViewById(R.id.textView7);
//        text.setText(((FirebaseUser)i.getStringExtra("login::user")).getUserName());

    }
}