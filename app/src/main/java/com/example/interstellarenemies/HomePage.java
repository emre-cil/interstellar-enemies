package com.example.interstellarenemies;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class HomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
    }

    @Override
    protected void onResume() {
        super.onResume();
        /*
         * TODO: sayfalarda geri donus olmayacak.
         */
        Intent goSinglePlayer = new Intent(this, SinglePlayerPage.class);
        Intent goJoinPlanet = new Intent(this, JoinPlanetPage.class);
        Intent goCreatePlanet = new Intent(this, CreatePlanetPage.class);

        Button singlePlayerBut = findViewById(R.id.singlePlayerBut);
        Button joinPlanetBut = findViewById(R.id.joinAPlanetBut);
        Button createPlanetBut = findViewById(R.id.createAPlanetBut);

        //go single player page
        singlePlayerBut.setOnClickListener((View v)-> startActivity(goSinglePlayer));

        //go join a planet page
        joinPlanetBut.setOnClickListener((View v)-> startActivity(goJoinPlanet));

        //go create a planet page
        createPlanetBut.setOnClickListener((View v)-> startActivity(goCreatePlanet));


    }
}