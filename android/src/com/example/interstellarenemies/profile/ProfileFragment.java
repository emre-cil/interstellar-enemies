package com.example.interstellarenemies.profile;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.*;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.interstellarenemies.R;
import com.example.interstellarenemies.ShopFragment;
import com.example.interstellarenemies.friends.FriendsFragment;
import com.example.interstellarenemies.invite.InvitesFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class ProfileFragment extends Fragment {
    NavigationView navigationView;
    TextView userName,userID;
    Button friendsButt, invitesButt;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    public ProfileFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        navigationView = getActivity().findViewById(R.id.nav_view);
        getActivity().getSupportFragmentManager().beginTransaction().replace(
                R.id.profileFragmentContainer, new FriendsFragment()).commit();
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshMoney();
        refreshName();

        userName = getActivity().findViewById(R.id.profileUserName);
        userID = getActivity().findViewById(R.id.profile_userID);
        friendsButt = getActivity().findViewById(R.id.profile_friends_button);
        invitesButt = getActivity().findViewById(R.id.profile_invites_button);

        //click to friends button
        changeButtonColor("#ED8200", "#FED123", R.id.profile_friends_button, new FriendsFragment());
        //click to invites button
        changeButtonColor("#FED123", "#ED8200", R.id.profile_invites_button, new InvitesFragment());

        //Hangar text click function
        getActivity().findViewById(R.id.profile_hangar_text).setOnClickListener((View v) -> {
            navigationView.setCheckedItem(R.id.nav_shop);
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new ShopFragment()).commit();
        });

        //add friend button function
        getActivity().findViewById(R.id.addFriendBut).setOnClickListener((View v) -> {
            EditText enterUsername = getActivity().findViewById(R.id.invite_player_editText);
            String playerName = enterUsername.getText().toString();


            /**
             * TODO: burda ne oluyo kullanilmayanlar var duzenlemesi lazim dikkatli
             */
            DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("users");
            dbRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot usersTable : snapshot.getChildren()) {
                        String key = usersTable.getKey();
                        if (playerName.equals(usersTable.child("name").getValue()) && !playerName.equals(userName.getText().toString()))
                            FirebaseDatabase.getInstance().getReference().child("users").child(key).child("invites").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue("request done");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


        });


    }

    public void refreshName() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("users").child(user.getUid());

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userName.setText(snapshot.child("name").getValue().toString());
                userID.setText(user.getUid().substring(0,8));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }

    public void refreshMoney() {
        TextView money = getActivity().findViewById(R.id.moneyProfile);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("users/" + user.getUid());
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                money.setText(snapshot.child("money").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    //changes buttons color on profile page
    public void changeButtonColor(String friendsColor, String invitesColor, int buttonId, Fragment fragment) {
        getActivity().findViewById(buttonId).setOnClickListener((View v) -> {
            friendsButt.setBackgroundColor(Color.parseColor(friendsColor));
            invitesButt.setBackgroundColor(Color.parseColor(invitesColor));
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.profileFragmentContainer,
                    fragment).commit();
        });
    }


}