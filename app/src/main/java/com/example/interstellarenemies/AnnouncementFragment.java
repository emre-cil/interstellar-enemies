package com.example.interstellarenemies;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AnnouncementFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AnnouncementFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private final LinkedList<AnnouncementObject> annList = new LinkedList<>();
    private final ArrayList<AnnouncementObject> listItems = new ArrayList<>();
    private AnnouncementAdapter adapter;

    public AnnouncementFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AnnouncementFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AnnouncementFragment newInstance(String param1, String param2) {
        AnnouncementFragment fragment = new AnnouncementFragment();
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
    public void onResume() {
        super.onResume();
        ListView lv = getActivity().findViewById(R.id.Announcements_ListView);
        adapter = new AnnouncementAdapter(getActivity(), android.R.layout.simple_list_item_1, listItems);

        lv.setAdapter(adapter);

        //TODO:
        // This is just for testing, but it works
        //  We will create AnnouncementObject(String: header, String: content)
        //  where AnnouncementAdapter only shows header in the list.
        //  but when we click on an item in the list,
        //  it should show content and header. (Not implemented yet.)
        for (int i = 0; i < 1000; i++) {
            annList.add(new AnnouncementObject(i + ": HEADER", i + ": CONTENT", new Date()));
        }

        adapter.addAll(annList);

        lv.setOnItemClickListener((parent, view, position, id) -> {
            AnnouncementObject ao = listItems.get(position);
            //TODO:
            // Show popup information about announcement.
            Toast.makeText(getActivity(),
                    String.format(
                            "Header: %s\n" +
                                    "Content: %s\n" +
                                    "UUID: %s\n" +
                                    "Date: %s\n",
                            ao.getHeader(),
                            ao.getContent(),
                            ao.getUUID().toString(),
                            ao.getDate().toString()
                    ), Toast.LENGTH_SHORT).show();
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_announcement, container, false);
    }
}