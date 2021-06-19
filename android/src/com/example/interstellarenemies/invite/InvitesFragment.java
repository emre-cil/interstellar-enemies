package com.example.interstellarenemies.invite;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.*;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

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
    private Dialog addFriendDialog;
    private TextView dialogText;
    private Button acceptBut;

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        addFriendDialog = new Dialog(getActivity());
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        View ret_view = inflater.inflate(R.layout.fragment_invites, container, false);
        mListView = ret_view.findViewById(R.id.invitesListView);
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
                            annListNew.add(new InvitesObject(name, key));
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

        mListView.setOnItemClickListener((parent, view, position, id) -> {
            addFriendDialog.setContentView(R.layout.sample_dialog);
            acceptBut = addFriendDialog.findViewById(R.id.DialogGreenButton);
            acceptBut.setText(getString(R.string.accept));
            //if click to accept button
            dialogText = addFriendDialog.findViewById(R.id.DialogText);
            dialogText.setText(getString(R.string.doYouWantAdd) + invList.get(position).username +" "+ getString(R.string.asAFriend));
            acceptBut.setOnClickListener((View v) -> {
                String newID = FirebaseDatabase.getInstance().getReference("planets/").push().getKey();
                rootRef.child(user.getUid()).child("friends").child(invList.get(position).userID).setValue(newID);
                rootRef.child(invList.get(position).userID).child("friends").child(user.getUid()).setValue(newID);
                rootRef.child(user.getUid()).child("invites").child(invList.get(position).userID).removeValue();

                adapter.clear();
                invList.remove(invList.get(position));
                adapter.addAll(invList);
                mListView.setAdapter(adapter);
                addFriendDialog.dismiss();
            });

            //if click to cancel
            addFriendDialog.findViewById(R.id.DialogCancelButton).setOnClickListener((View v) -> {
                addFriendDialog.dismiss();
            });
            addFriendDialog.show();
        });
        return ret_view;
    }

}