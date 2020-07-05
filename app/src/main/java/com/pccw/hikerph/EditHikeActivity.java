package com.pccw.hikerph;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.pccw.hikerph.Enum.RequestCode;
import com.pccw.hikerph.Utilities.ImageFilePath;
import com.pccw.hikerph.Utilities.Properties;
import com.pccw.hikerph.Model.Hike;
import com.pccw.hikerph.Model.Profile;
import com.pccw.hikerph.ViewModel.ParentActivity;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import androidx.annotation.Nullable;

public class EditHikeActivity extends ParentActivity implements View.OnClickListener {

    private static final String TAG = "EditHikeActivity";

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

    Calendar calendar = Calendar.getInstance();

    Profile currentProfile = null;
    Hike hike;
    int index_hike;

    Date _startDate = new Date();
    Date _endDate = new Date();

    String pathBanner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_hike);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initFields();
    }


    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private void initFields(){

        index_hike = (int)getIntent().getExtras().get("index_hike");
        hike = (Hike) getIntent().getParcelableExtra("hikeDto");

        System.out.println("aaaaa"+ hike.getPath_banner());
        currentProfile = Properties.getInstance().getCurrentProfile();
        eventName = findViewById(R.id.etEventName);
        mtName = findViewById(R.id.etMountainName);
        location = findViewById(R.id.etMtLocation);
        elevation = findViewById(R.id.etMtElevation);
        startDate = findViewById(R.id.etStartDate);
        endDate = findViewById(R.id.etEndDate);
        tourGuide = findViewById(R.id.etTourGuide);
        estBudget = findViewById(R.id.etEstBudget);

        btnNext = findViewById(R.id.btnNext_add_hike);
        btnNext.setOnClickListener(this);

        btnUpload = findViewById(R.id.btnUpload);
        btnUpload.setOnClickListener(this);

        imgBanner = findViewById(R.id.imgBanner);

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

        populateFields();

    }



    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.btnNext_add_hike:

                if(!validate()){
                    Toast.makeText(this,R.string.message_fill_all_fields,
                            Toast.LENGTH_SHORT).show();

                }
                else{
                    updateModel();
                }
                break;
            case R.id.btnUpload:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
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


    private void updateModel(){
        String strEventName = eventName.getText().toString();
        String strMtName = mtName.getText().toString();
        String strLocation = location.getText().toString();
        long longElevation = Long.parseLong(elevation.getText().toString());
        String strStartDate = startDate.getText().toString();
        String strEndDate = endDate.getText().toString();
        String strTourGuide = tourGuide.getText().toString();
        Double dblEstBudget = Double.parseDouble(estBudget.getText().toString());

        hike.setEventName(strEventName);
        hike.setMtName(strMtName);
        hike.setLocation(strLocation);
        hike.setElevation(longElevation);
        hike.setStartDate(strStartDate);
        hike.setEndDate(strEndDate);
        hike.setTourGuide(strTourGuide);
        hike.setEstBudget(dblEstBudget);

        Calendar startCal = Calendar.getInstance();
        Calendar endCal = Calendar.getInstance();
        try {
            startCal.setTime( Properties.dtFormat.parse(strStartDate));
            endCal.setTime( Properties.dtFormat.parse(strEndDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        hike.setDtStartDate(startCal.getTimeInMillis());
        hike.setDtEndDate(endCal.getTimeInMillis());

        showEditItiFragment();
    }

    private void populateFields(){

        eventName.setText(hike.getEventName());
        mtName.setText(hike.getMtName());
        location.setText(hike.getLocation());
        elevation.setText(""+ hike.getElevation());
        startDate.setText(hike.getStartDate());
        endDate.setText(hike.getEndDate());
        tourGuide.setText(hike.getTourGuide());
        estBudget.setText(hike.getEstBudget().toString());

        if(hike.getPath_banner() != null){

            Glide.with(this)
                    .load( hike.getPath_banner())
                    .placeholder(R.drawable.mt_icon)
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(imgBanner);
        }

    }

    private void showDatePicker(final boolean isStartDate){

        final Calendar cldr = Calendar.getInstance();
        int day = cldr.get(Calendar.DAY_OF_MONTH);
        int month = cldr.get(Calendar.MONTH);
        int year = cldr.get(Calendar.YEAR);

        long current_date = Calendar.getInstance().getTimeInMillis();

        picker = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        calendar.set(year, monthOfYear, dayOfMonth);

                        String strDate = dateformat.format(calendar.getTime());

                        if(isStartDate){

                            startDate.setText(strDate);
                            _startDate = calendar.getTime();

                            endDate.getText().clear();
                        }
                        else{

                            endDate.setText(strDate);
                            _endDate = calendar.getTime();


                        }
                    }
                }, year, month, day);
        if(isStartDate){

            picker.getDatePicker().setMinDate(Calendar.getInstance().getTimeInMillis());

        }
        else{

            picker.getDatePicker().setMinDate(
                    _startDate == null ? current_date : _startDate.getTime());

        }
        picker.show();
    }

    private void showEditItiFragment() {

        Intent intent = new Intent(this, EditHikeItineraryActivity.class);
        intent.putExtra("hikeDto", hike);
        intent.putExtra("index_hike",index_hike);

        startActivityForResult(intent,2);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == Properties.REQUEST_CODE_EDIT_ITINERARY){

            if(data.getBooleanExtra("isFinish",false) == true){
                finish();
            }
        }
        else if (requestCode == RequestCode.SELECT_PIC_GALLERY.getCode()
                && resultCode == RESULT_OK) {

            Uri imageUri = data.getData();

            hike.setPath_banner(imageUri.toString());

            Glide.with(this)
                    .load(imageUri)
                    .into(imgBanner);
        }
    }

    private boolean validate(){

        if(eventName.getText().toString().trim().isEmpty()){

            return false;
        }
        else if(mtName.getText().toString().trim().isEmpty()){

            return false;
        }
        else if(location.getText().toString().trim().isEmpty()){

            return false;
        }
        else if(elevation.getText().toString().trim().isEmpty()){

            return false;
        }
        else if(startDate.getText().toString().trim().isEmpty()){

            return false;
        }
        else if(endDate.getText().toString().trim().isEmpty()){

            return false;
        }   else if(tourGuide.getText().toString().trim().isEmpty()){

            return false;
        }
        else if(estBudget.getText().toString().trim().isEmpty()){

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
