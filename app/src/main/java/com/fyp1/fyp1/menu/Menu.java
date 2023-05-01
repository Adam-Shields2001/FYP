package com.fyp1.fyp1.menu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.fyp1.fyp1.ChatRoom;
import com.fyp1.fyp1.FightSelection;
import com.fyp1.fyp1.Login;
import com.fyp1.fyp1.PredictionDetails;
import com.fyp1.fyp1.R;
import com.fyp1.fyp1.Settings;
import com.fyp1.fyp1.SliderAdapter;
import com.fyp1.fyp1.ViewEvents;
import com.fyp1.fyp1.ViewFighters;
import com.google.android.material.navigation.NavigationView;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

public class Menu extends AppCompatActivity {

    SliderView sliderView;
    int[] images = {R.drawable.mcgregor,
            R.drawable.khabibvconor,
            R.drawable.gsp,
            R.drawable.adesanya,
            R.drawable.max};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        final DrawerLayout drawerLayout = findViewById(R.id.drawerLayout);

        findViewById(R.id.imageMenu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        NavigationView navigationView = findViewById(R.id.navigationView);
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if(id == R.id.nav_home) {
                    startActivity(new Intent(Menu.this, Menu.class));
                } else if (id == R.id.nav_scoring) {
                    startActivity(new Intent(Menu.this, FightSelection.class));
                } else if (id == R.id.nav_predictions) {
                    startActivity(new Intent(Menu.this, PredictionDetails.class));
                } else if (id == R.id.nav_fighters) {
                    startActivity(new Intent(Menu.this, ViewFighters.class));
                } else if (id == R.id.nav_events) {
                    startActivity(new Intent(Menu.this, ViewEvents.class));
                }else if (id == R.id.nav_share) {
                    startActivity(new Intent(Menu.this, ChatRoom.class));
                } else if (id == R.id.nav_settings) {
                    startActivity(new Intent(Menu.this, Settings.class));
                } else if (id == R.id.nav_logout) {
                    startActivity(new Intent(Menu.this, Login.class));
                }
                drawerLayout.closeDrawers();
                return true;
            }
        });

        sliderView = findViewById(R.id.image_slider);

        SliderAdapter sliderAdapter = new SliderAdapter(images);

        sliderView.setSliderAdapter(sliderAdapter);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
        sliderView.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION);
        sliderView.startAutoCycle();
    }
}