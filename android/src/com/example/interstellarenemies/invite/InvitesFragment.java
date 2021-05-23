package com.example.interstellarenemies.invite;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.*;
import android.widget.ListView;
import com.example.interstellarenemies.R;
import com.google.firebase.auth.*;
import com.google.firebase.database.*;
import java.util.ArrayList;
import java.util.LinkedList;

public class InvitesFragment extends Fragment {

    private final ArrayList<InvitesObject> listItems = new ArrayList<>();
    private LinkedList<InvitesObject> invList = new LinkedList<>();
    private InvitesAdapter adapter;
    private ListView mListView;


    public InvitesFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        View ret_view = inflater.inflate(R.layout.fragment_invites, container, false);
        mListView = (ListView) ret_view.findViewById(R.id.invitesListView);
        adapter = new InvitesAdapter(getActivity(), R.layout.list_item, listItems);
        mListView.setAdapter(adapter);


        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference("users");

        DatabaseReference messagesRef = rootRef.child(user.getUid()).child("invites");
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                LinkedList<InvitesObject> annListNew = new LinkedList<>();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String key = ds.getKey();

                    DatabaseReference keyRef = FirebaseDatabase.getInstance().getReference("users/").child(key + "/").child("name");
                    keyRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String name = snapshot.getValue().toString();
                            annListNew.add(new InvitesObject(name));
                            adapter.clear();
                            invList = annListNew;
                            adapter.addAll(invList);
                            mListView.setAdapter(adapter);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }

                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        messagesRef.addValueEventListener(eventListener);
        return ret_view;
    }
}