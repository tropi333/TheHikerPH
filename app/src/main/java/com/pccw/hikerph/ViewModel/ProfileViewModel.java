package com.pccw.hikerph.ViewModel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.pccw.hikerph.Model.Profile;
import com.pccw.hikerph.repository.ProfileRepository;

public class ProfileViewModel extends ViewModel {

    private MutableLiveData<Profile> profile;
    private ProfileRepository profileRepository;

    private Context context;

    public ProfileViewModel(Context context) {
        this.context = context;
    }

    public void init(){

        if(profileRepository == null){
            return;
        }
        profileRepository = ProfileRepository.getInstance(context);
        profileRepository.getProfile();

    }


    public LiveData<Profile> getProfile() {
        return profile;
    }
}
