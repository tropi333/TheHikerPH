package com.pccw.hikerph.repository;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.util.Log;

import com.pccw.hikerph.Model.Hike;
import com.pccw.hikerph.RoomDatabase.HikeDAO;
import com.pccw.hikerph.RoomDatabase.MyDatabase;
import com.pccw.hikerph.Utilities.ImageHelper;

import java.util.List;

import androidx.lifecycle.LiveData;

public class MyHikeRepository {

    private static final String TAG = "MyHikeRepository";

    private static MyHikeRepository instance;

    private HikeDAO hikeDAO;
    private LiveData<List<Hike>> allHike;

    private  Context context;


    public MyHikeRepository(Context context) {

        MyDatabase myDatabase = MyDatabase.getInstance(context);
        hikeDAO = myDatabase.hikeDAO();
        allHike = hikeDAO.getHikeList();
        this.context = context;

    }


    public long getMaxHikeId(){
        return  hikeDAO.getMaxHikeId();
    }




    public static MyHikeRepository getInstance(Context context){

        if (instance == null){
            instance = new MyHikeRepository(context);
        }
        return instance;
    }

    public void insertHike( Hike hike){

        new InsertHikeAsyncTask(hikeDAO).execute(hike);

    }
    public void updateHike(Hike hike){

        new UpdateHikeAsyncTask(hikeDAO).execute(hike);

    }
    public void deleteHike(Hike hike){

        new DeleteHikeAsyncTask(hikeDAO).execute(hike);

    }

    public LiveData<List<Hike>> getAllHike(){
        return allHike;
    }



    private static class InsertHikeAsyncTask extends AsyncTask<Hike, Void, Hike> {

        HikeDAO hikeDAO;

        private InsertHikeAsyncTask(HikeDAO hikeDAO) {
            this.hikeDAO = hikeDAO;
        }

        @Override
        protected Hike doInBackground(Hike... hikes) {
            hikeDAO.addHike(hikes[0]);

            return hikes[0];
        }

        @Override
        protected void onPostExecute(Hike hike) {
            super.onPostExecute(hike);

            Log.d(TAG, "onPostExecute: Saving banner");
            Uri banner = MediaStore.Images.Media.getContentUri("");

 /*           Bitmap banner = MediaStore.Images.Media.
                    getBitmap(context, imageUri);

            ImageHelper.saveImageToInternal(selectedImage,getContext(),
                    ImageHelper.HIKE_BANNER_FILE_TEMP_NAME);*/


        }

    }

    private static class UpdateHikeAsyncTask extends AsyncTask<Hike, Void, Void> {

        HikeDAO hikeDAO;

        private UpdateHikeAsyncTask(HikeDAO hikeDAO) {
            this.hikeDAO = hikeDAO;
        }

        @Override
        protected Void doInBackground(Hike... hikes) {
            hikeDAO.updateHike(hikes[0]);

            return null;
        }
    }

    private static class DeleteHikeAsyncTask extends AsyncTask<Hike, Void, Void> {

        HikeDAO hikeDAO;

        private DeleteHikeAsyncTask(HikeDAO hikeDAO) {
            this.hikeDAO = hikeDAO;
        }

        @Override
        protected Void doInBackground(Hike... hikes) {
            hikeDAO.deleteHike(hikes[0]);

            return null;
        }
    }

}
