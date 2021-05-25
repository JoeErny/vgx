package com.example.vgxchange.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.vgxchange.model.ProductAnnounce;

import java.util.List;

@Dao
public interface ProductAnnounceDao {

    @Insert
    void insertAll(ProductAnnounce... productAnnounces);
    @Query("SELECT * FROM product")
    List<ProductAnnounce> getAll();
    @Query("SELECT * FROM product WHERE idProduct IN (:productIds)")
    List<ProductAnnounce> loadAllByIds(String[] productIds);
    @Query("SELECT * FROM product WHERE idProduct LIKE :productId LIMIT 1")
    ProductAnnounce loadById(String productId);
    @Query("SELECT count(*) FROM product WHERE idProduct LIKE :productId")
    int countAllById(String productId);

    @Query("delete from product ")
    void deleteAll();

    @Update
    void updateAll(ProductAnnounce... product);
    @Delete
    void delete(ProductAnnounce product);

}
