package com.example.interstellarenemies;

import android.annotation.SuppressLint;
import android.app.Dialog;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.*;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.interstellarenemies.init.HomePage;
import com.example.interstellarenemies.invite.InvitesObject;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.LinkedList;

public class ShopFragment extends Fragment {
    private Dialog buyDialog;
    private int money;
    private Button acceptBut;
    private TextView dialogText;
    private ImageView hangar2, hangar3, hangar4, hangar5;
    private ArrayList<String> shipList;
    private ArrayList<ImageView> hangarList;
    private String[] hangarLocations;
    private int shipCount;


    public ShopFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hangarLocations = new String[4];

    }

    @Override
    public void onResume() {
        super.onResume();
        shipList = new ArrayList<>();
        hangarList = new ArrayList<>();
        shipCount = 0;
        buyDialog = new Dialog(getActivity());
        TextView moneyText = getActivity().findViewById(R.id.moneyShop);
        hangar2 = getActivity().findViewById(R.id.hangar2);
        hangar3 = getActivity().findViewById(R.id.hangar3);
        hangar4 = getActivity().findViewById(R.id.hangar4);
        hangar5 = getActivity().findViewById(R.id.hangar5);
        hangarList.add(hangar2);
        hangarList.add(hangar3);
        hangarList.add(hangar4);
        hangarList.add(hangar5);


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("users/" + user.getUid());

        refreshMoney(dbRef, moneyText);


        DatabaseReference messagesRef = dbRef.child("ships");
        ValueEventListener eventListener = new ValueEventListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (!ds.getKey().toString().equals("ship"))
                        shipList.add(ds.getKey());
                }
                for (int i = 0; i < shipList.size(); i++) {
                    hangarLocations[i] = shipList.get(i);
                    hangarList.get(i).setImageResource(getResources().getIdentifier(shipList.get(i), "drawable", getActivity().getPackageName()));
                }
                shipCount = shipList.size();
                dbRef.child("ship_count").setValue(String.valueOf(shipCount+1));
                shipList.clear();

                if (shipCount>0)
                    hangar2.setOnClickListener((View v) -> {
                        selectShip(dbRef, hangarLocations[0]);
                    });
                if (shipCount>1)
                    hangar3.setOnClickListener((View v) -> {

                        selectShip(dbRef, hangarLocations[1]);
                    });

                if (shipCount>2)
                    hangar4.setOnClickListener((View v) -> {

                        selectShip(dbRef, hangarLocations[2]);
                    });

                if (shipCount>3)
                    hangar5.setOnClickListener((View v) -> {

                        selectShip(dbRef, hangarLocations[3]);
                    });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        messagesRef.addValueEventListener(eventListener);



        getActivity().findViewById(R.id.ship1_button).setOnClickListener((View v) -> {
            dialogOperations(money, 25000, "ship1", "700175", dbRef, getView(), moneyText);
        });
        getActivity().findViewById(R.id.ship2_button).setOnClickListener((View v) -> {
            dialogOperations(money, 50000, "ship2", "700180", dbRef, getView(), moneyText);
        });
        getActivity().findViewById(R.id.ship3_button).setOnClickListener((View v) -> {
            dialogOperations(money, 75000, "ship3", "700177", dbRef, getView(), moneyText);
        });
        getActivity().findViewById(R.id.ship4_button).setOnClickListener((View v) -> {
            dialogOperations(money, 100000, "ship4", "700188", dbRef, getView(), moneyText);
        });

        getActivity().findViewById(R.id.hangar1).setOnClickListener((View v) -> {
            selectShip(dbRef, "ship");
        });



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_shop, container, false);
    }

    @SuppressLint("SetTextI18n")
    public void dialogOperations(int money, int shipPrice, String shipName, String shipId, DatabaseReference dbRef, View v, TextView moneyText) {
        buyDialog.setContentView(R.layout.sample_dialog);
        //if click to buy button
        dialogText = buyDialog.findViewById(R.id.DialogText);
        dialogText.setText("Do you want to pay\n " + shipPrice + " coin?");
        buyDialog.findViewById(R.id.DialogGreenButton).setOnClickListener(view -> {
            if (money >= shipPrice) {
                dbRef.child("ships").child(shipName).setValue(shipId);
                dbRef.child("money").setValue(String.valueOf(money - shipPrice));
                refreshMoney(dbRef, moneyText);
                buyDialog.dismiss();
            } else {
                buyDialog.dismiss();
                buyDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                Snackbar.make(v, "You do not have enough money to buy this ship", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        //if click to cancel
        buyDialog.findViewById(R.id.DialogCancelButton).setOnClickListener(view -> {
            buyDialog.dismiss();
        });
        buyDialog.show();
    }

    public void refreshMoney(DatabaseReference dbRef, TextView moneyText) {
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                money = Integer.parseInt(snapshot.child("money").getValue().toString());
                moneyText.setText(snapshot.child("money").getValue().toString());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }


    @SuppressLint("SetTextI18n")
    public void selectShip(DatabaseReference dbRef, String shipName) {
        buyDialog.setContentView(R.layout.sample_dialog);
        acceptBut = buyDialog.findViewById(R.id.DialogGreenButton);
        acceptBut.setText("ACCEPT");
        dialogText = buyDialog.findViewById(R.id.DialogText);
        dialogText.setText("Do you want to select\n " + "this ship?");
        acceptBut.findViewById(R.id.DialogGreenButton).setOnClickListener(view -> {
            dbRef.child("current_ship").setValue(shipName);
            buyDialog.dismiss();
        });

        //if click to cancel
        buyDialog.findViewById(R.id.DialogCancelButton).setOnClickListener(view -> {
            buyDialog.dismiss();
        });
        buyDialog.show();
    }
    public void selectShipOnDataChange(){

    }
}