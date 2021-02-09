package com.example.shanksvilla.home.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.shanksvilla.R;
import com.example.shanksvilla.home.HomeActivity;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;



public class ProfileFragment extends Fragment {
    public static final String FRAGMENT_TAG="PROFILE_FRAGMENT_TAG";
    // UI Elements
    private ShapeableImageView profileImgIv;
    private TextView displayNameTv;
    private TextView emailTv;
    private Button signOutBtn;


    // Firebase Auth
    private FirebaseAuth mAuth;
    FirebaseUser currentUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_profile, container, false);

        /* CONFIGURE UI*/
        // configure profile image imageview
        profileImgIv=v.findViewById(R.id.profile_img_iv);
        // configure display name textview
        displayNameTv=v.findViewById(R.id.displayname_tv);
        // configure email textview
        emailTv=v.findViewById(R.id.email_tv);
        // configure sign out button
        signOutBtn=v.findViewById(R.id.signout_btn);
        // give on click listener
        signOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // get reference to the calling activity HomeActivity
                HomeActivity homeActivity=(HomeActivity)getActivity();
                // sign out now
                homeActivity.signOut();
            }
        });
        // configure edit profile textview


        /* CONFIGURE FIREBASE AUTH*/
        mAuth= FirebaseAuth.getInstance();
        currentUser=mAuth.getCurrentUser();

        // if user is logged in
        if(currentUser!=null) {
            // load the profile image in profile picture imageview
            Uri photoUrl=currentUser.getPhotoUrl();
            /* this photoUrl is 90px size photo Url, we want to request of higher resolution*/
            if(photoUrl!=null) {
                // get the uri in string format
                String photoUrlStr=photoUrl.toString();
                // convert the uri string to required pixel size
                photoUrlStr= googlePhotoUrlOfPixelSize(photoUrlStr,360);
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

        return v;
    }


    public static String googlePhotoUrlOfPixelSize(String photoUrl, int pxWidth) {
        // Variable holding the original String portion of the url that will be replaced
        String originalPieceOfUrl = "s96-c";

        // Variable holding the new String portion of the url that does the replacing, to improve image quality
        String newPieceOfUrlToAdd = "s"+pxWidth+"-c";

        // Replace the original part of the Url with the new part
        String newPhotoUrl = photoUrl.replace(originalPieceOfUrl, newPieceOfUrlToAdd);

        // now the newPhotoUrl consist url of given pixel size
        return newPhotoUrl;
    }

}