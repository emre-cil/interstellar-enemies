package com.example.interstellarenemies;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FirebaseRealtimeUserIntegration {
    public static void userAdd() {
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference uidRef = rootRef.child("users").child(uid);
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(!dataSnapshot.exists()) {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    DatabaseReference childRef = FirebaseDatabase.getInstance().getReference().child("users").child(user.getUid());
                    //childRef.child("friends").child("0").setValue("supportCenterId");
                    //childRef.child("invites").child("0").setValue("supportCenterId");
                    childRef.child("status").setValue("offline");
                    childRef.child("high_score").setValue("0");
                    childRef.child("games_won").setValue("0");
                    String username = user.getEmail();
                    if (username == null) username = "";
                    username = email2userName(username);
                    childRef.child("name").setValue(username);
                    childRef.child("rank").setValue("0");
                    childRef.child("ships").child("0").setValue("ship1");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };
        uidRef.addListenerForSingleValueEvent(eventListener);

    }

    public static String email2userName(String email) {
        return email.substring(0, email.indexOf("@"));
    }
}
