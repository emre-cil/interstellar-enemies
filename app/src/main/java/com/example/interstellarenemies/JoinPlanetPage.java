package com.example.interstellarenemies;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

class JoinListObject {
    @NonNull
    private final Date createDate;
    @NonNull
    private final Integer maxPlayers;
    @NonNull
    private final UUID uuid;
    private String header;
    private String content;
    private Integer currPlayers;

    public JoinListObject(String header, String content, Date date, Integer maxPlayers) {
        this.header = header;
        this.content = content;
        this.maxPlayers = maxPlayers;
        this.currPlayers = 0;
        this.uuid = UUID.randomUUID();
        this.createDate = date;
    }

    public String getHeader() {
        return header;
    }

    public Integer getCurrPlayers() {
        return currPlayers;
    }

    public Integer getMaxPlayers() {
        return maxPlayers;
    }

    public UUID getUUID() {
        return uuid;
    }

    public String getContent() {
        return content;
    }

    public Date getCreateDate() {
        return createDate;
    }
}

class JoinListAdapter extends ArrayAdapter<JoinListObject> {

    public JoinListAdapter(@NonNull Context context, int resource, ArrayList<JoinListObject> jloList) {
        super(context, resource, jloList);
    }

    @SuppressLint("DefaultLocale")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        } else {
            view = convertView;
        }
        TextView textView = (TextView) view;
        JoinListObject jlo = getItem(position);
        textView.setText(String.format("%d/%d: %s", jlo.getCurrPlayers(), jlo.getMaxPlayers(), jlo.getHeader()));

        return view;
    }
}

public class JoinPlanetPage extends AppCompatActivity {
    ArrayList<JoinListObject> planetList = new ArrayList<>();
    ArrayList<JoinListObject> listItems = new ArrayList<>();
    ArrayAdapter<JoinListObject> adapter;
    SearchView searchView;

    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_planet_page);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        ListView lv = findViewById(R.id.joinPlanet_PlanetList);
        adapter = new JoinListAdapter
                (this, android.R.layout.simple_list_item_1, listItems);
        lv.setAdapter(adapter);
        lv.setTextFilterEnabled(true);

        lv.setOnItemClickListener((parent, view, position, id) -> {
            JoinListObject jlo = listItems.get(position);
            // TODO:
            //  Don't show toast
            //      Instead join the list or something like that.
            Toast.makeText(this,
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

    @Override
    protected void onResume() {
        super.onResume();
    }
}
