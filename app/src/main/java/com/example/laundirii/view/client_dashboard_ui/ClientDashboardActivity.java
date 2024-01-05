package com.example.laundirii.view.client_dashboard_ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.laundirii.R;
import com.example.laundirii.controller.DashboardController;
import com.example.laundirii.model.Client;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.laundirii.databinding.ActivityClientDashboardBinding;

public class ClientDashboardActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityClientDashboardBinding binding;
    private Client client;
    private TextView navClientText, navPhoneNoText;
    private DashboardController dashboardController;
    public static int notification_counter_number = 0;
    public static TextView notificationCounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityClientDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarClientDashboard.toolbar);
        binding.appBarClientDashboard.toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // None
            }
        });

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top-level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_current, R.id.nav_history, R.id.client_nav_notification)
                .setOpenableLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_client_dashboard);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        dashboardController = new DashboardController();
        setClient();
        notification_counter_number = dashboardController.getUnreadNotificationCount(client.getCustomerID(),0,this);
        if(notification_counter_number > 0)
        {
            LayoutInflater li = LayoutInflater.from(ClientDashboardActivity.this);
            notificationCounter = (TextView) li.inflate(R.layout.notification_counter, null);
            navigationView.getMenu().findItem(R.id.client_nav_notification).setActionView(notificationCounter);
            showNotificationCounter(notification_counter_number);
        }


        // initialize
        View headerView = binding.navView.getHeaderView(0);
        navClientText = headerView.findViewById(R.id.navClientName);
        navPhoneNoText = headerView.findViewById(R.id.navClientPhoneNo);

        // instantiate navigation bar values
        navClientText.setText("Good day, " + client.getName() + "!");
        navPhoneNoText.setText("Contact No: " + client.getContactNo());
    }

    private void setClient()
    {
        // Retrieve shared preferences
        SharedPreferences sharedPreferences = getSharedPreferences("LoginClientPreferences", Context.MODE_PRIVATE);
        // Retrieve values from SharedPreferences
        String clientUsername = sharedPreferences.getString("clientUsername", "");

        // Now, you can use these values as needed in CourierDashboardActivity
        client = dashboardController.getClient(clientUsername, this);
    }

    public static void showNotificationCounter(int count)
    {
        if (notificationCounter != null) {
            if (count > 0) {
                notificationCounter.setText(String.valueOf(count));
                notificationCounter.setVisibility(View.VISIBLE);
            } else {
                notificationCounter.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.client_dashboard, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_client_dashboard);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}