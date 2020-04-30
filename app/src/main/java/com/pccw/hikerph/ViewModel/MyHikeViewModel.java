package com.pccw.hikerph.ViewModel;

import android.app.Application;
import android.content.Context;

import com.pccw.hikerph.Model.HikeDto;
import com.pccw.hikerph.repository.MyHikeRepository;

import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class MyHikeViewModel extends AndroidViewModel {

    private LiveData<List<HikeDto>> allHike;
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

    public void insert(HikeDto hikeDto) {
        myHikeRepository.insertHike(hikeDto);
    }

    public void update(HikeDto hikeDto) {
        myHikeRepository.updateHike(hikeDto);
    }

    public void delete(HikeDto hikeDto) {
        myHikeRepository.deleteHike(hikeDto);
    }

    public LiveData<List<HikeDto>> getAllHikes() {
        return allHike;
    }

}
