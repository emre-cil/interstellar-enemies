package com.example.interstellarenemies.planet.join;

import androidx.annotation.NonNull;

import java.util.*;

public class JoinListObject {
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

