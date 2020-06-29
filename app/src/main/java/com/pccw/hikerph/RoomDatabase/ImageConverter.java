package com.pccw.hikerph.RoomDatabase;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

import androidx.room.TypeConverter;

public class ImageConverter {
    @TypeConverter
    public static byte[] bitmap2ByteArray(Bitmap bmp) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 50, stream);
        return stream.toByteArray();
    }

    public static Bitmap byteArray2Bitmap(byte[] byteArray) {
        return BitmapFactory.decodeByteArray(byteArray, 0,byteArray.length);
    }


}
