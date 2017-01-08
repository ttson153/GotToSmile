package com.google.android.gms.samples.vision.face.facetracker.posterdownloader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by tts on 1/7/17.
 */

public class PosterLoader {

    private Bitmap mThumb;
    private OnAsyncTaskFinished listener;

    public interface OnAsyncTaskFinished {
        void onFinish(Bitmap image);
    }

    public PosterLoader() {
    }

    private class RequestImage extends AsyncTask<String, Void, Bitmap> {
        public static final String MOVIEIMG_URL = "http://image.tmdb.org/t/p/";

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
            BufferedReader r = new BufferedReader(new InputStreamReader(is), 1000);
            for (String line = r.readLine(); line != null; line = r.readLine()) {
                sb.append(line);
            }
            is.close();
            return sb.toString();
        }

        /*
         * pass in size string and poster_path
         */
        @Override
        protected Bitmap doInBackground(String... text) {
            String urlRequest = MOVIEIMG_URL + text[0] + "/" + text[1];
            ArrayList<String> res = new ArrayList<>();
            URL url;
            try {
                url = new URL(urlRequest);
                Bitmap result = BitmapFactory.decodeStream(url.openConnection().getInputStream());

                return result;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (listener != null)
                listener.onFinish(bitmap);
        }
    }


    // pass in size and poster_path
    // e.g Bitmap bitmap = new PosterLoader().getThumb("w500", "/nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg");
    public Bitmap getThumb(String... posterLink) {
        new RequestImage().execute(posterLink);
        return mThumb;
    }

    public OnAsyncTaskFinished getListener() {
        return listener;
    }

    public void setListener(OnAsyncTaskFinished listener) {
        this.listener = listener;
    }
}
