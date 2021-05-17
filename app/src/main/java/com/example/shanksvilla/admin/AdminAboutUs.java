package com.example.shanksvilla.admin;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.shanksvilla.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AdminAboutUs extends AppCompatActivity {
    private EditText tag1, tag2, news, price;
    private Button save_button;
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    DocumentReference docRef = firebaseFirestore.collection("details").document("info");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_about_us);
        tag1= findViewById(R.id.admin_tagline1);
        tag2= findViewById(R.id.admin_tagline2);
        news= findViewById(R.id.admin_news);
        price= findViewById(R.id.admin_price);
        save_button = findViewById(R.id.admin_save_button);

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                tag1.setText(task.getResult().get("tagline1").toString());
                tag2.setText(task.getResult().get("tagline2").toString());
                news.setText(task.getResult().get("news").toString());
                price.setText(task.getResult().get("price").toString());
            }
        });

        save_button.setOnClickListener(v_->{
            Map<String, String> docData = new HashMap<>();
            docData.put("tagline1", tag1.getText().toString());
            docData.put("tagline2", tag2.getText().toString());
            docData.put("news", news.getText().toString());
            docData.put("price", price.getText().toString());
            docRef.set(docData);
            Toast.makeText(this, "Changes saved", Toast.LENGTH_SHORT).show();
        });
    }
}