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
import com.google.android.material.textfield.TextInputEditText;

public class WasherDashboardPendingrequestClothestoweight extends AppCompatActivity {
    DashboardController dashboardController;
    TextView ClientNameText, orderID,InitialLoad,TotalAmount;
    Button cancelledButton, acceptButton;
    TextInputEditText finalvalue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_washer_dashboard_pendingrequest_clothestoweight);

        dashboardController = new DashboardController();

        // Locate the button ID
        Phase1Order selectedOrder = (Phase1Order) getIntent().getSerializableExtra("selectedOrder");
        ClientNameText = findViewById(R.id.washer_dashboard_fragment_pendingrequest_clothestoweight_activity_clientname);
        InitialLoad = findViewById(R.id.washer_dashboard_fragment_pendingrequest_clothestoweight_initial_load);
        TotalAmount = findViewById(R.id.washer_dashboard_fragment_pendingrequest_clothestoweight_total_amount);
        acceptButton = findViewById(R.id.washer_dashboard_fragment_pendingrequest_clothestoweight_activity_accept);
        finalvalue = findViewById(R.id.washer_dashboard_fragment_pendingrequest_clothestoweight_activity_finalvalue);

        // Set Value of Buttons and Text
        ClientNameText.setText("Client Name: " + selectedOrder.getClient(getBaseContext()).getName());
        InitialLoad.setText("Initial Load:" + selectedOrder.getInitialLoad());
        TotalAmount.setText("Total Amount: " + ((selectedOrder.getInitialLoad()*selectedOrder.getWasher().getRatePerKg() )+ selectedOrder.getTotalCourierAmount()) );
//        int finalInitialLoad ;
//        try {

            // Use finalInitialLoad as needed

//        } catch (NumberFormatException e) {
//            Log.e("YAWA2", e.getMessage() + " final value : " +finalInitialLoad);
//            // Handle the case where the input is not a valid integer
//            e.printStackTrace(); // Log the exception or provide user feedback
//        }
//
//
//

        // Accept the booking request of the customer
        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(WasherDashboardPendingrequestClothestoweight.this)
                        .setTitle("Confirmation")
                        .setMessage("Are you sure this is correct weight?")
                        .setPositiveButton("Yes", (dialog, which) -> {

                            String input = finalvalue.getText().toString();
                            int finalInitialLoad = Integer.parseInt(input);
                            int finalInitialLoad1 = finalInitialLoad;

                            Toast.makeText(getApplicationContext(), "Order weight saved", Toast.LENGTH_SHORT).show();
                            // Update Status to 5
                            dashboardController.updatePhase1OrderStatus(selectedOrder.getOrderID(),5,getBaseContext());
                            //set initial load to final load
                            dashboardController.updatePhase1OrderInitialLoad(selectedOrder.getOrderID(),finalInitialLoad1,getBaseContext());
                            // Updated Phase1Order Total Due
                            double totalDue = (finalInitialLoad1 *selectedOrder.getWasher().getRatePerKg())+selectedOrder.getTotalCourierAmount();
                            dashboardController.updatePhase1OrderTotalDue(selectedOrder.getOrderID(),totalDue,getBaseContext());

                            // Send notification

                            //These are the actual message
                            String notificaitonTitle = selectedOrder.getWasher().getShopName()+" - Clothes Weighted";
                            String notificationMessage = "Recalibrated your clothes weight, total amount to pay: " + totalDue;
                            // Send Notification to Client
                            dashboardController.sendNotifications(0,selectedOrder.getClient().getCustomerID(),0,notificaitonTitle,notificationMessage,getBaseContext());

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