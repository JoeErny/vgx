package com.example.vgxchange;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.vgxchange.api.controllers.BrowsingApiController;
import com.example.vgxchange.api.controllers.CategoryApiController;
import com.example.vgxchange.api.controllers.GameApiController;
import com.example.vgxchange.database.RoomDbManager;
import com.example.vgxchange.database.service.LocalDbPopulator;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity {


    public RoomDbManager getDb() {
        return db;
    }

    private RoomDbManager db;

    public MainActivity()
    {

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        NavController navController = Navigation.findNavController(this, R.id.fragment);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                //R.id.firstFragment, R.id.secondFragment, R.id.thirdFragment, R.id.connectionFragment)
                R.id.homeFragment, R.id.browsingFragment, R.id.userFragment)
                .build();

        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        initializeDb();
    }

    public void switchContent(int id, Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(id, fragment, fragment.toString());
        ft.addToBackStack(null);
        ft.commit();
    }

    public void initializeDb()
    {
        db = RoomDbManager.getInstance(getApplicationContext());
        LocalDbPopulator populator = new LocalDbPopulator(db);
        populator.populateGames(new GameApiController());
        populator.populateProductsAnnounces(new BrowsingApiController());
        populator.populateCategories(new CategoryApiController());
    }


}