package com.stem.relief;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Locale;

public class SleepCycle extends Activity {

    FirebaseDatabase database;
    DatabaseReference myRef;

    ArrayList<Entry> dataVals = new ArrayList<Entry>();
    ArrayList<ILineDataSet> iLineDataSets = new ArrayList<ILineDataSet>();

    TextView hours;
    TextView mins;
    Button btnInsert;

    LineChart lineChart;
    LineData lineData;
    LineDataSet lineDataSet = new LineDataSet(null, null);

    int counter;
    float sum;

    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.sleep_cycle);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();

        ConnectivityManager cm = (ConnectivityManager) getApplication().getSystemService(Context.CONNECTIVITY_SERVICE);
        assert cm != null;
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (!(netInfo != null && netInfo.isConnectedOrConnecting())) { Toast.makeText(getApplicationContext(), "Please, Check your connection", Toast.LENGTH_SHORT).show();}

        hours = findViewById(R.id.hours);
        mins = findViewById(R.id.mins);
        lineChart = (LineChart) findViewById(R.id.bar_chart);
        btnInsert = findViewById(R.id.btn_insert);

        myRef.child(mAuth.getCurrentUser().getUid()).child("sleep_duration").child("records").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                DataPoint dataPoint = dataSnapshot.getValue(DataPoint.class);
                dataVals.add(new Entry(dataPoint.getxValue(), dataPoint.getyValue()));
                showChart(dataVals);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) { }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) { }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) { }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });

        btnInsert.setOnClickListener(new View.OnClickListener() {
            public void onClick(View param1View) {
                final String id = myRef.child(mAuth.getCurrentUser().getUid()).child("sleep_duration").child("records").push().getKey();

                    myRef.child(mAuth.getCurrentUser().getUid()).child("sleep_duration").child("counter").addListenerForSingleValueEvent(new ValueEventListener() {
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                        }

                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            try{counter = Integer.parseInt(dataSnapshot.getValue().toString());
                            }catch (Exception e){
                                myRef.child(mAuth.getCurrentUser().getUid()).child("sleep_duration").child("counter").setValue("0");
                                counter = 0;
                            }

                            sum = Float.parseFloat(hours.getText().toString()) + Float.parseFloat(mins.getText().toString()) / 60;
                            DataPoint dataPoint = new DataPoint(counter, sum);
                            myRef.child(mAuth.getCurrentUser().getUid()).child("sleep_duration").child("records").child(id).setValue(dataPoint);
                            dataVals.add(new Entry(dataPoint.getxValue(), dataPoint.getyValue()));

                            showChart(dataVals);

                            myRef.child(mAuth.getCurrentUser().getUid()).child("sleep_duration").child("counter").setValue(String.valueOf(counter + 1));
                            Toast.makeText(SleepCycle.this, "Added", Toast.LENGTH_SHORT).show();
                            recreate();
                        }
                    });
            }
        });
    }

    public void showChart(ArrayList<Entry> dataVals) {
        lineDataSet.setValues(dataVals);
        lineDataSet.setLabel("Sleep Duration");
        iLineDataSets.clear();
        iLineDataSets.add(lineDataSet);
        lineData = new LineData(iLineDataSets);
        lineChart.clear();
        lineChart.setData(lineData);
        lineChart.invalidate();
        lineChart.setDragEnabled(true);
        lineChart.setScaleEnabled(true);
        lineChart.setDrawGridBackground(false);
        lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        lineDataSet.setCubicIntensity(0.2F);
        lineDataSet.setDrawFilled(true);
        lineDataSet.setDrawCircles(false);
        lineDataSet.setLineWidth(1.8F);
        lineDataSet.setCircleRadius(4.0F);
        lineDataSet.setColor(Color.parseColor("#F9B7B8"));
        lineDataSet.setFillColor(Color.parseColor("#F9B7B8"));
        lineDataSet.setFillFormatter(new IFillFormatter() {
            public float getFillLinePosition(ILineDataSet param1ILineDataSet, LineDataProvider param1LineDataProvider) {
                return lineChart.getAxisLeft().getAxisMinimum();
            }
        });
        lineDataSet.setLineWidth(6.0F);
        lineChart.getDescription().setEnabled(false);
        lineChart.setTouchEnabled(true);
        lineChart.setDrawGridBackground(false);
        lineChart.getXAxis().setEnabled(false);
        YAxis yAxis = lineChart.getAxisLeft();
        yAxis.setLabelCount(6, false);
        yAxis.setTextColor(Color.parseColor("#ffffff"));
        yAxis.setTextSize(14.0F);
        yAxis.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
        yAxis.setDrawGridLines(false);
        yAxis.setAxisLineColor(Color.parseColor("#ffffff"));
        yAxis.setAxisLineWidth(4.0F);
        lineChart.getAxisRight().setEnabled(false);
    }

    public void increaseHours(View view) {
        if (Integer.parseInt(hours.getText().toString()) != 23) {
            hours.setText(String.format(Locale.US, "%02d", Integer.parseInt(hours.getText().toString()) + 1));
        }
    }

    public void increaseMins(View view) {
        if (Integer.parseInt(mins.getText().toString()) != 59) {
            mins.setText(String.format(Locale.US, "%02d", Integer.parseInt(mins.getText().toString()) + 1));
        }
    }

    public void decreaseHours(View view) {
        if (Integer.parseInt(hours.getText().toString()) != 0) {
            hours.setText(String.format(Locale.US, "%02d", Integer.parseInt(hours.getText().toString()) - 1));
        }
    }

    public void decreaseMins(View view) {
        if (Integer.parseInt(mins.getText().toString()) != 0) {
            mins.setText(String.format(Locale.US, "%02d", Integer.parseInt(mins.getText().toString()) - 1));
        }
    }
}