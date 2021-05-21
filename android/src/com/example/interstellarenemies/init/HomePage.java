package com.example.interstellarenemies.init;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.*;
import android.view.*;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.*;
import androidx.appcompat.app.*;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.example.interstellarenemies.*;
import com.example.interstellarenemies.profile.ProfileFragment;
import com.example.interstellarenemies.R;
import com.example.interstellarenemies.announcements.AnnouncementFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

/**
 * This class is not actually HomePage anymore.
 * Bu sayfa artik fragmentlarin kontrolu icin kullaniliyor.
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

        Intent goMain = new Intent(this, MainActivity.class);
        TextView logout = findViewById(R.id.logout);

        logout.setOnClickListener((View v) -> {
            MainActivity.getmAuth().signOut();
            MainActivity.getmGoogleSignInClient().signOut();
            FirebaseAuth.getInstance().signOut();
            startActivity(goMain);
        });
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

    // TODO: Only double back pressing on the game, which will be an Intent.
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
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);

        }
    }
}
