package com.example.interstellarenemies.friends;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.example.interstellarenemies.R;
import com.example.interstellarenemies.announcements.AnnouncementContentFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.LinkedList;

public class FriendsFragment extends Fragment {
    private final ArrayList<FriendsObject> listItems = new ArrayList<>();
    private LinkedList<FriendsObject> frList = new LinkedList<>();
    private FriendsAdapter adapter;
    private ListView mListView;

    public FriendsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        View ret_view = inflater.inflate(R.layout.fragment_friends, container, false);
        mListView = (ListView) ret_view.findViewById(R.id.friendsListView);
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("users").child(user.getUid()).child("friends");
        adapter = new FriendsAdapter(getActivity(), android.R.layout.simple_list_item_1, listItems);
        mListView.setAdapter(adapter);

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                LinkedList<FriendsObject> annListNew = new LinkedList<>();
                for (DataSnapshot friendsTable : snapshot.getChildren()) {
                    String username = "", userID = "";
                    username= friendsTable.getKey();
                    userID= friendsTable.getValue().toString();

                    annListNew.add(new FriendsObject(username,"offline",userID));
                }
                adapter.clear();
                frList = annListNew;
                adapter.addAll(frList);
                mListView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return ret_view;
    }
}