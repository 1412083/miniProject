package com.example.khangduyle.miniproject1412083.ModuleDirection;

import android.os.AsyncTask;

import com.example.khangduyle.miniproject1412083.Point;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by KHANGDUYLE on 08/05/2017.
 */

public class LocationFinder {
    private static final String DIRECTION_URL_API = "http://maps.google.com/maps/api/geocode/json?address=" ;
    private static final String DIRECTION_URL_API_LAST ="&sensor=false";

    private LocationFinderListener listener;
    private String location;


    public LocationFinder(LocationFinderListener listener, String origin) {
        this.listener = listener;
        this.location = origin;

    }

    public void execute() throws UnsupportedEncodingException {
        listener.onLocationFinderStart();
        new LocationFinder.DownloadRawData().execute(createUrl());
    }

    private String createUrl() throws UnsupportedEncodingException {
        String urlLocation = URLEncoder.encode(location, "utf-8");

        return DIRECTION_URL_API +urlLocation + DIRECTION_URL_API_LAST;
    }

    private class DownloadRawData extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String link = params[0];
            try {
                URL url = new URL(link);
                InputStream is = url.openConnection().getInputStream();
                StringBuffer buffer = new StringBuffer();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line).append("\n");
                }

                return buffer.toString();

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String res) {
            try {
                parseJSon(res);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void parseJSon(String data) throws JSONException {
        Point mPoint;
        mPoint=null;
        if (data != null) {


            JSONObject jsonData = new JSONObject(data);
            JSONArray jsonRoutes = jsonData.getJSONArray("results");
            JSONObject jsonClass= jsonRoutes.getJSONObject(0);

            JSONObject jsonGeometry = jsonClass.getJSONObject("geometry");

            JSONObject jsonLocation = jsonGeometry.getJSONObject("location");
             mPoint = new Point(new LatLng(jsonLocation.getDouble("lat"), jsonLocation.getDouble("lng")), jsonClass.getString("formatted_address"));

        }
        listener.onLoctionFinderSuccess(mPoint);
    }
}
