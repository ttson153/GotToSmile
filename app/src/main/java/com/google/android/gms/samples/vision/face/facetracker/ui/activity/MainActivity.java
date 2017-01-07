package com.google.android.gms.samples.vision.face.facetracker.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.google.android.gms.samples.vision.face.facetracker.Poster;
import com.google.android.gms.samples.vision.face.facetracker.R;
import com.google.android.gms.samples.vision.face.facetracker.ui.adapter.PosterAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private GridView posterGridView;
    private PosterAdapter posterAdapter;

    private List<Poster> posterList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        posterGridView = (GridView) findViewById(R.id.posterGridView);
        posterAdapter = new PosterAdapter(this);
        posterAdapter.setPosterList(posterList);

        // TODO: fill with genuine data
        for (int i = 0; i < 10; ++i)
            posterList.add(new Poster());

        posterGridView.setAdapter(posterAdapter);
        posterGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onPosterClicked((Poster) posterAdapter.getItem(position));
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);
        return true;
    }

    private void onPosterClicked(Poster poster) {
        Intent showFullPosterIntent = new Intent(MainActivity.this, PosterFullView.class);
        showFullPosterIntent.putExtra("Full_view", poster);
        startActivity(showFullPosterIntent);
    }
}
