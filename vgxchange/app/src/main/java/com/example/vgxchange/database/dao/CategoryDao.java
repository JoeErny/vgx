package com.example.vgxchange.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.vgxchange.model.Category;
import com.example.vgxchange.model.Game;

import java.util.List;

@Dao
public interface CategoryDao {

    @Query("SELECT * FROM category")
    List<Category> getAll();
    @Insert
    void insertAll(Category... categories);
    @Update
    void updateAll(Category... categories);
    @Query("SELECT count(*) FROM category WHERE idCategory LIKE :categoryId")
    int countAllById(String categoryId);
}
