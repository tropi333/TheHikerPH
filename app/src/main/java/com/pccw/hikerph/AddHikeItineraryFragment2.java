package com.pccw.hikerph;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.pccw.hikerph.Helper.Properties;
import com.pccw.hikerph.Model.HikeDto;
import com.pccw.hikerph.ViewModel.MyHikeViewModel;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddHikeItineraryFragment2 extends Fragment implements View.OnClickListener {

    Button btnSave;
    HikeDto hikeDto = null;
    EditText editText;

    private MyHikeViewModel myHikeViewModel;


    public AddHikeItineraryFragment2() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_add_hike_itinerary_fragment2, container, false);

        initFields(view);

        return  view;
    }

    private void initFields(View view){

        hikeDto = getArguments().getParcelable("hikeDto");
        editText = view.findViewById(R.id.editText_itinerary);

        myHikeViewModel = ViewModelProviders.of(this).get(MyHikeViewModel.class);

        btnSave = view.findViewById(R.id.btnSave_edit_hike_itinerary);
        btnSave.setOnClickListener(this);

    }


    @Override
    public String toString() {
        return "AddHikeItineraryFragment2";
    }


    @Override
    public void onClick(View v) {

        hikeDto.setItinerary(editText.getText().toString());
        myHikeViewModel.insert(hikeDto);

        Intent intent = new Intent(Properties.BROADCAST_RECEIVER_ADD_HIKE);
        LocalBroadcastManager.getInstance(getContext()).sendBroadcast(intent);
    }


}
