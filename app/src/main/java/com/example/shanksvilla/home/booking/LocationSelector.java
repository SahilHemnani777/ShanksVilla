package com.example.shanksvilla.home.booking;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shanksvilla.R;
import com.example.shanksvilla.adapters.CardAdapter;

import java.util.ArrayList;

public class LocationSelector extends AppCompatActivity {
    private static final String TAG = "LocationSelector";
    private RecyclerView cardView;
    private CardAdapter cardAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_selector);
        ArrayList<String> names= new ArrayList<>();
        names.add("Kihim Beach");
        names.add("Pune");
        ArrayList<Boolean> isAvailable= new ArrayList<>();
        Intent intent = getIntent();
        if(intent==null){
            Log.d(TAG, "onCreate: intent is null");
        }
        Bundle bundle = intent.getExtras();
        if (bundle == null){
            Log.d(TAG, "onCreate: bundle is null");
        }
        isAvailable.add(bundle.getString("database1").equals("found"));
        isAvailable.add(bundle.getString("database2").equals("found"));

        cardView= findViewById(R.id.recyclerCardView);
        cardView.setLayoutManager(new LinearLayoutManager(this));
        cardAdapter = new CardAdapter(getLayoutInflater(), names, isAvailable);
        cardView.setAdapter(cardAdapter);
    }
}