package com.pccw.hikerph.RoomDatabase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.pccw.hikerph.Model.HikeDto;
import com.pccw.hikerph.Model.Itinerary;
import com.pccw.hikerph.Model.Profile;

@Database(entities = {Profile.class, HikeDto.class, Itinerary.class},version = 20, exportSchema = false)

public abstract class MyDatabase extends RoomDatabase {

    private static MyDatabase instance;
    public abstract MyDAO myDAO();
    public abstract HikeDAO hikeDAO();
    public abstract ItineraryDAO itineraryDAO();



    public static synchronized MyDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    MyDatabase.class,"my_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
