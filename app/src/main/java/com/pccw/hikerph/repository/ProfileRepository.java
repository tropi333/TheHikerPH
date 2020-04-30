package com.pccw.hikerph.repository;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.pccw.hikerph.Model.Profile;
import com.pccw.hikerph.Helper.Properties;
import com.pccw.hikerph.RoomDatabase.MyDatabase;

import java.util.List;

public class ProfileRepository {

    private static final String TAG = "ProfileRepository";
    private static ProfileRepository instance;
    private Profile data;
    private Context context;

    public ProfileRepository(Context context) {
        this.context = context;
    }

    public static ProfileRepository getInstance(Context context){

        if(instance == null){
            instance = new ProfileRepository(context);
        }

        return  instance;
    }



    public void getProfile(){

        new GetProfileAsyncTask().execute();
    }


    public void setProfile(){

    }


    private class GetProfileAsyncTask extends AsyncTask<Void, Void, List<Profile>> {

        @Override
        protected List<Profile> doInBackground(Void... voids) {
            List<Profile> s = MyDatabase.getInstance(context).myDAO().getProfile();

            Log.d(TAG, "doInBackground: Getting profiles...");
            return  s;
        }

        @Override
        protected void onPostExecute(List<Profile> profiles) {
            Log.d(TAG, "onPostExecute: DOne getting profile");

            if(profiles.size() == 0){
                Log.d(TAG, "onPostExecute: No Profile yet1.");
            }
            else{
                Profile profile = profiles.get(0);
                Properties.getInstance().setCurrentProfile(profile);
            }

        }
    }


}
