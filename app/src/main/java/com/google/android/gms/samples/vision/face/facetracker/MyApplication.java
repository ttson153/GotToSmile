package com.google.android.gms.samples.vision.face.facetracker;

import android.app.Application;
import android.graphics.Bitmap;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

/**
 * Created by beekill on 1/7/17.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

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
