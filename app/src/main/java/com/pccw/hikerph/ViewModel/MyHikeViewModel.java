package com.pccw.hikerph.ViewModel;

import android.app.Application;

import com.pccw.hikerph.Model.Hike;
import com.pccw.hikerph.repository.MyHikeRepository;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class MyHikeViewModel extends AndroidViewModel {

    private LiveData<List<Hike>> allHike;
    private MyHikeRepository myHikeRepository;



 /*   public LiveData<List<HikeDto>> getAllHike() {
        return allHike;
    }*/
/*  public MyHikeViewModel(@NonNull Application application) {
        myHikeRepository = MyHikeRepository.getInstance(application);
        allHike = myHikeRepository.getAllHike();
    }*/

    /*  public void init(){

          if(myHikeRepository == null){
              return;
          }
          myHikeRepository = MyHikeRepository.getInstance(context);
          allHike = myHikeRepository.getAllHike();
      }
  */
    public MyHikeViewModel(@NonNull Application application) {
        super(application);

        myHikeRepository = MyHikeRepository.getInstance(application);
        allHike = myHikeRepository.getAllHike();

    }

   /* public void init(Context context) {
        if (allHike != null) {
            return;
        }
        myHikeRepository = MyHikeRepository.getInstance(context);
        allHike = myHikeRepository.getAllHike();

    }*/

    public void insert( Hike hike) {
        myHikeRepository.insertHike( hike);
    }

    public void update(Hike hike) {
        myHikeRepository.updateHike(hike);
    }

    public void delete(Hike hike) {
        myHikeRepository.deleteHike(hike);
    }

    public LiveData<List<Hike>> getAllHikes() {
        return allHike;
    }

    public long getMaxHikeId() {
        return myHikeRepository.getMaxHikeId();
    }

}
