package com.example.interstellarenemies;

import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.example.interstellarenemies.init.MainActivity;
import com.google.firebase.auth.FirebaseAuth;

public class SinglePlayerPage extends AndroidApplication{
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();

        //will be change for now it is static
        String shipName = "ship4";
        int laserCount = 2;
        int health = 100;
        int armor= 2;
        int shipSpeed= 65;

        initialize(new GamePage(shipName,laserCount,health,armor,shipSpeed), config);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }



}