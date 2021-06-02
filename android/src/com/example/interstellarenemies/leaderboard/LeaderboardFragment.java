package com.example.interstellarenemies.leaderboard;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.*;
import android.widget.ListView;

import com.example.interstellarenemies.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

public class LeaderboardFragment extends Fragment {
    private final ArrayList<LeaderboardObject> listItems = new ArrayList<>();
    private LinkedList<LeaderboardObject> annList = new LinkedList<>();
    private LeaderboardAdapter adapter;
    private ListView mListView;

    public LeaderboardFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View ret_view = inflater.inflate(R.layout.fragment_leaderboard, container, false);
        mListView = ret_view.findViewById(R.id.MessagesUserList_ListView);
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("users/");
        adapter = new LeaderboardAdapter(getActivity(), R.layout.leaderboard_item, listItems);
        mListView.setAdapter(adapter);

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                LinkedList<LeaderboardObject> annListNew = new LinkedList<>();
                for (DataSnapshot leaderTable : snapshot.getChildren()) {
                    String name = "", highScore = "";
                    for (DataSnapshot elem : leaderTable.getChildren()) {
                        switch (elem.getKey()) {
                            case "name":
                                name = elem.getValue().toString();
                                break;
                            case "high_score":
                                highScore = elem.getValue().toString();
                                break;
                        }
                    }
                    Integer score = Integer.parseInt(highScore);
                    annListNew.add(new LeaderboardObject(name, score));
                }
                adapter.clear();
                annList = annListNew;
                annList.sort(Comparator.comparing(LeaderboardObject::getGamesWon));
                Collections.sort(annList, (LeaderboardObject a, LeaderboardObject b) -> Integer.compare(b.score, a.score));
                adapter.addAll(annList);
                mListView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return ret_view;
    }
}