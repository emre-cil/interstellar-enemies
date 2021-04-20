package com.example.interstellarenemies;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link JoinPlanetFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class JoinPlanetFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    ArrayList<JoinListObject> planetList = new ArrayList<>();
    ArrayList<JoinListObject> listItems = new ArrayList<>();
    ArrayAdapter<JoinListObject> adapter;
    SearchView searchView;

    public JoinPlanetFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment JoinPlanetFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static JoinPlanetFragment newInstance(String param1, String param2) {
        JoinPlanetFragment fragment = new JoinPlanetFragment();
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
        return inflater.inflate(R.layout.fragment_join_planet, container, false);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onResume() {
        super.onResume();

        ListView lv = getActivity().findViewById(R.id.joinPlanet_PlanetList);
        adapter = new JoinListAdapter
                (getActivity(), android.R.layout.simple_list_item_1, listItems);
        lv.setAdapter(adapter);
        lv.setTextFilterEnabled(true);

        lv.setOnItemClickListener((parent, view, position, id) -> {
            JoinListObject jlo = listItems.get(position);
            // TODO:
            //  Don't show toast
            //      Instead join the list or something like that.
            Toast.makeText(getActivity(),
                    String.format(
                            "Header: %s\n" +
                                    "Content: %s\n" +
                                    "UUID: %s\n" +
                                    "Date: %s\n" +
                                    "Players: %d/%d\n",
                            jlo.getHeader(),
                            jlo.getContent(),
                            jlo.getUUID().toString(),
                            jlo.getCreateDate().toString(),
                            jlo.getCurrPlayers(),
                            jlo.getMaxPlayers()
                    ), Toast.LENGTH_SHORT).show();
        });

        //TODO:
        // This is for testing purposes.
        for (int i = 0; i < 1000; i++) {
            planetList.add(new JoinListObject(i + ": Header", i + ": Content", new Date(), 4));
        }

        getSearchResults("");

        searchView = getActivity().findViewById(R.id.joinPlanet_searchView);
        searchView.setIconifiedByDefault(false);
        searchView.setSubmitButtonEnabled(true);
        searchView.setQueryHint("Search");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //TODO: after user presses enter button.
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                getSearchResults(newText);
                return false;
            }
        });
    }

    private void getSearchResults(String query) {
        ArrayList<JoinListObject> filtered = new ArrayList<>();

        // TODO:
        //  Add extra search result matching with current players.
        for (JoinListObject jlo : planetList) {
            // TODO: Not a must but fuzzy search would be really nice.
            if (jlo.getHeader().toLowerCase().contains(query.toLowerCase())) {
                filtered.add(jlo);
            }
        }

        adapter.clear();
        adapter.addAll(filtered);
    }
}