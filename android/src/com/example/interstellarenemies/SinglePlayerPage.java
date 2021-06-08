package com.example.interstellarenemies;

import android.content.Intent;
import android.os.Bundle;
import android.view.*;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.database.FirebaseDatabase;

public class SinglePlayerPage extends AndroidApplication {
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


        //will be change for now it is static

        initialize(new GamePage(shipName, laserCount, health, armor, shipSpeed), config);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

    }

    @Override
    protected void onPause() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase.getInstance().getReference().child("users").child(user.getUid()).child("status").setValue("offline");

        super.onPause();
    }


}