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
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("users").child(user.getUid()).child("invites");
        adapter = new InvitesAdapter(getActivity(), R.layout.listview_invite_item, listItems);
        mListView.setAdapter(adapter);

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                LinkedList<InvitesObject> annListNew = new LinkedList<>();
                for (DataSnapshot invitesTable : snapshot.getChildren()) {
                    String username = "", userID = "";
                    username= invitesTable.getKey();
                    userID= invitesTable.getValue().toString();

                    annListNew.add(new InvitesObject(username));
                }
                adapter.clear();
                invList = annListNew;
                adapter.addAll(invList);
                mListView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return ret_view;  }
}