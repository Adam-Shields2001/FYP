package com.fyp1.fyp1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.fyp1.fyp1.Adapters.FighterAdapter;
import com.fyp1.fyp1.models.FightersModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ViewFighters extends AppCompatActivity {

    private List<FightersModel> fighterList;
    private RecyclerView recyclerView;
    private RequestQueue requestQueue;
    private SearchView searchView;
    private FighterAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_fighters);

        searchView = findViewById(R.id.searchView);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }
        });
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        requestQueue = VolleySingleton.getmInstance(this).getRequestQueue();

        fighterList = new ArrayList<>();
        fetchFighters();
    }

    private void fetchFighters() {
        String url = "https://api.sportsdata.io/v3/mma/scores/json/Fighters?key=0f1ac9ba07694202970b5e87ebeb902b";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        for (int i = 0 ; i < response.length() ; i ++){
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                String firstName = jsonObject.getString("FirstName");
                                String lastName = jsonObject.getString("LastName");
                                String nickname = jsonObject.getString("Nickname");
                                Double wins = jsonObject.getDouble("Wins");
                                Double losses = jsonObject.getDouble("Losses");
                                Double draws = jsonObject.getDouble("Draws");

                                FightersModel fm = new FightersModel(firstName, lastName, nickname, wins, losses, draws);
                                fighterList.add(fm);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            adapter = new FighterAdapter(ViewFighters.this , fighterList);

                            recyclerView.setAdapter(adapter);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ViewFighters.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(jsonArrayRequest);
    }

    private void filterList(String text) {
        List<FightersModel> filteredList = new ArrayList<>();
        for (FightersModel fighter : fighterList) {
            if(fighter.getFirstName().toLowerCase().contains(text.toLowerCase()) || fighter.getLastName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(fighter);
            }
        }

        if(filteredList.isEmpty()){
            Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show();
        } else {
            adapter.setFilteredList(filteredList);
        }
    }
}