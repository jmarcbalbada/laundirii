package com.example.laundirii.view.client_dashboard_ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.laundirii.R;
import com.example.laundirii.controller.DashboardController;
import com.example.laundirii.databinding.ClientBookServiceBinding;
import com.example.laundirii.model.Client;
import com.example.laundirii.model.Washer;

import java.util.ArrayList;
import java.util.List;

public class BookServiceActivity extends AppCompatActivity {
    private ClientBookServiceBinding binding;
    private ListView lv_washers;
    private Client client;
    private ArrayAdapter availableWashersAdapter;
    private DashboardController dashboardController;
    private Button cancelButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ClientBookServiceBinding.inflate(getLayoutInflater());
        setContentView(R.layout.client_book_service);
        dashboardController = new DashboardController();
        lv_washers = findViewById(R.id.lv_washersList);
        displayAvailableWashers();
        cancelButton = findViewById(R.id.book_service_cancel_button);
        SharedPreferences sharedPreferences = getSharedPreferences("LoginClientPreferences", Context.MODE_PRIVATE);
        // Retrieve values from SharedPreferences
        String clientUsername = sharedPreferences.getString("clientUsername", "");
        client = dashboardController.getClient(clientUsername, this);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BookServiceActivity.this, ClientDashboardActivity.class);
                startActivity(intent);
            }
        });
//
        lv_washers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Washer washer = (Washer) adapterView.getItemAtPosition(i);

                // Create a custom layout for the dialog
                View dialogView = getLayoutInflater().inflate(R.layout.dialog_schedule_input, null);

                // Initialize EditText for initial load
                EditText editTextInitialLoad = dialogView.findViewById(R.id.editTextInitialLoad);

                // Create an AlertDialog
                AlertDialog.Builder builder = new AlertDialog.Builder(BookServiceActivity.this);
                builder.setView(dialogView)
                        .setTitle("Schedule for " + washer.getShopName())
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                // Get selected month from the spinner

                                // Get date and time from the EditText fields
                                int initialLoad = Integer.parseInt(editTextInitialLoad.getText().toString());

                                // Perform actions with the selected schedule
                                // For example, display a toast with the selected information
                                boolean inserted = dashboardController.insertPhase1Order(client.getCustomerID(),washer.getWasherID(),initialLoad,BookServiceActivity.this);
                                if(inserted)
                                {
                                    Toast.makeText(getApplicationContext(), "Booked successfully!", Toast.LENGTH_SHORT).show();
                                    String notificationTitle = "You received a booking from " + client.getName() + "!";
                                    String notificationMessage = "Please review the transaction and start accepting orders! ";
                                    //send notification to washer
                                    dashboardController.sendNotifications(washer.getWasherID(),0,0,notificationTitle,notificationMessage,getBaseContext());
                                    //send notification to client
                                    notificationTitle = "We received your booking!";
                                    notificationMessage = "Your order is being reviewed by the Washer!";
                                    dashboardController.sendNotifications(0,client.getCustomerID(),0,notificationTitle,notificationMessage,getBaseContext());
                                    Intent intent = new Intent(BookServiceActivity.this, ClientDashboardActivity.class);
                                    startActivity(intent);
                                }
                                else {
                                    Toast.makeText(getApplicationContext(), "Something went wrong!", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(BookServiceActivity.this, ClientDashboardActivity.class);
                                    startActivity(intent);
                                }
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                // User clicked cancel
                                dialogInterface.dismiss();
                            }
                        });

                // Show the AlertDialog
                builder.create().show();
            }
        });


//        lv_washers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Washer washer = (Washer) adapterView.getItemAtPosition(i);
//                Log.e("SelectedWasher", washer.toString());
//                Toast.makeText(getApplicationContext(), "Selected: " + washer.getShopName(), Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    public void displayAvailableWashers() {
        List<Washer> availableWashers = new ArrayList<>();
        availableWashers = dashboardController.getAvailableWashers(this);
        availableWashersAdapter = new ArrayAdapter<Washer>(this, android.R.layout.simple_list_item_1, availableWashers);
        lv_washers.setAdapter(availableWashersAdapter);
    }
}
