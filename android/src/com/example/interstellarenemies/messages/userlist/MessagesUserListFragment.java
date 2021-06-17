package com.example.interstellarenemies.messages.userlist;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.*;
import android.widget.ListView;
import com.example.interstellarenemies.R;
import com.example.interstellarenemies.messages.conv.MessagesConvFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.LinkedList;

public class MessagesUserListFragment extends Fragment {
    private final ArrayList<MessagesUserListObject> listItems = new ArrayList<>();
    private LinkedList<MessagesUserListObject> messagesList = new LinkedList<>();
    private MessagesUserListAdapter adapter;
    private ListView mListView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View ret_view = inflater.inflate(R.layout.fragment_messages, container, false);

        mListView = ret_view.findViewById(R.id.MessagesUserList_ListView);
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("users/" +  FirebaseAuth.getInstance().getCurrentUser().getUid() + "/friends/");
        adapter = new MessagesUserListAdapter(getActivity(), R.layout.list_item, listItems);
        mListView.setAdapter(adapter);

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                LinkedList<MessagesUserListObject> messagesListNew = new LinkedList<>();
                for (DataSnapshot messagesTable : snapshot.getChildren()) {
                    String id = messagesTable.getKey();

                    DatabaseReference keyRef = FirebaseDatabase.getInstance().getReference("users/").child(id + "/").child("name");
                    keyRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshotInside) {
                            String name = snapshotInside.getValue().toString();
                            messagesListNew.add(new MessagesUserListObject(id, name));
                            adapter.clear();
                            messagesList = messagesListNew;
                            adapter.addAll(messagesList);
                            mListView.setAdapter(adapter);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {}
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });

        mListView.setOnItemClickListener((parent, view, position, id) -> {
            getActivity().getIntent().putExtra(
                    "fragment::messages::receiver::id", messagesList.get(position).userid);
            getActivity().getIntent().putExtra(
                    "fragment::messages::receiver::userName", messagesList.get(position).username);
            getActivity().getIntent().putExtra(
                    "fragment::messages::planet::id", "");
            getActivity().getSupportFragmentManager().beginTransaction().replace(
                    R.id.fragment_container, new MessagesConvFragment()).commit();
        });

        return ret_view;
    }
}