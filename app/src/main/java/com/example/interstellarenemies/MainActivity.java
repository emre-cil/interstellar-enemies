package com.example.interstellarenemies;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import static android.view.View.SYSTEM_UI_FLAG_FULLSCREEN;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }


    /**
     * TODO: make fullscreen with no black bars.
     */
    @Override
    protected void onResume() {
        super.onResume();
        TextView signUpGo = findViewById(R.id.textSignUp);
        signUpGo.setOnClickListener((View v) -> {
            Intent i = new Intent(this, RegisterPage.class);
            startActivity(i);
        });

        ImageView emailSignIn = findViewById(R.id.emailView);
        emailSignIn.setOnClickListener((View v) -> {
            Intent i = new Intent(this, SignInPage.class);
            startActivity(i);
        });

        View view = getWindow().getDecorView();
        int uiOptions = SYSTEM_UI_FLAG_FULLSCREEN;
        view.setSystemUiVisibility(uiOptions);

    }
}