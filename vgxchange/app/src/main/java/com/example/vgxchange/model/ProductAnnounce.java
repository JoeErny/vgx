package com.example.vgxchange.model;


import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

@Entity(tableName = "product")
public class ProductAnnounce implements Serializable
{
    @PrimaryKey
    @NotNull
    @ColumnInfo(name = "idproduct")
    public String id;
    public String parution;
    public String announceType;
    public String announceState;
    public double price;
    public String photoLink;

    @Embedded(prefix = "product_game_")
    public Game game;
    @Embedded(prefix = "product_announcer_")
    public User announcer;

    public ProductAnnounce() {
    }

    @Ignore
    public ProductAnnounce(@NotNull String id, String parution, String announceType, String announceState, double price, String photoLink, Game game, User announcer) {
        this.id = id;
        this.parution = parution;
        this.announceType = announceType;
        this.announceState = announceState;
        this.price = price;
        this.photoLink = photoLink;
        this.game = game;
        this.announcer = announcer;
    }

    public String getId() {
        return id;
    }

    public String getAnnounceType() {
        return announceType;
    }

    public String getAnnounceState() {
        return announceState;
    }

    public double getPrice() {
        return price;
    }

    public String getPhotoLink() {
        return photoLink;
    }

    public Game getGame() {
        return game;
    }

    public User getAnnouncer() {
        return announcer;
    }

    public String getParution() {
        return parution;
    }



    @Override
    public String toString() {
        return "ProductAnnounce{" +
                "id='" + id + '\'' +
                ", parution='" + parution + '\'' +
                ", announcer=" + announcer +
                ", announceType='" + announceType + '\'' +
                ", announceState='" + announceState + '\'' +
                ", price=" + price +
                ", photoLink='" + photoLink + '\'' +
                ", game=" + game +
                '}';
    }
}
