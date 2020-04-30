package com.pccw.hikerph.RoomDatabase;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.pccw.hikerph.Model.Profile;

import java.util.List;

@Dao
public interface MyDAO {

    @Insert
    public long saveProfile(Profile profile);


    @Update
    public void updateProfile(Profile profile);


    @Query("Select * from Profile")
    public List<Profile> getProfile();


    @Query("Delete from Profile")
    public void deleteAllProfiles();


    /*

    @Insert
    public long insertHike(HikeDto hike);


    @Query("Select * from hike")
    public List<HikeDto> getHike(HikeDto hike);
*/

}
