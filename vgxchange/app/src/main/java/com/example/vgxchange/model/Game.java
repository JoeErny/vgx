package com.example.vgxchange.model;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;


@Entity(tableName = "game")
public class Game {

    @PrimaryKey
    @NotNull
    @ColumnInfo(name = "idgame")
    private String id;
    private String name;
    private String description;
    private int rating;


    @Embedded(prefix = "game_category_")
    public Category category;

    public Game(@NotNull String id, String name, String description, int rating, Category category) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.rating = rating;
        this.category = category;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getRating() {
        return rating;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Game{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", rating=" + rating +
                '}';
    }
}