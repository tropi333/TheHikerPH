package com.pccw.hikerph.repository;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.pccw.hikerph.Helper.Properties;
import com.pccw.hikerph.Model.HikeDto;
import com.pccw.hikerph.Model.Profile;
import com.pccw.hikerph.RoomDatabase.HikeDAO;
import com.pccw.hikerph.RoomDatabase.MyDatabase;

import java.util.List;

import androidx.lifecycle.LiveData;

public class MyHikeRepository {

    private static final String TAG = "MyHikeRepository";

    private static MyHikeRepository instance;

    private HikeDAO hikeDAO;
    private LiveData<List<HikeDto>> allHike;



    public MyHikeRepository(Context context) {

        MyDatabase myDatabase = MyDatabase.getInstance(context);
        hikeDAO = myDatabase.hikeDAO();
        allHike = hikeDAO.getHikeList();

    }




    public static MyHikeRepository getInstance(Context context){

        if (instance == null){
            instance = new MyHikeRepository(context);
        }
        return instance;
    }

    public void insertHike(HikeDto hikeDto){

        new InsertHikeAsyncTask(hikeDAO).execute(hikeDto);

    }
    public void updateHike(HikeDto hikeDto){

        new UpdateHikeAsyncTask(hikeDAO).execute(hikeDto);

    }
    public void deleteHike(HikeDto hikeDto){

        new DeleteHikeAsyncTask(hikeDAO).execute(hikeDto);

    }

    public LiveData<List<HikeDto>> getAllHike(){
        return allHike;
    }



    private static class InsertHikeAsyncTask extends AsyncTask<HikeDto, Void, HikeDto> {

        HikeDAO hikeDAO;

        private InsertHikeAsyncTask(HikeDAO hikeDAO) {
            this.hikeDAO = hikeDAO;
        }

        @Override
        protected HikeDto doInBackground(HikeDto... hikeDtos) {
            hikeDAO.addHike(hikeDtos[0]);

            return hikeDtos[0];
        }

        @Override
        protected void onPostExecute(HikeDto hikeDto) {
            super.onPostExecute(hikeDto);

            Log.i(TAG, "Done adding hike: ");
        }

    }

    private static class UpdateHikeAsyncTask extends AsyncTask<HikeDto, Void, Void> {

        HikeDAO hikeDAO;

        private UpdateHikeAsyncTask(HikeDAO hikeDAO) {
            this.hikeDAO = hikeDAO;
        }

        @Override
        protected Void doInBackground(HikeDto... hikeDtos) {
            hikeDAO.updateHike(hikeDtos[0]);

            return null;
        }
    }

    private static class DeleteHikeAsyncTask extends AsyncTask<HikeDto, Void, Void> {

        HikeDAO hikeDAO;

        private DeleteHikeAsyncTask(HikeDAO hikeDAO) {
            this.hikeDAO = hikeDAO;
        }

        @Override
        protected Void doInBackground(HikeDto... hikeDtos) {
            hikeDAO.deleteHike(hikeDtos[0]);
/*
            MyDatabase.getInstance(context).hikeDAO().addHike(hikeDtos[0]);
            Properties.getInstance().getHikeDtoList().add(0, hikeDtos[0]);*/
            return null;
        }
    }

}
