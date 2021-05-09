package com.example.interstellarenemies;

import android.os.*;
import android.view.*;
import android.widget.*;
import androidx.annotation.*;
import androidx.fragment.app.Fragment;
import com.google.firebase.database.*;
import java.util.*;

public class AnnouncementFragment extends Fragment {
    private final ArrayList<AnnouncementObject> listItems = new ArrayList<>();
    private LinkedList<AnnouncementObject> annList = new LinkedList<>();
    private AnnouncementAdapter adapter;
    private ListView mListView;

    public AnnouncementFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View ret_view = inflater.inflate(R.layout.fragment_announcement, container, false);
        mListView = (ListView) ret_view.findViewById(R.id.Announcements_ListView);
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("announcements/");
        adapter = new AnnouncementAdapter(getActivity(), android.R.layout.simple_list_item_1, listItems);
        mListView.setAdapter(adapter);

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                LinkedList<AnnouncementObject> annListNew = new LinkedList<>();
                for (DataSnapshot announcementTable : snapshot.getChildren()) {
                    String date = "", id, content = "", header = "";
                    for (DataSnapshot elem : announcementTable.getChildren()) {
                        switch (elem.getKey()) {
                            case "content":
                                content = elem.getValue().toString();
                                break;
                            case "header":
                                header = elem.getValue().toString();
                                break;
                            case "date":
                                date = elem.getValue().toString();
                                break;
                        }
                    }
                    id = announcementTable.getKey();
                    annListNew.add(new AnnouncementObject(id, header, content, date));
                }
                adapter.clear();
                annList = annListNew;
                adapter.addAll(annList);
                mListView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        mListView.setOnItemClickListener((parent, view, position, id) -> {
            getActivity().getIntent().putExtra(
                    "fragment::announcement::content::id", annList.get(position).id);
            getActivity().getSupportFragmentManager().beginTransaction().replace(
                    R.id.fragment_container, new AnnouncementContentFragment()).commit();
        });

        return ret_view;
    }
}