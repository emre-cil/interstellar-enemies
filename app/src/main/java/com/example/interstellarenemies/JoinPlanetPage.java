package com.example.interstellarenemies;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class JoinPlanetPage extends AppCompatActivity {
    ArrayList<String> planetList = new ArrayList<String>();
    ArrayList<String> listItems = new ArrayList<String>();
    ArrayAdapter<String> adapter;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_planet_page);

        ListView lv = findViewById(R.id.joinPlanet_PlanetList);
        adapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, listItems);
        lv.setAdapter(adapter);
        lv.setTextFilterEnabled(true);

        //TODO:
        // This is for testing purposes.
        for (int i = 0; i < 100000; i++) {
            planetList.add(i + "");
        }

        getSearchResults("");

        searchView = findViewById(R.id.joinPlanet_searchView);
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
        ArrayList<String> filtered = new ArrayList<>();

        for (String s : planetList) {
            // TODO: Not a must but fuzzy search would be really nice.
            if (s.toLowerCase().contains(query.toLowerCase())) {
                filtered.add(s);
            }
        }

        adapter.clear();
        adapter.addAll(filtered);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
