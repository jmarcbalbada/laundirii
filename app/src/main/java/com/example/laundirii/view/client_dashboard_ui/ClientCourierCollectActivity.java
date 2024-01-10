package com.example.laundirii.view.client_dashboard_ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.laundirii.R;
import com.example.laundirii.controller.DashboardController;
import com.example.laundirii.databinding.ClientCourierCollectLayoutBinding;
import com.example.laundirii.model.Client;
import com.example.laundirii.model.Phase2Order;

public class ClientCourierCollectActivity extends AppCompatActivity {
    private ClientCourierCollectLayoutBinding binding;
    private Client client;
    private DashboardController dashboardController;
    private Button cancelButton;
    private Button proceedButton;
    private EditText refNoText;
    String refNoTextStr;
    private Phase2Order phase2OrderSelected;
    private TextView washerPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ClientCourierCollectLayoutBinding.inflate(getLayoutInflater());
        setContentView(R.layout.client_courier_collect_layout);
        dashboardController = new DashboardController();
        SharedPreferences sharedPreferences = getSharedPreferences("LoginClientPreferences", Context.MODE_PRIVATE);
        // Retrieve values from SharedPreferences
        String clientUsername = sharedPreferences.getString("clientUsername", "");
        client = dashboardController.getClient(clientUsername, this);
        proceedButton = findViewById(R.id.textViewCourierCollectActivityProceedButton);
        cancelButton = findViewById(R.id.textViewCourierCollectActivityCancelButton);
        refNoText = findViewById(R.id.textViewCourierCollectActivityWasherPhoneText);
        Intent intent = getIntent();
        phase2OrderSelected = (Phase2Order) intent.getSerializableExtra("PHASE2_ORDER_EXTRA");
        washerPhone = findViewById(R.id.textViewCourierCollectActivityWasherPhone);
        washerPhone.setText("Washer's GCash account:\t\t" + phase2OrderSelected.getWasher().getContactNo());
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ClientCourierCollectActivity.this, ClientDashboardActivity.class);
                startActivity(intent);
            }
        });
        proceedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refNoTextStr = refNoText.getText().toString();
                if(refNoTextStr.isEmpty())
                {
                    refNoText.setError("Fields required!");
                    Toast.makeText(getApplicationContext(), "Sup", Toast.LENGTH_SHORT).show();
                }else
                {
                    Log.e("Reference Number GCASHHH",""+refNoTextStr);
                    boolean success = dashboardController.updateReferenceNo(phase2OrderSelected.getOrderID(),refNoTextStr, getBaseContext());
                    dashboardController.updatePhase2OrderStatus(phase2OrderSelected.getOrderID(), 10, getBaseContext());
                    if(success)
                    {
                        // send notification to washer
                        String notificationTitle = "[COURIER-COLLECT] - Client booked for collect laundry!";
                        String notificationMessage = "Client under the order ID " + phase2OrderSelected.getOrderID() + " selected Courier-Collect, please review the payment.";
                        dashboardController.sendNotifications(phase2OrderSelected.getWasherID(), 0,0,notificationTitle,notificationMessage,getBaseContext());
                        Toast.makeText(getApplicationContext(), "Waiting for washer to accept!", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "Something went wrong!", Toast.LENGTH_SHORT).show();
                    }
                    Intent intent = new Intent(ClientCourierCollectActivity.this, ClientDashboardActivity.class);
                    startActivity(intent);
                }

            }
        });
    }
}
