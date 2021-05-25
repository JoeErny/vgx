package com.example.vgxchange.database.service;

import android.util.Log;

import com.example.vgxchange.api.controllers.BrowsingApiController;
import com.example.vgxchange.api.controllers.CategoryApi;
import com.example.vgxchange.api.controllers.CategoryApiController;
import com.example.vgxchange.api.controllers.GameApiController;
import com.example.vgxchange.api.controllers.ProductAnnounceApiController;
import com.example.vgxchange.fragments.browsing_products.BrowsingProductsFragment;
import com.example.vgxchange.model.Category;
import com.example.vgxchange.model.Game;
import com.example.vgxchange.model.ProductAnnounce;
import com.example.vgxchange.database.RoomDbManager;

public class LocalDbPopulator {

    private RoomDbManager db;

    public LocalDbPopulator(RoomDbManager db) {
        this.db = db;
    }

    public synchronized void populateProductsAnnounces(BrowsingApiController browsingApiController) {
        Thread thread = new Thread(() -> {
            try {
                db.ProductAnnounceDao().deleteAll();

                for (ProductAnnounce p : browsingApiController.getAllSynchronous()) {

                    if (db.ProductAnnounceDao().countAllById(p.getId()) <= 0) {
                        db.ProductAnnounceDao().insertAll(p);
                    } else {
                        db.ProductAnnounceDao().updateAll(p);
                    }
                }

                for (ProductAnnounce p : db.ProductAnnounceDao().getAll()) {
                    Log.d("ROOM PRODUCT", p.toString());
                }

                Log.d("ROOM NB populateProdAn", String.valueOf(db.ProductAnnounceDao().getAll().size()));
            } catch (
                    Exception e) {
                e.printStackTrace();
            }
        });

            thread.start();
        try {
            thread.sleep(1100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void populateGames(GameApiController gameApiController) {
        Thread thread = new Thread(() -> {
            try {
                db.GameDao().deleteAll();
                for (Game g : gameApiController.getAllSynchronous()) {
                    if (db.GameDao().countAllById(g.getId()) <= 0) {
                        db.GameDao().insertAll(g);
                    } else {
                        db.GameDao().updateAll(g);
                        Log.d("ROOM UPDATE ", "GAME : " + g.getName());
                    }
                }
                for (Game g : db.GameDao().getAll()) {
                    Log.d("ROOM GAME", g.toString());
                }
            } catch (
                    Exception e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }

    public void populateCategories(CategoryApiController categoryApiController) {
        Thread thread = new Thread(() -> {
            try {
                for (Category c : categoryApiController.getAllSynchronous()) {

                    if (db.CategoryDao().countAllById(c.getId()) <= 0) {
                        db.CategoryDao().insertAll(c);
                    } else {
                        db.CategoryDao().updateAll(c);
                        Log.d("ROOM UPDATE ", "CATEGORY : " + c.getLabel());
                    }
                }
                for (Category c : db.CategoryDao().getAll()) {
                    Log.d("ROOM CATEGORY", c.toString());
                }
            } catch (
                    Exception e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }

    //TODO User

}


