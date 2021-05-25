package com.example.vgxchange.database.service;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.vgxchange.database.RoomDbManager;

public class DbActivity extends Activity {

    public static RoomDbManager getDb() {
        return db;
    }

    private static RoomDbManager db;


    public DbActivity() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

        db = Room.databaseBuilder(getApplicationContext(),
                RoomDbManager.class, "vgxdb").build();

    }
}
