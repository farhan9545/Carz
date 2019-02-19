package com.example.dell.carz;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface AdDao {

    @Query("SELECT * FROM ad")
    List<Ad> getAll();

    @Query("SELECT * FROM ad where user LIKE  :user")
    List<Ad> findByUser(String user);

    @Query("SELECT COUNT(*) from ad")
    int countAds();

    @Insert
    void insertAll(Ad... ads);

    @Delete
    void delete(Ad ad);

}
