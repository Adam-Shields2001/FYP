package com.fyp1.fyp1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import com.fyp1.fyp1.models.PredictionsModel;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IPieDataSet;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class PredictionResult extends AppCompatActivity {

    private PieChart mFighterChart;
    private BarChart mMethodChart;
    private BarChart mRoundChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prediction_result);

        // Initialize views
        mFighterChart = findViewById(R.id.fighter_chart);
        mMethodChart = findViewById(R.id.method_chart);
        mRoundChart = findViewById(R.id.round_chart);

        // Get the number of users who selected each fighter, method of victory and round from your Firestore database
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Predictions")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        int sterlingCount = 0;
                        int cejudoCount = 0;
                        int knockoutCount = 0;
                        int submissionCount = 0;
                        int decisionCount = 0;
                        int round1Count = 0;
                        int round2Count = 0;
                        int round3Count = 0;

                        // Loop through each prediction and count the number of users who selected each fighter, method of victory and round
                        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                            PredictionsModel prediction = document.toObject(PredictionsModel.class);

                            if (prediction.getFighter().equals("Sterling")) {
                                sterlingCount++;
                            } else if (prediction.getFighter().equals("Cejudo")) {
                                cejudoCount++;
                            }

                            if (prediction.getMethod().equals("Knockout")) {
                                knockoutCount++;
                            } else if (prediction.getMethod().equals("Submission")) {
                                submissionCount++;
                            } else if (prediction.getMethod().equals("Decision") && !prediction.getRound().equals("Decision")) {
                                decisionCount++;
                            }

                            if (prediction.getRound().equals("Round 1")) {
                                round1Count++;
                            } else if (prediction.getRound().equals("Round 2")) {
                                round2Count++;
                            } else if (prediction.getRound().equals("Round 3")) {
                                round3Count++;
                            }
                        }

                        // Create a PieEntry list for each fighter
                        List<PieEntry> fighterEntries = new ArrayList<>();
                        fighterEntries.add(new PieEntry(sterlingCount, "Sterling"));
                        fighterEntries.add(new PieEntry(cejudoCount, "Cejudo"));

                        // Create a PieDataSet for the fighters
                        PieDataSet fighterDataSet = new PieDataSet(fighterEntries, "");

                        // Set the colors for each PieDataSet
                        fighterDataSet.setColors(Color.BLUE, Color.RED);

                        // Set the value format for the PieData object to percent
                        PieData fighterData = new PieData(fighterDataSet);
                        fighterData.setValueFormatter(new PercentFormatter());

                        // Set the data for the PieChart
                        mFighterChart.setData(fighterData);

                        // Set the description text for the PieChart
                        mFighterChart.getDescription().setText("Number of users who selected each fighter");

                        // Refresh the PieChart
                        mFighterChart.invalidate();

                        // Create a BarEntry list for each method of victory
                        List<BarEntry> methodEntries = new ArrayList<>();
                        methodEntries.add(new BarEntry(0, knockoutCount));
                        methodEntries.add(new BarEntry(1, submissionCount));
                        methodEntries.add(new BarEntry(2, decisionCount));

                        // Create a BarDataSet for the methods of victory
                        BarDataSet methodDataSet = new BarDataSet(methodEntries, "Method of Victory");
                        methodDataSet.setColors(Color.GREEN, Color.YELLOW, Color.GRAY);
                        methodDataSet.setBarBorderColor(Color.BLACK);
                        methodDataSet.setBarBorderWidth(1f);

                        // Set the value format for the BarData object to whole numbers
                        BarData methodData = new BarData(methodDataSet);
                        methodData.setValueFormatter(new ValueFormatter() {
                            @Override
                            public String getFormattedValue(float value) {
                                return String.valueOf((int) value);
                            }
                        });

                        // Set the data for the BarChart
                        mMethodChart.setData(methodData);

                        // Set the description text for the BarChart
                        mMethodChart.getDescription().setText("Number of users who selected each method of victory");

                        // Set the x-axis labels for the BarChart
                        final String[] xLabels = new String[]{"Knockout", "Submission", "Decision"};
                        XAxis xAxis = mMethodChart.getXAxis();
                        xAxis.setValueFormatter(new IndexAxisValueFormatter(xLabels));
                        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                        xAxis.setDrawGridLines(false);
                        xAxis.setDrawAxisLine(true);
                        xAxis.setGranularity(1f);
                        xAxis.setLabelCount(xLabels.length);

                        // Set the y-axis labels for the BarChart
                        YAxis yAxis = mMethodChart.getAxisLeft();
                        yAxis.setValueFormatter(new ValueFormatter() {
                            @Override
                            public String getFormattedValue(float value) {
                                return String.valueOf((int) value);
                            }
                        });
                        yAxis.setDrawGridLines(false);
                        yAxis.setGranularity(1f);
                        yAxis.setAxisMinimum(0f);

                        // Disable the right y-axis
                        mMethodChart.getAxisRight().setEnabled(false);

                        // Refresh the BarChart
                        mMethodChart.invalidate();

                        // Create a BarEntry list for each round
                        List<BarEntry> roundEntries = new ArrayList<>();
                        roundEntries.add(new BarEntry(0, round1Count));
                        roundEntries.add(new BarEntry(1, round2Count));
                        roundEntries.add(new BarEntry(2, round3Count));

                        // Create a BarDataSet for the methods of victory
                        BarDataSet roundDataSet = new BarDataSet(methodEntries, "Rounds");
                        methodDataSet.setColors(Color.GREEN, Color.YELLOW, Color.GRAY);
                        methodDataSet.setBarBorderColor(Color.BLACK);
                        methodDataSet.setBarBorderWidth(1f);

                        // Set the value format for the BarData object to whole numbers
                        BarData roundData = new BarData(roundDataSet);
                        roundData.setValueFormatter(new ValueFormatter() {
                            @Override
                            public String getFormattedValue(float value) {
                                return String.valueOf((int) value);
                            }
                        });

                        // Set the data for the BarChart
                        mRoundChart.setData(roundData);

                        // Set the description text for the BarChart
                        mRoundChart.getDescription().setText("Number of users who selected each round");

                        // Set the x-axis labels for the BarChart
                        final String[] roundLabels = new String[]{"Round 1", "Round 2", "Round 3"};
                        XAxis roundXAxis = mRoundChart.getXAxis();
                        roundXAxis.setValueFormatter(new IndexAxisValueFormatter(roundLabels));
                        roundXAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

                        // Refresh the BarChart
                        mRoundChart.invalidate();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(PredictionResult.this, "Error retrieving predictions", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
