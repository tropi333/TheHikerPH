package com.pccw.hikerph.ViewModel;

import android.content.Context;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import androidx.appcompat.app.AppCompatActivity;

public class ParentActivity extends AppCompatActivity {

    public static final int PERMISSION_CODE_READ_EXTERNAL = 1001;

    public static final Calendar calendar = Calendar.getInstance();

    public static final SimpleDateFormat dateformat = new SimpleDateFormat("MMM dd, yyyy");

    public void showToastMessage(Context context, String message, int length) {
        Toast.makeText(context, message, length).show();
    }
}
