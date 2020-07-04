package com.pccw.hikerph.Utilities;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageHelper {

    public static String IMAGE_DIR = "imageDir";
    public static String PROFILE_FILE_NAME = "profile_pic";
    public static String HIKE_BANNER_FILE_NAME = "banner_pic"; //will concat to hikeId
    public static String HIKE_BANNER_FILE_TEMP_NAME = "tempbanner_pic"; //will concat to hikeId

    public static String saveImageToInternal(Bitmap bitmap, Context context, String fileName){

        ContextWrapper cw = new ContextWrapper(context);
        File directory = cw.getDir(IMAGE_DIR, Context.MODE_PRIVATE);
        File file = new File(directory, fileName+ ".jpg");

        if (file.exists()) {
            file.delete();
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }

        Log.d("pathimage", file.toString());

        return file.toString();
    }

    public static void displayImage(Context context, ImageView imageView){

    }

}
