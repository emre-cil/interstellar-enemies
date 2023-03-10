package com.example.interstellarenemies.toolbar.settings;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.interstellarenemies.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.LinkedList;

import static com.example.interstellarenemies.init.home.HomePage.refreshHeader;


public class ChangeUsernameFragment extends Fragment {
    private LinkedList<String> usernames = new LinkedList<>();

    @Override
    public void onResume() {
        super.onResume();
        Button button = getActivity().findViewById(R.id.changeUsernameFragmentBut);
        EditText username = getActivity().findViewById(R.id.resetUsernameEdit);
        button.setOnClickListener(view ->
        {

            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            DatabaseReference childRef = FirebaseDatabase.getInstance().getReference().child("users").child(user.getUid());
            String name = username.getText().toString();

            DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("users");
            dbRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    usernames.clear();
                    for (DataSnapshot userTable : snapshot.getChildren()) {
                        for (DataSnapshot elem : userTable.getChildren()) {
                            switch (elem.getKey()) {
                                case "name":
                                    usernames.add(elem.getValue().toString());
                                    refreshHeader();
                                    break;
                            }
                        }

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });

            boolean foundSameName = false;
            for (String temp : usernames) {
                if (temp.equals(name)) {
                    foundSameName = true;
                    break;
                }
            }
            if (foundSameName)
                Snackbar.make(getView(), getString(R.string.usernameAlreadyTaken), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            else if (name.length() > 6 && name.length() < 20) {
                childRef.child("name").setValue(name);
            } else
                Snackbar.make(getView(), getString(R.string.usernameToSmall), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
        });

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_change_username, container, false);
    }
}
