package com.example.interstellarenemies.friends;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.interstellarenemies.R;
import com.example.interstellarenemies.messages.userlist.MessagesUserListFragment;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        View ret_view = inflater.inflate(R.layout.fragment_friends, container, false);
        mListView = ret_view.findViewById(R.id.friendsListView);
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("users").child(user.getUid());
        adapter = new FriendsAdapter(getActivity(), R.layout.listview_image_item, listItems);
        mListView.setAdapter(adapter);

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                LinkedList<FriendsObject> annListNew = new LinkedList<>();

                for (DataSnapshot friendsTable : snapshot.child("friends").getChildren()) {
                    String ID = friendsTable.getKey();

                    DatabaseReference keyRef = FirebaseDatabase.getInstance().getReference("users").child(ID);
                    keyRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshotInside) {
                            String name = snapshotInside.child("name").getValue().toString();
                            String status = snapshotInside.child("status").getValue().toString();
                            annListNew.add(new FriendsObject(name, status, ID));
                            adapter.clear();
                            frList = annListNew;
                            adapter.addAll(frList);
                            mListView.setAdapter(adapter);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        mListView.setOnItemClickListener((parent, view, position, id) -> {
            getActivity().getIntent().putExtra(
                    "fragment::friends::user::id", frList.get(position).userID);
            getActivity().getSupportFragmentManager().beginTransaction().replace(
                    R.id.fragment_container, new MessagesUserListFragment()).commit();
        });

        return ret_view;
    }
}