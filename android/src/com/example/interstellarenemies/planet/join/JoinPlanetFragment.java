package com.example.interstellarenemies.planet.join;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.*;
import android.widget.*;
import com.example.interstellarenemies.R;
import com.example.interstellarenemies.messages.conv.MessagesConvFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import java.util.*;

public class JoinPlanetFragment extends Fragment {

    LinkedList<JoinListObject> planetList = new LinkedList<>();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View ret_view = inflater.inflate(R.layout.fragment_join_planet, container, false);
        ListView lv = ret_view.findViewById(R.id.joinPlanet_PlanetList);
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("planets");
        adapter = new JoinPlanetAdapter(getActivity(), android.R.layout.simple_list_item_1, listItems);
        lv.setAdapter(adapter);
        lv.setTextFilterEnabled(true);

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                LinkedList<JoinListObject> jlo = new LinkedList<>();
                for (DataSnapshot planetTable : snapshot.getChildren()) {
                    String max_users = "", name = "", playing = "", id = "";
                    List<String> users = new LinkedList<>();
                    for (DataSnapshot elem : planetTable.getChildren()) {
                        switch (elem.getKey()) {
                            case "max_users": max_users = elem.getValue().toString(); break;
                            case "name": name = elem.getValue().toString(); break;
                            case "playing": playing = elem.getValue().toString(); break;
                            case "users":
                                users = elem.getValue(new GenericTypeIndicator<List<String>>(){});
                                break;
                        }
                    }
                    if (playing.equals("false")) {
                        id = planetTable.getKey();
                        jlo.add(new JoinListObject(id, name, max_users, playing, users));
                    }
                }
                adapter.clear();
                planetList = jlo;
                adapter.addAll(planetList);
                lv.setAdapter(adapter);
                getSearchResults("");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        searchView = ret_view.findViewById(R.id.joinPlanet_searchView);
        searchView.setIconifiedByDefault(false);
        searchView.setSubmitButtonEnabled(true);
        searchView.setQueryHint("Search");

        lv.setOnItemClickListener((parent, view, position, id) -> {
            JoinListObject jlo = listItems.get(position);
            getActivity().getIntent().putExtra("fragment::messages::receiver::id", FirebaseAuth.getInstance().getCurrentUser().getUid());
            getActivity().getIntent().putExtra("fragment::messages::planet::id", jlo.id);
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new MessagesConvFragment()).commit();
        });

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

        return ret_view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void getSearchResults(String query) {
        ArrayList<JoinListObject> filtered = new ArrayList<>();
        for (JoinListObject jlo : planetList) {
            if (jlo.name.toLowerCase().contains(query.toLowerCase())) {
                filtered.add(jlo);
            } else if (jlo.id.toLowerCase().contains(query.toLowerCase())) {
                filtered.add(jlo);
            }
        }

        adapter.clear();
        adapter.addAll(filtered);
    }

}