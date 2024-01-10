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

public class WasherDashboardClothesToReturnClothesHandedOverToCourier extends AppCompatActivity {

    private TextView textViewClientName, textViewAddress, textViewDatePlaced, textViewTotalAmount,
            textViewGcashReferenceNumber, textViewContactNumber;

    private Button buttonReceived, buttonNotReceived;


    DashboardController dashboardController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_washer_dashboard_clothes_to_return_clothes_handed_over_to_courier);

        Phase2Order selectedOrder = (Phase2Order) getIntent().getSerializableExtra("selectedOrder");
        dashboardController = new DashboardController();
////
        textViewClientName = findViewById(R.id.textViewClientName);
        textViewAddress = findViewById(R.id.textViewAddress);
        textViewDatePlaced = findViewById(R.id.textViewDatePlaced);
        textViewTotalAmount = findViewById(R.id.textViewTotalAmount);
        textViewContactNumber = findViewById(R.id.textViewContactNumber);
////
        textViewClientName.setText("Client Name : " +selectedOrder.getClient().getName());
        textViewAddress.setText("Address : "+selectedOrder.getClient().getAddress());
        textViewDatePlaced.setText("Date Placed: " + selectedOrder.getDatePlaced());
        textViewTotalAmount.setText("Total Amount: "+ selectedOrder.getTotalDue());
        textViewContactNumber.setText("Client Contact Number: "+ selectedOrder.getClient().getContactNo());

        buttonReceived = findViewById(R.id.washer_dashboard_fragment_clothes_to_return_receivedButton);
//        buttonNotReceived = findViewById(R.id.washer_dashboard_fragment_clothes_to_return_notreceivedButton);
//
        buttonReceived.setOnClickListener(v -> new AlertDialog.Builder(WasherDashboardClothesToReturnClothesHandedOverToCourier.this)
                .setTitle("Confirmation")
                .setMessage("Did you handed over the clothes and payment to the washer?")
                .setPositiveButton("Yes", (dialog, which) ->{

                    int Phase2OrderID = selectedOrder.getOrderID();
                    // set status to 13
                    dashboardController.updatePhase2OrderStatus(Phase2OrderID,13,getBaseContext());

                    // notify client thet courier receivedt the clothes
                    String notificaitonTitle = selectedOrder.getWasher().getShopName()+" - Clothes Received by the Couruier";
                    String notificationMessage = "Courier Received the Clothes";
                    // Sending Notification to Client
                    dashboardController.sendNotifications(0,selectedOrder.getClient().getCustomerID(),0,notificaitonTitle,notificationMessage,getBaseContext());



                    Toast.makeText(getApplicationContext(), "Option was saved", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(WasherDashboardClothesToReturnClothesHandedOverToCourier.this, WasherDashboardActivity.class);
                    startActivity(intent);

                }).setNegativeButton("No", (dialog, which) -> {
                    Toast.makeText(getApplicationContext(), "No Action Taken", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(WasherDashboardClothesToReturnClothesHandedOverToCourier.this, WasherDashboardActivity.class);
                    startActivity(intent);
                    //
                })
                .show());


    }
}