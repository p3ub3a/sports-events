package com.sportsevents.api.model;

public class LeaderboardEntryModel {
    private Long rank;
    private String playerName;
    private Long gamesWon;

    public Long getRank() {
        return this.rank;
    }

    public void setRank(Long rank) {
        this.rank = rank;
    }

    public String getPlayerName() {
        return this.playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public Long getGamesWon() {
        return this.gamesWon;
    }

    public void setGamesWon(Long gamesWon) {
        this.gamesWon = gamesWon;
    }
}
