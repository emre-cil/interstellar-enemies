package com.example.interstellarenemies;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.*;

public class MessagesFragment extends Fragment {
    public MessagesFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_messages, container, false);
    }
}