package com.example.vgxchange.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

import java.util.List;

@Entity(tableName = "category")
public class Category {

    @PrimaryKey
    @NotNull
    @ColumnInfo(name = "idCategory")
    private String id;
    private String label;
    @Ignore
    public Category(@NotNull String id, String label) {
        this.id = id;
        this.label = label;
    }
    public Category() {
    }

    public String getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public void setId(@NotNull String id) {
        this.id = id;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
