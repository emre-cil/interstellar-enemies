package com.example.interstellarenemies.leaderboard;

public class LeaderboardObject {
    String name;
    Integer score;

    public LeaderboardObject(String name, Integer highScore) {
        this.name = name;
        this.score = highScore;
    }

    public Integer getGamesWon() {
        return score;
    }
}
