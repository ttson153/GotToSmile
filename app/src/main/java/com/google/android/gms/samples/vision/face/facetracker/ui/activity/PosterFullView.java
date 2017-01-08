package com.google.android.gms.samples.vision.face.facetracker.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.gms.samples.vision.face.facetracker.MyApplication;
import com.google.android.gms.samples.vision.face.facetracker.Poster;
import com.google.android.gms.samples.vision.face.facetracker.R;
import com.google.android.gms.samples.vision.face.facetracker.util.MoviePosterUrl;
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
//        posterImage = BitmapFactory.decodeResource(getResources(), R.drawable.common_full_open_on_phone);

        final ImageView posterView = (ImageView) findViewById(R.id.posterView);
//        posterView.setImageBitmap(posterImage);

        loadTarget = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                PosterFullView.this.onBitmapLoaded(bitmap);
                MyApplication app = (MyApplication) getApplication();
                app.storeBitmap(bitmap);
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

        Picasso.with(this)
                .load(MoviePosterUrl.getPosterUrl(selectedPoster.getImagePath(), MoviePosterUrl.MoviePosterSize.LARGE))
                .placeholder(R.drawable.image_placeholder)
                .error(R.drawable.image_error)
                .into(loadTarget);
    }

    public void btnPosterViewOnClicked(View view) {
        Intent takePictureIntent = new Intent(PosterFullView.this, FaceTrackerActivity.class);
        takePictureIntent.putExtra("poster", selectedPoster);
        startActivity(takePictureIntent);
    }

    private void onBitmapLoaded(Bitmap bitmap) {
        final ImageView posterView = (ImageView) findViewById(R.id.posterView);
        posterImage = bitmap;
        posterView.setImageBitmap(posterImage);
        pressButton.setEnabled(true);
    }
}
