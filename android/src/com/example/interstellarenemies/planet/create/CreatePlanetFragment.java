package com.example.interstellarenemies.planet.create;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.interstellarenemies.R;
import com.example.interstellarenemies.messages.conv.MessagesConvFragment;
import com.example.interstellarenemies.planet.join.JoinListObject;
import com.example.interstellarenemies.planet.join.JoinPlanetFragment;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class CreatePlanetFragment extends Fragment {

    public CreatePlanetFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View ret_view = inflater.inflate(R.layout.fragment_create_planet, container, false);
        Button createPlanet = ret_view.findViewById(R.id.createPlanetFragmentButton);
        EditText maxUsers   = ret_view.findViewById(R.id.createPlanet_maxPlayersInput);
        EditText planetName = ret_view.findViewById(R.id.createPlanet_nameInput);
        createPlanet.setOnClickListener(view -> {
            String strPlanetName = planetName.getText().toString();
            int intMaxUsers  = Integer.parseInt(maxUsers.getText().toString());
            if (intMaxUsers <= 1) {
                Snackbar.make(ret_view, "Please enter bigger numbers for maximum users.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            } else if (strPlanetName.length() <= 5) {
                Snackbar.make(ret_view, "Please enter a longer name for your planet.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();}
            else if (strPlanetName.length() > 40) {
                Snackbar.make(ret_view, "Please enter a shorter name for your planet.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();}
              else if (intMaxUsers > 16) {
                    Snackbar.make(ret_view, "Max user is 16.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
            } else {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                String id = FirebaseDatabase.getInstance().getReference("planets/").push().getKey();
                DatabaseReference childRef = FirebaseDatabase.getInstance().getReference().child("planets/" + id);
                childRef.child("max_users").setValue(Integer.toString(intMaxUsers));
                childRef.child("name").setValue(strPlanetName);
                childRef.child("playing").setValue("false");
                childRef.child("users").child("0").setValue(user.getUid());

                getActivity().getIntent().putExtra("fragment::messages::receiver::id", FirebaseAuth.getInstance().getCurrentUser().getUid());
                getActivity().getIntent().putExtra("fragment::messages::planet::id", id);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new MessagesConvFragment()).commit();
            }

        });
        return ret_view;
    }
}