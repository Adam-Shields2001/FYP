package com.fyp1.fyp1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.fyp1.fyp1.Adapters.EventAdapter;
import com.fyp1.fyp1.Adapters.UFCEventAdapter;
import com.fyp1.fyp1.models.Fight;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FightSelection extends AppCompatActivity implements UFCEventAdapter.OnItemClickListener {

    private RecyclerView recyclerView;
    private List<Fight> fightsList;
    private RequestQueue requestQueue;
    private UFCEventAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fight_selection);

        // RecyclerView
        recyclerView = findViewById(R.id.ufcRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new UFCEventAdapter(this, new ArrayList<>());
        recyclerView.setAdapter(adapter);

        // Initialize RequestQueue using VolleySingleton
        requestQueue = VolleySingleton.getmInstance(this).getRequestQueue();

        fightsList = new ArrayList<>();
        getEvents();
    }

    private void getEvents() {
        String url = "https://api.sportsdata.io/v3/mma/scores/json/Event/297?key=0f1ac9ba07694202970b5e87ebeb902b";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String name = response.getString("Name");
                            JSONArray fightsArray = response.getJSONArray("Fights");
                            for (int i = 0; i < fightsArray.length(); i++) {
                                JSONObject fightObj = fightsArray.getJSONObject(i);
                                JSONArray fightersArray = fightObj.getJSONArray("Fighters");
                                if (fightersArray.length() >= 2) {
                                    String firstName1 = fightersArray.getJSONObject(0).getString("FirstName");
                                    String lastName1 = fightersArray.getJSONObject(0).getString("LastName");
                                    String preFightWins1 = fightersArray.getJSONObject(0).getString("PreFightWins");
                                    String preFightLosses1 = fightersArray.getJSONObject(0).getString("PreFightLosses");
                                    String moneyLine1 = fightersArray.getJSONObject(0).getString("Moneyline");

                                    String firstName2 = fightersArray.getJSONObject(1).getString("FirstName");
                                    String lastName2 = fightersArray.getJSONObject(1).getString("LastName");
                                    String preFightWins2 = fightersArray.getJSONObject(1).getString("PreFightWins");
                                    String preFightLosses2 = fightersArray.getJSONObject(1).getString("PreFightLosses");
                                    String moneyLine2 = fightersArray.getJSONObject(1).getString("Moneyline");

                                    int rounds = 0;
                                    if (!fightObj.isNull("Rounds")) {
                                        rounds = fightObj.getInt("Rounds");
                                    }

                                    Fight f = new Fight(firstName1, lastName1, firstName2, lastName2, preFightWins1,
                                            preFightLosses1, preFightWins2, preFightLosses2, moneyLine1, moneyLine2, i);
                                    fightsList.add(f);
                                    Log.d("FightSelection", "Parsed fight: " + f.getFighters());
                                } else {
                                    Log.e("FightSelection", "Error parsing JSON: Not enough fighters in fight " + i);
                                }
                            }
                            adapter = new UFCEventAdapter(FightSelection.this, fightsList);
                            adapter.setOnItemClickListener(FightSelection.this);
                            recyclerView.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("FightSelection", "Error parsing JSON: " + e.getMessage());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(FightSelection.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(jsonObjectRequest);
    }


    @Override
    public void onItemClick(Fight fight) {
        Intent intent = new Intent(this, ScoringSystem.class);
        intent.putExtra("fight", fight);
        intent.putExtra("firstName1", fight.getFirstName());
        intent.putExtra("lastName1", fight.getLastName());
        intent.putExtra("firstName2", fight.getOpponentFirstName());
        intent.putExtra("lastName2", fight.getOpponentLastName());
        intent.putExtra("rounds", fight.getRounds());
        startActivity(intent);
    }
}



