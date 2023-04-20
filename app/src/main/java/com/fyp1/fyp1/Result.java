package com.fyp1.fyp1;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.List;

public class Result extends AppCompatActivity {

    ArrayList<BarEntry> round1Data = new ArrayList<>();
    ArrayList<BarEntry> round2Data = new ArrayList<>();
    ArrayList<BarEntry> round3Data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        getRound1Data();
        getRound2Data();
        getRound3Data();
    }

    private void getRound1Data() {
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();

        // Assuming that you have a collection called "fights" in your database
        CollectionReference fightsRef = firestore.collection("Fights");
        String fightId = "Edson Barboza vs Billy Quarantillo";

        // Query for the fight document with the given ID
        DocumentReference fightDocRef = fightsRef.document(fightId);

        fightDocRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    int redScore = documentSnapshot.getLong("Red - Round 1").intValue();
                    int blueScore = documentSnapshot.getLong("Blue - Round 1").intValue();
                    // Create new ArrayLists and add BarEntry objects to them
                    ArrayList<BarEntry> redRound1Data = new ArrayList<>();
                    redRound1Data.add(new BarEntry(1.6f, redScore));
                    ArrayList<BarEntry> blueRound1Data = new ArrayList<>();
                    blueRound1Data.add(new BarEntry(4.0f, blueScore));

                    // Set the new ArrayLists as the data for the bar chart
                    BarChart round1BarChart = findViewById(R.id.round1_barchart);
                    BarDataSet redRound1DataSet = new BarDataSet(redRound1Data, "Red Fighter");
                    redRound1DataSet.setColor(Color.RED);
                    BarDataSet blueRound1DataSet = new BarDataSet(blueRound1Data, "Blue Fighter");
                    blueRound1DataSet.setColor(Color.BLUE);
                    BarData round1BarData = new BarData(redRound1DataSet, blueRound1DataSet);
                    round1BarData.setBarWidth(1.6f);
                    round1BarChart.setData(round1BarData);
                    round1BarChart.getDescription().setEnabled(false);

                    YAxis leftAxis = round1BarChart.getAxisLeft();
                    YAxis rightAxis = round1BarChart.getAxisRight();
                    leftAxis.setAxisMinimum(0f);
                    leftAxis.setAxisMaximum(30f);
                    rightAxis.setAxisMinimum(0f);
                    rightAxis.setAxisMaximum(30f);
                    XAxis xAxis = round1BarChart.getXAxis();
                    xAxis.setAxisMinimum(0f);
                    xAxis.setAxisMaximum(5f);

                    // Notify the chart that the data has changed
                    round1BarChart.notifyDataSetChanged();
                    round1BarChart.invalidate();
                }
            }
        });
    }

    private void getRound2Data() {
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();

        // Assuming that you have a collection called "fights" in your database
        CollectionReference fightsRef = firestore.collection("Fights");
        String fightId = "Edson Barboza vs Billy Quarantillo";

        // Query for the fight document with the given ID
        DocumentReference fightDocRef = fightsRef.document(fightId);

        fightDocRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    int redScore = documentSnapshot.getLong("Red - Round 2").intValue();
                    int blueScore = documentSnapshot.getLong("Blue - Round 2").intValue();
                    // Create new ArrayLists and add BarEntry objects to them
                    ArrayList<BarEntry> redRound2Data = new ArrayList<>();
                    redRound2Data.add(new BarEntry(1.6f, redScore));
                    ArrayList<BarEntry> blueRound2Data = new ArrayList<>();
                    blueRound2Data.add(new BarEntry(4.0f, blueScore));

                    // Set the new ArrayLists as the data for the bar chart
                    BarChart round2BarChart = findViewById(R.id.round2_barchart);
                    BarDataSet redRound2DataSet = new BarDataSet(redRound2Data, "Red Fighter");
                    redRound2DataSet.setColor(Color.RED);
                    BarDataSet blueRound2DataSet = new BarDataSet(blueRound2Data, "Blue Fighter");
                    blueRound2DataSet.setColor(Color.BLUE);
                    BarData round2BarData = new BarData(redRound2DataSet, blueRound2DataSet);
                    round2BarData.setBarWidth(1.6f);
                    round2BarChart.setData(round2BarData);
                    round2BarChart.getDescription().setEnabled(false);

                    YAxis leftAxis = round2BarChart.getAxisLeft();
                    YAxis rightAxis = round2BarChart.getAxisRight();
                    leftAxis.setAxisMinimum(0f);
                    leftAxis.setAxisMaximum(30f);
                    rightAxis.setAxisMinimum(0f);
                    rightAxis.setAxisMaximum(30f);
                    XAxis xAxis = round2BarChart.getXAxis();
                    xAxis.setAxisMinimum(0f);
                    xAxis.setAxisMaximum(5f);

                    // Notify the chart that the data has changed
                    round2BarChart.notifyDataSetChanged();
                    round2BarChart.invalidate();
                }
            }
        });
    }

    private void getRound3Data() {
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();

        // Assuming that you have a collection called "fights" in your database
        CollectionReference fightsRef = firestore.collection("Fights");
        String fightId = "Edson Barboza vs Billy Quarantillo";

        // Query for the fight document with the given ID
        DocumentReference fightDocRef = fightsRef.document(fightId);

        fightDocRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    int redScore = documentSnapshot.getLong("Red - Round 3").intValue();
                    int blueScore = documentSnapshot.getLong("Blue - Round 3").intValue();
                    // Create new ArrayLists and add BarEntry objects to them
                    ArrayList<BarEntry> redRound3Data = new ArrayList<>();
                    redRound3Data.add(new BarEntry(1.6f, redScore));
                    ArrayList<BarEntry> blueRound3Data = new ArrayList<>();
                    blueRound3Data.add(new BarEntry(4.0f, blueScore));

                    // Set the new ArrayLists as the data for the bar chart
                    BarChart round3BarChart = findViewById(R.id.round3_barchart);
                    BarDataSet redRound3DataSet = new BarDataSet(redRound3Data, "Red Fighter");
                    redRound3DataSet.setColor(Color.RED);
                    BarDataSet blueRound3DataSet = new BarDataSet(blueRound3Data, "Blue Fighter");
                    blueRound3DataSet.setColor(Color.BLUE);
                    BarData round3BarData = new BarData(redRound3DataSet, blueRound3DataSet);
                    round3BarData.setBarWidth(1.6f);
                    round3BarChart.setData(round3BarData);
                    round3BarChart.getDescription().setEnabled(false);

                    YAxis leftAxis = round3BarChart.getAxisLeft();
                    YAxis rightAxis = round3BarChart.getAxisRight();
                    leftAxis.setAxisMinimum(0f);
                    leftAxis.setAxisMaximum(30f);
                    rightAxis.setAxisMinimum(0f);
                    rightAxis.setAxisMaximum(30f);
                    XAxis xAxis = round3BarChart.getXAxis();
                    xAxis.setAxisMinimum(0f);
                    xAxis.setAxisMaximum(5f);

                    // Notify the chart that the data has changed
                    round3BarChart.notifyDataSetChanged();
                    round3BarChart.invalidate();
                }
            }
        });
    }
}
