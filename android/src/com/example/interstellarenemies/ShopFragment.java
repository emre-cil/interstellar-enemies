package com.example.interstellarenemies;

import android.annotation.SuppressLint;
import android.app.Dialog;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.*;

import android.widget.TextView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ShopFragment extends Fragment {
    private Dialog buyDialog;
    private int money;
    private TextView dialogText;


    public ShopFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void onResume() {
        super.onResume();

        TextView moneyText = getActivity().findViewById(R.id.moneyShop);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("users/" + user.getUid());

        refreshMoney(dbRef,moneyText);

        buyDialog = new Dialog(getActivity());

        getActivity().findViewById(R.id.ship1_button).setOnClickListener((View v) -> {
            dialogOperations(money, 25000, "ship1", dbRef, getView(),moneyText);
        });
        getActivity().findViewById(R.id.ship2_button).setOnClickListener((View v) -> {
            dialogOperations(money, 50000, "ship2", dbRef, getView(),moneyText);
        });
        getActivity().findViewById(R.id.ship3_button).setOnClickListener((View v) -> {
            dialogOperations(money, 75000, "ship3", dbRef, getView(),moneyText);
        });
        getActivity().findViewById(R.id.ship4_button).setOnClickListener((View v) -> {
            dialogOperations(money, 100000, "ship4", dbRef, getView(),moneyText);
        });


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_shop, container, false);
    }

    @SuppressLint("SetTextI18n")
    public void dialogOperations(int money, int shipPrice, String shipName, DatabaseReference dbRef, View v, TextView moneyText) {
        buyDialog.setContentView(R.layout.sample_dialog);
        //if click to buy button
        dialogText =  buyDialog.findViewById(R.id.DialogText);
        dialogText.setText("Do you want to pay\n "+shipPrice +" coin?");
        buyDialog.findViewById(R.id.DialogGreenButton).setOnClickListener(view -> {
            if (money >= shipPrice) {
                dbRef.child("ships").child(shipName).setValue("using");
                dbRef.child("money").setValue(money - shipPrice);
                refreshMoney(dbRef,moneyText);
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

    public void refreshMoney(DatabaseReference dbRef,TextView moneyText) {
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
}