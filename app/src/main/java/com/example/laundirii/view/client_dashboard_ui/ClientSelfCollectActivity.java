package com.example.laundirii.view.client_dashboard_ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.laundirii.R;
import com.example.laundirii.controller.DashboardController;
import com.example.laundirii.databinding.ClientSelfCollectLayoutBinding;
import com.example.laundirii.model.Client;

public class ClientSelfCollectActivity extends AppCompatActivity {
    private ClientSelfCollectLayoutBinding binding;
    private Client client;
    private DashboardController dashboardController;

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
    }
}
