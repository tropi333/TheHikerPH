package com.pccw.hikerph.Utilities;

import com.pccw.hikerph.Model.Hike;
import com.pccw.hikerph.Model.Profile;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Properties {

    private static Properties instance;

    public static final int REQUEST_CODE_EDIT_PROFILE = 1;

    public static final String TAG = "";

    public static final int REQUEST_CODE_EDIT_ITINERARY = 2;

    public static final int REQUEST_CODE_EDIT_HIKE = 3;

    public static final String BROADCAST_RECEIVER_ADD_HIKE = "AddHikeBroadcastReceiver";
    public static final String BROADCAST_RECEIVER_UPCOMING_HIKES = "GetUpcomingHikeBroadcastReceiver";


    public static SimpleDateFormat dtFormat = new SimpleDateFormat("MMM dd, yyyy");
    public static DateTimeFormatter dtFormat2 =  DateTimeFormatter.ofPattern("MMM dd, yyyy");


    private Profile currentProfile;

    private List<Hike> hikeList;

    public static Properties getInstance() {
        if (instance == null) {
            instance = new Properties();
        }
        return instance;
    }


    public Profile getCurrentProfile() {
        return currentProfile;
    }

    public void setCurrentProfile(Profile currentProfile) {
        this.currentProfile = currentProfile;
    }

    public List<Hike> getHikeList() {
        return hikeList;
    }

    public void setHikeList(List<Hike> hikeList) {
        this.hikeList = hikeList;
    }

    //validator
    public static boolean isValid(String email) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }

}
