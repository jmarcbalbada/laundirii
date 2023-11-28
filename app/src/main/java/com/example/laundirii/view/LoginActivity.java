package com.example.laundirii.view;



import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.laundirii.R;
import com.example.laundirii.controller.RegisterAndLoginController;
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
                switch (type) {
                    case 0:
                        if(loginController.loginClient(username,password,LoginActivity.this))
                        {
                            Toast.makeText(getApplicationContext(), "Logging in as Client", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), "Invalid username or password!", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case 1:
                        if(loginController.loginCourier(username,password,LoginActivity.this))
                        {
                            Toast.makeText(getApplicationContext(), "Logging in as Courier", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), "Invalid username or password!", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case 2:
                        if(loginController.loginWasher(username,password,LoginActivity.this))
                        {
                            Toast.makeText(getApplicationContext(), "Logging in as Washer", Toast.LENGTH_SHORT).show();
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