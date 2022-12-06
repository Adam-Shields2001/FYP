package com.fyp1.fyp1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Scoring extends AppCompatActivity {

    int head = 0;
    int body = 0;
    int leg = 0;
    int takedown = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoring);
    }

    public void head(View view) {
        head = head + 1;
        display(head);
    }

    public void body(View view) {
        body = body + 1;
        display(body);
    }

    public void leg(View view) {
        leg = leg + 1;
        display(leg);
    }

    public void takedown(View view) {
        takedown = takedown + 1;
        display(takedown);
    }

    public void display(int counter) {
        TextView headStrike = findViewById(R.id.btnheadStrike);
        headStrike.setText("Head Strikes = " + head);

        TextView bodyStrike = findViewById(R.id.btnbodyStrike);
        bodyStrike.setText("Body Strikes = " + body);

        TextView legStrike = findViewById(R.id.btnlegStrike);
        legStrike.setText("Leg Strikes = " + leg);

        TextView takedowns = findViewById(R.id.btnTakedown);
        takedowns.setText("Takedown = " + takedown);
    }
}