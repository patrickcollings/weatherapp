package com.example.patch.labs;

import android.os.AsyncTask;
import android.os.Message;
import android.util.JsonReader;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.Buffer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class Function {

    // Project Created by Ferdousur Rahman Shajib
    // www.androstock.com
    static List time = new ArrayList<String>();
    static List weather = new ArrayList<String>();


    private static final String OPEN_WEATHER_MAP_URL =
            "https://api.openweathermap.org/data/2.5/forecast?q=London,us";

    private static final String OPEN_WEATHER_MAP_API = "20ee5a1edd0d3148cfef17ce336df3a0";

    public interface AsyncResponse {

        void processFinish(List<String> time, List<String> weather);
    }


    public static class placeIdTask extends AsyncTask<String, Void, JSONObject> {

        public AsyncResponse delegate = null;//Call back interface

        public placeIdTask(AsyncResponse asyncResponse) {
            delegate = asyncResponse;//Assigning call back interfacethrough constructor
        }

        @Override
        protected JSONObject doInBackground(String... params) {

            JSONObject jsonWeather = null;
            try {
                getWeatherJSON(params[0], params[1]);
            } catch (Exception e) {
                Log.d("Error", "Cannot process JSON results", e);
            }


            return jsonWeather;
        }

        @Override
        protected void onPostExecute(JSONObject json) {
//            try {
//                JSONObject details = json.getJSONArray("weather").getJSONObject(0);
//                JSONObject main = json.getJSONObject("main");
//                DateFormat df = DateFormat.getDateTimeInstance();
//                Log.i("lab1", details.toString());
//
//                String city = json.getString("name").toUpperCase(Locale.US) + ", " + json.getJSONObject("sys").getString("country");
//                String description = details.getString("description").toUpperCase(Locale.US);
//                String temperature = String.format("%.2f", main.getDouble("temp")) + "°";
//                String humidity = main.getString("humidity") + "%";
//                String pressure = main.getString("pressure") + " hPa";
//                String updatedOn = df.format(new Date(json.getLong("dt") * 1000));

                delegate.processFinish(time, weather);
//
//            } catch (JSONException e) {
//                //Log.e(LOG_TAG, "Cannot process JSON results", e);
//            }


        }
    }


    public static void getWeatherJSON(String lat, String lon) {
        BufferedReader reader = null;
        Log.i("lab1", "Getting Weather JSON");
        try {
            URL url = new URL(OPEN_WEATHER_MAP_URL);
            Log.i("lab1", "Creating Reader");
            HttpURLConnection connection =
                    (HttpURLConnection) url.openConnection();
            connection.addRequestProperty("x-api-key", OPEN_WEATHER_MAP_API);

            connection.setRequestMethod("POST");
            connection.connect();

            reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));


            StringBuilder stringBuilder = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line + "\n");
            }
            Log.i("lab1", stringBuilder.toString());
            JSONObject obj = new JSONObject(stringBuilder.toString());
            reformatObject(obj);

        } catch (Exception e) {
            Log.i("lab1", "Error found" + e.getMessage());
        } finally {
            // close the reader; this can throw an exception too, so
            // wrap it in another try/catch block.
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
        }
    }

    public static void reformatObject(JSONObject obj) {

        try {
            JSONArray days = obj.getJSONArray("list");
            for(int i = 0; i < days.length(); i++) {
                JSONObject day = days.getJSONObject(i);
                time.add( reformatDate(day.getString("dt")) );
                // Get weather description
                String description = day.getJSONArray("weather").getJSONObject(0).getString("description");
                weather.add(description);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < time.size(); i++) {
            Log.i("lab1", time.get(i) + " : " + weather.get(i) );
        }
    }

    public static String reformatDate(String unix) {
        int time = Integer.parseInt(unix);
        Date date = new Date(time*1000L);
        // the format of your date
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        // give a timezone reference for formatting (see comment at the bottom)
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        String formattedDate = sdf.format(date);
        return formattedDate;
    }


}