package com.example.newniquam;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Humidity_Graph extends AppCompatActivity {
    Button button_props;
DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("READING/hum");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_humidity_graph);

        button_props = findViewById(R.id.props);

        button_props.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Properties.class);
                startActivity(intent);
                finish();
            }
        });




        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                List<Entry> entries = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    int xValue = Integer.parseInt(snapshot.getKey());
                    int yValue = snapshot.getValue(Integer.class);
                    entries.add(new Entry(xValue, yValue));
                }
                LineDataSet lineDataSet = new LineDataSet(entries, "Dataset 1");
                LineData lineData = new LineData(lineDataSet);
                Chart<LineData> lineChart = null;
                assert false;
                lineChart.setData(lineData);
                lineChart.invalidate(); // refresh the chart
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // This method is called if the listener fails to receive data for any reason
                Log.w(TAG, "Failed to read value.", databaseError.toException());
            }
        };
        databaseReference.addValueEventListener(valueEventListener);




    }
}

