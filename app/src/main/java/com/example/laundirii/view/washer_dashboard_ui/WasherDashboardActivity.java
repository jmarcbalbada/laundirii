package com.example.laundirii.view.washer_dashboard_ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.laundirii.R;
import com.example.laundirii.controller.DashboardController;
import com.example.laundirii.databinding.ActivityWasherDashboardUiBinding;
import com.example.laundirii.model.Washer;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

public class WasherDashboardActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityWasherDashboardUiBinding binding;
    private DashboardController dashboardController;
    private Washer washer;
    private TextView WasherNavNameText;
    private TextView WashernavPhoneNumberText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityWasherDashboardUiBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarWasherDashboard.toolbar);
        binding.appBarWasherDashboard.fab.setOnClickListener(new View.OnClickListener() {
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
                R.id.washer_history_fragment, R.id.washer_pendingrequest_fragment, R.id.washer_nav_notification,R.id.washer_received_clothes_fragment,R.id.washer_clothes_to_return_fragment)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_washer_dashboard_ui);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


//        //initialize
        View headerView = binding.navView.getHeaderView(0);
        WasherNavNameText = headerView.findViewById(R.id.WasherNavNameText);
        WashernavPhoneNumberText = headerView.findViewById(R.id.WashernavPhoneNumberText);
        //shared Preferences
        SharedPreferences sharedPreferences = getSharedPreferences("LoginWasherPreferences", Context.MODE_PRIVATE);
        String washerUsername = sharedPreferences.getString("washerUsername", "");
        //getting washer
        dashboardController = new DashboardController();
        this.washer = dashboardController.getWasher(washerUsername, this);
//        // instantiate navigation bar values
        WasherNavNameText.setText("Good day, " + washer.getShopName() + "!");
        WashernavPhoneNumberText.setText("Contact No: " + washer.getContactNo());

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.washer_dashboard_ui, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_washer_dashboard_ui);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}