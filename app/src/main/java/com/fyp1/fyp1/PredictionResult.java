package com.fyp1.fyp1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.fyp1.fyp1.models.PredictionsModel;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
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
import java.util.Arrays;
import java.util.List;

public class PredictionResult extends AppCompatActivity {

    private PieChart mFighterChart;
    private BarChart mMethodChart;
    private BarChart mRoundChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prediction_result);

        mFighterChart = findViewById(R.id.fighter_chart);
        mMethodChart = findViewById(R.id.method_chart);
        mRoundChart = findViewById(R.id.round_chart);

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

                            if (prediction.getRound().equals("1")) {
                                round1Count++;
                            } else if (prediction.getRound().equals("2")) {
                                round2Count++;
                            } else if (prediction.getRound().equals("3")) {
                                round3Count++;
                            }
                        }

                        LegendEntry sterlingEntry = new LegendEntry();
                        sterlingEntry.label = "Sterling";
                        sterlingEntry.formColor = Color.BLUE;

                        LegendEntry cejudoEntry = new LegendEntry();
                        cejudoEntry.label = "Cejudo";
                        cejudoEntry.formColor = Color.RED;

                        List<PieEntry> fighterEntries = new ArrayList<>();
                        fighterEntries.add(new PieEntry(sterlingCount, "", sterlingEntry));
                        fighterEntries.add(new PieEntry(cejudoCount, "", cejudoEntry));

                        PieDataSet fighterDataSet = new PieDataSet(fighterEntries, "");

                        fighterDataSet.setColors(Color.BLUE, Color.RED);

                        PieData fighterData = new PieData(fighterDataSet);
                        fighterData.setValueFormatter(new PercentFormatter());

                        mFighterChart.setData(fighterData);

                        mFighterChart.getDescription().setText("");

                        Legend customLegend = mFighterChart.getLegend();
                        customLegend.setCustom(Arrays.asList(sterlingEntry, cejudoEntry));
                        customLegend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
                        customLegend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
                        customLegend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
                        customLegend.setDrawInside(false);
                        customLegend.setWordWrapEnabled(true);
                        customLegend.setXEntrySpace(50f);

                        mFighterChart.setHoleRadius(40f);
                        mFighterChart.setTransparentCircleRadius(45f);

                        mFighterChart.invalidate();

                        List<BarEntry> methodEntries = new ArrayList<>();
                        methodEntries.add(new BarEntry(0, knockoutCount));
                        methodEntries.add(new BarEntry(1, submissionCount));
                        methodEntries.add(new BarEntry(2, decisionCount));

                        BarDataSet methodDataSet = new BarDataSet(methodEntries, "Method of Victory");
                        methodDataSet.setColors(Color.GREEN, Color.YELLOW, Color.GRAY);
                        methodDataSet.setBarBorderColor(Color.BLACK);
                        methodDataSet.setBarBorderWidth(1f);

                        BarData methodData = new BarData(methodDataSet);
                        methodData.setValueFormatter(new ValueFormatter() {
                            @Override
                            public String getFormattedValue(float value) {
                                return String.valueOf((int) value);
                            }
                        });

                        mMethodChart.setData(methodData);

                        mMethodChart.getDescription().setText("");

                        final String[] xLabels = new String[]{"Knockout", "Submission", "Decision"};
                        XAxis xAxis = mMethodChart.getXAxis();
                        xAxis.setValueFormatter(new IndexAxisValueFormatter(xLabels));
                        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                        xAxis.setDrawGridLines(false);
                        xAxis.setDrawAxisLine(true);
                        xAxis.setGranularity(1f);
                        xAxis.setLabelCount(xLabels.length);

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

                        mMethodChart.getAxisRight().setEnabled(false);

                        Legend methodLegend = mMethodChart.getLegend();
                        methodLegend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
                        methodLegend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
                        methodLegend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
                        methodLegend.setDrawInside(false);
                        methodLegend.setWordWrapEnabled(true);

                        mMethodChart.invalidate();

                        List<BarEntry> roundEntries = new ArrayList<>();
                        roundEntries.add(new BarEntry(0, round1Count));
                        roundEntries.add(new BarEntry(1, round2Count));
                        roundEntries.add(new BarEntry(2, round3Count));

                        BarDataSet roundDataSet = new BarDataSet(roundEntries, "Rounds");
                        roundDataSet.setColors(Color.GREEN, Color.YELLOW, Color.GRAY);
                        roundDataSet.setBarBorderColor(Color.BLACK);
                        roundDataSet.setBarBorderWidth(1f);

                        BarData roundData = new BarData(roundDataSet);
                        roundData.setValueFormatter(new ValueFormatter() {
                            @Override
                            public String getFormattedValue(float value) {
                                return String.valueOf((int) value);
                            }
                        });

                        mRoundChart.setData(roundData);

                        mRoundChart.getDescription().setText("");

                        final String[] roundLabels = new String[]{"Round 1", "Round 2", "Round 3"};
                        XAxis roundXAxis = mRoundChart.getXAxis();
                        roundXAxis.setValueFormatter(new IndexAxisValueFormatter(roundLabels));
                        roundXAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                        roundXAxis.setDrawGridLines(false);
                        roundXAxis.setDrawAxisLine(true);
                        roundXAxis.setGranularity(1f);
                        roundXAxis.setLabelCount(roundLabels.length);

                        YAxis roundYAxis = mRoundChart.getAxisLeft();
                        roundYAxis.setValueFormatter(new ValueFormatter() {
                            @Override
                            public String getFormattedValue(float value) {
                                return String.valueOf((int) value);
                            }
                        });
                        roundYAxis.setDrawGridLines(false);
                        roundYAxis.setGranularity(1f);
                        roundYAxis.setAxisMinimum(0f);

                        mRoundChart.getAxisRight().setEnabled(false);

                        Legend roundLegend = mRoundChart.getLegend();
                        roundLegend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
                        roundLegend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
                        roundLegend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
                        roundLegend.setDrawInside(false);
                        roundLegend.setWordWrapEnabled(true);

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
