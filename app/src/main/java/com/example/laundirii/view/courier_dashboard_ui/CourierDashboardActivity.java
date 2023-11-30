package com.example.laundirii.view.courier_dashboard_ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;

import com.example.laundirii.R;
import com.example.laundirii.model.Courier;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.laundirii.databinding.ActivityCourierDashboardBinding;

public class CourierDashboardActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityCourierDashboardBinding binding;
    private Courier courier;
    public Context context;
    private TextView navCourierNameText;
    private TextView navPhoneNoText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
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
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
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
    }

    private void setCourier()
    {
        // Retrieve shared preferences
        SharedPreferences sharedPreferences = getSharedPreferences("LoginCourierPreferences", Context.MODE_PRIVATE);

        // Retrieve values from SharedPreferences
        int courierID = sharedPreferences.getInt("courierID", -1);
        String courierUsername = sharedPreferences.getString("courierUsername", "");
        String courierPassword = sharedPreferences.getString("courierPassword", "");
        String courierName = sharedPreferences.getString("courierName", "");
        String courierContactNo = sharedPreferences.getString("courierContactNo", "");
        String courierPlateNo = sharedPreferences.getString("courierPlateNo", "");
        boolean courierStatus = sharedPreferences.getBoolean("courierStatus", false);

        // Now, you can use these values as needed in CourierDashboardActivity
        courier = new Courier(courierID,courierUsername,courierPassword,courierName,courierContactNo,courierPlateNo,courierStatus);
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

    public Context getContext()
    {
        return this.context;
    }
}