package com.example.laundirii.view.washer_dashboard_ui.washer_dashboard_clothes_to_return;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.laundirii.R;
import com.example.laundirii.controller.DashboardController;
import com.example.laundirii.model.Phase2Order;
import com.example.laundirii.view.washer_dashboard_ui.WasherDashboardActivity;

public class WasherDashboardClothesToReturnClientPaymentConfirmation extends AppCompatActivity {

    private TextView textViewClientName, textViewAddress, textViewDatePlaced, textViewTotalAmount,
            textViewGcashReferenceNumber, textViewContactNumber;

    private Button buttonReceived, buttonNotReceived;
    private Phase2Order selectOrder;


    DashboardController dashboardController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_washer_dashboard_clothes_to_return_client_payment_confirmation);

        dashboardController = new DashboardController();

        // Find TextViews by their IDs
        Phase2Order selectedOrder = (Phase2Order) getIntent().getSerializableExtra("selectedOrder");
        textViewClientName = findViewById(R.id.textViewClientName);
        textViewAddress = findViewById(R.id.textViewAddress);
        textViewDatePlaced = findViewById(R.id.textViewDatePlaced);
        textViewTotalAmount = findViewById(R.id.textViewTotalAmount);
        textViewGcashReferenceNumber = findViewById(R.id.textViewGcashReferenceNumber);
        textViewContactNumber = findViewById(R.id.textViewContactNumber);

        textViewClientName.setText("Client Name : " +selectedOrder.getClient().getName());
        textViewAddress.setText("Order Status : "+selectedOrder.getPhase2OrderStatus());
        textViewDatePlaced.setText("Date Placed: ");
        buttonReceived = findViewById(R.id.washer_dashboard_fragment_clothes_to_return_receivedButton);
        buttonNotReceived = findViewById(R.id.washer_dashboard_fragment_clothes_to_return_notreceivedButton);

        buttonReceived.setOnClickListener(v -> {
            new AlertDialog.Builder(WasherDashboardClothesToReturnClientPaymentConfirmation.this)
                    .setTitle("Confirmation")
                    .setMessage("Did you receive the payment?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        int Phase2OrderID = selectedOrder.getOrderID();
                        // find available courier
                        int availableCourierID = dashboardController.availableCourier(getBaseContext());

                        // assign to a random courier using the controller
                        if (availableCourierID == -1) {
                            Toast.makeText(getApplicationContext(), "No Available Courier. Please Try Again Later", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        // set courier on phase2order
                        dashboardController.updatePhase2OrderCourierID(Phase2OrderID, availableCourierID, getBaseContext());

                        // update phase2order date_courier
                        dashboardController.updatePhase2OrderDateCourier(Phase2OrderID, getBaseContext());


                        // set phase2order status to 11
                        dashboardController.updatePhase2OrderStatus(Phase2OrderID, 11, getBaseContext());

                        // send notification to client
                        String notificaitonTitle = selectedOrder.getWasher().getShopName()+" - Request Accepted";
                        String notificationMessage = "Courier is coming to collect the clothes" ;
                        // Sending Notification to Client
                        dashboardController.sendNotifications(0,selectedOrder.getClient().getCustomerID(),0,notificaitonTitle,notificationMessage,getBaseContext());

                        Toast.makeText(getApplicationContext(), "Courier is coming", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(WasherDashboardClothesToReturnClientPaymentConfirmation.this, WasherDashboardActivity.class);
                        startActivity(intent);
                    })
                    .setNegativeButton("No", (dialog, which) -> {



                        Intent intent = new Intent(WasherDashboardClothesToReturnClientPaymentConfirmation.this, WasherDashboardActivity.class);
                        startActivity(intent);

                        Toast.makeText(getApplicationContext(), "No action taken", Toast.LENGTH_SHORT).show();
                    })
                    .show();
        });

        this.selectOrder = selectedOrder;
        buttonNotReceived.setOnClickListener(v ->
                new AlertDialog.Builder(WasherDashboardClothesToReturnClientPaymentConfirmation.this)
                        .setTitle("What Happened?")
                        .setPositiveButton("Gcash Reference Did not match", (dialog1, which1) -> {
                            // TODO: Logic for Gcash reference did not match

                            // Send message to the client
                            String notificationTitle = selectedOrder.getWasher().getShopName() + " - Invalid Gcash Reference";
                            String notificationMessage = "The payment is insufficient. Please make sure you paid the exact amount or communicate with us.";
                            // Sending Notification to Client
                            dashboardController.sendNotifications(0, selectedOrder.getClient().getCustomerID(), 0, notificationTitle, notificationMessage, getBaseContext());

                            // Set phase1order to 6
                            // dashboardController.updatePhase1OrderStatus(, getBaseContext());

                            Toast.makeText(getApplicationContext(), "Client has been Notified", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(WasherDashboardClothesToReturnClientPaymentConfirmation.this, WasherDashboardActivity.class);
                            startActivity(intent);
                        })
                        .setNegativeButton("Paid Amount is Insufficient", (dialog2, which2) -> {
                            // TODO: Logic for insufficient amount

                            // Send notification to the client that the amount is insufficient
                            String notificationTitle = selectedOrder.getWasher().getShopName() + " - Insufficient Payment";
                            String notificationMessage = "The payment is insufficient. Please make sure you paid the exact amount or communicate with us.";
                            // Sending Notification to Client
                            dashboardController.sendNotifications(0, selectedOrder.getClient().getCustomerID(), 0, notificationTitle, notificationMessage, getBaseContext());

                            // Set phase1order to 6
                            // dashboardController.updatePhase1OrderStatus(, getBaseContext());

                            Toast.makeText(getApplicationContext(), "Client has been Notified", Toast.LENGTH_SHORT).show();
                        })
                        .setNeutralButton("Not Take action", (dialog3, which3) -> {
                            Toast.makeText(getApplicationContext(), "No Action Taken", Toast.LENGTH_SHORT).show();
                        })
                        .show());


    }

}
