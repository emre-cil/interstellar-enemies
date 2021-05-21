package com.example.interstellarenemies;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.*;

public class LeaderboardFragment extends Fragment {
    public LeaderboardFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_leaderboard, container, false);
    }
}