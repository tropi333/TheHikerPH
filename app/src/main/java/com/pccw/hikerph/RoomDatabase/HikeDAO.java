package com.pccw.hikerph.RoomDatabase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.pccw.hikerph.Model.Hike;

import java.util.List;

@Dao
public interface HikeDAO {

    @Insert
    public void addHike(Hike hike);

    @Update
    public void updateHike(Hike hike);

    @Delete
    public void deleteHike(Hike hike);


    @Query("Select * from Hike order by dtStartDate desc")
    public LiveData<List<Hike>> getHikeList();

    @Query("Select max(id) from Hike")
    public long getMaxHikeId();


    @Query("Delete from Hike")
    public void deleteAll();

}
