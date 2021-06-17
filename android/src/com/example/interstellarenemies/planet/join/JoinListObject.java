package com.example.interstellarenemies.planet.join;

import java.util.*;

public class JoinListObject {
    public String name, id, max_users, playing;
    public List<String> users;
    private String password_hash;

    public JoinListObject(String id, String name, String max_users, String playing, List<String> users) {
        this.id = id;
        this.name = name;
        this.max_users = max_users;
        this.playing = playing;
        this.users = users;
    }
}

