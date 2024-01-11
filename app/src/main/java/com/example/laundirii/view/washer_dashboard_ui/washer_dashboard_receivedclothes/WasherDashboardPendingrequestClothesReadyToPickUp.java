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
    TextView ClientNameText, InitialLoad,TotalAmount, dateplaced,status,description,phonenumber;

    Button readyToPickUpButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_washer_dashboard_pendingrequest_clothes_ready_to_pick_up);

        dashboardController = new DashboardController();


//        "Notify the client that there clothes are already clean. They will process a pick up and will send you the payment"
        // Locate the button ID
        Phase1Order selectedOrder = (Phase1Order) getIntent().getSerializableExtra("selectedOrder");
        phonenumber = findViewById(R.id.washer_dashboard_fragment_pendingrequest_clothesreadytopickup_activity_phonenumber);
        dateplaced = findViewById(R.id.washer_dashboard_fragment_pendingrequest_clothesreadytopickup_activity_dateplaced);
        status = findViewById(R.id.washer_dashboard_fragment_pendingrequest_clothesreadytopickup_activity_status2);
        description = findViewById(R.id.washer_dashboard_fragment_pendingrequest_clothesreadytopickup_activity_description2);
        ClientNameText = findViewById(R.id.washer_dashboard_fragment_pendingrequest_clothesreadytopickup_activity_clientname);
        InitialLoad = findViewById(R.id.washer_dashboard_fragment_pendingrequest_clothesreadytopickupt_initial_load);
        TotalAmount = findViewById(R.id.washer_dashboard_fragment_pendingrequest_clothesreadytopickup_total_amount);
        readyToPickUpButton = findViewById(R.id.washer_dashboard_fragment_pendingrequest_clothesreadytopickup_activity_accept);

        // Set Value of Buttons and Text
        status.setText("Progress Status: Completion Confirmation");
        dateplaced.setText("Date Placed: " + selectedOrder.getDatePlaced());
        description.setText("Notify the client that their clothes are ready to pick up. They will process a collect option and will send you the payment in you GCASH number. Make sure you have a gcash account to receive the payment");
        ClientNameText.setText("Client Name: " + selectedOrder.getClient(getBaseContext()).getName());
        InitialLoad.setText("Initial Load:" + selectedOrder.getInitialLoad() +" KG");
        TotalAmount.setText("Total Amount:" + selectedOrder.getTotalDue());
        phonenumber.setText("Customer Phone Number: "+ selectedOrder.getClient().getContactNo());


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

                            // this function will only notify the client and washer

                            // Send notification

                            //These are the actual message
                            String notificaitonTitle = selectedOrder.getWasher().getShopName()+" - Clothes Ready to Pick up";
                            String notificationMessage = "Clothes ready to collect. Total Amount to pay: " + selectedOrder.getTotalDue();
                            // Send Notification to Client
                            dashboardController.sendNotifications(0,selectedOrder.getClient().getCustomerID(),0,notificaitonTitle,notificationMessage,getBaseContext());

                            Toast.makeText(getApplicationContext(), "Customer has been notified", Toast.LENGTH_SHORT).show();
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