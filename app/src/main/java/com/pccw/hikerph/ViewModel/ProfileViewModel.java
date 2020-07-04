package com.pccw.hikerph.ViewModel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.pccw.hikerph.Model.Profile;
import com.pccw.hikerph.repository.MyHikeRepository;
import com.pccw.hikerph.repository.ProfileRepository;

public class ProfileViewModel extends AndroidViewModel {

    private LiveData<Profile> profile;
    private ProfileRepository profileRepository;

    private Context context;

    public ProfileViewModel(@NonNull Application application) {
        super(application);

        profileRepository = profileRepository.getInstance(application);
        profile = profileRepository.getProfile();

    }
    /*public ProfileViewModel(Context context) {
        this.context = context;
    }*/

 /*   public void init(){

        if(profileRepository == null){
            return;
        }
        profileRepository = ProfileRepository.getInstance(context);
        profileRepository.getProfile();

    }*/

    public LiveData<Profile> getProfile() {
        return profile;
    }

    public void saveProfile(Profile profile){
        profileRepository.saveProfile(profile);
    }

    public void updateProfile(Profile profile){
        profileRepository.updateProfile(profile);
    }
}
