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

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;

public class Result extends AppCompatActivity {

//    ArrayList barArraylist;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_result);
//
//        BarChart barChart = findViewById(R.id.barchart);
//        getData();
//        BarDataSet barDataSet = new BarDataSet(barArraylist, "Pavlovich vs Blaydes");
//        BarData barData = new BarData(barDataSet);
//        barChart.setData(barData);
//        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
//        barDataSet.setValueTextColor(Color.BLACK);
//        barDataSet.setValueTextSize(16f);
//        barChart.getDescription().setEnabled(true);
//    }
//
//    private void getData() {
//        barArraylist = new ArrayList();
//        barArraylist.add(new BarEntry(2f, 10));
//        barArraylist.add(new BarEntry(3f, 20));
//        barArraylist.add(new BarEntry(4f, 30));
//        barArraylist.add(new BarEntry(5f, 40));
//        barArraylist.add(new BarEntry(6f, 50));
//    }

    ArrayList<BarEntry> round1Data = new ArrayList<>();
    ArrayList<BarEntry> round2Data = new ArrayList<>();
    ArrayList<BarEntry> round3Data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        // Round 1
        BarChart round1BarChart = findViewById(R.id.round1_barchart);
        getRound1Data();
        BarDataSet redRound1DataSet = new BarDataSet(round1Data.subList(0, 1), "Red Fighter");
        redRound1DataSet.setColor(Color.RED);
        BarDataSet blueRound1DataSet = new BarDataSet(round1Data.subList(1, 2), "Blue Fighter");
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

        // Round 2
        BarChart round2BarChart = findViewById(R.id.round2_barchart);
        getRound2Data();
        BarDataSet redRound2DataSet = new BarDataSet(round2Data.subList(0, 1), "Red Fighter");
        redRound2DataSet.setColor(Color.RED);
        BarDataSet blueRound2DataSet = new BarDataSet(round2Data.subList(1, 2), "Blue Fighter");
        blueRound2DataSet.setColor(Color.BLUE);
        BarData round2BarData = new BarData(redRound2DataSet, blueRound2DataSet);
        round2BarData.setBarWidth(1.6f);
        round2BarChart.setData(round2BarData);
        round2BarChart.getDescription().setEnabled(false);

        leftAxis = round2BarChart.getAxisLeft();
        rightAxis = round2BarChart.getAxisRight();
        leftAxis.setAxisMinimum(0f);
        leftAxis.setAxisMaximum(30f);
        rightAxis.setAxisMinimum(0f);
        rightAxis.setAxisMaximum(30f);
        xAxis = round2BarChart.getXAxis();
        xAxis.setAxisMinimum(0f);
        xAxis.setAxisMaximum(5f);

        // Round 3
        BarChart round3BarChart = findViewById(R.id.round3_barchart);
        getRound3Data();
        BarDataSet redRound3DataSet = new BarDataSet(round3Data.subList(0, 1), "Red Fighter");
        redRound3DataSet.setColor(Color.RED);
        BarDataSet blueRound3DataSet = new BarDataSet(round3Data.subList(1, 2), "Blue Fighter");
        blueRound3DataSet.setColor(Color.BLUE);
        BarData round3BarData = new BarData(redRound3DataSet, blueRound3DataSet);
        round3BarData.setBarWidth(1.6f);
        round3BarChart.setData(round3BarData);
        round3BarChart.getDescription().setEnabled(false);

        leftAxis = round3BarChart.getAxisLeft();
        rightAxis = round3BarChart.getAxisRight();
        leftAxis.setAxisMinimum(0f);
        rightAxis.setAxisMinimum(0f);
        xAxis = round3BarChart.getXAxis();
        xAxis.setAxisMinimum(0f);
        xAxis.setAxisMaximum(5f);
    }

    private void getRound1Data() {
        round1Data.add(new BarEntry(1.6f, 15));
        round1Data.add(new BarEntry(4.0f, 20));
    }

    private void getRound2Data() {
        round2Data.add(new BarEntry(1.6f, 20));
        round2Data.add(new BarEntry(4.0f, 25));
    }

    private void getRound3Data() {
        round3Data.add(new BarEntry(1.6f, 25));
        round3Data.add(new BarEntry(4.0f, 30));
    }
}
