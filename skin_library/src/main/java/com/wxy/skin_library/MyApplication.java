package com.wxy.skin_library;

import android.app.Application;
import android.os.Environment;

import com.wxy.skin_library.mode.SkinManager;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        String path = Environment.getExternalStorageDirectory() + "/Music/skin.apk";
        SkinManager.getInstance().from(this).parserApk(path);
    }
}
