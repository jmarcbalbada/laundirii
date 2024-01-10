package com.example.laundirii.view.washer_dashboard_ui.washer_dashboard_clothes_to_return;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
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


    private DashboardController dashboardController;
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
        textViewAddress.setText("Address : "+selectedOrder.getClient().getAddress());
        textViewDatePlaced.setText("Date Placed: " + selectedOrder.getDatePlaced());
        textViewTotalAmount.setText("Total Amount: "+ selectedOrder.getTotalDue());

        Log.e("GCASH ref",""+ selectedOrder.getReferenceNo());
        textViewGcashReferenceNumber.setText("Gcash Reference Number: "+ selectedOrder.getReferenceNo());
        textViewContactNumber.setText("Client Contact Number: "+ selectedOrder.getClient().getContactNo());
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

                        // set phase2 paymentstatus to paid amount
                        dashboardController.updatePhase2OrderPaymentStatus(Phase2OrderID,1,getBaseContext());

                        // update total paid
                        dashboardController.updatePhase2OrderTotalPaid(Phase2OrderID,selectedOrder.getTotalDue(),getBaseContext());

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


        // NO BUTTON
        this.selectOrder = selectedOrder;
        buttonNotReceived.setOnClickListener(v -> {
            new AlertDialog.Builder(WasherDashboardClothesToReturnClientPaymentConfirmation.this)
                    .setTitle("What Happened?")
                    .setPositiveButton("Gcash Reference Did not match", (dialog1, which1) -> {
                        // TODO: Logic for Gcash reference did not match
                        int Phase2OrderID = selectedOrder.getOrderID();

                        // Send message to the client that gcash reference did not match
                        String notificationTitle = selectedOrder.getWasher().getShopName() + " - Invalid Gcash Reference";
                        String notificationMessage = "The reference Gcash is invalid Kindly double \ncheck the reference and prhocess another\n collect request with a correct Gcash reference \nnumber so that we can receive the payment.\n You can call us at " + selectedOrder.getWasher().getContactNo();
                        // Sending Notification to Client
                        dashboardController.sendNotifications(0, selectedOrder.getClient().getCustomerID(), 0, notificationTitle, notificationMessage, getBaseContext());


                        // Set phase1order to 6
                        dashboardController.updatePhase1OrderStatuss(selectedOrder.getPhase2_phase1OrderID(), 6, getBaseContext());

                        // Set phase2Order to -1
                        dashboardController.updatePhase2OrderStatus(Phase2OrderID, -1, getBaseContext());

                        Toast.makeText(getApplicationContext(), "Client has been Notified", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(WasherDashboardClothesToReturnClientPaymentConfirmation.this, WasherDashboardActivity.class);
                        startActivity(intent);
                    })
                    .setNegativeButton("Client Insufficient Payment", (dialog2, which2) -> {
                        // Show a dialog to enter the amount
                        AlertDialog.Builder amountDialogBuilder = new AlertDialog.Builder(WasherDashboardClothesToReturnClientPaymentConfirmation.this);
                        amountDialogBuilder.setTitle("Enter Amount Received");
                        amountDialogBuilder.setMessage("Kindly provide the received amount to notify the client of the remaining outstanding balance.");

                        final EditText amountInput = new EditText(WasherDashboardClothesToReturnClientPaymentConfirmation.this);
                        amountInput.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                        amountDialogBuilder.setView(amountInput);

                        // Set up buttons
                        amountDialogBuilder.setPositiveButton("Continue", (amountDialog, amountWhich) -> {
                            String amountReceived = amountInput.getText().toString();
                            // Handle the amount received, and proceed as needed

                            // For example, you can show another dialog
                            new AlertDialog.Builder(WasherDashboardClothesToReturnClientPaymentConfirmation.this)
                                    .setTitle("Payment Confirmation")
                                    .setMessage("s this the correct amount you received? " + amountReceived)
                                    .setPositiveButton("Yes", (nextDialog, nextWhich) -> {
                                        // Handle further steps if needed
                                        double kuwangNiClient = selectedOrder.getTotalDue() - Integer.parseInt(amountReceived) ;
                                        if(kuwangNiClient < 0){
                                            new AlertDialog.Builder(WasherDashboardClothesToReturnClientPaymentConfirmation.this)
                                                    .setTitle("Client Already paid Completely")
                                                    .setMessage("Client balance is completely paid review the payment")
                                                    .setNeutralButton("Confirm",(dialog3,which3)->{
                                            }).show();

//                                            Toast.makeText(getApplicationContext(), "Client is fully paid review the payment ", Toast.LENGTH_SHORT).show();
                                            Toast.makeText(getApplicationContext(), "Kuwang ni Client" + kuwangNiClient, Toast.LENGTH_SHORT).show();
                                            return;
                                        }
                                        Log.e("Kuwang Ni Client",""+ kuwangNiClient);


                                        // Can be reused
                                        int Phase2OrderID = selectedOrder.getOrderID();

                                        // Set Phase1Order Total Paid
                                        dashboardController.updatePhase1OrderTotalPaid(selectedOrder.getPhase2_phase1OrderID(),Integer.parseInt(amountReceived) ,getBaseContext());

                                        // update Phase1 total due
                                        // TODO - 50 kuwang ni client hardcoded
                                        dashboardController.updatePhase1OrderTotalDue(selectedOrder.getPhase2_phase1OrderID(),kuwangNiClient-50,getBaseContext());

                                        // Send message to the client that gcash reference did not match
                                        String notificationTitle = selectedOrder.getWasher().getShopName() + " - Insufficient Payment";
                                        String notificationMessage = "The payment is insufficient. Please make sure \nyou paid the exact amount or communicate \nwith us. Please call us at " + selectedOrder.getWasher().getContactNo();
                                        // Sending Notification to Client
                                        dashboardController.sendNotifications(0, selectedOrder.getClient().getCustomerID(), 0, notificationTitle, notificationMessage, getBaseContext());

                                        // Set phase1order to 6
                                        dashboardController.updatePhase1OrderStatus(selectedOrder.getPhase2_phase1OrderID(), 6, getBaseContext());


                                        // Set phase2Order to -1
                                        dashboardController.updatePhase2OrderStatus(Phase2OrderID, -1, getBaseContext());

                                        // update Phase 2 Total Paid
                                        dashboardController.updatePhase2OrderTotalPaid(Phase2OrderID,Integer.parseInt(amountReceived),getBaseContext());

                                        Toast.makeText(getApplicationContext(), "Client has been Notified", Toast.LENGTH_SHORT).show();

                                        Intent intent = new Intent(WasherDashboardClothesToReturnClientPaymentConfirmation.this, WasherDashboardActivity.class);
                                        startActivity(intent);


                                        Toast.makeText(getApplicationContext(), "Client has been notified", Toast.LENGTH_SHORT).show();
                                    })
                                    .setNegativeButton("No", (nextDialog, nextWhich) -> {
                                        Toast.makeText(getApplicationContext(), "No Action Taken", Toast.LENGTH_SHORT).show();
                                    })
                                    .show();
                        });

                        amountDialogBuilder.setNegativeButton("Cancel", (amountDialog, amountWhich) -> {
                            amountDialog.cancel();
                            Toast.makeText(getApplicationContext(), "No Action Taken", Toast.LENGTH_SHORT).show();
                        });

                        // Show the amount input dialog
                        amountDialogBuilder.show();
                    })
                    .setNeutralButton("Not Take action", (dialog3, which3) -> {
                        Toast.makeText(getApplicationContext(), "No Action Taken", Toast.LENGTH_SHORT).show();
                    })
                    .show();
        });



    }

}
