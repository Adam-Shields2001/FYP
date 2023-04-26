package com.fyp1.fyp1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.SearchView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.fyp1.fyp1.Adapters.PredictionAdapter;
import com.fyp1.fyp1.models.FightModel;
import com.fyp1.fyp1.models.PredictionModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UFC_Prediction extends AppCompatActivity {

    private List<PredictionModel> list;
    private RecyclerView recyclerView;
    private RequestQueue requestQueue;
    private PredictionAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ufc_prediction);

        recyclerView = findViewById(R.id.rv);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        requestQueue = VolleySingleton.getmInstance(this).getRequestQueue();

        list = new ArrayList<>();
        fetchEvent();
    }

    private void fetchEvent() {
        String url = "https://api.sportsdata.io/v3/mma/scores/json/Event/297?key=0f1ac9ba07694202970b5e87ebeb902b";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0 ; i < response.length() ; i ++){
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                String name = jsonObject.getString("Name");
                                String firstName = jsonObject.getString("FirstName");
                                String lastName = jsonObject.getString("LastName");
                                String wins = jsonObject.getString("PreFightWins");
                                String losses = jsonObject.getString("PreFightLosses");
                                String moneyline = jsonObject.getString("Moneyline");

                                PredictionModel pm = new PredictionModel(name, firstName, lastName, wins, losses, moneyline);
                                list.add(pm);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            adapter = new PredictionAdapter(UFC_Prediction.this , list);

                            recyclerView.setAdapter(adapter);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(UFC_Prediction.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(jsonArrayRequest);
    }
}