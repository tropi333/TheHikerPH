package com.pccw.hikerph;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.pccw.hikerph.Model.HikeDto;
import com.pccw.hikerph.Model.Itinerary;
import com.pccw.hikerph.ViewModel.MyHikeViewModel;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddHikeItineraryFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "AddHikeItineraryFragmen";

    Button btnSave;
    HikeDto hikeDto = null;
    List<Itinerary> itineraries = null;

    private MyHikeViewModel myHikeViewModel;


    public AddHikeItineraryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_hike_itinerary, container, false);


        myHikeViewModel = ViewModelProviders.of(this).get(MyHikeViewModel.class);
//        myHikeViewModel.init(getContext());


        btnSave = view.findViewById(R.id.btnSave_edit_hike_itinerary);
        btnSave.setOnClickListener(this);


        itineraries = new ArrayList<>();
        hikeDto = getArguments().getParcelable("hikeDto");

        return view;

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btnSave_edit_hike_itinerary:

                myHikeViewModel.insert(hikeDto);

                myHikeViewModel.getAllHikes().observe(this, new Observer<List<HikeDto>>() {
                    @Override
                    public void onChanged(List<HikeDto> hikeDtos) {
                        Log.i(TAG, "Hike Size."+hikeDtos.size());
                    }
                });

                break;
        }
    }

    private void showToastMessage(String message){

        Toast.makeText(getActivity(),message,Toast.LENGTH_SHORT).show();
        //this.

    }
}
