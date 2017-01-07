package com.google.android.gms.samples.vision.face.facetracker.ui.activity;

import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
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

        // add callback for text changed
        EditText searchEditText = (EditText) MenuItemCompat.getActionView(menu.findItem(R.id.searchEditText));
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // do nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // do nothing
            }

            @Override
            public void afterTextChanged(Editable s) {
                String searchString = s.toString();
                onSearchPoster(searchString);
            }
        });
        return true;
    }

    private void onPosterClicked(Poster poster) {
        Intent showFullPosterIntent = new Intent(MainActivity.this, PosterFullView.class);
        showFullPosterIntent.putExtra("Full_view", poster);
        startActivity(showFullPosterIntent);
    }

    private void onSearchPoster(String movieName) {
        // TODO: search for film name
        // and display
    }
}
