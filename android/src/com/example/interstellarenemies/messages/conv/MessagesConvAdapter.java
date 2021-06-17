package com.example.interstellarenemies.messages.conv;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.interstellarenemies.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.LinkedList;

public class MessagesConvAdapter extends RecyclerView.Adapter<MessagesConvAdapter.ViewHolder> {
    public static final int MSG_TYPE_LEFT = 0;
    public static final int MSG_TYPE_RIGHT = 1;
    private Context context;
    private String imageURL;
    private ArrayList<MessagesConvObject> aoList;
    FirebaseUser fuser;

    public MessagesConvAdapter(Context context, ArrayList<MessagesConvObject> aoList, String imageURL) {
        this.aoList = aoList;
        this.context = context;
    }

    public void clear() {
        aoList.clear();
    }

    public void addAll(LinkedList<MessagesConvObject> messagesList) {
        aoList.addAll(messagesList);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView show_message;
        public ImageView profile_image;
        public TextView userName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            show_message = itemView.findViewById(R.id.messages_show_message);
            profile_image = itemView.findViewById(R.id.messages_profile_image);
            userName = itemView.findViewById(R.id.msg_receiver_name);
        }
    }

    @NonNull
    @Override
    public MessagesConvAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                LayoutInflater
                        .from(context)
                        .inflate(
                                viewType == MSG_TYPE_LEFT ? R.layout.fragment_messages_conv_left
                                : R.layout.fragment_messages_conv_right
                                , parent
                                , false
                        )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull MessagesConvAdapter.ViewHolder holder, int position) {
        MessagesConvObject msg = aoList.get(position);
        holder.show_message.setText(msg.message);
        if (holder.getItemViewType() == MSG_TYPE_LEFT)
        holder.userName.setText(msg.userName);
        // TODO: If we want to put images, its here.
    }


    @Override
    public int getItemViewType(int position) {
        fuser = FirebaseAuth.getInstance().getCurrentUser();

        if (aoList.get(position).sender.equals(fuser.getUid())) {
            return MSG_TYPE_RIGHT;
        } else {
            return MSG_TYPE_LEFT;
        }
    }

    @Override
    public int getItemCount() {
        return aoList.size();
    }
}

