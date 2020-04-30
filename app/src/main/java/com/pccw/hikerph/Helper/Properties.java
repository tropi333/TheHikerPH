package com.pccw.hikerph.Helper;

import android.content.Context;
import android.widget.Toast;

import com.pccw.hikerph.Model.HikeDto;
import com.pccw.hikerph.Model.Profile;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;

public class Properties {

    private static Properties instance;

    public static final int REQUEST_CODE_EDIT_PROFILE = 1;

    public static final int REQUEST_CODE_EDIT_ITINERARY = 2;

    public static final int REQUEST_CODE_EDIT_HIKE = 3;

    public static final String BROADCAST_RECEIVER_ADD_HIKE = "AddHikeBroadcastReceiver";
    public static final String BROADCAST_RECEIVER_UPCOMING_HIKES = "GetUpcomingHikeBroadcastReceiver";


    public static SimpleDateFormat dtFormat = new SimpleDateFormat("MMM dd, yyyy");
    public static DateTimeFormatter dtFormat2 =  DateTimeFormatter.ofPattern("MMM dd, yyyy");


    private Profile currentProfile;

    private List<HikeDto> hikeDtoList;

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

    public List<HikeDto> getHikeDtoList() {
        return hikeDtoList;
    }

    public void setHikeDtoList(List<HikeDto> hikeDtoList) {
        this.hikeDtoList = hikeDtoList;
    }

    //validator
    public static boolean isValid(String email) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }

}
