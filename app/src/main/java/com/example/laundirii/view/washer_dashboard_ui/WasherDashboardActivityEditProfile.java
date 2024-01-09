package com.example.laundirii.view.washer_dashboard_ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.laundirii.R;
import com.example.laundirii.controller.DashboardController;
import com.example.laundirii.model.Washer;
import com.google.android.material.textfield.TextInputEditText;

public class WasherDashboardActivityEditProfile extends AppCompatActivity {

    private Washer washer;
    private DashboardController dashboardController;
    private Button buttonWasherEditProfileConfirm;
    private TextInputEditText textInputWasherShopName;
    private TextInputEditText textInputWasherShopLocation;
    private TextInputEditText textInputWasherContactNumber;
    private TextInputEditText textInputWasherRate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_washer_dashboard_edit_profile);
        dashboardController = new DashboardController();
        washer =  (Washer) getIntent().getSerializableExtra("washer");

        buttonWasherEditProfileConfirm = findViewById(R.id.buttonWasherEditProfileConfirm);
        textInputWasherShopName = findViewById(R.id.TextInputWasherShopName);
        textInputWasherShopLocation = findViewById(R.id.TextInputWasherShopLocation);
        textInputWasherContactNumber = findViewById(R.id.TextInputWasherContactNumber);
        textInputWasherRate = findViewById(R.id.TextInputWasherRate);



        textInputWasherShopName.setText("" + washer.getShopName());
        textInputWasherShopLocation.setText("" + washer.getShopLocation());
        textInputWasherContactNumber.setText("" + washer.getContactNo());
        textInputWasherRate.setText(""+ washer.getContactNo());

        buttonWasherEditProfileConfirm.setOnClickListener(e -> {

            String shopName = textInputWasherShopName.getText().toString();
            String shopLocation = textInputWasherShopLocation.getText().toString();
            String shopContact = textInputWasherContactNumber.getText().toString();
            String shopRateStr = textInputWasherRate.getText().toString();

            if (isValidUpdate(shopName, shopLocation, shopContact, shopRateStr)) {
                // Convert shopRate to Double if validation passes
                Double shopRate = Double.parseDouble(shopRateStr);

                // update Profile
                int updatedProfileResult = dashboardController.updateWasherProfile(washer.getWasherID(), shopName, shopLocation, shopContact, shopRate, getBaseContext());
                Toast.makeText(getApplicationContext(), "Profile Save", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(this, WasherDashboardActivity.class);
                startActivity(intent);
            }
        });


    }
    public boolean isValidUpdate(String shopName, String shopLocation, String shopContact, String shopRateStr) {
        boolean valid = true;

        // check entries if isEmpty
        if (shopName.isEmpty()) {
            textInputWasherShopName.setError("Required Field");
            valid = false;
        }

        if (shopLocation.isEmpty()) {
            textInputWasherShopLocation.setError("Required Field");
            valid = false;
        }

        if (shopContact.isEmpty()) {
            textInputWasherContactNumber.setError("Required Field");
            valid = false;
        }

        if (shopRateStr.isEmpty()) {
            textInputWasherRate.setError("Required Field");
            valid = false;
        } else {
            // Additional validation for shopRate (assuming it must be a valid Double)
            try {
                Double.parseDouble(shopRateStr);
            } catch (NumberFormatException e) {
                textInputWasherRate.setError("Invalid Rate");
                valid = false;
            }
        }

        return valid;
    }
}