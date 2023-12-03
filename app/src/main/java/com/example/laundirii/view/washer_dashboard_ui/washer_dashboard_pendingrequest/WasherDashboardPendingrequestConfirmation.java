package com.example.laundirii.view.washer_dashboard_ui.washer_dashboard_pendingrequest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.example.laundirii.R;
import com.example.laundirii.model.Phase1Order;

public class WasherDashboardPendingrequestConfirmation extends AppCompatActivity {

    TextView cliendIDText, CourierNameText, ClientNameText, AmountToReceiveText;
    Button receivedButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.washer_dashboard_fragment_pendingrequest_confirmation_activity);

        // Locate the button ID
        Phase1Order selectedOrder = (Phase1Order) getIntent().getSerializableExtra("selectedOrder");
        cliendIDText = findViewById(R.id.washer_dashboard_fragment_pendingrequest_confirmation_acitivity_cliendIDText);
        CourierNameText = findViewById(R.id.washer_dashboard_fragment_pendingrequest_confirmation_acitivity_courierNameText);
        ClientNameText = findViewById(R.id.washer_dashboard_fragment_pendingrequest_confirmation_acitivity_clientNameText);
        AmountToReceiveText = findViewById(R.id.washer_dashboard_fragment_pendingrequest_confirmation_acitivity_amountDueText);
        receivedButton = findViewById(R.id.washer_dashboard_fragment_pendingrequest_confirmation_acitivity_receivedButton);

        // Set Value of Buttons and Text
        cliendIDText.setText(Integer.toString(selectedOrder.getClientID()));
        CourierNameText.setText(selectedOrder.getCourier().getName());
        ClientNameText.setText(selectedOrder.getCourier().getName());
        AmountToReceiveText.setText(selectedOrder.getCourier().getName());

        Log.e("YAWA",Integer.toString(selectedOrder.getClientID()));


        receivedButton.callOnClick();

    }
}