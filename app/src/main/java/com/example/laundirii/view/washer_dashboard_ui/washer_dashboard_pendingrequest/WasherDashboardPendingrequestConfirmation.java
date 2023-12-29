package com.example.laundirii.view.washer_dashboard_ui.washer_dashboard_pendingrequest;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.laundirii.R;
import com.example.laundirii.model.Phase1Order;

public class WasherDashboardPendingrequestConfirmation extends AppCompatActivity {

    TextView clientIDText, CourierNameText, ClientNameText, AmountToReceiveText;
    Button receivedButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.washer_dashboard_fragment_pendingrequest_confirmation_activity);

        // Locate the button ID
        Phase1Order selectedOrder = (Phase1Order) getIntent().getSerializableExtra("selectedOrder");
        clientIDText = findViewById(R.id.washer_dashboard_fragment_pendingrequest_confirmation_acitivity_cliendIDText);
        CourierNameText = findViewById(R.id.washer_dashboard_fragment_pendingrequest_confirmation_acitivity_courierNameText);
        ClientNameText = findViewById(R.id.washer_dashboard_fragment_pendingrequest_confirmation_acitivity_clientNameText);
        AmountToReceiveText = findViewById(R.id.washer_dashboard_fragment_pendingrequest_confirmation_acitivity_amountDueText);
        receivedButton = findViewById(R.id.washer_dashboard_fragment_pendingrequest_confirmation_acitivity_receivedButton);

        // Set Value of Buttons and Text
        clientIDText.setText("Client ID: " + String.valueOf(selectedOrder.getClientID()));
        CourierNameText.setText("Courier Name: " + selectedOrder.getCourier().getName());
        ClientNameText.setText("Client Name: "+selectedOrder.getClient().getName());
        AmountToReceiveText.setText("Amount to Receive: " + selectedOrder.getCourier().getName());

        Log.e("YAWA",Integer.toString(selectedOrder.getClientID()));

        receivedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(WasherDashboardPendingrequestConfirmation.this)
                        .setTitle("Confirmation")
                        .setMessage("Are you sure you want to show the toast?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // User clicked Yes, show the toast
                                Toast.makeText(getApplicationContext(), "HELLO", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // User clicked No, show a different toast
                                Toast.makeText(getApplicationContext(), "OMG", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .show();
            }
        });


    }
}