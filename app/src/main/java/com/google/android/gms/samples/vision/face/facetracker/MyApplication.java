package com.google.android.gms.samples.vision.face.facetracker;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

/**
 * Created by beekill on 1/7/17.
 */

public class MyApplication extends Application {
    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();

        context = getApplicationContext();
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
    }

    private Bitmap tmpBitmap;

    public Bitmap getBitmap() {
        return tmpBitmap;
    }

    public void storeBitmap(Bitmap tmpBitmap) {
        this.tmpBitmap = tmpBitmap;
    }
}
