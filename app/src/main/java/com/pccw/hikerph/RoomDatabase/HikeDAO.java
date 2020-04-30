package com.pccw.hikerph.RoomDatabase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.pccw.hikerph.Model.HikeDto;

import java.util.List;

@Dao
public interface HikeDAO {

    @Insert
    public void addHike(HikeDto hikeDto);

    @Update
    public void updateHike(HikeDto hikeDto);

    @Delete
    public void deleteHike(HikeDto hikeDto);


    @Query("Select * from Hike order by dtStartDate desc")
    public LiveData<List<HikeDto>> getHikeList();


    @Query("Delete from Hike")
    public void deleteAll();

}
