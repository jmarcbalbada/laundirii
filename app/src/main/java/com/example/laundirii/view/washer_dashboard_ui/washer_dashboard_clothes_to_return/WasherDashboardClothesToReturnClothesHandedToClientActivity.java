package com.example.laundirii.view.washer_dashboard_ui.washer_dashboard_clothes_to_return;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.laundirii.R;
import com.example.laundirii.controller.DashboardController;
import com.example.laundirii.model.Phase2Order;

public class WasherDashboardClothesToReturnClothesHandedToClientActivity extends AppCompatActivity {

    private TextView textViewClientName, textViewDatePlaced, textViewTotalAmount,
            textViewContactNumber,TextViewStatus, TextViewDescription, textViewLaundryWeight;

    private Button buttonReceived;
    DashboardController dashboardController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_washer_dashboard_clothes_to_return_clothes_handed_to_client);
        dashboardController = new DashboardController();

        Phase2Order selectedOrder = (Phase2Order) getIntent().getSerializableExtra("selectedOrder");

        textViewLaundryWeight = findViewById(R.id.textViewLaundryWeight);
        textViewClientName = findViewById(R.id.textViewClientName);
        textViewDatePlaced = findViewById(R.id.textViewDatePlaced);
        textViewTotalAmount = findViewById(R.id.textViewChargeAmount);
        textViewContactNumber = findViewById(R.id.textViewContactNumber);
        TextViewStatus = findViewById(R.id.textViewStatus);
        TextViewDescription = findViewById(R.id.textViewDescription);


        int laundryweight = dashboardController.getPhase1LaundryWeight(selectedOrder.getPhase2_phase1OrderID(),getBaseContext());
        buttonReceived = findViewById(R.id.washer_dashboard_fragment_clothes_to_return_receivedButton);

        // SET TEXT VALUE
        textViewLaundryWeight.setText("Laundry Weight: " + laundryweight);
        textViewClientName.setText("Client Name: "+ selectedOrder.getClient().getName());  // Set text to blank
        textViewDatePlaced.setText("Date Placed: "+ selectedOrder.getDatePlaced());
        textViewTotalAmount.setText("Total Charge: "+ selectedOrder.getTotalDue());  // Set text to blank
        textViewContactNumber.setText("Customer Contact Number: " + selectedOrder.getWasher().getContactNo());  // Set text to blank
        TextViewStatus.setText("Status: Client Payment and Handover");  // Set text to blank
        TextViewDescription.setText("The client will pick up their laundry, and pay the total charge.\n\n Note: total charge includes the courier to washer delivery fee");  // Set text to blank

        buttonReceived.setOnClickListener(v -> {
            // send notification
        });


    }
}