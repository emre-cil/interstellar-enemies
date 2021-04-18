package com.example.interstellarenemies;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
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
        Intent goJoinPlanet = new Intent(getActivity().getApplicationContext(), JoinPlanetPage.class);
        Intent goCreatePlanet = new Intent(getActivity().getApplicationContext(), CreatePlanetPage.class);

        Button singlePlayerBut = getActivity().findViewById(R.id.singlePlayerBut);
        Button joinPlanetBut = getActivity().findViewById(R.id.joinAPlanetBut);
        Button createPlanetBut = getActivity().findViewById(R.id.createAPlanetBut);

        //go single player page
        singlePlayerBut.setOnClickListener((View v) -> startActivity(goSinglePlayer));

        //go join a planet page
        joinPlanetBut.setOnClickListener((View v) -> startActivity(goJoinPlanet));

        //go create a planet page
        createPlanetBut.setOnClickListener((View v) -> startActivity(goCreatePlanet));
    }
}