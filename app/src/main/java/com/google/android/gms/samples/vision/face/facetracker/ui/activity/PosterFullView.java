package com.google.android.gms.samples.vision.face.facetracker.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.samples.vision.face.facetracker.Poster;
import com.google.android.gms.samples.vision.face.facetracker.R;

public class PosterFullView extends AppCompatActivity {
    Poster selectedPoster;
    Bitmap posterImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poster_full_view);
        Intent intent = getIntent();
        selectedPoster = intent.getParcelableExtra("Full_view");

        if (selectedPoster == null)
            finish();

        // else
        // populate the image view
        // TODO: download full size poster image
        posterImage = BitmapFactory.decodeResource(getResources(), R.drawable.common_full_open_on_phone);

        // TODO: set image poster
        ImageView posterView = (ImageView) findViewById(R.id.posterView);
        posterView.setImageBitmap(posterImage);
    }

    public void btnPosterViewOnClicked(View view) {
        Intent takePictureIntent = new Intent(PosterFullView.this, FaceTrackerActivity.class);
        takePictureIntent.putExtra("poster", posterImage);
        startActivity(takePictureIntent);
    }
}
