package com.example.interstellarenemies;

import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.example.interstellarenemies.init.MainActivity;
import com.google.firebase.auth.FirebaseAuth;

public class SinglePlayerPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_player_page);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    protected void onResume() {
        super.onResume();
        /*
         * it can be add to side tab.
         */
        Intent goMain = new Intent(this, MainActivity.class);
        Button signOutBut = findViewById(R.id.signOutBut);

        signOutBut.setOnClickListener((View v) -> {
            MainActivity.getmAuth().signOut();
            MainActivity.getmGoogleSignInClient().signOut();
            FirebaseAuth.getInstance().signOut();
            startActivity(goMain);
        });
    }


}