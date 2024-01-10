package com.example.laundirii.view.washer_dashboard_ui.washer_dashboard_clothes_to_return;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.laundirii.R;
import com.example.laundirii.controller.DashboardController;
import com.example.laundirii.model.Phase2Order;

public class WasherDashboardClothesToReturnInsufficientPayment extends AppCompatActivity {

    private DashboardController dashboardController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_washer_dashboard_clothes_to_return_insufficient_payment);

        dashboardController = new DashboardController();

        Phase2Order selectedOrder = (Phase2Order) getIntent().getSerializableExtra("selectedOrder");


        // Send notification to the client that the amount is insufficient
        String notificationTitle = selectedOrder.getWasher().getShopName() + " - Insufficient Payment";
        String notificationMessage = "The payment is insufficient. Please make sure you paid the exact amount or communicate with us.";
        // Sending Notification to Client
        dashboardController.sendNotifications(0, selectedOrder.getClient().getCustomerID(), 0, notificationTitle, notificationMessage, getBaseContext());

        // Set phase1order to 6

        Toast.makeText(getApplicationContext(), "Client has been Notified", Toast.LENGTH_SHORT).show();

    }
}