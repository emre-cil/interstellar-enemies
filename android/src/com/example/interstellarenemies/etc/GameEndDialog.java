package com.example.interstellarenemies.etc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.interstellarenemies.R;
import com.example.interstellarenemies.SinglePlayerPage;
import com.example.interstellarenemies.init.home.HomePage;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK;
import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class GameEndDialog extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_end_dialog);
        int score = Integer.parseInt(getIntent().getStringExtra("score"));
        Intent goSinglePlayer = new Intent(this, SinglePlayerPage.class);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        Dialog gameEndDialog = new Dialog(this);
        gameEndDialog.setContentView(R.layout.game_end_dialog);
        Button acceptBut = gameEndDialog.findViewById(R.id.AcceptButton);
        acceptBut.setText(getString(R.string.playAgain));
        //if click to accept button
        TextView dialogText = gameEndDialog.findViewById(R.id.dialogText1);
        TextView dialogText2 = gameEndDialog.findViewById(R.id.dialogText2);
        dialogText2.setText(getResources().getString(R.string.hasBeenDestroyed) + " " + score);
        dialogText.setText(getResources().getString(R.string.anotherGame));
        acceptBut.setOnClickListener((View v) -> {

            FirebaseDatabase rootRef = FirebaseDatabase.getInstance();
            rootRef.getReference("users").child(user.getUid()).child("current_ship").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String shipName = snapshot.getValue().toString();

                    rootRef.getReference().child("ships").child(shipName).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String laserCount = snapshot.child("laser_count").getValue().toString();
                            String health = snapshot.child("health").getValue().toString();
                            String armor = snapshot.child("armor").getValue().toString();
                            String shipSpeed = snapshot.child("ship_speed").getValue().toString();
                            goSinglePlayer.putExtra("laser_count", laserCount);
                            goSinglePlayer.putExtra("health", health);
                            goSinglePlayer.putExtra("armor", armor);
                            goSinglePlayer.putExtra("ship_speed", shipSpeed);
                            goSinglePlayer.putExtra("ship_name", shipName);
                            startActivity(goSinglePlayer);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }

            });
            gameEndDialog.dismiss();
        });

        //if click to cancel
        gameEndDialog.findViewById(R.id.CancelButton).setOnClickListener((View v) -> {
            Intent i = new Intent(this, HomePage.class);
            i.setFlags(FLAG_ACTIVITY_NEW_TASK | FLAG_ACTIVITY_CLEAR_TASK | FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            gameEndDialog.dismiss();
        });
        gameEndDialog.show();
    }
}