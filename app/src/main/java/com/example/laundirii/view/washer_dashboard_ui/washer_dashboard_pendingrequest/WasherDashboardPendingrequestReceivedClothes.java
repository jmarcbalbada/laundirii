package com.example.laundirii.view.washer_dashboard_ui.washer_dashboard_pendingrequest;

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

public class WasherDashboardPendingrequestReceivedClothes extends AppCompatActivity {

    DashboardController dashboardController;
    TextView ClientNameText, orderID,InitialLoad,TotalAmount;
    Button notReceivedButton, receivedButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_washer_dashboard_pending_request_received_clothes);
        dashboardController = new DashboardController();

        // Locate the button ID
        Phase1Order selectedOrder = (Phase1Order) getIntent().getSerializableExtra("selectedOrder");
        ClientNameText = findViewById(R.id.washer_dashboard_fragment_pendingrequest_receivedclothes_acitivity_clientNameText);
        InitialLoad = findViewById(R.id.washer_dashboard_fragment_pendingrequest_receivedclothes_acitivity_InitialLoad);
        TotalAmount = findViewById(R.id.washer_dashboard_fragment_pendingrequest_receivedclothes_acitivity_totalamount);
        notReceivedButton = findViewById(R.id.washer_dashboard_fragment_pendingrequest_receivedclothes_acitivity_cancelbutton);
        receivedButton = findViewById(R.id.washer_dashboard_fragment_pendingrequest_receivedclothes_acitivity_acceptbutton);

        // Set Value of Buttons and Text
        ClientNameText.setText("Client Name: "+selectedOrder.getClient(getBaseContext()).getName());
        InitialLoad.setText("Initial Load:" + selectedOrder.getInitialLoad());
        TotalAmount.setText("Initial Total Amount: " + ((selectedOrder.getInitialLoad()*selectedOrder.getWasher().getRatePerKg() )+ selectedOrder.getTotalCourierAmount()) );


        // Cancel the booking request of the customer
        notReceivedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(WasherDashboardPendingrequestReceivedClothes.this)
                        .setTitle("Confirmation")
                        .setMessage("Are you sure you want to take this action?")
                        .setPositiveButton("Yes", (dialog, which) -> {
                            //implement when order did not received
                            Toast.makeText(getApplicationContext(), "Cancelled", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(WasherDashboardPendingrequestReceivedClothes.this, WasherDashboardActivity.class);
                            startActivity(intent);
                        })
                        .setNegativeButton("No", (dialog, which) -> {
                            // User did not take action
                            Toast.makeText(getApplicationContext(), "No Actions Taken", Toast.LENGTH_SHORT).show();
                        })
                        .show();
            }
        });

        // Accept the booking request of the customer
        receivedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(WasherDashboardPendingrequestReceivedClothes.this)
                        .setTitle("Confirmation")
                        .setMessage("Are you sure you received the clothes")
                        .setPositiveButton("Yes", (dialog, which) -> {
                            Toast.makeText(getApplicationContext(), "Accepted", Toast.LENGTH_SHORT).show();

                            //implement when clothes received
                            // set order status to 4
                            dashboardController.updatePhase1OrderStatus(selectedOrder.getOrderID(), 4,getBaseContext());

                            // change date received PHASE1_ORDER
                            dashboardController.updatePhase1OrderDateReceivedToCurrentDate(selectedOrder.getOrderID() ,getBaseContext());

                            // Send notification
                            //These are the actual message
                            String notificaitonTitle = selectedOrder.getWasher().getShopName()+" - Clothes Successfully Delivered";
                            String notificationMessage = "Thank you for the patronage will clean your clothes as soon as possible";
                            // Send Notification to Client
                            dashboardController.sendNotifications(0,selectedOrder.getClient().getCustomerID(),0,notificaitonTitle,notificationMessage,getBaseContext());

                            notificaitonTitle = selectedOrder.getWasher().getShopName()+" - Delivery Received";
                            notificationMessage = "We received the delivery.";
                            // Send Notification to Courier
                            dashboardController.sendNotifications(0,0,selectedOrder.getCourier().getCourierID(),notificaitonTitle,notificationMessage,getBaseContext());

                            Intent intent = new Intent(WasherDashboardPendingrequestReceivedClothes.this, WasherDashboardActivity.class);
                            startActivity(intent);

                        })
                        .setNegativeButton("No", (dialog, which) -> {
                            // User did not take action
                            Toast.makeText(getApplicationContext(), "No action taken", Toast.LENGTH_SHORT).show();
                        })
                        .show();
            }
        });
    }

}