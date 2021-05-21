package com.example.interstellarenemies;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseRealtimeUserAddition {
    public static void userAdd() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference childRef =  FirebaseDatabase.getInstance().getReference().child("users").child(user.getUid());
        childRef.child("friends").child("0").setValue("supportCenterId");
        childRef.child("games_won").setValue("0");
        String username = user.getEmail();
        if (username == null) username = "";
        username = username.substring(0, username.indexOf("@"));
        childRef.child("name").setValue(username);
        childRef.child("rank").setValue("0");
        childRef.child("ships").child("0").setValue("ship1");
    }
}
