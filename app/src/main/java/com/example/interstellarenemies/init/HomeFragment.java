package com.example.interstellarenemies.init;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.*;
import android.widget.Button;

import com.example.interstellarenemies.planet.join.JoinPlanetFragment;
import com.example.interstellarenemies.R;
import com.example.interstellarenemies.SinglePlayerPage;
import com.example.interstellarenemies.planet.create.CreatePlanetPage;

public class HomeFragment extends Fragment {

    public HomeFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        /*
         * TODO: sayfalarda geri donus olmayacak.
         *  Bazi intentleri fragment ile degistir
         *  Ama oyun farkli bir layout gerektirdigi icin
         *  ayri bir Intent ile calismali.
         *
         */
        Intent goSinglePlayer = new Intent(getActivity().getApplicationContext(), SinglePlayerPage.class);
        Intent goCreatePlanet = new Intent(getActivity().getApplicationContext(), CreatePlanetPage.class);

        Button singlePlayerBut = getActivity().findViewById(R.id.singlePlayerBut);
        Button createPlanetBut = getActivity().findViewById(R.id.createAPlanetBut);

        //go single player page
        singlePlayerBut.setOnClickListener((View v) -> {
            startActivity(goSinglePlayer);
        });

        //go join a planet page
        getActivity().findViewById(R.id.joinAPlanetBut).setOnClickListener((View v) -> {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new JoinPlanetFragment()).commit();
        });

        //go create a planet page
        createPlanetBut.setOnClickListener((View v) -> startActivity(goCreatePlanet));
    }
}