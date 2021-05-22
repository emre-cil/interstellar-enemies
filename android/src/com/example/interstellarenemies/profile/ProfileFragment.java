package com.example.interstellarenemies.profile;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.*;
import android.widget.Button;
import android.widget.TextView;

import com.example.interstellarenemies.R;
import com.example.interstellarenemies.ShopFragment;
import com.example.interstellarenemies.friends.FriendsFragment;
import com.example.interstellarenemies.friends.FriendsObject;
import com.example.interstellarenemies.invite.InvitesFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.LinkedList;
import java.util.Objects;

public class ProfileFragment extends Fragment {
    NavigationView navigationView;
    TextView userName;
    Button friendsButt, invitesButt;

    public ProfileFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        navigationView = getActivity().findViewById(R.id.nav_view);
        //friendsButt.setBackgroundColor(Color.parseColor("#ED8200"));
        getActivity().getSupportFragmentManager().beginTransaction().replace(
                R.id.profileFragmentContainer, new FriendsFragment()).commit();
    }


    @Override
    public void onResume() {
        super.onResume();
        userName = (getActivity()).findViewById(R.id.profileUserName);
        friendsButt = getActivity().findViewById(R.id.profile_friends_button);
        invitesButt = getActivity().findViewById(R.id.profile_invites_button);

        //click to friends button
        getActivity().findViewById(R.id.profile_friends_button).setOnClickListener((View v) -> {
            friendsButt.setBackgroundColor(Color.parseColor("#ED8200"));
            invitesButt.setBackgroundColor(Color.parseColor("#FED123"));
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.profileFragmentContainer,
                    new FriendsFragment()).commit();
        });

        //click to invites button
        getActivity().findViewById(R.id.profile_invites_button).setOnClickListener((View v) -> {
            invitesButt.setBackgroundColor(Color.parseColor("#ED8200"));
            friendsButt.setBackgroundColor(Color.parseColor("#FED123"));
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.profileFragmentContainer,
                    new InvitesFragment()).commit();
        });

        getActivity().findViewById(R.id.profile_hangar_text).setOnClickListener((View v) -> {
            navigationView.setCheckedItem(R.id.nav_shop);
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new ShopFragment()).commit();
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("users").child(user.getUid());

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                userName.setText(snapshot.child("name").getValue().toString());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        return inflater.inflate(R.layout.fragment_profile, container, false);
    }
}