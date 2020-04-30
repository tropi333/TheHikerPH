package com.pccw.hikerph.RoomDatabase;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.pccw.hikerph.Model.Itinerary;

import java.util.List;

@Dao
public interface ItineraryDAO {

    @Insert
    public void addItineraries(List<Itinerary> itineraries);


    @Query("Delete from itinerary")
    public void deleteAll();

}
