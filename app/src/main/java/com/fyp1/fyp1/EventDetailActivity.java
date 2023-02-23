package com.fyp1.fyp1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class EventDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);

        TextView shortName_text = findViewById(R.id.shortName_text);
        TextView name_text = findViewById(R.id.name_text);
        TextView season_text = findViewById(R.id.year_text);
        TextView dateTime_text = findViewById(R.id.dateTime_text);
        ImageView imageView = findViewById(R.id.poster_image);

        Bundle bundle = getIntent().getExtras();

        String shortName = bundle.getString("ShortName");
        String name = bundle.getString("Name");
        String season = bundle.getString("Season");
        String dateTime = bundle.getString("DateTime");

        shortName_text.setText(shortName);
        name_text.setText(name);
        season_text.setText(season);
        dateTime_text.setText(dateTime);
        imageView.setImageResource(R.drawable.ufc);
    }
}