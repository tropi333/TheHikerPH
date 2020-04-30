package com.pccw.hikerph;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.pccw.hikerph.Helper.Properties;
import com.pccw.hikerph.Model.HikeDto;
import com.pccw.hikerph.Model.Itinerary;
import com.pccw.hikerph.RoomDatabase.MyDatabase;
import com.pccw.hikerph.ViewModel.MyHikeViewModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class MainActivity extends AppCompatActivity{

    private static final String TAG = "MainActivity";

    public static FragmentManager fragmentManager;
    public static FragmentTransaction fragmentTransaction;
    public static MyDatabase myDatabase;

    BottomNavigationView nav;

    private MyHikeViewModel myHikeViewModel;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Fragment selectedFragment = null;
            switch (item.getItemId()) {

                case R.id.navigation_profile:
                    selectedFragment = new ProfileFragment();
                    break;

                case R.id.navigation_add_hike:
                    selectedFragment = new AddHikeFragment();
                    break;

                case R.id.navigation_home:
                    selectedFragment = new MyHikeFragment();
                    break;

            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();

            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i(TAG, "onCreate: ");

        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiverAddHike,
                new IntentFilter(Properties.BROADCAST_RECEIVER_ADD_HIKE));

       /* if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted

            Log.d(TAG, "READ_EXTERNAL_STORAGE: Not Granted");
        }
        else{
            Log.d(TAG, "READ_EXTERNAL_STORAGE:  Granted");

        }


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            Log.d(TAG, "WRITE_EXTERNAL_STORAGE: Not Granted");
        }
        else{
            Log.d(TAG, "WRITE_EXTERNAL_STORAGE:  Granted");

        }*/

        if (findViewById(R.id.fragment_container) != null) {
            if (savedInstanceState != null) {
                return;
            }
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();

        }

        initFields();
        showProfileFragment();


    }

    @Override
    protected void onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiverAddHike);
        super.onDestroy();
    }

    private BroadcastReceiver broadcastReceiverAddHike = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            showMyHikeFragment();
        }
    };

    private void initFields() {

        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        nav = findViewById(R.id.nav_view);


    }

    private void showHomeFragment() {

        MyHikeFragment myHikeFragment = new MyHikeFragment();
        fragmentTransaction.add(R.id.fragment_container, myHikeFragment, null);
        fragmentTransaction.commit();

    }

    private void showProfileFragment() {

        ProfileFragment profileFragment = new ProfileFragment();
        fragmentTransaction.add(R.id.fragment_container, profileFragment, null);
        fragmentTransaction.commit();

    }

    private void showMyHikeFragment() {

        nav.getMenu().getItem(2).setChecked(true);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MyHikeFragment()).commit();


    }


}
