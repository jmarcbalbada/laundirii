package com.example.laundirii.view.client_dashboard_ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.laundirii.R;
import com.example.laundirii.controller.DashboardController;
import com.example.laundirii.databinding.ClientSelfCollectLayoutBinding;
import com.example.laundirii.model.Client;
import com.example.laundirii.model.Phase2Order;

public class ClientSelfCollectActivity extends AppCompatActivity {
    private ClientSelfCollectLayoutBinding binding;
    private Client client;
    private DashboardController dashboardController;
    private Phase2Order phase2OrderSelected;
    private Button cancelButton;
    private Button proceedButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ClientSelfCollectLayoutBinding.inflate(getLayoutInflater());
        setContentView(R.layout.client_self_collect_layout);
        dashboardController = new DashboardController();
        SharedPreferences sharedPreferences = getSharedPreferences("LoginClientPreferences", Context.MODE_PRIVATE);
        // Retrieve values from SharedPreferences
        String clientUsername = sharedPreferences.getString("clientUsername", "");
        client = dashboardController.getClient(clientUsername, this);
        proceedButton = findViewById(R.id.textViewSelfCollectActivityProceedButton);
        cancelButton = findViewById(R.id.textViewSelfCollectActivityCancelButton);
        Intent intent = getIntent();
        phase2OrderSelected = (Phase2Order) intent.getSerializableExtra("PHASE2_ORDER_EXTRA");

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ClientSelfCollectActivity.this, ClientDashboardActivity.class);
                startActivity(intent);
            }
        });
        proceedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dashboardController.updatePhase2OrderStatus(phase2OrderSelected.getOrderID(), 20, getBaseContext());
                //send notification to washer
                String notificationTitle = "[SELF-COLLECT] - Client is on the way!";
                String notificationMessage = "Client under the order ID " + phase2OrderSelected.getOrderID() + " is on the way to your shop to collect the laundry. Please prepare.";
                dashboardController.sendNotifications(phase2OrderSelected.getWasherID(), 0,0,notificationTitle,notificationMessage,getBaseContext());

                //send notification to client
                notificationTitle = "Please proceed to Washer's Shop!";
                notificationMessage = "Proceed to " + phase2OrderSelected.getWasher().getShopLocation() + " for the payment.";
                dashboardController.sendNotifications(0, phase2OrderSelected.getClientID(), 0,notificationTitle,notificationMessage,getBaseContext());
                Toast.makeText(getApplicationContext(), "Confirmed, proceed to Washer's Location!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ClientSelfCollectActivity.this, ClientDashboardActivity.class);
                startActivity(intent);

            }
        });
    }
}
