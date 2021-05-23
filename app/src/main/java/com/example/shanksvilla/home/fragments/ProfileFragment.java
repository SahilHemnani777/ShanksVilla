package com.example.shanksvilla.home.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shanksvilla.R;
import com.example.shanksvilla.home.HomeActivity;
import com.example.shanksvilla.model.booking;
import com.example.shanksvilla.signin.GoogleSignInActivity;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.squareup.picasso.Picasso;


public class ProfileFragment extends Fragment {
    public static final String FRAGMENT_TAG = "PROFILE_FRAGMENT_TAG";
    // UI Elements
    private ShapeableImageView profileImgIv;
    private TextView displayNameTv;
    private TextView emailTv;
    private Button signOutBtn;
    private RecyclerView bookings_view_list;
    private  FirestoreRecyclerAdapter adapter;


    // Firebase Auth
    private FirebaseAuth mAuth;
    FirebaseUser currentUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

        /* CONFIGURE UI*/
        // configure profile image imageview
        profileImgIv = v.findViewById(R.id.profile_img_iv);
        // configure display name textview
        displayNameTv = v.findViewById(R.id.displayname_tv);
        // configure email textview
        emailTv = v.findViewById(R.id.email_tv);
        //configure the list view
        bookings_view_list = v.findViewById(R.id.bookings_view_list);
        // configure sign out button
        signOutBtn = v.findViewById(R.id.signout_btn);
        // give on click listener
        signOutBtn.setOnClickListener(view -> {
            // get reference to the calling activity HomeActivity
            HomeActivity homeActivity = (HomeActivity) getActivity();
            // sign out now
            homeActivity.signOut();
            startActivity(new Intent(homeActivity, GoogleSignInActivity.class));
        });
        // configure edit profile textview


        /* CONFIGURE FIREBASE AUTH*/
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        // if user is logged in
        if (currentUser != null) {
            // load the profile image in profile picture imageview
            Uri photoUrl = currentUser.getPhotoUrl();
            /* this photoUrl is 90px size photo Url, we want to request of higher resolution*/
            if (photoUrl != null) {
                // get the uri in string format
                String photoUrlStr = photoUrl.toString();
                // convert the uri string to required pixel size
                photoUrlStr = googlePhotoUrlOfPixelSize(photoUrlStr, 360);
                // load the picture in profile picture imageview
                Picasso.get().
                        load(photoUrlStr)
                        .placeholder(R.drawable.profile)  // placeholder for network image
                        .error(R.drawable.profile)    // if placeholder for error
                        .into(profileImgIv);
            }
            // load user name in display name textview
            displayNameTv.setText(currentUser.getDisplayName());
            // load user email in email textview
            emailTv.setText(currentUser.getEmail());
        }

        //Query
        Query query = firebaseFirestore.collection("users/" + currentUser.getUid() + "/bookings");

        //RecyclerOptions
        FirestoreRecyclerOptions<booking> options = new FirestoreRecyclerOptions.Builder<booking>()
                .setQuery(query, booking.class).build();

        //Adapter
        adapter = new FirestoreRecyclerAdapter<booking, BookingsViewHolder>(options) {
            @NonNull
            @Override
            public BookingsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.booking_item, parent, false );
                return new BookingsViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull BookingsViewHolder holder, int position, @NonNull booking model) {
                holder.id.setText(model.getBookingId());
                holder.sdate.setText(convertDate(model.getDateFrom()));
                holder.eDate.setText(convertDate(model.getDateTo()));
                holder.details.setText(model.getDetails());

            }
        };
        bookings_view_list.setHasFixedSize(true);
        bookings_view_list.setLayoutManager(new LinearLayoutManager(getContext()));
        bookings_view_list.setAdapter(adapter);

        return v;
    }


    public static String googlePhotoUrlOfPixelSize(String photoUrl, int pxWidth) {
        // Variable holding the original String portion of the url that will be replaced
        String originalPieceOfUrl = "s96-c";

        // Variable holding the new String portion of the url that does the replacing, to improve image quality
        String newPieceOfUrlToAdd = "s" + pxWidth + "-c";

        // Replace the original part of the Url with the new part
        String newPhotoUrl = photoUrl.replace(originalPieceOfUrl, newPieceOfUrlToAdd);

        // now the newPhotoUrl consist url of given pixel size
        return newPhotoUrl;
    }

    private class BookingsViewHolder extends  RecyclerView.ViewHolder{

        private TextView id, sdate, eDate, details;

         public BookingsViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.admin_bookingId);
            sdate= itemView.findViewById(R.id.admin_sDate);
            eDate= itemView.findViewById(R.id.admin_eDate);
            details= itemView.findViewById(R.id.admin_detail_of_bookng);
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