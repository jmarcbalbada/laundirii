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

public class WasherDashboardPendingrequestRequestConfirmation extends AppCompatActivity {

    DashboardController dashboardController;
    TextView ClientNameText, InitialLoad,TotalAmount, dateplaced,status,description,phonenumber;

    Button cancelledButton, acceptButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.washer_dashboard_fragment_pendingrequest_confirmation_activity);
        dashboardController = new DashboardController();

        // Locate the button ID
        Phase1Order selectedOrder = (Phase1Order) getIntent().getSerializableExtra("selectedOrder");
        phonenumber = findViewById(R.id.washer_dashboard_fragment_pendingrequest_requestconfirmation_activity_customerphonenumber);
        dateplaced = findViewById(R.id.washer_dashboard_fragment_pendingrequest_requestconfirmation_activity_dateplaced);
        status = findViewById(R.id.washer_dashboard_fragment_pendingrequest_requestsconfirmation_activity_status);
        description = findViewById(R.id.washer_dashboard_fragment_pendingrequest_requestconfirmation_activity_description2);
        ClientNameText = findViewById(R.id.washer_dashboard_fragment_pendingrequest_confirmation_acitivity_clientNameText);
        InitialLoad = findViewById(R.id.washer_dashboard_fragment_pendingrequest_confirmation_acitivity_InitialLoad);
        TotalAmount = findViewById(R.id.washer_dashboard_fragment_pendingrequest_confirmation_acitivity_totalamount);
        cancelledButton = findViewById(R.id.washer_dashboard_fragment_pendingrequest_confirmation_acitivity_cancelbutton);
        acceptButton = findViewById(R.id.washer_dashboard_fragment_pendingrequest_confirmation_acitivity_acceptbutton);

        // Set Value of Buttons and Text
        ClientNameText.setText("Client Name: "+selectedOrder.getClient(getBaseContext()).getName());
        InitialLoad.setText("Initial Laundry Weight:" + selectedOrder.getInitialLoad() + " KG");
        TotalAmount.setText("Expected to Earn:" +( selectedOrder.getInitialLoad() *selectedOrder.getWasher().getRatePerKg()) );
        description.setText("Client Book a Laundry Appointment.\n Expected charge of laundry = "+(selectedOrder.getInitialLoad()*selectedOrder.getWasher().getRatePerKg())+".\nRider Delivery Fee: "+selectedOrder.getTotalCourierAmount()+".\nExpected Client charge: "+((selectedOrder.getInitialLoad()*selectedOrder.getWasher().getRatePerKg())+ selectedOrder.getTotalCourierAmount()) );
        dateplaced.setText("Date Book: " + selectedOrder.getDatePlaced());
        status.setText("Progress Status: Booking Confirmation" );
        phonenumber.setText("Customer Contact Number: "+ selectedOrder.getClient().getContactNo());



        // Cancel the booking request of the customer
        cancelledButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(WasherDashboardPendingrequestRequestConfirmation.this)
                        .setTitle("Confirmation")
                        .setMessage("Are you sure you want to cancel the request?")
                        .setPositiveButton("Yes", (dialog, which) -> {
                            // set status to -1

                            dashboardController.updatePhase1OrderStatus(selectedOrder.getOrderID(),-1,getBaseContext());
                            // User cancel the book request
                            Toast.makeText(getApplicationContext(), "Cancelled", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(WasherDashboardPendingrequestRequestConfirmation.this, WasherDashboardActivity.class);
                            startActivity(intent);
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
                new AlertDialog.Builder(WasherDashboardPendingrequestRequestConfirmation.this)
                        .setTitle("Confirmation")
                        .setMessage("Are you sure you wanted to accept booking?")
                        .setPositiveButton("Yes", (dialog, which) -> {
                            // User accept the book request

                            //get the id of available courier and update the courier status to 0(getting the status of available courier)
                            int availableCourierID = dashboardController.availableCourier(getBaseContext());

                            //assign to a random courier using the controller
                            if(availableCourierID == - 1){
                                Toast.makeText(getApplicationContext(), "No Available Courier Please Try Again Later",Toast.LENGTH_SHORT).show();
                                return;
                            }
                            dashboardController.washerUpdatePhase1OrderCourierIDAndCourierDate(selectedOrder.getOrderID(),availableCourierID,getApplicationContext());
                            //getting the phase1OrderID
                            int phase1OrderID = selectedOrder.getOrderID();

                            String notificaitonTitle = selectedOrder.getWasher().getShopName()+" - Request Accepted";
                            String notificationMessage = "Courier is coming to pick up clothes";
                            // Sending Notification to Client
                            dashboardController.sendNotifications(0,selectedOrder.getClient().getCustomerID(),0,notificaitonTitle,notificationMessage,getBaseContext());


                            notificaitonTitle = selectedOrder.getWasher().getShopName()+" - Request Accepted";
                            notificationMessage = "Collect the clothes" ;
                            // Sending Notification to Courier
                            dashboardController.sendNotifications(0,0,availableCourierID,notificaitonTitle,notificationMessage,getBaseContext());


                            int result = dashboardController.washerAcceptClientRequest(phase1OrderID,availableCourierID,getBaseContext());
                            if (result == 0){
                                return;
                            }
                            Toast.makeText(getApplicationContext(), "Courier has been assigned", Toast.LENGTH_SHORT).show();


                            Intent intent = new Intent(WasherDashboardPendingrequestRequestConfirmation.this, WasherDashboardActivity.class);
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