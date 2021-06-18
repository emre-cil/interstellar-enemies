package com.example.interstellarenemies.messages.conv;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import com.example.interstellarenemies.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class MessagesConvFragment extends Fragment {
    private final ArrayList<MessagesConvObject> listItems = new ArrayList<>();
    private LinkedList<MessagesConvObject> messagesList = new LinkedList<>();
    String otherSide;
    String planetID = null;
    String myID;
    String userName;
    private RecyclerView mRecyclerView;
    private MessagesConvAdapter adapter;


    public MessagesConvFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View ret_view = inflater.inflate(R.layout.fragment_messages_conv, container, false);

        otherSide = getActivity().getIntent().getStringExtra("fragment::messages::receiver::id");
        userName = getActivity().getIntent().getStringExtra("fragment::messages::receiver::userName");
        myID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mRecyclerView = ret_view.findViewById(R.id.conv_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        adapter = new MessagesConvAdapter(getActivity(), listItems);
        mRecyclerView.setAdapter(adapter);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity().getApplicationContext());
        mRecyclerView.setLayoutManager(llm);
        planetID = getActivity().getIntent().getStringExtra("fragment::messages::planet::id");

        if (planetID == null || planetID.equals("")) {
            DatabaseReference planetIDref = FirebaseDatabase.getInstance().getReference("users/" + myID + "/friends/" + otherSide + "/");

            ValueEventListener planetIDRefListener = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    planetID = snapshot.getValue().toString();
                    receiverMessages(ret_view);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            };
            planetIDref.addListenerForSingleValueEvent(planetIDRefListener);
        } else {
            receiverMessages(ret_view);
        }


        EditText ed = ret_view.findViewById(R.id.messages_edit_text);
        ImageButton btn = ret_view.findViewById(R.id.button_send);
        btn.setOnClickListener(v -> {
            String msg = ed.getText().toString();
            if (!msg.isEmpty()) {
                MessagesConvObject mco = new MessagesConvObject(myID, otherSide, msg,userName);
                sendMessage(mco);
                ed.setText("");
            } else {
                Snackbar.make(ret_view, getString(R.string.notSendEmptyText), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        return ret_view;
    }

    private void receiverMessages(View ret_view) {
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("planets/" + planetID + "/messages/");


        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                LinkedList<MessagesConvObject> messagesListNew = new LinkedList<>();
                for (DataSnapshot messagesTable : snapshot.getChildren()) {
                    String message = ((HashMap<String, String>)messagesTable.getValue()).get("message");
                    String sender = ((HashMap<String, String>)messagesTable.getValue()).get("sender");
                    MessagesConvObject mco = new MessagesConvObject(sender, myID, message,userName);
                    messagesListNew.add(mco);

                    adapter.clear();
                    messagesList = messagesListNew;
                    adapter.addAll(messagesList);
                    mRecyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
    }

    private void sendMessage(MessagesConvObject o) {
        DatabaseReference newRef = FirebaseDatabase.getInstance().getReference("planets/" + planetID + "/messages/").push();
        newRef.child("sender").setValue(o.sender);
        newRef.child("message").setValue(o.message);
    }
}