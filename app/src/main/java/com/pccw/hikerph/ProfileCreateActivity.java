package com.pccw.hikerph;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.pccw.hikerph.Enum.RequestCode;
import com.pccw.hikerph.Model.Profile;
import com.pccw.hikerph.Utilities.ImageHelper;
import com.pccw.hikerph.Utilities.Properties;
import com.pccw.hikerph.ViewModel.ParentActivity;
import com.pccw.hikerph.ViewModel.ProfileViewModel;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class ProfileCreateActivity extends ParentActivity implements View.OnClickListener {

    private static final String TAG = "ProfileCreateActivity";


    Profile currentProfile;

    ImageView imageView;

    TextView etFname;
    TextView etLname;
    TextView etGender;
    TextView etBday;
    TextView etEmail;
    TextView etContactNo;
    TextView etMotto;

    Button btnBrowse;
    Button btnSave;

    DatePickerDialog picker;

    ProgressBar progressBar;

    String path_profilePic;
    Bitmap selectedImage;

    boolean isInitialObserve = true;

    public static final Calendar calendar = Calendar.getInstance();
    private ProfileViewModel profileViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_create);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        currentProfile = Properties.getInstance().getCurrentProfile();

        initFields();

        if (currentProfile != null) {

            populateFields();
        }
        else{
//            testPopulate();
            Glide.with(this)
                    .load(R.drawable.profile)
                    .into(imageView);
        }
    }

    private void testPopulate(){
        etEmail.setText("jomar");
        etMotto.setText("sss");
        etContactNo.setText("05");
        etFname.setText("first name");
        etLname.setText("last name");
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private void initFields() {

        profileViewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);

        profileViewModel.getProfile().observe(this, new Observer<Profile>() {
            @Override
            public void onChanged(Profile profile) {
                //initial observe : true - upon first opening of this fragment
                //initial observe : false - after saving/updating profile
                if(isInitialObserve){
                    isInitialObserve = false;
                }
                else{
                    Toast.makeText(ProfileCreateActivity.this, R.string.profile_save_success,
                            Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);

        imageView = findViewById(R.id.imgView_profile);
        etFname = findViewById(R.id.etFName_profile_create);
        etLname = findViewById(R.id.etlName_profile_create);

        etGender = findViewById(R.id.etGender);
        etGender.setFocusableInTouchMode(false);
        etGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog("Gender", getResources().getStringArray(R.array.gender));
            }
        });

        etBday = findViewById(R.id.etBday_profile_create2);
        etEmail = findViewById(R.id.etEmail_profile_create);
        etContactNo = findViewById(R.id.etContact_profile_create);
        etMotto = findViewById(R.id.etMotto_profile_create);


        btnBrowse = findViewById(R.id.btnBrowse);
        btnSave = findViewById(R.id.btnSave_profile_create);

        btnSave.setOnClickListener(this);
        btnBrowse.setOnClickListener(this);
        etBday.setFocusable(false);

        etBday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePicker();
            }
        });
    }

    private void populateFields() {

        etFname.setText(currentProfile.getFirstName());
        etLname.setText(currentProfile.getLastName());
        etGender.setText(currentProfile.getGender());
        etBday.setText(currentProfile.getBday());
        etEmail.setText(currentProfile.getEmail());
        etEmail.setText(currentProfile.getEmail());
        etContactNo.setText(currentProfile.getContactNo());
        etMotto.setText(currentProfile.getMotto());

        if(currentProfile.getProfilePic_bitMap() != null){
            Uri uri = Uri.fromFile(new File(currentProfile.getProfilePic_bitMap()));

            Glide.with(this)
                    .load(uri)
                    .placeholder(R.drawable.profile)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .into(imageView);
            path_profilePic = currentProfile.getProfilePic_bitMap();

        }
        else{

            Glide.with(this)
                    .load(R.drawable.profile)
                    .into(imageView);

        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.btnBrowse:

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ==
                            PackageManager.PERMISSION_DENIED) {

                        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                        requestPermissions(permissions, PERMISSION_CODE_READ_EXTERNAL);

                    } else {
                        pickImageGallery();
                    }

                } else {
                    pickImageGallery();
                }

                break;

            case R.id.btnSave_profile_create:

                if (validate()) {
                    saveProfile();
                }

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RequestCode.SELECT_PIC_GALLERY.getCode()
                && resultCode == RESULT_OK) {

            Uri imageUri = data.getData();

            try {
                selectedImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }


            Glide.with(this)
                    .load(selectedImage)
                    .into(imageView);
        }
    }

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

    private void pickImageGallery() {

        int reqCode = RequestCode.SELECT_PIC_GALLERY.getCode();
        Intent gallery = new Intent();
        gallery.setType("image/*");
        gallery.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(gallery, "Select profile picture"), reqCode);
    }

    private void showDatePicker() {
        final Calendar cldr = Calendar.getInstance();
        int day = cldr.get(Calendar.DAY_OF_MONTH);
        int month = cldr.get(Calendar.MONTH);
        int year = cldr.get(Calendar.YEAR);

        picker = new DatePickerDialog(ProfileCreateActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        calendar.set(year, monthOfYear, dayOfMonth);

                        String strDate = dateformat.format(calendar.getTime());

                        etBday.setText(strDate);
                    }
                }, year, month, day);
        picker.show();
    }

    private void saveProfile() {

        showProgressBar(true);

        if(selectedImage !=null ) {
            new SaveImageAsyncTAsk().execute();
        }
        else{
            contSaveProfile();
        }
    }

    private void contSaveProfile(){

        String fName = etFname.getText().toString();
        String lName = etLname.getText().toString();
        String gender = etGender.getText().toString();
        String bday = etBday.getText().toString();
        String email = etEmail.getText().toString();
        String contactNo = etContactNo.getText().toString();
        String motto = etMotto.getText().toString();

        if (currentProfile == null) {
            currentProfile = new Profile(fName, "mName", lName, bday, email, contactNo, motto,
                    path_profilePic, gender);

            profileViewModel.saveProfile(currentProfile);


        } else {
            currentProfile.setFirstName(fName);
            currentProfile.setLastName(lName);
            currentProfile.setBday(bday);
            currentProfile.setEmail(email);
            currentProfile.setContactNo(contactNo);
            currentProfile.setMotto(motto);
            currentProfile.setGender(gender);
            currentProfile.setProfilePic_bitMap(path_profilePic);
            profileViewModel.updateProfile(currentProfile);
        }

        showProgressBar(false);

    }

    private Boolean validate() {

        String email = etEmail.getText().toString().trim();

        if (etFname.getText().toString().trim().isEmpty()) {

            String message = getResources().getString(R.string.message_empty_fName);
            showToastMessage(getApplicationContext(), message, Toast.LENGTH_SHORT);

            return false;
        } else if (etLname.getText().toString().trim().isEmpty()) {

            String message = getResources().getString(R.string.message_empty_lName);
            showToastMessage(getApplicationContext(), message, Toast.LENGTH_SHORT);

            return false;
        } else if (etGender.getText().toString().trim().isEmpty()) {

            String message = getResources().getString(R.string.message_empty_gender);
            showToastMessage(getApplicationContext(), message, Toast.LENGTH_SHORT);
            return false;
        } else if (etBday.getText().toString().trim().isEmpty()) {
            String message = getResources().getString(R.string.message_empty_bday);

            showToastMessage(getApplicationContext(), message, Toast.LENGTH_SHORT);
            return false;
        } else if (email.isEmpty()) {
            String message = getResources().getString(R.string.message_empty_email);
            showToastMessage(getApplicationContext(), message, Toast.LENGTH_SHORT);
            return false;
        } else if (!Properties.isValid(email)) {
            String message = getResources().getString(R.string.message_invalid_email);
            showToastMessage(getApplicationContext(), message, Toast.LENGTH_SHORT);
            etEmail.requestFocus();
            return false;
        } else if (etContactNo.getText().toString().trim().isEmpty()) {
            String message = getResources().getString(R.string.message_empty_contact);
            showToastMessage(getApplicationContext(), message, Toast.LENGTH_SHORT);
            return false;
        } else if (etMotto.getText().toString().trim().isEmpty()) {
            String message = getResources().getString(R.string.message_empty_motto);
            showToastMessage(getApplicationContext(), message, Toast.LENGTH_SHORT);
            return false;
        }

        return true;

    }

    private void showDialog(String title, String[] collections) {

        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle(title);
        dialog.setItems(collections, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int index) {
                etGender.setText(collections[index]);
            }

        });

        dialog.show();

    }

    private class SaveImageAsyncTAsk extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            path_profilePic = ImageHelper.saveImageToInternal(selectedImage, getApplicationContext(),
                    ImageHelper.PROFILE_FILE_NAME);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            contSaveProfile();
        }
    }

    private void showProgressBar(boolean isShown){

        if(isShown){

            progressBar.setVisibility(View.VISIBLE);
            Properties.enableUiInteraction(this, false);

        } else{

            progressBar.setVisibility(View.INVISIBLE);
            Properties.enableUiInteraction(this, true);

        }
    }
}
