package com.example.laundirii.view.courier_dashboard_ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.laundirii.R;
import com.example.laundirii.controller.DashboardController;
import com.example.laundirii.databinding.ActivityCourierDashboardBinding;
import com.example.laundirii.model.Courier;
import com.example.laundirii.view.LoginActivity;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

public class CourierDashboardActivity extends AppCompatActivity{

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityCourierDashboardBinding binding;
    private DashboardController dashboardController;
    private Courier courier;
    private TextView navCourierNameText;
    private TextView navPhoneNoText;
    private Switch courierSwitch;
    public static int notification_counter_number = 0;
    public static TextView notificationCounter;

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
                R.id.cour_nav_home, R.id.cour_nav_history, R.id.cour_nav_notification)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_courier_dashboard);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        setCourier();
        notification_counter_number = dashboardController.getUnreadNotificationCount(courier.getCourierID(),1,this);
        if(notification_counter_number > 0)
        {
            LayoutInflater li = LayoutInflater.from(CourierDashboardActivity.this);
            notificationCounter = (TextView) li.inflate(R.layout.notification_counter, null);
            navigationView.getMenu().findItem(R.id.cour_nav_notification).setActionView(notificationCounter);
            showNotificationCounter(notification_counter_number);
        }

        //initialize
        View headerView = binding.navView.getHeaderView(0);
        navCourierNameText = headerView.findViewById(R.id.navCourierName);
        navPhoneNoText = headerView.findViewById(R.id.navPhoneNo);

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
                boolean hasActivePhase1 = dashboardController.hasActiveTransactionOnPhase1Order(courier.getCourierID(),this);
                boolean hasActivePhase2 = dashboardController.hasActiveTransactionOnPhase2Order(courier.getCourierID(),this);
                Log.e("ID ON ACTIVITY", courier.getCourierID() + "");
                Log.e("COURIER STATUS ON ACTIVITY", courier.getStatus() + "");
                Log.e("PHASE1 ON ACTIVITY", hasActivePhase1 + "");
                Log.e("PHASE2 ON ACTIVITY", hasActivePhase2 + "");
                if(hasActivePhase1 || hasActivePhase2)
                {
                    // do NOT enable switch
                    courierSwitch.setEnabled(false);
                    courierSwitch.setText("PENDING");
                }
                else
                {
                    courierSwitch.setEnabled(true);
                    courierSwitch.setText("OFF");
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
                recreate();
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
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.courier_action_logout){
            SharedPreferences sharedPreferences = getSharedPreferences("LoginCourierPreferences", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();

            // Redirect to LoginActivity
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
        if(id == R.id.courier_action_refresh) {
            recreate();
        }
        return super.onOptionsItemSelected(item);
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