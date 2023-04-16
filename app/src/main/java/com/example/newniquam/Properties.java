package com.example.newniquam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Properties extends AppCompatActivity {

    FirebaseAuth auth;
    Button button,button_Hum,button_Temp;
    TextView textView,hum,temp;
    FirebaseUser user;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference referenceInput1 = database.getReference("READING");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_properties);

        auth = FirebaseAuth.getInstance();
        button = findViewById(R.id.logout);
        button_Hum = findViewById(R.id.graph_hum);
        button_Temp = findViewById(R.id.graph_temp);
        hum = findViewById(R.id.hum);
        temp = findViewById(R.id.temp);
        textView = findViewById(R.id.user_details);
        user = auth.getCurrentUser();
        if (user == null){
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            finish();
        }
        else {
            textView.setText(user.getEmail());
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
            }
        });
        referenceInput1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String T = snapshot.child("TEMPERATURE").getValue().toString();
                temp.setText(T);
                String H = snapshot.child("HUMIDITY").getValue().toString();
                hum.setText(H);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        button_Hum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Humidity_Graph.class);
                startActivity(intent);
                finish();
            }
        });

        button_Temp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Humidity_Graph.class);
                startActivity(intent);
                finish();
            }
        });
    }
}