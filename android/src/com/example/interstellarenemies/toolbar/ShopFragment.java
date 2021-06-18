package com.example.interstellarenemies.toolbar;

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

import com.example.interstellarenemies.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ShopFragment extends Fragment {
    private Dialog buyDialog, infoDialog, selectDialog;
    private int money;
    private Button acceptBut;
    private TextView dialogText, ship1area, ship2area, ship3area, ship4area,
            healthValue, armorValue, speedValue, laserCoValue, moneyText;
    private ImageView hangar2, hangar3, hangar4, hangar5, shipPicture;
    private ArrayList<String> shipList;
    private ArrayList<ImageView> hangarList;
    private String[] hangarLocations;
    private int shipCount;
    private DatabaseReference dbRef;
    private FirebaseUser user;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public void onResume() {
        super.onResume();
        refreshMoney(dbRef, moneyText);
        //ship dialog operations.
        ship1area.setOnClickListener((View v) -> {
            infoDialogInformation("ship1");
        });

        ship2area.setOnClickListener((View v) -> {
            infoDialogInformation("ship2");
        });

        ship3area.setOnClickListener((View v) -> {
            infoDialogInformation("ship3");
        });

        ship4area.setOnClickListener((View v) -> {
            infoDialogInformation("ship4");
        });



        DatabaseReference shipRef = dbRef.child("ships");
        ValueEventListener eventListener = new ValueEventListener()  {
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
                dbRef.child("ship_count").setValue(String.valueOf(shipCount + 1));
                shipList.clear();

                if (shipCount > 0)
                    hangar2.setOnClickListener((View v) -> {
                        selectShip(dbRef, hangarLocations[0]);
                    });
                if (shipCount > 1)
                    hangar3.setOnClickListener((View v) -> {

                        selectShip(dbRef, hangarLocations[1]);
                    });

                if (shipCount > 2)
                    hangar4.setOnClickListener((View v) -> {

                        selectShip(dbRef, hangarLocations[2]);
                    });

                if (shipCount > 3)
                    hangar5.setOnClickListener((View v) -> {
                        selectShip(dbRef, hangarLocations[3]);
                    });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        shipRef.addValueEventListener(eventListener);
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
        View view = inflater.inflate(R.layout.fragment_shop, container, false);
        user = FirebaseAuth.getInstance().getCurrentUser();
        dbRef = FirebaseDatabase.getInstance().getReference("users/" + user.getUid());
        hangarLocations = new String[4];
        shipList = new ArrayList<>();
        hangarList = new ArrayList<>();
        shipCount = 0;
        moneyText = view.findViewById(R.id.moneyShop);
        //hangar images assign.
        hangar2 = view.findViewById(R.id.hangar2);
        hangar3 = view.findViewById(R.id.hangar3);
        hangar4 = view.findViewById(R.id.hangar4);
        hangar5 = view.findViewById(R.id.hangar5);
        //adding them into list.
        hangarList.add(hangar2);
        hangarList.add(hangar3);
        hangarList.add(hangar4);
        hangarList.add(hangar5);
        //ship images assign.
        ship1area = view.findViewById(R.id.ship1background);
        ship2area = view.findViewById(R.id.ship2background);
        ship3area = view.findViewById(R.id.ship3background);
        ship4area = view.findViewById(R.id.ship4background);
        return view;
    }

    public void infoDialogInformation(String shipName) {
        infoDialog = new Dialog(getActivity());
        infoDialog.setContentView(R.layout.dialog_ship_information);
        healthValue = infoDialog.findViewById(R.id.shipHealthValueShop);
        armorValue = infoDialog.findViewById(R.id.shipArmorValueShop);
        speedValue = infoDialog.findViewById(R.id.shipSpeedValueShop);
        laserCoValue = infoDialog.findViewById(R.id.shipLaserCountValueShop);
        shipPicture = infoDialog.findViewById(R.id.shipInfoImageShop);

        shipPicture.setImageResource(getResources().getIdentifier(shipName, "drawable", getActivity().getPackageName()));
        DatabaseReference shipRef = FirebaseDatabase.getInstance().getReference("ships/" + shipName);
        shipRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                healthValue.setText(snapshot.child("health").getValue(Integer.class).toString());
                armorValue.setText(snapshot.child("armor").getValue(Integer.class).toString());
                laserCoValue.setText(snapshot.child("laser_count").getValue(Integer.class).toString());
                speedValue.setText(snapshot.child("ship_speed").getValue(Integer.class).toString());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        //if click to cancel
        infoDialog.findViewById(R.id.closeButtonShipInfo).setOnClickListener(view -> {
            infoDialog.dismiss();
        });
        infoDialog.show();
    }

    @SuppressLint("SetTextI18n")
    public void dialogOperations(int money, int shipPrice, String shipName, String shipId, DatabaseReference dbRef, View v, TextView moneyText) {
        buyDialog = new Dialog(getActivity());
        buyDialog.setContentView(R.layout.sample_dialog);
        //if click to buy button
        dialogText = buyDialog.findViewById(R.id.DialogText);
        dialogText.setText(getString(R.string.doYouWantToPay) + shipPrice + getString(R.string.coin));
        buyDialog.findViewById(R.id.DialogGreenButton).setOnClickListener(view -> {
            if (money >= shipPrice) {
                dbRef.child("ships").child(shipName).setValue(shipId);
                dbRef.child("money").setValue(String.valueOf(money - shipPrice));
                refreshMoney(dbRef, moneyText);
                buyDialog.dismiss();
            } else {
                buyDialog.dismiss();
                buyDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                Snackbar.make(v, getString(R.string.notEnoughMoney), Snackbar.LENGTH_LONG)
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
        selectDialog = new Dialog(getActivity());
        selectDialog.setContentView(R.layout.sample_dialog);
        acceptBut = selectDialog.findViewById(R.id.DialogGreenButton);
        acceptBut.setText(getString(R.string.accept));
        dialogText = selectDialog.findViewById(R.id.DialogText);
        dialogText.setText(getString(R.string.doYouWantToSelect));
        acceptBut.findViewById(R.id.DialogGreenButton).setOnClickListener(view -> {
            dbRef.child("current_ship").setValue(shipName);
            selectDialog.dismiss();
        });

        //if click to cancel
        selectDialog.findViewById(R.id.DialogCancelButton).setOnClickListener(view -> {
            selectDialog.dismiss();
        });
        selectDialog.show();
    }

}