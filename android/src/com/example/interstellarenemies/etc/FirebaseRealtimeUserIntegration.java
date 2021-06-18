package com.example.interstellarenemies.etc;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseRealtimeUserIntegration {
    public static void userAdd() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference childRef = FirebaseDatabase.getInstance().getReference().child("users").child(user.getUid());
        childRef.child("money").setValue("0");
        childRef.child("ship_count").setValue("1");
        childRef.child("status").setValue("offline");
        childRef.child("high_score").setValue("0");
        String username = user.getEmail();
        if (username == null) username = "";
        username = email2userName(username);
        childRef.child("name").setValue(username);
        childRef.child("rank").setValue("0");
        childRef.child("current_ship").setValue("ship");
        childRef.child("ships").child("ship").setValue("700004");
    }

    public static String email2userName(String email) {
        return email.substring(0, email.indexOf("@"));
    }
}