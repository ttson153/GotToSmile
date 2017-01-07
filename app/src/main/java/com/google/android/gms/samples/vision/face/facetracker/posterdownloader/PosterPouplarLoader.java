package com.google.android.gms.samples.vision.face.facetracker.posterdownloader;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by beekill on 1/8/17.
 */

public class PosterPouplarLoader {

    private OnPosterPopularAsyncFinish listener;

    public interface OnPosterPopularAsyncFinish {
        void handleResult(List<String> listLinks);
    }

    private class RequestJSON extends AsyncTask<Void, Void, ArrayList<String>> {
        private static final String MOVIEDB_URL = "https://api.themoviedb.org/3/discover/movie?";
        private static final String API_KEY = "15d2ea6d0dc1d476efbca3eba2b9bbfb";
        private static final String POPULAR_MOVIE = "sort_by=popularity.desc";

        public String iStreamToString(InputStream is1) {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is1), 4096);
            String line;
            StringBuilder sb = new StringBuilder();
            try {
                while ((line = rd.readLine()) != null) {
                    sb.append(line);
                }
                rd.close();

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return sb.toString();
        }

        private String readStream(InputStream is) throws IOException {
            StringBuilder sb = new StringBuilder();
            BufferedReader r = new BufferedReader(new InputStreamReader(is),1000);
            for (String line = r.readLine(); line != null; line =r.readLine()){
                sb.append(line);
            }
            is.close();
            return sb.toString();
        }

        @Override
        protected ArrayList<String> doInBackground(Void... params) {
            String urlRequest = MOVIEDB_URL + "api_key=" + API_KEY + '&' + POPULAR_MOVIE;
            ArrayList<String> res = new ArrayList<>();
            try {
                URL url;
                try {
                    url = new URL(urlRequest);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in;
                String fullJson = "";

                try {
                    in = new BufferedInputStream(urlConnection.getInputStream());
                    fullJson = readStream(in);
                    Log.d("JSON", fullJson);
                } finally {
                    urlConnection.disconnect();
                }

                // parse
                try {
                    JSONObject responseObject = new JSONObject(fullJson);
                    JSONArray result = responseObject.getJSONArray("results");

                    for (int i = 0; i < result.length(); i++) {
                        String poster_path = result.getJSONObject(i).getString("poster_path");
//                        res.add(MOVIEIMG_URL + IMG_SIZE + poster_path);
                        res.add(poster_path);
//                        Log.d("Movie poster url", MOVIEIMG_URL + IMG_SIZE + poster_path);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return res;
        }

        @Override
        protected void onPostExecute(ArrayList<String> strings) {
            handleResult(strings);
        }
    }

    private void handleResult(ArrayList<String> listLinks) {
        if (listener != null)
            listener.handleResult(listLinks);
    }

    public void start() {
        new RequestJSON().execute();
    }

    public OnPosterPopularAsyncFinish getListener() {
        return listener;
    }

    public void setListener(OnPosterPopularAsyncFinish listener) {
        this.listener = listener;
    }
}
