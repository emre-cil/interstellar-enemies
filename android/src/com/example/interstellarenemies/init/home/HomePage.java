package com.example.interstellarenemies.init.home;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.*;
import android.view.*;
import android.widget.TextView;
import androidx.annotation.*;
import androidx.appcompat.app.*;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.example.interstellarenemies.init.MainActivity;
import com.example.interstellarenemies.leaderboard.LeaderboardFragment;
import com.example.interstellarenemies.messages.userlist.MessagesUserListFragment;
import com.example.interstellarenemies.toolbar.ProfileFragment;
import com.example.interstellarenemies.R;
import com.example.interstellarenemies.announcements.AnnouncementFragment;
import com.example.interstellarenemies.toolbar.settings.SettingsFragment;
import com.example.interstellarenemies.toolbar.ShopFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomePage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    static NavigationView navigationView;
    static boolean doubleBackToExitPressedOnce = false;
    private DrawerLayout drawerLayout;
    private String lastId;
    private Toolbar toolbar;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        TextView logout = findViewById(R.id.logout);
        lastId = MainActivity.getmAuth().getUid();
        logout.setOnClickListener((View v) -> {
            FirebaseDatabase.getInstance().getReference().child("users").child(lastId).child("status").setValue("offline");
            Intent goMain = new Intent(this, MainActivity.class);
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

        //set homepage as a default on navigation.
        navigationView.setNavigationItemSelectedListener(this);
        if (savedInstanceState == null) {
            goFragment(new HomeFragment());
            navigationView.setCheckedItem(R.id.nav_home);
        }

        //refresh the header username.
        refreshHeader();
    }

    //displays fragments.
    private void goFragment(Fragment f) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, f).commit();
    }

    //refresh the user name on toolbar header.
    public static void refreshHeader() {
        TextView headerUserName = navigationView.getHeaderView(0).findViewById(R.id.toolbarHeaderUserName);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("users/" + user.getUid());
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                headerUserName.setText(snapshot.child("name").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
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
                goFragment(new MessagesUserListFragment());
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

        //refresh username.
        refreshHeader();
        return true;
    }

    @Override
    protected void onPause() {
        FirebaseDatabase.getInstance().getReference().child("users").child(lastId).child("status").setValue("offline");
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
            //refresh header
            refreshHeader();
        } else {

            if (doubleBackToExitPressedOnce) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                FirebaseDatabase.getInstance().getReference().child("users").child(user.getUid()).child("status").setValue("offline");
                super.onBackPressed();
                return;
            } else
                goFragment(new HomeFragment());

            this.doubleBackToExitPressedOnce = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }
    }
}