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
        adapter = new InvitesAdapter(getActivity(), android.R.layout.simple_list_item_1, listItems);
        mListView.setAdapter(adapter);


        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference("users");
        DatabaseReference messagesRef = rootRef.child(user.getUid()).child("invites");
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                LinkedList<InvitesObject> annListNew = new LinkedList<>();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String key = ds.getKey();
                    System.out.println(key+"test");

                    DatabaseReference keyRef = rootRef.child(key).child("name");
                    ValueEventListener valueEventListener = new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot ds1 : dataSnapshot.getChildren()) {
                                String name = ds1.getValue().toString();
                                System.out.println(name+"test");
                                annListNew.add(new InvitesObject(name));
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {}
                    };
                    keyRef.addListenerForSingleValueEvent(valueEventListener);
                }
                adapter.clear();
                invList = annListNew;
                adapter.addAll(invList);
                mListView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };
        messagesRef.addListenerForSingleValueEvent(eventListener);
        return ret_view;  }
}