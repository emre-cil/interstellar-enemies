package com.example.interstellarenemies.planet.create;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.*;
import com.example.interstellarenemies.R;

public class CreateAPlanetFragment extends Fragment {
    public CreateAPlanetFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_create_a_planet, container, false);
    }
}