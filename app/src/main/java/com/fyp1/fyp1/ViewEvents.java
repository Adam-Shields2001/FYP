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
import com.fyp1.fyp1.models.EventsModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ViewEvents extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RequestQueue requestQueue;
    private List<EventsModel> eventsList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_events);

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        requestQueue = VolleySingleton.getmInstance(this).getRequestQueue();

        eventsList = new ArrayList<>();
        fetchEvents();
    }

    private void fetchEvents() {

        String url = "https://api.sportsdata.io/v3/mma/scores/json/Schedule/UFC/2023?key=0f1ac9ba07694202970b5e87ebeb902b";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        for (int i = 0 ; i < response.length() ; i ++){
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                String shortName = jsonObject.getString("ShortName");
                                String name = jsonObject.getString("Name");
                                String season = jsonObject.getString("Season");
                                String dateTime = jsonObject.getString("DateTime");

                                EventsModel em = new EventsModel(shortName, name, season, dateTime);
                                eventsList.add(em);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            EventAdapter adapter = new EventAdapter(ViewEvents.this , eventsList);

                            recyclerView.setAdapter(adapter);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ViewEvents.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(jsonArrayRequest);
    }
}