package com.example.shanksvilla.home.booking;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

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

    private Button buttonK, buttonP, searchButton;
    private FrameLayout frameLayout;
    private TextView error_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_selector);
        ArrayList<String> names = new ArrayList<>();
        names.add("Kihim Beach");
        names.add("Pune");
        ArrayList<Boolean> isAvailable = new ArrayList<>();
        Intent intent = getIntent();
        if (intent == null) {
            Log.d(TAG, "onCreate: intent is null");
        }
        Bundle bundle = intent.getExtras();
        if (bundle == null) {
            Log.d(TAG, "onCreate: bundle is null");
        }
        isAvailable.add(bundle.getString("database1").equals("found"));
        isAvailable.add(bundle.getString("database2").equals("found"));


        cardView = findViewById(R.id.recyclerCardView);
        cardView.setLayoutManager(new LinearLayoutManager(this));
        cardAdapter = new CardAdapter(getLayoutInflater(), names, isAvailable);
        cardView.setAdapter(cardAdapter);

        buttonK = findViewById(R.id.buttonK);
        buttonP = findViewById(R.id.buttonP);
        searchButton = findViewById(R.id.buttonSearch);
        error_text = findViewById(R.id.error_text);
        frameLayout = findViewById(R.id.frame_not_found);

        if (!bundle.getString("database1").equals("found")) buttonK.setVisibility(View.GONE);
        if (!bundle.getString("database2").equals("found")) buttonP.setVisibility(View.GONE);

        if (!bundle.getString("database1").equals("found") && !bundle.getString("database2").equals("found")) {
            searchButton.setVisibility(View.VISIBLE);
            error_text.setVisibility(View.VISIBLE);
            frameLayout.setVisibility(View.VISIBLE);
            searchButton.setOnClickListener(v -> {
                startActivity(new Intent(LocationSelector.this, BookingActivity.class));
                finish();
            });
        }
        buttonK.setOnClickListener(v -> {
            Intent intent1 = new Intent(LocationSelector.this, DetailsActivity.class);
            intent1.putExtra("location", "kihim");
            intent1.putExtra("people", bundle.getInt("people"));
            intent1.putExtra("dates", bundle.getIntegerArrayList("dates"));
            startActivity(intent1);
            finish();
        });
        buttonP.setOnClickListener(v -> {
            Intent intent12 = new Intent(LocationSelector.this, DetailsActivity.class);
            intent12.putExtra("location", "pune");
            intent12.putExtra("people", bundle.getInt("people"));
            intent12.putExtra("dates", bundle.getIntegerArrayList("dates"));
            startActivity(intent12);
            finish();
        });
    }
}