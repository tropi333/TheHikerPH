package com.pccw.hikerph.RoomDatabase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.pccw.hikerph.Model.Profile;

import java.util.List;

@Dao
public interface ProfileDAO {

    @Insert
    public long saveProfile(Profile profile);


    @Update
    public void updateProfile(Profile profile);


    @Query("Select * from Profile LIMIT 1")
    public LiveData<Profile> getProfile();


    @Query("Delete from Profile")
    public void deleteAllProfiles();


    /*

    @Insert
    public long insertHike(HikeDto hike);


    @Query("Select * from hike")
    public List<HikeDto> getHike(HikeDto hike);
*/

}
