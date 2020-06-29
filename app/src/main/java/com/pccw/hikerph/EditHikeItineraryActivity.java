package com.pccw.hikerph;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.pccw.hikerph.Helper.Properties;
import com.pccw.hikerph.Model.HikeDto;
import com.pccw.hikerph.ViewModel.MyHikeViewModel;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class EditHikeItineraryActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "EditHikeItineraryActivi";

    Button btnSave;
    HikeDto hikeDto = null;
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

        hikeDto = getIntent().getParcelableExtra("hikeDto");
        index_hike = (int)getIntent().getExtras().get("index_hike");

        editText = findViewById(R.id.editText_itinerary);
        btnSave = findViewById(R.id.btnSave_edit_hike_itinerary);
        btnSave.setOnClickListener(this);

        myHikeViewModel = ViewModelProviders.of(this).get(MyHikeViewModel.class);
    }



    @Override
    public void onClick(View view) {
        hikeDto.setItinerary(editText.getText().toString());
        myHikeViewModel.update(hikeDto);
        myHikeViewModel.getAllHikes().observe(this, new Observer<List<HikeDto>>() {
            @Override
            public void onChanged(List<HikeDto> hikeDtos) {
                Toast.makeText(getApplicationContext(),"Your changes was saved successfully.",
                        Toast.LENGTH_SHORT).show();
                close();

            }
        });
    }


    private void populateFields(){
        editText.setText(hikeDto.getItinerary());
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
