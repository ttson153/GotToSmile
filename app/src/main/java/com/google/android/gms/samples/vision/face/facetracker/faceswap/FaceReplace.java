package com.google.android.gms.samples.vision.face.facetracker.faceswap;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.util.Log;
import android.util.SparseArray;

import com.google.android.gms.samples.vision.face.facetracker.MyApplication;
import com.google.android.gms.samples.vision.face.facetracker.facedetector.SafeFaceDetector;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;

/**
 * Created by chauuser on 1/8/17.
 */

public class FaceReplace implements FaceSwapInterface {



    private static Bitmap overlay(Bitmap bmp1, Bitmap bmp2, int x, int y) {
        Bitmap bmOverlay = Bitmap.createBitmap(bmp1.getWidth(), bmp1.getHeight(), bmp1.getConfig());
        Canvas canvas = new Canvas(bmOverlay);
        canvas.drawBitmap(bmp1, new Matrix(), null);
        canvas.drawBitmap(bmp2, x, y, null);
        return bmOverlay;
    }

    @Override
    public Bitmap swapFaces(Bitmap poster, Bitmap picture, SparseArray<Face> posterFaces, SparseArray<Face> pictureFaces) {


        return null;
    }

    public static Bitmap replaceOneFace(Bitmap posterImage, Bitmap userImage, Face posterFace, Face userFace) {


        /*get userFaceBitmap and posterFaceBitmap:*/

        //// posterBitmapCroped:
        int posterHeight = (int)posterFace.getHeight();
        int posterWidth = (int)posterFace.getWidth();
        PointF posterPosition = posterFace.getPosition();

        Bitmap posterBitmapCroped = Bitmap.createBitmap(posterImage, (int)posterPosition.x, (int)posterPosition.y, posterWidth, posterHeight);

        //// posterBitmapCroped:
        int userHeight = (int)userFace.getHeight();
        int userWidth = (int)userFace.getWidth();
        PointF userPosition = userFace.getPosition();

        Bitmap userBitmapCroped = Bitmap.createBitmap(userImage, (int)userPosition.x, (int)userPosition.y, userWidth, userHeight);
       /**/


        /*salce faceUserBitmap;*/
        Bitmap faceUserResized = Bitmap.createScaledBitmap(userBitmapCroped, posterWidth, posterHeight, true);
        /**/

        /*replace*/
        Bitmap res = overlay(posterImage, faceUserResized, (int)userPosition.x, (int)userPosition.y);

        return null;
    }

    public static Bitmap replaceOneFace(Bitmap posterImage, Bitmap userImage) {
        FaceDetector detector = new FaceDetector.Builder(MyApplication.context)
                .setTrackingEnabled(false)
                .setLandmarkType(FaceDetector.ALL_LANDMARKS)
                .build();

        // This is a temporary workaround for a bug in the face detector with respect to operating
        // on very small images.  This will be fixed in a future release.  But in the near term, use
        // of the SafeFaceDetector class will patch the issue.
        Detector<Face> safeDetector = new SafeFaceDetector(detector);

        // Create a frame from the bitmap and run face detection on the frame.
        Frame frame = new Frame.Builder().setBitmap(userImage).build();
        SparseArray<Face> userFaces = safeDetector.detect(frame);
        Face userFace = null;
        if (userFaces.size() == 0) {
            Log.d("GotToSmile", "Face not detected. Please try again");
            return null;
        }
        else {
            userFace = userFaces.get(userFaces.keyAt(0));
        }


        //detect poster faces
        // Create a frame from the bitmap and run face detection on the frame.
        Frame framePoster = new Frame.Builder().setBitmap(posterImage).build();
        SparseArray<Face> posterFaces = safeDetector.detect(framePoster);
        Face posterFace = null;
        if (posterFaces.size() == 0) {
            Log.d("GotToSmile", "Poster not detected. Please try again");
            return null;
        }
        posterFace = posterFaces.get(posterFaces.keyAt(0));

        /*get userFaceBitmap and posterFaceBitmap:*/

        //// posterBitmapCroped:
        int posterHeight = (int)posterFace.getHeight();
        int posterWidth = (int)posterFace.getWidth();
        PointF posterPosition = posterFace.getPosition();

        //// posterBitmapCroped:
        int userHeight = (int)userFace.getHeight();
        int userWidth = (int)userFace.getWidth();
        PointF userPosition = userFace.getPosition();

        Bitmap userBitmapCroped = Bitmap.createBitmap(userImage, (int)userPosition.x, (int)userPosition.y, userWidth, userHeight);
       /**/


        /*salce faceUserBitmap;*/
        Bitmap faceUserResized = Bitmap.createScaledBitmap(userBitmapCroped, posterWidth, posterHeight, true);
        /**/

        /*replace*/
        Bitmap res = overlay(posterImage, faceUserResized, (int)posterPosition.x, (int)posterPosition.y);

        return res;
    }
}
