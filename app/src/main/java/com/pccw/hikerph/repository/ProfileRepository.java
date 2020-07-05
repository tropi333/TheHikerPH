package com.pccw.hikerph.repository;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.pccw.hikerph.Model.Profile;
import com.pccw.hikerph.R;
import com.pccw.hikerph.RoomDatabase.ProfileDAO;
import com.pccw.hikerph.Utilities.Properties;
import com.pccw.hikerph.RoomDatabase.MyDatabase;

import java.util.List;

import androidx.lifecycle.LiveData;

public class ProfileRepository {

    private static final String TAG = "ProfileRepository";
    private static ProfileRepository instance;
    private LiveData<Profile> profile;
    private Context context;

    private ProfileDAO profileDao;


    public ProfileRepository(Context context) {

        this.context = context;

        MyDatabase myDatabase = MyDatabase.getInstance(context);
        profileDao = myDatabase.myDAO();
        profile = profileDao.getProfile();

    }

    public static ProfileRepository getInstance(Context context){

        if(instance == null){
            instance = new ProfileRepository(context);
        }

        return  instance;
    }



    public LiveData<Profile> getProfile(){
        return profile;
    }

    public void saveProfile(Profile profile){
        new SaveProfileAsyncTask(profileDao).execute(profile);
    }

    public void updateProfile(Profile profile){
        new UpdateProfileAsyncTask(profileDao).execute(profile);
    }


    private class SaveProfileAsyncTask extends AsyncTask<Profile, Void, Profile> {

        ProfileDAO profileDAO;

        public SaveProfileAsyncTask(ProfileDAO profileDAO) {
            this.profileDAO = profileDAO;
        }


        @Override
        protected Profile doInBackground(Profile... profiles) {

            profileDAO.saveProfile(profiles[0]);

            return profiles[0];
        }

        @Override
        protected void onPostExecute(Profile profile) {
            Properties.getInstance().setCurrentProfile(profile);
        }


    }

    private class UpdateProfileAsyncTask extends AsyncTask<Profile, Void, Profile> {

        private ProfileDAO profileDAO;
        public UpdateProfileAsyncTask(ProfileDAO profileDAO) {
            this.profileDAO = profileDAO;

        }

        @Override
        protected Profile doInBackground(Profile... profiles) {
            profileDAO.updateProfile(profiles[0]);

            return profiles[0];
        }

        @Override
        protected void onPostExecute(Profile profile) {
            Properties.getInstance().setCurrentProfile(profile);
        }
    }



}
