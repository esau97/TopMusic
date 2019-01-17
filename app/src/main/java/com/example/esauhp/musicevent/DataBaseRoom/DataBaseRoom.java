package com.example.esauhp.musicevent.DataBaseRoom;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.esauhp.musicevent.Artist;
import com.example.esauhp.musicevent.ArtistDAO;

@Database(entities = {Artist.class}, version = 2, exportSchema = false)

public abstract class DataBaseRoom extends RoomDatabase {

    public abstract ArtistDAO artistDAO();
    private static DataBaseRoom INSTANCE=null;

    public static DataBaseRoom getInstance(final Context context){
        if(INSTANCE==null){
            INSTANCE= Room.databaseBuilder(context.getApplicationContext(), DataBaseRoom.class, "artistasFavs.db").fallbackToDestructiveMigration().build();
        }
        return INSTANCE;
    }
    public void destroyInstance(){INSTANCE = null;}
}


