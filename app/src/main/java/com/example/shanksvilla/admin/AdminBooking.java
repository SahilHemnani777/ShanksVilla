package com.example.shanksvilla.admin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shanksvilla.R;
import com.example.shanksvilla.model.booking;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class AdminBooking extends AppCompatActivity {
    private RecyclerView bookings_view_list;
    private FirestoreRecyclerAdapter adapter;
    // Firebase Auth



//
//    private ImageView searchButton;
//    private EditText searchBar;
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    Query query = firebaseFirestore.collection("bookings-global/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_booking);

        bookings_view_list= findViewById(R.id.admin_booking_list_view);






        //Query
        query = firebaseFirestore.collection("bookings-global/");

        //RecyclerOptions
        FirestoreRecyclerOptions<booking> options = new FirestoreRecyclerOptions.Builder<booking>()
                .setQuery(query, booking.class).build();

        //Adapter
        adapter = new FirestoreRecyclerAdapter<booking, BookingsViewHolder>(options) {
            @NonNull
            @Override
            public BookingsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_booking_item, parent, false );
                return new BookingsViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull BookingsViewHolder holder, int position, @NonNull booking model) {
                holder.id.setText(model.getBookingId());
                holder.sdate.setText(convertDate(model.getDateFrom()));
                holder.eDate.setText(convertDate(model.getDateTo()));
                holder.details.setText(model.getDetails());
                holder.email.setText(model.getEmail());
                holder.location.setText(model.getLocation());

            }
        };
        bookings_view_list.setHasFixedSize(true);
        bookings_view_list.setLayoutManager(new LinearLayoutManager(this));
        bookings_view_list.setAdapter(adapter);
    }
    private class BookingsViewHolder extends  RecyclerView.ViewHolder{

        private TextView id, sdate, eDate, details, email, location;

        public BookingsViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.admin_bookingId);
            sdate= itemView.findViewById(R.id.admin_sDate);
            eDate= itemView.findViewById(R.id.admin_eDate);
            details= itemView.findViewById(R.id.admin_detail_of_bookng);
            email=itemView.findViewById(R.id.admin_email);
            location=itemView.findViewById(R.id.location);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    String convertDate(String date){
        String day = date.substring(0,2);
        String month = date.substring(2,4);
        String year =date.substring(4);

        return day +"-"+month+"-"+year;
    }
}