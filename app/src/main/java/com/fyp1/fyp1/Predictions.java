package com.fyp1.fyp1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.fyp1.fyp1.Adapters.EventAdapter;
import com.fyp1.fyp1.Adapters.FightAdapter;
import com.fyp1.fyp1.models.EventsModel;
import com.fyp1.fyp1.models.FightModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Predictions extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RequestQueue requestQueue;
    private List<FightModel> fightList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_predictions);

        recyclerView = findViewById(R.id.recyclerViewPredictions);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        requestQueue = VolleySingleton.getmInstance(this).getRequestQueue();

        fightList = new ArrayList<>();
        fetchFights();
    }

    private void fetchFights() {

        String url = "https://api.sportsdata.io/v3/mma/scores/json/Event/285?key=0f1ac9ba07694202970b5e87ebeb902b";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        for (int i = 0 ; i < response.length() ; i ++){
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                String shortName = jsonObject.getString("ShortName");
                                String name = jsonObject.getString("Name");
                                String firstName = jsonObject.getString("FirstName");
                                String lastName = jsonObject.getString("LastName");
                                Double wins = jsonObject.getDouble("PreFightWins");
                                Double losses = jsonObject.getDouble("PreFightLosses");
                                Double draws = jsonObject.getDouble("PreFightDraws");
                                Double noContests = jsonObject.getDouble("PreFightNoContests");
                                Double moneyline = jsonObject.getDouble("Moneyline");

                                //FightModel fm = new FightModel(shortName, name, firstName, lastName, wins, losses, draws, noContests, moneyline);
                                //fightList.add(fm);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            FightAdapter adapter = new FightAdapter(Predictions.this , fightList);

                            recyclerView.setAdapter(adapter);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Predictions.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(jsonArrayRequest);
    }
}