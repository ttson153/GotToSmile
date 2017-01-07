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
 * Created by tts on 1/7/17.
 */

public class PosterLinkLoader {
    private ArrayList<String> listLink;

    public PosterLinkLoader() {
    }

    public interface AsyncTaskListener {
        void onFinish(List<String> listLink);
    }

    public class RequestJSON extends AsyncTask<String, Void, ArrayList<String>> {
        private static final String MOVIEDB_URL = "https://api.themoviedb.org/3/search/movie?";
        public static final String API_KEY = "15d2ea6d0dc1d476efbca3eba2b9bbfb";
        public static final String MOVIEIMG_URL = "http://image.tmdb.org/t/p/";
        public static final String IMG_SIZE = "w500/";

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
        protected ArrayList<String> doInBackground(String... text) {
            String urlRequest = MOVIEDB_URL + "api_key=" + API_KEY + "&query=" + text[0];
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
                        res.add(MOVIEIMG_URL + IMG_SIZE + poster_path);
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

    public void handleResult(ArrayList<String> movieName) {
        //TODO replace with your own code (i.e. load image with Picasso)
        for (int i = 0; i < movieName.size(); i++) {
            Log.d("Movie poster url", movieName.get(i));
        }
    }

    public void start(String movieName) {
        new RequestJSON().execute(movieName);
    }
}
