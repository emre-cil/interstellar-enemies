package com.example.interstellarenemies;

import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import androidx.annotation.NonNull;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.example.interstellarenemies.init.HomePage;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.Objects;

public class SinglePlayerPage extends AndroidApplication implements Transfer {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase.getInstance().getReference().child("users").child(user.getUid()).child("status").setValue("online");
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        Intent i = getIntent();
        String shipName = i.getStringExtra("ship_name");
        int laserCount = Integer.parseInt(i.getStringExtra("laser_count"));
        int health = Integer.parseInt(i.getStringExtra("health"));
        int armor = Integer.parseInt(i.getStringExtra("armor"));
        int shipSpeed = Integer.parseInt(i.getStringExtra("ship_speed"));

        initialize(new GamePage(shipName, laserCount, health, armor, shipSpeed, this), config);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

    }

    @Override
    protected void onPause() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase.getInstance().getReference().child("users").child(user.getUid()).child("status").setValue("offline");

        super.onPause();
    }

    @Override
    public void onBackPressed() {
        Intent homePage = new Intent(this, HomePage.class);
        startActivity(homePage);
    }

    @Override
    public void submitScore(int score) {
        Intent gameEnd = new Intent(this, GameEndDialog.class);
        gameEnd.putExtra("score",score+"");
        startActivity(gameEnd);

        //update score on database
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference keyRef = FirebaseDatabase.getInstance().getReference("users").child(user.getUid());
        keyRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int finalScore = Integer.parseInt(Objects.requireNonNull(snapshot.child("high_score").getValue(String.class)));
                if (score > finalScore)
                    keyRef.child("high_score").setValue(score + "");
                int totalMoney = Integer.parseInt(snapshot.child("money").getValue(String.class));
                keyRef.child("money").setValue((totalMoney + score)+"");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


    }
}