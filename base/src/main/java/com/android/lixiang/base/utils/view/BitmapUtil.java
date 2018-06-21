package com.android.lixiang.base.utils.view;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;

public class BitmapUtil {
    public static Bitmap getBitmapFromLocal(String fileName) {
        File FILE_PATH = new File(Environment.getExternalStorageDirectory().getAbsoluteFile() + "image");//设置保存路径

        try {
            File file = new File(FILE_PATH, fileName);
            if (file.exists()) {
                Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(
                        file));
                return bitmap;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
