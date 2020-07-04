package com.pccw.hikerph;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.pccw.hikerph.Model.Hike;
import com.pccw.hikerph.Model.Profile;
import com.pccw.hikerph.Utilities.Properties;
import com.pccw.hikerph.ViewModel.MyHikeViewModel;
import com.pccw.hikerph.ViewModel.ProfileViewModel;
import com.pccw.hikerph.adapter.ProfileHikeAdapter;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment implements View.OnClickListener,
        ProfileHikeAdapter.OnProfileHikeEventClickListener {

    private static final String TAG = "ProfileFragment";


    ImageView imageView;
    Button btnEdit;

    TextView tvNoRecord;
    TextView tvFullName;
    TextView tvMotto;
    TextView tvTotalHike;
    TextView tvHighestPeak;
    TextView tvRecentHike;

    RecyclerView recyclerView;
    ProfileHikeAdapter profileHikeAdapter;

//    List<Hike> hikeLS;
    List<Hike> upcomingHikeList;

    private ProfileViewModel profileViewModel;
    private MyHikeViewModel myHikeViewModel;
//    private Profile currentProfile;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        initFields(view);

        initViewModel();


        return view;
    }


    private void initFields(View view) {

        upcomingHikeList = new ArrayList<>();

        tvNoRecord = view.findViewById(R.id.tvNoRecord);
        tvFullName = view.findViewById(R.id.tvFullName_profile_home);
        tvMotto = view.findViewById(R.id.tvMotto_profile_home);
        tvTotalHike = view.findViewById(R.id.tvTotalHike_profile_home);
        tvHighestPeak = view.findViewById(R.id.tvHighestPeak_profile_home);
        tvRecentHike = view.findViewById(R.id.tvRecentHike_profile_home);
        btnEdit = view.findViewById(R.id.btnProfileEdit_profile);
        imageView = view.findViewById(R.id.imgViewProfile_profile_create);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,
                false));

        btnEdit.setText(getString(R.string.btn_create_profile));
        btnEdit.setOnClickListener(this);

    }


    private void initViewModel() {

        profileViewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);

        profileViewModel.getProfile().observe(this, new Observer<Profile>() {
            @Override
            public void onChanged(Profile profile) {

                populatePersonalInfo(profile);
            }
        });

        myHikeViewModel = ViewModelProviders.of(this).get(MyHikeViewModel.class);
        myHikeViewModel.getAllHikes().observe(this, new Observer<List<Hike>>() {
            @Override
            public void onChanged(List<Hike> hikeLs) {

                populateHikeInfo(hikeLs);
            }
        });


    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.btnProfileEdit_profile:

                showProfileCreateActivity();

                break;

        }
    }

    private void populatePersonalInfo(Profile currentProfile) {


        if (currentProfile == null) {

            tvFullName.setText("----------------");
            btnEdit.setText(getString(R.string.btn_create_profile));

            Glide.with(getContext()).load(R.drawable.profile).into(imageView);

        } else {


            btnEdit.setText(getString(R.string.btn_edit_profile));
            tvFullName.setText(currentProfile.getLastName() + ", " + currentProfile.getFirstName());
            tvMotto.setText("' " + currentProfile.getMotto() + "' ");

            loadProfilePicture(currentProfile.getProfilePic_bitMap());

        }
    }

    private void populateHikeInfo(List<Hike> hikeLs){

        tvTotalHike.setText("" + hikeLs.size());

        if (hikeLs.size() == 0) {

            tvHighestPeak.setText("0");
            tvRecentHike.setText("n/a");

        } else {

            List<Hike> completedHikes = getFilteredHikes(true, hikeLs);

            if(completedHikes.size() > 0){
                Hike recentHike = completedHikes.stream().findFirst().get();
                tvRecentHike.setText(recentHike.getMtName());

                Hike highestPeak = Collections.max(completedHikes, Comparator.comparing(Hike::getElevation));
                tvHighestPeak.setText(highestPeak.getMtName() + "(" + highestPeak.getElevation() + " masl)");

            }

            getUpcomingHikes(hikeLs);
        }
    }

    private void loadProfilePicture(String profilePic_path){

        if (profilePic_path != null) {

            Glide.with(getContext())
                    .load(profilePic_path)
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .placeholder(R.drawable.profile).
                    into(imageView);
        } else {

            Glide.with(getContext()).load(R.drawable.profile)
                    .into(imageView);
        }
    }

    private void showProfileCreateActivity() {

        Intent intent = new Intent(getActivity(), ProfileCreateActivity.class);
        startActivityForResult(intent, Properties.REQUEST_CODE_EDIT_PROFILE);
    }

    private void getUpcomingHikes(List<Hike> hikeList) {

        upcomingHikeList = getFilteredHikes(false, hikeList);


        if (upcomingHikeList.size() > 0) {

            tvNoRecord.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);

        } else {

            tvNoRecord.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }

        recyclerView.setAdapter(new ProfileHikeAdapter(getContext(), upcomingHikeList,
                this));

    }

    private List<Hike> getFilteredHikes(boolean isCompletedHikes, List<Hike> hikeList){

        final Calendar calCurrent = Calendar.getInstance();
        calCurrent.setTime(new Date());
        calCurrent.set(Calendar.HOUR_OF_DAY, 0);
        calCurrent.set(Calendar.MINUTE, 0);
        calCurrent.set(Calendar.SECOND, 0);
        calCurrent.set(Calendar.MILLISECOND, 0);


        List<Hike> hikes = hikeList.stream()
                .filter(hike -> {
                    try {
                        final Calendar calStartDate = Calendar.getInstance();

                        Date startDate = Properties.dtFormat.parse(hike.getStartDate());
                        calStartDate.setTime(startDate);

                        return  isCompletedHikes ? calStartDate.compareTo(calCurrent) <= 0:
                        calStartDate.compareTo(calCurrent) > 0;

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    return false;
                })

                .sorted((a, b) -> {
                    Date dtStart1;
                    Date dtStart2;
                    try {
                        dtStart1 = Properties.dtFormat.parse(a.getStartDate());
                        dtStart2 = Properties.dtFormat.parse(b.getStartDate());
                        return dtStart1.compareTo(dtStart2);

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    return 0;
                })

                .collect(Collectors.toList());

        return hikes;
    }

    @Override
    public void onProfileHikeEventClickListener(int position) {
   /*     Toast.makeText(getContext(), hikeLS.get(position).getMtName() + "" + position,
                Toast.LENGTH_SHORT).show();*/

        Toast.makeText(getContext(), ""+position, Toast.LENGTH_SHORT).show();
    }

}
