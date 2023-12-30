package com.example.laundirii.view.washer_dashboard_ui.washer_dashboard_pendingrequest;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.laundirii.R;
import com.example.laundirii.controller.DashboardController;
import com.example.laundirii.model.Phase1Order;
import com.example.laundirii.view.washer_dashboard_ui.WasherDashboardActivity;

public class WasherDashboardPendingrequestConfirmation extends AppCompatActivity {

    DashboardController dashboardController;
    TextView  ClientNameText, orderID,InitialLoad,TotalAmount;
    Button cancelledButton, acceptButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.washer_dashboard_fragment_pendingrequest_confirmation_activity);
        dashboardController = new DashboardController();

        // Locate the button ID
        Phase1Order selectedOrder = (Phase1Order) getIntent().getSerializableExtra("selectedOrder");
        ClientNameText = findViewById(R.id.washer_dashboard_fragment_pendingrequest_confirmation_acitivity_clientNameText);
        InitialLoad = findViewById(R.id.washer_dashboard_fragment_pendingrequest_confirmation_acitivity_InitialLoad);
        TotalAmount = findViewById(R.id.washer_dashboard_fragment_pendingrequest_confirmation_acitivity_totalamount);
        cancelledButton = findViewById(R.id.washer_dashboard_fragment_pendingrequest_confirmation_acitivity_cancelbutton);
        acceptButton = findViewById(R.id.washer_dashboard_fragment_pendingrequest_confirmation_acitivity_acceptbutton);

//        int cxid = selectedOrder.getClient().getCustomerID();
//        Client c = selectedOrder.getClient().getClient(cxid,getBaseContext());

        // Set Value of Buttons and Text
        ClientNameText.setText("Client Name: "+selectedOrder.getClient(getBaseContext()).getName());
//        TODO
        InitialLoad.setText("Initial Load:" + selectedOrder.getInitialLoad());
//        find the rate of the washer system
//TODO

        TotalAmount.setText("Total Amount:" + selectedOrder.getWasher(getBaseContext()).getRatePerKg() * selectedOrder.getInitialLoad());
        double hey =  selectedOrder.getWasher().getRatePerKg();
        Log.e("TOTAL AMOUNT", Double.toString(hey));
        Log.e("Order ID", Integer.toString(selectedOrder.getOrderID()));
//        Log.e("Initial Load", Integer.toString(selectedOrder.));



        // Cancel the booking request of the customer
        cancelledButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(WasherDashboardPendingrequestConfirmation.this)
                        .setTitle("Confirmation")
                        .setMessage("Are you sure you want to show the toast?")
                        .setPositiveButton("Yes", (dialog, which) -> {
                            // set status to -1
                            selectedOrder.setPhase1OrderStatus(-1,getBaseContext());
                            //
                            Intent intent = new Intent(WasherDashboardPendingrequestConfirmation.this, WasherDashboardActivity.class);
                            startActivity(intent);


                            // User cancel the book request
                            Toast.makeText(getApplicationContext(), "Cancelled", Toast.LENGTH_SHORT).show();
                        })
                        .setNegativeButton("No", (dialog, which) -> {
                            // User did not take action
                            Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT).show();
                        })
                        .show();
            }
        });

        // Accept the booking request of the customer
        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(WasherDashboardPendingrequestConfirmation.this)
                        .setTitle("Confirmation")
                        .setMessage("Are you sure you want to show the toast?")
                        .setPositiveButton("Yes", (dialog, which) -> {
                            // User accept the book request

                            int availableCourierID = dashboardController.availableCourier(getBaseContext());
                            //assign to a random courier using the controller
                            Log.e("AVAILME",availableCourierID+": this is id of");
                            if(availableCourierID == - 1){
                                Toast.makeText(getApplicationContext(), "No Available Courier Please Try Again Later",Toast.LENGTH_SHORT).show();
                                return;
                            }
                            //getting the phase1OrderID
                            int phase1OrderID = selectedOrder.getOrderID();

                            // udpating the phase1Order_Courier_ID
                            Log.e("Phase1CourierID",availableCourierID+": this is id of");
                            selectedOrder.getCourier().setCourierID(phase1OrderID,availableCourierID,getBaseContext());

                            //TODO if there are no available courier make sure the funciton above will not run
                            // set the status to accepted 1 = means accepted and would be randomly assign to a new courier
                            selectedOrder.setPhase1OrderStatus(1,getBaseContext());

                            Toast.makeText(getApplicationContext(), "Accepted", Toast.LENGTH_SHORT).show();


                            Intent intent = new Intent(WasherDashboardPendingrequestConfirmation.this, WasherDashboardActivity.class);
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