package com.google.android.gms.samples.vision.face.facetracker.faceswap;

import android.graphics.Bitmap;
import android.util.SparseArray;

import com.google.android.gms.vision.face.Face;

/**
 * Created by beekill on 1/7/17.
 */

public interface FaceSwapInterface {
    Bitmap swapFaces(Bitmap poster, Bitmap picture, SparseArray<Face> posterFaces, SparseArray<Face> pictureFaces);
}
