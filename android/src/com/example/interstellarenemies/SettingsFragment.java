package com.example.interstellarenemies;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.*;
import android.widget.Button;
import com.example.interstellarenemies.init.MainActivity;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

public class SettingsFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        Button chPass = (Button) getActivity().findViewById(R.id.changePasswordButton);
        Button chUsername = getActivity().findViewById(R.id.changeUserNameButton);
        chPass.setOnClickListener((View v) -> {
            FirebaseAuth mAuth = MainActivity.getmAuth();
            mAuth.sendPasswordResetEmail(mAuth.getCurrentUser().getEmail());
            Snackbar.make(v, "A Password Reset Mail Has Sent", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        });


        chUsername.setOnClickListener((View v) -> {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new ChangeUsernameFragment()).commit();
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }
}