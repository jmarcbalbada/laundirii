package com.example.laundirii.view.courier_dashboard_ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.example.laundirii.R;
import com.example.laundirii.controller.DashboardController;
import com.example.laundirii.databinding.ActivityCourierDashboardBinding;
import com.example.laundirii.model.Courier;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

public class CourierDashboardActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityCourierDashboardBinding binding;
    private DashboardController dashboardController;
    private Courier courier;
    private TextView navCourierNameText;
    private TextView navPhoneNoText;
    private Switch courierSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCourierDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarCourierDashboard.toolbar);
        binding.appBarCourierDashboard.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.cour_nav_home, R.id.cour_nav_history, R.id.cour_nav_logout)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_courier_dashboard);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        //initialize
        View headerView = binding.navView.getHeaderView(0);
        navCourierNameText = headerView.findViewById(R.id.navCourierName);
        navPhoneNoText = headerView.findViewById(R.id.navPhoneNo);
        setCourier();

        // instantiate navigation bar values
        navCourierNameText.setText("Good day, " + courier.getName() + "!");
        navPhoneNoText.setText("Contact No: " + courier.getContactNo());
        courierSwitch = findViewById(R.id.courierSwitchStatus);
        int status = 0;
        if (courier.getStatus())
        {
            status = 1;
        }
        switch(status)
        {
            // OFF - COURIER STATUS
            case 0:
                courierSwitch.setChecked(false);
                courierSwitch.setText("OFF");
                boolean hasActive = dashboardController.hasActiveTransactionOnPhase1Order(courier.getCourierID(),this);
                if(hasActive)
                {
                    // do NOT enable switch
                    courierSwitch.setEnabled(false);
                }
                else
                {
                    courierSwitch.setEnabled(true);
                }
                break;
            // ON - COURIER STATUS
            case 1:
                courierSwitch.setChecked(true);
                courierSwitch.setText("ON");
                courierSwitch.setEnabled(true);
                break;
        }

        courierSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                // perform logic if ON
                if(isChecked)
                {
                    dashboardController.setCourierStatusOnDatabase(courier.getCourierID(),true,CourierDashboardActivity.this.getApplicationContext());
                    courierSwitch.setText("ON");
                }
                else
                {
                    dashboardController.setCourierStatusOnDatabase(courier.getCourierID(),false,CourierDashboardActivity.this.getApplicationContext());
                    courierSwitch.setText("OFF");
                }
            }
        });
    }

    private void setCourier()
    {
        // Retrieve shared preferences
        SharedPreferences sharedPreferences = getSharedPreferences("LoginCourierPreferences", Context.MODE_PRIVATE);
        String courierUsername = sharedPreferences.getString("courierUsername", "");

        dashboardController = new DashboardController();

        // fetch courier
        courier = dashboardController.getCourier(courierUsername, this);
        Log.e("DashboardCourier", courier.toString());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.courier_dashboard, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_courier_dashboard);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

}