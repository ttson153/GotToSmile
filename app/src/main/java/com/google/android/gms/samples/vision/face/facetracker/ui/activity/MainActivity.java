package com.google.android.gms.samples.vision.face.facetracker.ui.activity;

import android.content.Intent;
import android.os.Message;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.SearchView;

import com.google.android.gms.samples.vision.face.facetracker.Poster;
import com.google.android.gms.samples.vision.face.facetracker.R;
import com.google.android.gms.samples.vision.face.facetracker.posterdownloader.PosterLinkLoader;
import com.google.android.gms.samples.vision.face.facetracker.posterdownloader.PosterPouplarLoader;
import com.google.android.gms.samples.vision.face.facetracker.ui.adapter.PosterAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
    implements PosterLinkLoader.AsyncTaskListener, PosterPouplarLoader.OnPosterPopularAsyncFinish
{

//    private static final int QUERY_POPULAR_MSG = 15;
//    private static final int QUERY_MOVIVE_MSG = 28;

    private GridView posterGridView;
    private PosterAdapter posterAdapter;
    private SearchView searchView;
//    private Handler loadDatabaseHandler = new Handler(Looper.getMainLooper()) {
//        @Override
//        public void handleMessage(Message msg) {
//            switch (msg.what) {
//                case QUERY_MOVIVE_MSG:
//                    break;
//                case QUERY_POPULAR_MSG:
//                    break;
//                default:
//                    super.handleMessage(msg);
//            }
//
//        }
//    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        posterGridView = (GridView) findViewById(R.id.posterGridView);
        posterAdapter = new PosterAdapter(this);

        posterGridView.setAdapter(posterAdapter);
        posterGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onPosterClicked((Poster) posterAdapter.getItem(position));
            }
        });

        onDiscoverPopularMovie();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);

        // add callback for text changed
//        EditText searchEditText = (EditText) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
//        searchEditText.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                // do nothing
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                // do nothing
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                String searchString = s.toString();
//
//                if (s.equals(""))
//                    onDiscoverPopularMovie();
//                else
//                    onSearchPoster(searchString);
//            }
//        });

        final MenuItem myActionMenuItem = menu.findItem( R.id.action_search);
        searchView = (SearchView) myActionMenuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.setQuery(query, false);
                searchView.clearFocus();

//                if( ! searchView.isIconified()) {
//                    searchView.setIconified(true);
//                }

                myActionMenuItem.collapseActionView();
                onSearchPoster(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                // UserFeedback.show( "SearchOnQueryTextChanged: " + s);
                return false;
            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                onDiscoverPopularMovie();

                searchView.clearFocus();
                searchView.onActionViewCollapsed();

                return true;
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
        String noWhiteSpaceMovieName = movieName.replace(" ", "%20");

        PosterLinkLoader linkLoader = new PosterLinkLoader();
        linkLoader.setListener(this);
        linkLoader.start(noWhiteSpaceMovieName);
    }

    private void onDiscoverPopularMovie() {
        PosterPouplarLoader loader = new PosterPouplarLoader();
        loader.setListener(this);
        loader.start();
    }

    @Override
    public void onFinish(List<String> listLinks) {
        List<Poster> posterList = new ArrayList<>();
        for (String link : listLinks) {
            posterList.add(new Poster("", "", link));
        }

        // update ui
        posterAdapter.setPosterList(posterList);
        posterAdapter.notifyDataSetChanged();
    }

    @Override
    public void handleResult(List<String> listLinks) {
        List<Poster> posterList = new ArrayList<>();
        for (String link : listLinks) {
            posterList.add(new Poster("", "", link));
        }

        // update ui
        posterAdapter.setPosterList(posterList);
        posterAdapter.notifyDataSetChanged();
    }
}
