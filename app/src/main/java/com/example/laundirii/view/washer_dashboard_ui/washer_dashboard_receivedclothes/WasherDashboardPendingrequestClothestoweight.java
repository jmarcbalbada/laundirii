package com.example.laundirii.view.washer_dashboard_ui.washer_dashboard_receivedclothes;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.laundirii.R;
import com.example.laundirii.controller.DashboardController;
import com.example.laundirii.model.Phase1Order;
import com.example.laundirii.view.washer_dashboard_ui.WasherDashboardActivity;

public class WasherDashboardPendingrequestClothestoweight extends AppCompatActivity {
    DashboardController dashboardController;
    TextView ClientNameText, orderID,InitialLoad,TotalAmount;
    Button cancelledButton, acceptButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_washer_dashboard_pendingrequest_clothestoweight);

        dashboardController = new DashboardController();

        // Locate the button ID
        Phase1Order selectedOrder = (Phase1Order) getIntent().getSerializableExtra("selectedOrder");
        ClientNameText = findViewById(R.id.washer_dashboard_fragment_pendingrequest_clothestoweight_activity_clientname);
        InitialLoad = findViewById(R.id.washer_dashboard_fragment_pendingrequest_clothestoweight_initial_load);
        TotalAmount = findViewById(R.id.washer_dashboard_fragment_pendingrequest_clothestoweight_initial_load);
        acceptButton = findViewById(R.id.washer_dashboard_fragment_pendingrequest_clothestoweight_activity_accept);

        // Set Value of Buttons and Text
        ClientNameText.setText("Client Name: " + selectedOrder.getClient(getBaseContext()).getName());
        InitialLoad.setText("Initial Load:" + selectedOrder.getInitialLoad());
        TotalAmount.setText("Total Amount:" + selectedOrder.getInitialLoad()*selectedOrder.getWasher().getRatePerKg());


        // Accept the booking request of the customer
        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(WasherDashboardPendingrequestClothestoweight.this)
                        .setTitle("Confirmation")
                        .setMessage("Are you sure you want to show the toast?")
                        .setPositiveButton("Yes", (dialog, which) -> {

                            Toast.makeText(getApplicationContext(), "Accepted", Toast.LENGTH_SHORT).show();
                            //TODO update orderStatus
                            dashboardController.updatePhase1OrderStatus(selectedOrder.getOrderID(),5,getBaseContext());
                            //TODO set total Due
                            double totalDue = selectedOrder.getTotalDue()*selectedOrder.getWasher().getRatePerKg();
                            dashboardController.updatePhase1OrderTotalDue(selectedOrder.getOrderID(),totalDue,getBaseContext());


                            Intent intent = new Intent(WasherDashboardPendingrequestClothestoweight.this, WasherDashboardActivity.class);
                            startActivity(intent);

                        })
                        .setNegativeButton("No", (dialog, which) -> {
                            // User did not take action
                            Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT).show();
                        })
                        .show();
            }
        });
    }
}