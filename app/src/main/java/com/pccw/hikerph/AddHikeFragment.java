package com.pccw.hikerph;


import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.pccw.hikerph.Enum.RequestCode;
import com.pccw.hikerph.Helper.ImageFilePath;
import com.pccw.hikerph.Model.HikeDto;
import com.pccw.hikerph.Model.Profile;
import com.pccw.hikerph.Helper.Properties;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import static android.app.Activity.RESULT_OK;
import static com.pccw.hikerph.ViewModel.ParentActivity.PERMISSION_CODE_READ_EXTERNAL;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddHikeFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "AddHikeFragment";

    DatePickerDialog picker;

    ImageView imgBanner;

    Button btnNext;
    Button btnUpload;

    EditText eventName;
    EditText mtName;
    EditText location;
    EditText elevation;
    EditText startDate;
    EditText endDate;
    EditText tourGuide;
    EditText estBudget;

    Profile currentProfile = null;

    Calendar calendar = Calendar.getInstance();

    Date _startDate = new Date();
    Date _endDate = new Date();

    private String path_banner = "";

    public AddHikeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        currentProfile = Properties.getInstance().getCurrentProfile();

        View view = inflater.inflate(R.layout.fragment_add_hike, container, false);
        initFields(view);

        return view;
    }

    private void initFields(View view) {

        eventName = view.findViewById(R.id.etEventName);
        mtName = view.findViewById(R.id.etMountainName);
        location = view.findViewById(R.id.etMtLocation);
        elevation = view.findViewById(R.id.etMtElevation);
        startDate = view.findViewById(R.id.etStartDate);
        endDate = view.findViewById(R.id.etEndDate);
        tourGuide = view.findViewById(R.id.etTourGuide);
        estBudget = view.findViewById(R.id.etEstBudget);

        btnNext = view.findViewById(R.id.btnNext_add_hike);
        btnNext.setOnClickListener(this);

        btnUpload = view.findViewById(R.id.btnUpload);
        btnUpload.setOnClickListener(this);

        imgBanner = view.findViewById(R.id.hikeBanner);

        startDate.setFocusable(false);
        endDate.setFocusable(false);

        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePicker(true);
            }
        });

        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePicker(false);
            }
        });

//        initFields_test();
    }
/*
    private void initFields_test() {
        eventName.setText("wew");
        mtName.setText("wew");
        location.setText("wew");
        elevation.setText("222");
        tourGuide.setText("2s22");
        estBudget.setText("222");
        startDate.setText("Mar 25, 2020");
        endDate.setText("Mar 25, 2020");
        _startDate = new Date();
        _endDate = new Date();

    }*/

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull
            int[] grantResults) {
        switch (requestCode) {

            case PERMISSION_CODE_READ_EXTERNAL:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    pickImageGallery();
                }
                break;
        }
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btnNext_add_hike:

                if (Properties.getInstance().getCurrentProfile() == null) {

                    Toast.makeText(getContext(), "Please create your account first.", Toast.LENGTH_SHORT).show();

                } else if (!validate()) {

                    Toast.makeText(getContext(), "Please fill-up all fields.", Toast.LENGTH_SHORT).show();

                } else {
                    createModel();
                }

        break;
        case R.id.btnUpload:

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (getContext().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_DENIED) {

                String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                requestPermissions(permissions, PERMISSION_CODE_READ_EXTERNAL);

            } else {
                pickImageGallery();
            }

        } else {
            pickImageGallery();
        }

        break;
    }

}

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RequestCode.SELECT_PIC_GALLERY.getCode()
                && resultCode == RESULT_OK) {

            Uri imageUri = data.getData();

            String realPath = ImageFilePath.getPath(getContext(), data.getData());
            path_banner = realPath;

            Glide.with(this)
                    .load(imageUri)
                    .into(imgBanner);
        } else if (requestCode == RequestCode.SELECT_PIC_GALLERY.getCode()
                && resultCode == RESULT_OK) {

            Uri imageUri = data.getData();

            String realPath = ImageFilePath.getPath(getContext(), data.getData());
            path_banner = realPath;

            Glide.with(this)
                    .load(imageUri)
                    .into(imgBanner);
        }
    }


    private void createModel() {

        String strEventName = eventName.getText().toString();
        String strMtName = mtName.getText().toString();
        String strLocation = location.getText().toString();
        long longElevation = Long.parseLong(elevation.getText().toString());
        String strStartDate = startDate.getText().toString();
        String strEndDate = endDate.getText().toString();
        String strTourGuide = tourGuide.getText().toString();
        Double dblEstBudget = Double.parseDouble(estBudget.getText().toString());

        Calendar startCal = Calendar.getInstance();
        Calendar endCal = Calendar.getInstance();
        try {
            startCal.setTime( Properties.dtFormat.parse(strStartDate));
            endCal.setTime( Properties.dtFormat.parse(strEndDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (currentProfile != null) {

            HikeDto hikeDto = new HikeDto(strEventName, strMtName, strLocation, strStartDate, strEndDate, strTourGuide
                    , dblEstBudget, longElevation, currentProfile.getId(), path_banner, startCal.getTimeInMillis(),endCal.getTimeInMillis() );

            showAddItiFragment(hikeDto);

        } else {
            Toast.makeText(getContext(), "Please create your profile first.", Toast.LENGTH_SHORT).show();
        }

    }

    private void showAddItiFragment(HikeDto hikeDto) {

        AddHikeItineraryFragment2 nextFragment = new AddHikeItineraryFragment2();
        Bundle bundle = new Bundle();
        bundle.putParcelable("hikeDto", hikeDto);

        nextFragment.setArguments(bundle);

        getActivity().getSupportFragmentManager().beginTransaction().show(nextFragment)
                .replace(R.id.fragment_container, nextFragment, "addHikeItineraryFragment2")
                .addToBackStack(null)
                .commit();


    }


    private void showDatePicker(final boolean isStartDate) {
        final Calendar cldr = Calendar.getInstance();
        int day = cldr.get(Calendar.DAY_OF_MONTH);
        int month = cldr.get(Calendar.MONTH);
        int year = cldr.get(Calendar.YEAR);

        long current_date = Calendar.getInstance().getTimeInMillis();

        // date picker dialog
        picker = new DatePickerDialog(getContext(),

                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        calendar.set(year, monthOfYear, dayOfMonth);

                        String strDate = Properties.dtFormat.format(calendar.getTime());

                        if (isStartDate) {

                            startDate.setText(strDate);
                            _startDate = calendar.getTime();

                            endDate.getText().clear();

                        } else {

                            endDate.setText(strDate);
                            _endDate = calendar.getTime();

                        }
                    }
                }, year, month, day);

        if (isStartDate) {

            picker.getDatePicker().setMinDate(Calendar.getInstance().getTimeInMillis());

        } else {

            picker.getDatePicker().setMinDate(
                    _startDate == null ? current_date : _startDate.getTime());

        }

        picker.show();
    }

    private boolean validate() {

        if (eventName.getText().toString().trim().isEmpty()) {

            return false;
        } else if (mtName.getText().toString().trim().isEmpty()) {

            return false;
        } else if (location.getText().toString().trim().isEmpty()) {

            return false;
        } else if (elevation.getText().toString().trim().isEmpty()) {

            return false;
        } else if (startDate.getText().toString().trim().isEmpty()) {

            return false;
        } else if (endDate.getText().toString().trim().isEmpty()) {

            return false;
        } else if (tourGuide.getText().toString().trim().isEmpty()) {

            return false;
        } else if (estBudget.getText().toString().trim().isEmpty()) {

            return false;
        }

        return true;

    }

    private void pickImageGallery() {

        int reqCode = RequestCode.SELECT_PIC_GALLERY.getCode();
        //browsePic();
        Intent gallery = new Intent();
        gallery.setType("image/*");
        gallery.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(gallery, "Select profile picture"), reqCode);
    }
}
