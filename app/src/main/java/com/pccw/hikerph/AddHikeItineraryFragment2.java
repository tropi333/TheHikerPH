package com.pccw.hikerph;


import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.pccw.hikerph.Model.Hike;
import com.pccw.hikerph.Utilities.ImageHelper;
import com.pccw.hikerph.ViewModel.MyHikeViewModel;

import java.io.IOException;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddHikeItineraryFragment2 extends Fragment implements View.OnClickListener {

    Button btnSave;
    Hike hike = null;
    EditText editText;

    ProgressBar progressBar;

    private MyHikeViewModel myHikeViewModel;

    private boolean isInitialObserve = true;
    private long lastHikeIndex = 0;

    public AddHikeItineraryFragment2() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_add_hike_itinerary_fragment2, container, false);

        initFields(view);

//        initViewModel();

        return  view;
    }

    private void initFields(View view){

        hike = getArguments().getParcelable("hikeDto");

        editText = view.findViewById(R.id.editText_itinerary);

 /*       progressBar = view.findViewById(R.id.pbAddHike);
        progressBar.setVisibility(View.INVISIBLE);*/

        btnSave = view.findViewById(R.id.btnSave_edit_hike_itinerary);
        btnSave.setOnClickListener(this);
        myHikeViewModel = ViewModelProviders.of(this).get(MyHikeViewModel.class);
        initViewModel();

    }

    private void initViewModel(){


        myHikeViewModel.getAllHikes().observe(this, new Observer<List<Hike>>() {
            @Override
            public void onChanged(List<Hike> hikes) {

                //initial observe : true - upon first opening of this fragment
                //initial observe : false - after saving hike
                if(hikes.size() > 0){
                    lastHikeIndex = hikes.get(hikes.size() - 1).getId();
                }

                if(isInitialObserve){
                    isInitialObserve = false;
                }
                else{

                    //stop progress bar here
                    Toast.makeText(getContext(), R.string.hike_save_success,
                            Toast.LENGTH_SHORT).show();

                    showMyHikes();
                }
            }
        });
    }

    @Override
    public String toString() {
        return "AddHikeItineraryFragment2";
    }

    @Override
    public void onClick(View v) {

        hike.setItinerary(editText.getText().toString());

        if(hike.getPath_banner() != null)
            saveBanner();

        myHikeViewModel.insert(hike);


    }

    private void saveBanner(){
        Uri uri = Uri.parse(hike.getPath_banner());
        try {
            Bitmap selectedImage = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),
                    uri);
            long hikeId = lastHikeIndex + 1;
            String filePath = hikeId + "" + ImageHelper.HIKE_BANNER_FILE_NAME;
            String newDir = ImageHelper.saveImageToInternal(selectedImage, getContext(),filePath );
            hike.setPath_banner(newDir);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showMyHikes(){

        ((MainActivity)getActivity()).showMyHikeFragment();

    }

}
