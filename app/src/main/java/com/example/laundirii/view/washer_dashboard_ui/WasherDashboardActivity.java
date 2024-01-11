package com.example.laundirii.view.washer_dashboard_ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.laundirii.R;
import com.example.laundirii.controller.DashboardController;
import com.example.laundirii.databinding.ActivityWasherDashboardUiBinding;
import com.example.laundirii.model.Orders;
import com.example.laundirii.model.Phase1Order;
import com.example.laundirii.model.Phase2Order;
import com.example.laundirii.model.Washer;
import com.example.laundirii.view.LoginActivity;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class WasherDashboardActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityWasherDashboardUiBinding binding;
    private DashboardController dashboardController;
    private Washer washer;
    private TextView WasherNavNameText;
    private TextView WashernavPhoneNumberText;

    private Switch washerSwitchStatus;
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
                R.id.washer_history_fragment, R.id.washer_pendingrequest_fragment, R.id.washer_nav_notification,
                R.id.washer_received_clothes_fragment,R.id.washer_clothes_to_return_fragment)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_washer_dashboard_ui);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


//        //initialize
        View headerView = binding.navView.getHeaderView(0);
        WasherNavNameText = headerView.findViewById(R.id.WasherNavNameText);
        WashernavPhoneNumberText = headerView.findViewById(R.id.WashernavPhoneNumberText);
        washerSwitchStatus = findViewById(R.id.washerSwitchStatus);

        //shared Preferences
        SharedPreferences sharedPreferences = getSharedPreferences("LoginWasherPreferences", Context.MODE_PRIVATE);
        String washerUsername = sharedPreferences.getString("washerUsername", "");
        //getting washer
        dashboardController = new DashboardController();
        this.washer = dashboardController.getWasher(washerUsername, this);
//        // instantiate navigation bar values
        WasherNavNameText.setText("Good day, " + washer.getShopName() + "!");
        WashernavPhoneNumberText.setText("Contact No: " + washer.getContactNo());
        // check all pending deliveries on phase order 1 and 2
        // 1,2,3
//        List<Phase1Order> list1 = dashboardController.getAllPhase1PendingDeliveries(washer.getWasherID(),getBaseContext());
        // 10,11,12,13
//        List<Phase2Order> list2 = dashboardController.getAllPhase2PendingDeliveries(washer.getWasherID(),getBaseContext());

        // set for shop status and set its value to the previous value upon on create

            // check its pending deliveries
            int statusSetter = dashboardController.getWasherPhasePendingTransaction(washer.getWasherID(),getBaseContext());

            // if washer status is 1 or 0
            int washeractive = dashboardController.getWasherStatus(washer.getWasherID(),getBaseContext());
            Log.e("PENDINGGG",""+statusSetter);
        if(statusSetter > 0 && washeractive == 0){
            washerSwitchStatus.setText("Pending");
            washerSwitchStatus.setChecked(false);
        }else if (statusSetter < 1 && washeractive == 0){
            washerSwitchStatus.setText("Off");
            washerSwitchStatus.setChecked(false);
        }else {
            washerSwitchStatus.setText("On");
            washerSwitchStatus.setChecked(true);
        }

        // TODO: auto cancel if more that 2days and still not settle the deliveries


        // send notification penalty


        washerSwitchStatus.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            if (isChecked) {
                // set washer status to 1
                dashboardController.updateWasherStatus(washer.getWasherID(),1,getBaseContext());

                String notificationTitle3 = washer.getShopName()+ " - Shop Open Notice";
                String notificationMessage3 = "We are now open. And we are ready to receive and delivery orders";

                // client with status (11,12,13,14,15) will receive notification
                List<Phase2Order> Phase2list = dashboardController.getWasherPhase2StatusGetter(washer.getWasherID(), getBaseContext());
//                Phase2list.forEach(client -> {dashboardController.sendNotifications(0, client.getClient().getCustomerID(), 0, notificationTitle3, notificationMessage3, getBaseContext());});

                // client with status (0,1,2,3) will receive notification
                List<Phase1Order> Phase1list = dashboardController.getWasherPhase1StatusGetter(washer.getWasherID(),getBaseContext());
//                Phase1list.forEach(client -> {dashboardController.sendNotifications(0, client.getClient().getCustomerID(), 0, notificationTitle3, notificationMessage3, getBaseContext());});

                List<Orders> sendnotif = new ArrayList<>();
                sendnotif.addAll(Phase1list);
                sendnotif.addAll(Phase2list);

                HashMap<Integer, Integer> map = new HashMap<>();
                for (Orders orders : sendnotif) {
                    if (orders instanceof Phase1Order) {
                        int value = ((Phase1Order) orders).getClient().getCustomerID();
                        map.put(value, value);  // Using put directly, as it replaces the value for an existing key
                    }
                    if (orders instanceof Phase2Order) {
                        int value = ((Phase2Order) orders).getClient().getCustomerID();
                        map.put(value, value);  // Using put directly, as it replaces the value for an existing key
                    }
                }

                map.forEach((key, value) -> {
                    dashboardController.sendNotifications(0, key, 0, notificationTitle3, notificationMessage3, getBaseContext());
                });


                washerSwitchStatus.setChecked(true);
                washerSwitchStatus.setText("On");
            } else if(isChecked == false){
                View datePickerView = getLayoutInflater().inflate(R.layout.washer_date_picker_layout, null);
                DatePicker datePicker = datePickerView.findViewById(R.id.datePicker);

                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.DAY_OF_MONTH, 15);
                datePicker.setMaxDate(calendar.getTimeInMillis());

                Calendar calendar2 = Calendar.getInstance();
                calendar2.add(Calendar.DAY_OF_MONTH, 3);  // Corrected line
                datePicker.setMinDate(calendar2.getTimeInMillis());

                new AlertDialog.Builder(this)
                        .setTitle("Closing The Shop")
                        .setMessage("Set a date to open the shop")
                        .setView(datePickerView)
                        .setPositiveButton("Continue", (dialog, which) -> {
                            // Retrieve the selected date
                            int year = datePicker.getYear();
                            int month = datePicker.getMonth();
                            int dayOfMonth = datePicker.getDayOfMonth();

                            // TODO: Add logic to handle when the user confirms closing the shop

                            // washer status to 0
                            dashboardController.updateWasherStatus(washer.getWasherID(),0,getBaseContext());


                            // check if there are pending deliveries and notify the clients

                            // Send notification to all phase1 Orders Clients
//                            String notificaitonTitle = washer.getShopName() + " - Important Shop Closing Notice";
//                            String notificationMessage = "Dear valued customer,\n\n" +
//                                    "We hope this message finds you well. We would like to inform you that our shop, " + washer.getShopName() +
//                                    ", will be close and will reopen on " +
//                                    year + "-" + (month + 1) + "-" + dayOfMonth + ".";
//
//                            // sending the message using controller
//                            // client with status (0,1,2,3) will receive notification
////                            List<Phase1Order> Phase1list = dashboardController.getPendingDeliveriesOnWasher(washer.getWasherID(), getBaseContext());
//                            //TODO send notifiction to phase1 order 0,1,2,3,4,5,6
//
                            List<Phase1Order> Phase1list = dashboardController.getWasherPhase1StatusGetter(washer.getWasherID(),getBaseContext());
//                            Log.e("PHASE1 LLIST","" + Phase1list.size() );
//                            Log.e("PHASE1 LLIST","" + washer.getWasherID() );
//                            Phase1list.forEach(client -> {
//                                dashboardController.sendNotifications(0, client.getClient().getCustomerID(), 0, notificaitonTitle, notificationMessage, getBaseContext());});

                            // send notification to all phase2 Orders Clients
                            String notificaitonTitle1 = washer.getShopName() + " - Shop Closing Notice";
                            String notificationMessage2 = "We will be closing our shop and expected \n" +
                                    "to be back on " + year + "-" + (month + 1) + "-" + dayOfMonth +"." +
                                    "You can collect your clothes within 2 days.\n"+
                                    "\nYou also can process a collect on your \n" +
                                    "clothes before our shop closes. For \n" +
                                    "those with incoming or outcoming deliveries \n" +
                                    "dont worry we will make sure to process you \n" +
                                    "laundry. \n";

                            // sending the message using controller
                            List<Phase2Order> Phase2list = dashboardController.getWasherPhase2ClohtesToReturn(washer.getWasherID(), getBaseContext());
//                            //TODO send notification to phase2 Order 0,11,12,13,14,15,20,21,22
//                            List<Phase2Order> Phase2list = dashboardController.getWasherPhase2StatusGetter(washer.getWasherID(), getBaseContext());
//                            // client with status (11,12,13,14,15) will receive notification
//                            Phase2list.forEach(client -> {
//                                    dashboardController.sendNotifications(0, client.getClient().getCustomerID(), 0, notificaitonTitle1, notificationMessage2, getBaseContext());
//                                });

                            List<Orders> sendnotif = new ArrayList<>();
                            sendnotif.addAll(Phase1list);
                            sendnotif.addAll(Phase2list);

                            HashMap<Integer, Integer> map = new HashMap<>();
                            for (Orders orders : sendnotif) {
                                if (orders instanceof Phase1Order) {
                                    int value = ((Phase1Order) orders).getClient().getCustomerID();
                                    map.put(value, value);  // Using put directly, as it replaces the value for an existing key
                                }
                                if (orders instanceof Phase2Order) {
                                    int value = ((Phase2Order) orders).getClient().getCustomerID();
                                    map.put(value, value);  // Using put directly, as it replaces the value for an existing key
                                }
                            }

                            map.forEach((key, value) -> {
                                dashboardController.sendNotifications(0, key, 0, notificaitonTitle1, notificationMessage2, getBaseContext());
                            });


                            //TODO
                            // send message to self regarding make sure there are no pending deliveries within next 2 days
                            String notificationTitle3 = "System Notice";
                            String notificationMessage3 = "Please make sure to deliver all pending deliveries withn 2 days or the shop will face consequenses";
                            dashboardController.sendNotifications(washer.getWasherID(),0,0,notificationTitle3,notificationMessage3,getBaseContext());


                            // check pending deliveries on phase1 and 2
                            int washerPendingTransaction = dashboardController.getWasherPhasePendingTransaction(washer.getWasherID(),getBaseContext());
                            if( washerPendingTransaction > 0){
                                washerSwitchStatus.setText("Pending");
                                washerSwitchStatus.setChecked(false);
                            }else{
                                washerSwitchStatus.setText("Off");
                                washerSwitchStatus.setChecked(false);
                            }

                            Toast.makeText(this, "Client have been notified", Toast.LENGTH_SHORT).show();
                        })
                        .setNegativeButton("No", (dialog, which) -> {


                            washerSwitchStatus.setChecked(true);
                            washerSwitchStatus.setText("On");
                        })
                        .show();
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.washer_action_profile) {
            // Handle the profile item click here
            Intent intent = new Intent(this, WasherDashboardAcitivityProfile.class); // Replace YourProfileActivity with the actual class name for your profile
            intent.putExtra("washer",washer);
            startActivity(intent);
            return true;
        }else if (id == R.id.washer_action_logout) {
            // Handle the logout item click here
            // Clear SharedPreferences
            SharedPreferences sharedPreferences = getSharedPreferences("LoginWasherPreferences", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();

            // Redirect to LoginActivity
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
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