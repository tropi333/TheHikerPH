package com.pccw.hikerph;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.pccw.hikerph.Model.Hike;
import com.pccw.hikerph.Utilities.ImageHelper;
import com.pccw.hikerph.Utilities.Properties;
import com.pccw.hikerph.ViewModel.MyHikeViewModel;

import java.io.IOException;
import java.util.List;

public class EditHikeItineraryActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "EditHikeItineraryActivi";

    Button btnSave;
    Hike hike = null;
    int index_hike;
    EditText editText;

    private MyHikeViewModel myHikeViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_hike_itinerary);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initFields();
        populateFields();

    }
    @Override
    public boolean onSupportNavigateUp() {

        Intent intent = new Intent();
        intent.putExtra("isFinish",false);
        setResult(Properties.REQUEST_CODE_EDIT_ITINERARY, intent);

        finish();
        return true;
    }

    private void initFields(){

        hike = getIntent().getParcelableExtra("hikeDto");
        index_hike = (int)getIntent().getExtras().get("index_hike");
        editText = findViewById(R.id.editText_itinerary);
        btnSave = findViewById(R.id.btnSave_edit_hike_itinerary);
        btnSave.setOnClickListener(this);

        myHikeViewModel = ViewModelProviders.of(this).get(MyHikeViewModel.class);
    }

    @Override
    public void onClick(View view) {

        hike.setItinerary(editText.getText().toString());

        if(hike.getPath_banner() != null)
            saveBanner();
        myHikeViewModel.update(hike);


        myHikeViewModel.getAllHikes().observe(this, new Observer<List<Hike>>() {
            @Override
            public void onChanged(List<Hike> hikes) {
                Toast.makeText(getApplicationContext(),R.string.hike_update_success,
                        Toast.LENGTH_SHORT).show();
                close();
            }
        });
    }

    private void saveBanner(){

        Uri uri = Uri.parse(hike.getPath_banner());
        try {
            Bitmap selectedImage = MediaStore.Images.Media.getBitmap(getContentResolver(),
                    uri);
            long hikeId = hike.getId();
            String filePath = hikeId + "" + ImageHelper.HIKE_BANNER_FILE_NAME;
            String newDir = ImageHelper.saveImageToInternal(selectedImage, this,filePath );
            hike.setPath_banner(newDir);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void populateFields(){
        editText.setText(hike.getItinerary());
    }

    private void close(){

        Intent intent = new Intent();
        intent.putExtra("isFinish",true);
        setResult(Properties.REQUEST_CODE_EDIT_ITINERARY, intent);
        finish();
    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent();
        intent.putExtra("isFinish",false);
        setResult(Properties.REQUEST_CODE_EDIT_ITINERARY, intent);

        super.onBackPressed();
    }

}
