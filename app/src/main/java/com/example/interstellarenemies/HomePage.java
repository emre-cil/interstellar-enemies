package com.example.interstellarenemies;


import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class HomePage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        /**
         * TODO: fullscreen bakilacak
         */
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        /*---Hooks---*/
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

        /*---Tool Bar---*/
        setSupportActionBar(toolbar);

        /*---Navigation Drawer Menu---*/
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setCheckedItem(R.id.nav_home);
        navigationView.setNavigationItemSelectedListener(this);


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_home:
                break;
            case R.id.nav_shop:
              //  Intent intent ---> start activity
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START);
        else
            super.onBackPressed();

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
        singlePlayerBut.setOnClickListener((View v) -> startActivity(goSinglePlayer));

        //go join a planet page
        joinPlanetBut.setOnClickListener((View v) -> startActivity(goJoinPlanet));

        //go create a planet page
        createPlanetBut.setOnClickListener((View v) -> startActivity(goCreatePlanet));


    }

}