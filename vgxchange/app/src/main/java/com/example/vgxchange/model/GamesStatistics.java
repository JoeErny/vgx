package com.example.vgxchange.model;

public class GamesStatistics {
    public int totalSold;
    public String name;

    public GamesStatistics(String name, int totalSold) {
        this.name = name;
        this.totalSold = totalSold;
    }

    public GamesStatistics() {
    }

    public int getTotalGame() { return totalSold; }
    public String getNameGame() { return name; }
    public void setTotalGame(int totalGame) { this.totalSold = totalSold; }
    public void setNameGame(String nameGame) { this.name = name; }
}
