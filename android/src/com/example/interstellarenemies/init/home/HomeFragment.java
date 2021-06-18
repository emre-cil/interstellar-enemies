package com.example.interstellarenemies.init.home;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.*;
import android.widget.Button;

import com.example.interstellarenemies.init.home.HomePage;
import com.example.interstellarenemies.planet.create.CreatePlanetFragment;
import com.example.interstellarenemies.planet.join.JoinPlanetFragment;
import com.example.interstellarenemies.R;
import com.example.interstellarenemies.SinglePlayerPage;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase.getInstance().getReference().child("users").child(user.getUid()).child("status").setValue("online");
        Intent goSinglePlayer = new Intent(getActivity().getApplicationContext(), SinglePlayerPage.class);

        //create button object
        Button singlePlayerBut = getActivity().findViewById(R.id.singlePlayerBut);
        Button createPlanetBut = getActivity().findViewById(R.id.createAPlanetBut);

        //go single player page
        singlePlayerBut.setOnClickListener((View v) -> {
            FirebaseDatabase rootRef = FirebaseDatabase.getInstance();
            rootRef.getReference("users").child(user.getUid()).child("current_ship").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String shipName = snapshot.getValue().toString();

                    rootRef.getReference().child("ships").child(shipName).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String laserCount = snapshot.child("laser_count").getValue().toString();
                            String health = snapshot.child("health").getValue().toString();
                            String armor = snapshot.child("armor").getValue().toString();
                            String shipSpeed = snapshot.child("ship_speed").getValue().toString();
                            goSinglePlayer.putExtra("laser_count", laserCount);
                            goSinglePlayer.putExtra("health", health);
                            goSinglePlayer.putExtra("armor", armor);
                            goSinglePlayer.putExtra("ship_speed", shipSpeed);
                            goSinglePlayer.putExtra("ship_name", shipName);
                            startActivity(goSinglePlayer);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        });

        //go join a planet page
        getActivity().findViewById(R.id.joinAPlanetBut).setOnClickListener((View v) -> {
            HomePage.doubleBackToExitPressedOnce = false;
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new JoinPlanetFragment()).commit();
        });

        //go create a planet page
        createPlanetBut.setOnClickListener((View v) -> {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new CreatePlanetFragment()).commit();
        });


    }
}