package com.example.interstellarenemies;

import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.example.interstellarenemies.init.MainActivity;
import com.example.interstellarenemies.invite.InvitesObject;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.LinkedList;

public class SinglePlayerPage extends AndroidApplication {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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


}