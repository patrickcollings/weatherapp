package com.example.patch.labs;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView textView2, textView3, textView4, textView5, textView6, textView7, textView8, textView9, textView10;
    TextView textView12, textView13, textView14, textView15, textView16, textView17, textView18, textView19, textView20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        textView2 = findViewById(R.id.textView2);
        textView3 = findViewById(R.id.textView3);
        textView4 = findViewById(R.id.textView4);
        textView5 = findViewById(R.id.textView5);
        textView6 = findViewById(R.id.textView6);
        textView7 = findViewById(R.id.textView7);
        textView8 = findViewById(R.id.textView8);
        textView9 = findViewById(R.id.textView9);
        textView10 = findViewById(R.id.textView10);

        textView12 = findViewById(R.id.textView12);
        textView13 = findViewById(R.id.textView13);
        textView14 = findViewById(R.id.textView14);
        textView15 = findViewById(R.id.textView15);
        textView16 = findViewById(R.id.textView16);
        textView17= findViewById(R.id.textView17);
        textView18 = findViewById(R.id.textView18);
        textView19 = findViewById(R.id.textView19);
        textView20 = findViewById(R.id.textView20);




        Function.placeIdTask asyncTask =new Function.placeIdTask(new Function.AsyncResponse() {
            public void processFinish(List<String> time, List<String> weather) {
                Log.i("lab1", "Process Finished" + time.toString() + " " + weather.toString());
                textView2.setText(time.get(0));
                textView3.setText(time.get(1));
                textView4.setText(time.get(2));
                textView5.setText(time.get(3));
                textView6.setText(time.get(4));
                textView7.setText(time.get(5));
                textView8.setText(time.get(6));
                textView9.setText(time.get(7));
                textView10.setText(time.get(8));

                textView12.setText(weather.get(0));
                textView13.setText(weather.get(1));
                textView14.setText(weather.get(2));
                textView15.setText(weather.get(3));
                textView16.setText(weather.get(4));
                textView17.setText(weather.get(5));
                textView18.setText(weather.get(6));
                textView19.setText(weather.get(7));
                textView20.setText(weather.get(8));
            }
        });
        asyncTask.execute("25.180000", "89.530000"); //  asyncTask.execute("Latitude", "Longitude")
    }
}
