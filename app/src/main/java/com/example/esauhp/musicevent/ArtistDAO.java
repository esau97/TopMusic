package com.example.esauhp.musicevent;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface ArtistDAO {
    @Insert
    public long insertArtist(Artist artist);

    @Delete
    public int deleteArtist(Artist artist);



    @Query("SELECT * FROM artist WHERE nombreArtista=:name")
    public Artist getArtistFav(String name);

    @Query("SELECT * FROM artist")
    public LiveData<List<Artist >> getArtist(); //LiveData - Observe para cuando cambien automaticamente los datos se realice el cambio

}
