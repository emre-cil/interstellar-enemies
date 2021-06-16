package com.example.interstellarenemies;

import androidx.annotation.NonNull;

import com.example.interstellarenemies.friends.FriendsObject;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FirebaseRealtimeUserIntegration {
    public static void userAdd() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference keyRef = FirebaseDatabase.getInstance().getReference("users").child(user.getUid());
        keyRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshotInside) {
            if (!snapshotInside.exists()){
                DatabaseReference childRef = FirebaseDatabase.getInstance().getReference().child("users").child(user.getUid());
                childRef.child("money").setValue("500000");
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
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

    }


    public static String email2userName(String email) {
        return email.substring(0, email.indexOf("@"));
    }
}
