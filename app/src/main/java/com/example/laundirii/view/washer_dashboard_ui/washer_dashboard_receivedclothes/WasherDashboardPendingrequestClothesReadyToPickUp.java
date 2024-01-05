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

public class WasherDashboardPendingrequestClothesReadyToPickUp extends AppCompatActivity {
    DashboardController dashboardController;
    TextView ClientNameText, orderID,InitialLoad,TotalAmount;
    Button cancelledButton, readyToPickUpButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_washer_dashboard_pendingrequest_clothes_ready_to_pick_up);

        dashboardController = new DashboardController();

        // Locate the button ID
        Phase1Order selectedOrder = (Phase1Order) getIntent().getSerializableExtra("selectedOrder");
        ClientNameText = findViewById(R.id.washer_dashboard_fragment_pendingrequest_clothesreadytopickup_activity_clientname);
        InitialLoad = findViewById(R.id.washer_dashboard_fragment_pendingrequest_clothesreadytopickupt_initial_load);
        TotalAmount = findViewById(R.id.washer_dashboard_fragment_pendingrequest_clothesreadytopickup_total_amount);
        readyToPickUpButton = findViewById(R.id.washer_dashboard_fragment_pendingrequest_clothesreadytopickup_activity_accept);

        // Set Value of Buttons and Text
        ClientNameText.setText("Client Name: " + selectedOrder.getClient(getBaseContext()).getName());
        InitialLoad.setText("Initial Load:" + selectedOrder.getInitialLoad());
        TotalAmount.setText("Total Amount:" + selectedOrder.getTotalDue());


        // Accept the booking request of the customer
        readyToPickUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(WasherDashboardPendingrequestClothesReadyToPickUp.this)
                        .setTitle("Confirmation")
                        .setMessage("Are you sure you want to notify the Customer?")
                        .setPositiveButton("Yes", (dialog, which) -> {
                            // User accept the book request

                            // set status to 6
                            // mark as ready to collect
                            dashboardController.updatePhase1OrderStatus(selectedOrder.getOrderID(),6,getBaseContext());

                            // TODO send notification to Client
                            // this function will only notify the client and washer
                            String notificaitonTitle = selectedOrder.getWasher().getShopName()+" - Clothes Ready to Pick up";
                            String notificationMessage = "Clothes ready to collect. Total Amount to pay: " + selectedOrder.getTotalDue();
                            dashboardController.washerSendNotificationToClient(selectedOrder.getWasher().getWasherID(),selectedOrder.getClient().getCustomerID(),0,notificaitonTitle,notificationMessage,getBaseContext());
                            Toast.makeText(getApplicationContext(), "Clothes will soon be pick up", Toast.LENGTH_SHORT).show();


                            Intent intent = new Intent(WasherDashboardPendingrequestClothesReadyToPickUp.this, WasherDashboardActivity.class);
                            startActivity(intent);

                        })
                        .setNegativeButton("No", (dialog, which) -> {
                            // User did not take action
                            Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT).show();
                        })
                        .show();
            }
        });
    }}