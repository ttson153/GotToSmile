package com.google.android.gms.samples.vision.face.facetracker;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by beekill on 1/7/17.
 */

public class Poster implements Parcelable {

    private String name;
    private String description;
    private String imagePath;

    public Poster() {

    }

    public Poster(String name, String description, String imagePath) {
        this.name = name;
        this.description = description;
        this.imagePath = imagePath;
    }

    public  Poster(Parcel in) {
        String[] arr = in.createStringArray();

        name = arr[0];
        description = arr[1];
        imagePath = arr[2];
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[] {name, description, imagePath});
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Poster createFromParcel(Parcel in) {
            return new Poster(in);
        }

        public Poster[] newArray(int size) {
            return new Poster[size];
        }
    };

}
