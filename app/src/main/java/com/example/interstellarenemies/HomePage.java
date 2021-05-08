package com.example.interstellarenemies;


import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.navigation.NavigationView;

/**
 * This class is not actually HomePage anymore.
 *  Bu sayfa artik fragmentlarin kontrolu icin kullaniliyor.
 */
public class HomePage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    boolean doubleBackToExitPressedOnce = false;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        /*
         * TODO: fullscreen bakilacak
         */
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        /*---Hooks---*/
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");

        /*---Tool Bar---*/
        setSupportActionBar(toolbar);

        /*---Navigation Drawer Menu---*/
        navigationView.bringToFront();

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.getDrawerArrowDrawable().setColor(getColor(R.color.yellow));
        toggle.getDrawerArrowDrawable().setBarThickness(10);
        toggle.getDrawerArrowDrawable().setBarLength(75);

        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        if (savedInstanceState == null) {
            goFragment(new HomeFragment());
            navigationView.setCheckedItem(R.id.nav_home);
        }
    }

    private void goFragment(Fragment f) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, f).commit();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                goFragment(new HomeFragment());
                break;
            case R.id.nav_shop:
                goFragment(new ShopFragment());
                break;
            case R.id.nav_announcements:
                goFragment(new AnnouncementFragment());
                break;
            case R.id.nav_messages:
                goFragment(new MessagesFragment());
                break;
            case R.id.nav_leaderboard:
                goFragment(new LeaderboardFragment());
                break;
            case R.id.nav_profile:
                goFragment(new ProfileFragment());
                break;
            case R.id.nav_settings:
                goFragment(new SettingsFragment());
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {

                if (doubleBackToExitPressedOnce) {
                    super.onBackPressed();
                    return;
                }

                this.doubleBackToExitPressedOnce = true;
                Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        doubleBackToExitPressedOnce=false;
                    }
                }, 2000);

        }
    }
}
