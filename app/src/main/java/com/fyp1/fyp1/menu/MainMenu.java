package com.fyp1.fyp1.menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.fyp1.fyp1.Profile;
import com.fyp1.fyp1.R;
import com.fyp1.fyp1.ScoringSystem;
import com.fyp1.fyp1.Settings;

public class MainMenu extends AppCompatActivity {

    CardView cardHome, cardSearch, cardProfile, cardExplore, cardSettings, cardLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        cardHome = (CardView) findViewById(R.id.cardHome);
        cardSearch = (CardView) findViewById(R.id.cardSearch);
        cardProfile = (CardView) findViewById(R.id.cardProfile);
        cardExplore = (CardView) findViewById(R.id.cardExplore);
        cardSettings = (CardView) findViewById(R.id.cardSettings);
        cardLogout = (CardView) findViewById(R.id.cardLogout);

        cardHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showToast("Scoring Game Clicked");
                openScoring();
            }
        });
        cardSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showToast("Search Clicked");
            }
        });
        cardProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showToast("Profile Clicked");
                openProfile();
            }
        });
        cardExplore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showToast("Explore Clicked");
            }
        });
        cardSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showToast("Settings Clicked");
                openSettings();
            }
        });
        cardLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showToast("Logout Clicked");
            }
        });
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void openProfile() {
        Intent intent = new Intent(this, Profile.class);
        startActivity(intent);
    }

    public void openScoring() {
        Intent intent = new Intent(this, ScoringSystem.class);
        startActivity(intent);
    }

    public void openSettings() {
        Intent intent = new Intent(this, Settings.class);
        startActivity(intent);
    }
}