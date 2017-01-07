package com.google.android.gms.samples.vision.face.facetracker.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.gms.samples.vision.face.facetracker.Poster;
import com.google.android.gms.samples.vision.face.facetracker.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

public class PosterFullView extends AppCompatActivity {
    private Poster selectedPoster;
    private Bitmap posterImage;
    private Button pressButton;

    private Target loadTarget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poster_full_view);
        Intent intent = getIntent();
        selectedPoster = intent.getParcelableExtra("Full_view");

        pressButton = (Button) findViewById(R.id.btnPosterView);
        pressButton.setEnabled(false);

        if (selectedPoster == null)
            finish();

        // else
        // populate the image view
        // TODO: download full size poster image
        posterImage = BitmapFactory.decodeResource(getResources(), R.drawable.common_full_open_on_phone);

        // TODO: set image poster
        final ImageView posterView = (ImageView) findViewById(R.id.posterView);
        posterView.setImageBitmap(posterImage);

        loadTarget = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                PosterFullView.this.onBitmapLoaded(bitmap);
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
                posterView.setImageDrawable(errorDrawable);
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
                posterView.setImageDrawable(placeHolderDrawable);
            }
        };

//        Picasso.with(this).load("url").into(loadTarget);
    }

    public void btnPosterViewOnClicked(View view) {
        Intent takePictureIntent = new Intent(PosterFullView.this, FaceTrackerActivity.class);
        takePictureIntent.putExtra("poster", posterImage);
        startActivity(takePictureIntent);
    }

    private void onBitmapLoaded(Bitmap bitmap) {
        final ImageView posterView = (ImageView) findViewById(R.id.posterView);
        posterImage = bitmap;
        posterView.setImageBitmap(posterImage);
        pressButton.setEnabled(true);
    }
}
