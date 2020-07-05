package com.pccw.hikerph;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.WindowManager;

import com.pccw.hikerph.Model.Hike;
import com.pccw.hikerph.Model.Profile;
import com.pccw.hikerph.Utilities.Properties;
import com.pccw.hikerph.ViewModel.MyHikeViewModel;
import com.pccw.hikerph.ViewModel.ParentActivity;
import com.pccw.hikerph.ViewModel.ProfileViewModel;

import java.util.List;

public class SplashScreen extends ParentActivity {

    private static final String TAG = "SplashScreen";
    private ProfileViewModel profileViewModel;
    private MyHikeViewModel hikeViewModel;

    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        profileViewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);
        hikeViewModel = ViewModelProviders.of(this).get(MyHikeViewModel.class);

        handler = new Handler();

        handler.postDelayed( runnable,3000);



    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {

            getProfile();
        }
    };

    public void getProfile(){

        profileViewModel.getProfile().observe(this, new Observer<Profile>() {
            @Override
            public void onChanged(Profile profile) {

                Properties.getInstance().setCurrentProfile(profile);
                showMainActivity();

                getHikes();
            }

        });
    }
  private void getHikes(){
        Log.d(TAG, "Getting hikes..");
        hikeViewModel.getAllHikes().observe(this, new Observer<List<Hike>>() {
            @Override
            public void onChanged(List<Hike> hikes) {

                Properties.getInstance().setHikeList(hikes);
                showMainActivity();
            }
        });

    }

    private void showMainActivity(){

        Intent intent=new Intent(SplashScreen.this, MainActivity.class);
        startActivity(intent);
        finish();
    }


}
