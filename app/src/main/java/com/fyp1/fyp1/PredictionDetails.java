package com.fyp1.fyp1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class PredictionDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prediction_details);

        TextView name_text = findViewById(R.id.event_name);
        TextView firstName_text = findViewById(R.id.fighter1_name);
        TextView lastName_text = findViewById(R.id.fighter2_name);

        Bundle bundle = getIntent().getExtras();

        String name = bundle.getString("Name");
        String firstName = bundle.getString("FirstName");
        String lastName = bundle.getString("LastName");

        name_text.setText(name);
        firstName_text.setText(firstName);
        lastName_text.setText(lastName);
    }
}