package com.example.vgxchange.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import com.example.vgxchange.model.Game;

import java.util.List;

@Dao
public interface GameDao {

    @Query("SELECT * FROM game")
    List<Game> getAll();
    @Query("SELECT * FROM game WHERE idGame IN (:gamesIds)")
    List<Game> loadAllByIds(String[] gamesIds);

    @Query("SELECT * FROM game WHERE idGame LIKE :gameId")
    List<Game> loadAllById(String gameId);

    @Query("SELECT count(*) FROM game WHERE idGame LIKE :gameId")
    int countAllById(String gameId);

    @Query("SELECT * FROM game WHERE idGame LIKE :gamesId LIMIT 1")
    Game loadById(String gamesId);

    @Query("SELECT * FROM game WHERE name LIKE :name LIMIT 1")
    Game findByName(String name);
    @Insert
    void insertAll(Game... games);

    @Update
    void updateAll(Game... games);

    @Delete
    void delete(Game game);

    @Query("delete from game ")
    void deleteAll();
}

