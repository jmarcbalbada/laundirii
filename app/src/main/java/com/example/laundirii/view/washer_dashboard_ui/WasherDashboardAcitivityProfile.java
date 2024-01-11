package com.example.laundirii.view.washer_dashboard_ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.laundirii.R;
import com.example.laundirii.controller.DashboardController;
import com.example.laundirii.model.Washer;

public class WasherDashboardAcitivityProfile extends AppCompatActivity {
    private Washer washer;
    private DashboardController dashboardController;
    private Button buttonWasherProfileConfirm;
    private TextView textViewWasherContactNumber;
    private TextView textViewWasherRate;
    private TextView textViewWasherShopLocation;
    private TextView textViewWasherShopName;
    private TextView textViewWasherRating;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_washer_dashboard_acitivity_profile);

        dashboardController = new DashboardController();
        washer =  (Washer) getIntent().getSerializableExtra("washer");

        buttonWasherProfileConfirm = findViewById(R.id.buttonWasherProfileConfirm);
        textViewWasherRating = findViewById(R.id.textViewOverallRating);
        textViewWasherContactNumber = findViewById(R.id.textViewWasherContactNumber);
        textViewWasherRate = findViewById(R.id.textViewWasherRate);
        textViewWasherShopLocation = findViewById(R.id.textViewWasherShopLocation);
        textViewWasherShopName = findViewById(R.id.textViewWasherShopName);

        // Now you can use these views in your code

        // You can set text or perform other operations on the TextViews
        //TODO SET overall rating
        textViewWasherRating.setText("Overall Rating: " + washer.getOverAllRating());
        textViewWasherContactNumber.setText("Contact Number: " + washer.getContactNo());
        textViewWasherRate.setText("Rate Kilo:  " + washer.getRatePerKg());
        textViewWasherShopLocation.setText("Shop Location: "+ washer.getShopLocation());
        textViewWasherShopName.setText("Shop Name: " + washer.getShopName() );

        buttonWasherProfileConfirm.setOnClickListener(v -> {
            Intent intent = new Intent(this, WasherDashboardActivityEditProfile.class); // Replace YourProfileActivity with the actual class name for your profile
            intent.putExtra("washer",washer);
            startActivity(intent);
        });


    }
}