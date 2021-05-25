package com.example.vgxchange.database;
import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.vgxchange.database.dao.CategoryDao;
import com.example.vgxchange.database.dao.GameDao;
import com.example.vgxchange.database.dao.ProductAnnounceDao;
import com.example.vgxchange.model.Category;
import com.example.vgxchange.model.Game;
import com.example.vgxchange.model.ProductAnnounce;

@Database(entities = {ProductAnnounce.class, Game.class, Category.class}, version = 1)
public abstract class RoomDbManager extends RoomDatabase{
    public abstract GameDao GameDao();
    public abstract ProductAnnounceDao ProductAnnounceDao();
    public abstract CategoryDao CategoryDao();
    private static String DB_NAME = "vgxdb";


    private static volatile RoomDbManager instance;

    public static synchronized RoomDbManager getInstance(Context context) {
        if (instance == null) {
            instance = create(context);
        }
        return instance;
    }

    private static RoomDbManager create(final Context context) {
        return Room.databaseBuilder(
                context,
                RoomDbManager.class,
                DB_NAME).allowMainThreadQueries().build();

        //db = Room.databaseBuilder(getApplicationContext(),
        // RoomDbManager.class, "vgxdb").allowMainThreadQueries().build();

    }



}


