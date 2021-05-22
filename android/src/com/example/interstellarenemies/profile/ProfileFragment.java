package com.example.interstellarenemies.profile;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.*;

import com.example.interstellarenemies.R;
import com.example.interstellarenemies.ShopFragment;
import com.example.interstellarenemies.friends.FriendsFragment;
import com.example.interstellarenemies.invite.InvitesFragment;
import com.google.android.material.navigation.NavigationView;

public class ProfileFragment extends Fragment {
    NavigationView navigationView;

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

        //click to friends button
        getActivity().findViewById(R.id.profile_friends_button).setOnClickListener((View v) -> {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.profileFragmentContainer,
                    new FriendsFragment()).commit();
        });

        //click to invites button
        getActivity().findViewById(R.id.profile_invites_button).setOnClickListener((View v) -> {
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
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }
}