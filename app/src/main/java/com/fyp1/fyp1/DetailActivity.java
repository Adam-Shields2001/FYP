package com.fyp1.fyp1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        TextView firstName_text = findViewById(R.id.firstName_text);
        TextView lastName_text = findViewById(R.id.lastName_text);
        TextView nickname_text = findViewById(R.id.nickname_text);
        TextView wins_text = findViewById(R.id.wins_text);
        TextView losses_text = findViewById(R.id.losses_text);
        TextView draws_text = findViewById(R.id.draws_text);
        ImageView imageView = findViewById(R.id.poster_image);

        Bundle bundle = getIntent().getExtras();

        String firstName = bundle.getString("FirstName");
        String lastName = bundle.getString("LastName");
        String nickname = bundle.getString("Nickname");
        double wins = bundle.getDouble("Wins");
        double losses = bundle.getDouble("Losses");
        double draws = bundle.getDouble("Draws");

        firstName_text.setText(firstName);
        lastName_text.setText(lastName);
        nickname_text.setText(nickname);
        wins_text.setText(Double.toString(wins));
        losses_text.setText(Double.toString(losses));
        draws_text.setText(Double.toString(draws));
        imageView.setImageResource(R.drawable.ufc);
    }
}