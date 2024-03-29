package com.example.laundirii.view;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.laundirii.R;
import com.example.laundirii.controller.DashboardController;
import com.example.laundirii.controller.RegisterAndLoginController;
import com.example.laundirii.model.Client;
import com.example.laundirii.model.Courier;
import com.example.laundirii.model.Washer;
import com.example.laundirii.view.client_dashboard_ui.ClientDashboardActivity;
import com.example.laundirii.view.courier_dashboard_ui.CourierDashboardActivity;
import com.example.laundirii.view.washer_dashboard_ui.WasherDashboardActivity;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {

    RegisterAndLoginController loginController;
    Button loginButton;
    String username, password;
    TextInputEditText loginUsernameText;
    TextInputEditText loginPasswordText;
    TextView test, signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginButton = findViewById(R.id.loginButton);
        loginUsernameText = findViewById(R.id.loginUsernameText);
        loginPasswordText = findViewById(R.id.loginPasswordText);
        test = findViewById(R.id.labelSignin);
        loginController = new RegisterAndLoginController();
        signup = findViewById(R.id.signupText);
        //DashboardController dashboardController = new DashboardController();
        //boolean inserted = dashboardController.insertDummyValuesOnOrder(this);
        //PHASE1_PAYMENT_STATUS

//        DashboardController dashboardController = new DashboardController();
//        for (int i = 0; i < 5; i++){
//            boolean inserted = dashboardController.insertDummyPhase1Order(this);
//        }
////        Log.e("DUMMY", inserted + "");

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = loginUsernameText.getText().toString();
                password = loginPasswordText.getText().toString();
                if(username.isEmpty())
                {
                    loginUsernameText.setError("Fields required!");
                }

                if(password.isEmpty())
                {
                    loginPasswordText.setError("Fields required!");
                }

                int type = typeOfUser(username);
                // check if successful
                // TODO Implement redirect to dashboard when log in
                switch (type) {
                    case 0:
                        if(loginController.loginClient(username,password,LoginActivity.this))
                        {
                            DashboardController dashboardController = new DashboardController();
                            Client client = dashboardController.getClient(username,LoginActivity.this);
                            // Get SharedPreferences instance
                            SharedPreferences sharedPreferences = getSharedPreferences("LoginClientPreferences", Context.MODE_PRIVATE);

//                          // Create an editor to modify SharedPreferences
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("clientUsername", client.getUsername());
                            editor.apply();

                            Toast.makeText(getApplicationContext(), "Logging in as Client", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, ClientDashboardActivity.class);
                            startActivity(intent);
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), "Invalid username or password!", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case 1:
                        if(loginController.loginCourier(username,password,LoginActivity.this))
                        {
                            DashboardController dashboardController = new DashboardController();
                            Courier courier = dashboardController.getCourier(username,LoginActivity.this);
                            // Get SharedPreferences instance
                            SharedPreferences sharedPreferences = getSharedPreferences("LoginCourierPreferences", Context.MODE_PRIVATE);

//                          // Create an editor to modify SharedPreferences
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("courierUsername", courier.getUsername());
                            editor.apply();

                            Toast.makeText(getApplicationContext(), "Logging in as Courier", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, CourierDashboardActivity.class);
                            startActivity(intent);
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), "Invalid username or password!", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case 2:
                        if(loginController.loginWasher(username,password,LoginActivity.this))
                        {
                            DashboardController dashboardController = new DashboardController();
                            Washer washer = dashboardController.getWasher(username,LoginActivity.this);
                            // Get SharedPreferences instance
                            SharedPreferences sharedPreferences = getSharedPreferences("LoginWasherPreferences", Context.MODE_PRIVATE);

//                          // Create an editor to modify SharedPreferences
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("washerUsername", washer.getUsername());
                            editor.apply();

                            Toast.makeText(getApplicationContext(), "Logging in as Washer", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, WasherDashboardActivity.class);
                            startActivity(intent);
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), "Invalid username or password!", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    default:
                        Toast.makeText(getApplicationContext(), "Invalid username or password!", Toast.LENGTH_LONG).show();
                }
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private int typeOfUser(String username)
    {
        // 0 - Client, 1 - Courier, 2 - Washer, -1 - No entry
        return loginController.typeOfUser(username,this);
    }

}