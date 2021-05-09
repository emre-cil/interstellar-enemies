package com.example.interstellarenemies.planet.join;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.*;
import android.widget.*;
import com.example.interstellarenemies.R;
import java.util.*;

public class JoinPlanetFragment extends Fragment {

    ArrayList<JoinListObject> planetList = new ArrayList<>();
    ArrayList<JoinListObject> listItems = new ArrayList<>();
    ArrayAdapter<JoinListObject> adapter;
    SearchView searchView;

    public JoinPlanetFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_join_planet, container, false);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onResume() {
        super.onResume();

        ListView lv = getActivity().findViewById(R.id.joinPlanet_PlanetList);
        adapter = new JoinPlanetAdapter
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