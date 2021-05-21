package com.example.interstellarenemies.leaderboard;

public class LeaderboardObject {
    String name;
    Integer gamesWon;

    public LeaderboardObject(String name, Integer gamesWon) {
        this.name = name;
        this.gamesWon = gamesWon;
    }

    public Integer getGamesWon() {
        return gamesWon;
    }
}
