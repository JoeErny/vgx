package com.example.vgxchange.database.service;

import android.util.Log;

import com.example.vgxchange.api.controllers.BrowsingApiController;
import com.example.vgxchange.database.RoomDbManager;
import com.example.vgxchange.model.ProductAnnounce;

public class BrowsingDbManager {

    private RoomDbManager db;

    public BrowsingDbManager(RoomDbManager db) {
        this.db = db;
    }

    public void populateProductsAnnouncesByCategory(BrowsingApiController browsingApiController, String categoryId) {
        Thread thread = new Thread(() -> {
            try {
                db.ProductAnnounceDao().deleteAll();
                for (ProductAnnounce p : browsingApiController.getByCategorySynchronous(categoryId)) {
                    if (db.ProductAnnounceDao().countAllById(p.getId()) <= 0) {
                        db.ProductAnnounceDao().insertAll(p);
                    } else {
                        db.ProductAnnounceDao().updateAll(p);
                    }
                }

                for (ProductAnnounce p : db.ProductAnnounceDao().getAll()) {
                    Log.d("ROOM PRODUCT", p.toString());
                }
                Log.d("ROOM NB popProdAnByC", String.valueOf(db.ProductAnnounceDao().getAll().size()));
            } catch (
                    Exception e) {
                e.printStackTrace();
            }
        });

            thread.start();
        try {
            thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }






}
