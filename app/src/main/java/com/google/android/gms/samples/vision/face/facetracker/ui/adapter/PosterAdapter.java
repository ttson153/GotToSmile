package com.google.android.gms.samples.vision.face.facetracker.ui.adapter;

import android.content.Context;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.google.android.gms.samples.vision.face.facetracker.Poster;
import com.google.android.gms.samples.vision.face.facetracker.R;
import com.google.android.gms.samples.vision.face.facetracker.util.MoviePosterUrl;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by beekill on 1/7/17.
 */

public class PosterAdapter extends BaseAdapter {

    private Context context;
    private List<Poster> posterList = new ArrayList<>();
    private static int POSTER_THUMBNAIL_WIDTH = 500;
    private static final String TAG = "GotToSmile";

    public PosterAdapter(Context context) {
        this.context = context;
    }

    public List<Poster> getPosterList() {
        return posterList;
    }

    public void setPosterList(List<Poster> posterList) {
        this.posterList = posterList;
    }

    @Override
    public int getCount() {
        return posterList.size();
    }

    @Override
    public Object getItem(int position) {
        return posterList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
//        ImageView imageView = (ImageView) convertView;
//
//        Display display = context.getDefaultDisplay();
//        int width = display.getWidth();
//        double ratio = ((float) (width))/300.0;
//        int height = (int)(ratio*50);

        if (convertView == null) {
            imageView = new ImageView(context);
            imageView.setLayoutParams(new GridView.LayoutParams(POSTER_THUMBNAIL_WIDTH, ViewGroup.LayoutParams.WRAP_CONTENT));
//            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(5, 5, 5, 5);
        } else {
            imageView = (ImageView) convertView;
        }

//        imageView.setImageResource(android.R.drawable.ic_menu_add);

        Poster poster = (Poster) getItem(position);
//        Log.i(TAG, MoviePosterUrl.getPosterUrl(poster.getImagePath(), MoviePosterUrl.MoviePosterSize.SMALL));

        Picasso
                .with(context)
                .load(MoviePosterUrl.getPosterUrl(poster.getImagePath(), MoviePosterUrl.MoviePosterSize.SMALL))
                .placeholder(R.drawable.image_placeholder)
                .error(R.drawable.image_error)
                .into(imageView);

        return imageView;
    }
}
