package com.example.laundirii.view;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.laundirii.R;
import com.example.laundirii.controller.RegisterAndLoginController;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class RegisterActivity extends AppCompatActivity {

    //controller
    RegisterAndLoginController registerController;

    //buttons
    RadioButton radioButtonClient;
    RadioButton radioButtonCourier;
    RadioButton radioButtonWasher;
    Button cancelButton;
    Button submitButton;

    TextView generalErrorText;

    //entries
    private TextInputEditText firstName;
    private TextInputEditText lastName;
    private TextInputEditText username;
    private TextInputEditText password;
    private TextInputEditText confirmPassword;
    private TextInputEditText phoneNo;
    private TextInputEditText address;

    private TextInputLayout phoneLayout;
    private TextInputLayout firstNameLayout;
    private TextInputLayout lastNameLayout;
    private TextInputLayout addressLayout;
    boolean radioButtonRequiredChecked = false;

    // 0 - Client, 1 - Courier , 2 - Washer
    int currentTypeOfUser;

    // copy string
    String fname = "", lname = "", uname = "", pword = "", conpword = "", phone = "", addr = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // instantiate
        registerController = new RegisterAndLoginController();
        currentTypeOfUser = 0;

        radioButtonClient = findViewById(R.id.radioButtonClient);
        radioButtonCourier = findViewById(R.id.radioButtonCourier);
        radioButtonWasher = findViewById(R.id.radioButtonWasher);
        cancelButton = findViewById(R.id.cancelSignupButton);
        submitButton = findViewById(R.id.submitSignupButton);
        generalErrorText = findViewById(R.id.generalErrorTxt);

        //initialize radioButtonClient to default
        radioButtonClient.setChecked(true);

        // entries
        firstName = findViewById(R.id.firstNameText);
        lastName = findViewById(R.id.lastNameText);
        username = findViewById(R.id.usernameSignupText);
        password = findViewById(R.id.passwordSignupText);
        confirmPassword = findViewById(R.id.confirmPasswordSignupText);
        phoneNo = findViewById(R.id.phoneNoSignupText);
        address = findViewById(R.id.addressSignupText);

        // additional
        phoneLayout = findViewById(R.id.phoneNoSignupLayout);
        firstNameLayout = findViewById(R.id.firstNameLayout);
        lastNameLayout = findViewById(R.id.lastNameLayout);
        addressLayout = findViewById(R.id.addressSignupLayout);

        //listeners
        radioButtonClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                radioButtonRequiredChecked = setRadioButtons(0) == true ? true : false;
                currentTypeOfUser = 0;
                refreshUI(0);
            }
        });

        radioButtonCourier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                radioButtonRequiredChecked = setRadioButtons(1) == true ? true : false;
                currentTypeOfUser = 1;
                refreshUI(1);
            }
        });

        radioButtonWasher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                radioButtonRequiredChecked = setRadioButtons(2) == true ? true : false;
                currentTypeOfUser = 2;
                refreshUI(2);
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                builder.setTitle("Warning")
                        .setMessage("Are you sure you want to cancel? Any changes will be lost.")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // User clicked Yes, navigate to LoginActivity
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // User clicked No, do nothing or handle as needed
                            }
                        });

                // Show the alert dialog
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                builder.setTitle("Warning")
                        .setMessage("Are you sure you want to submit? Please review all changes")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // User clicked Yes, navigate to LoginActivity
                                boolean isSignupValid = isValidRegister() == true ? true : false;
                                if(isSignupValid)
                                {
                                    Toast.makeText(getApplicationContext(), "Created successfully!", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                }
                                else
                                {
                                    Toast.makeText(getApplicationContext(), "Something went wrong!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // User clicked No, do nothing or handle as needed
                            }
                        });

                // Show the alert dialog
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

    }

    public boolean setRadioButtons(int state)
    {
        boolean isChecked = false;
        // 0 - Client , 1 - Courier, - 2 Washers
        switch(state)
        {
            case 0:
                radioButtonCourier.setChecked(false);
                radioButtonWasher.setChecked(false);
                isChecked = true;
                break;
            case 1:
                radioButtonClient.setChecked(false);
                radioButtonWasher.setChecked(false);
                isChecked = true;
                break;
            case 2:
                radioButtonClient.setChecked(false);
                radioButtonCourier.setChecked(false);
                isChecked = true;
                break;
        }

        return isChecked;
    }

    public void refreshUI(int typeOfUser)
    {
        switch(typeOfUser)
        {
                // Client
            case 0:
                addressLayout.setHint("Address");
                generalErrorText.setText("");
                lastNameLayout.setVisibility(View.VISIBLE);
                firstNameLayout.setHint("First Name");
                phoneLayout.setHint("Phone No.");
                addressLayout.setVisibility(View.VISIBLE);
                lastName.setError(null);
                break;
                // Courier
            case 1:
                generalErrorText.setText("");
                firstNameLayout.setHint("First Name");
                addressLayout.setVisibility(View.VISIBLE);
                addressLayout.setHint("Plate No.");
                lastNameLayout.setVisibility(View.VISIBLE);
                break;
                // Washer
            case 2:
                generalErrorText.setText("");
                addressLayout.setHint("Address");
                lastName.setError(null);
                address.setError(null);
                firstNameLayout.setHint("Shop Name");
                lastNameLayout.setVisibility(View.GONE);
                break;
        }
    }

    public boolean isValidRegister()
    {
        boolean valid = true;

        fname = firstName.getText().toString();
        lname = lastName.getText().toString();
        uname = username.getText().toString();
        pword = password.getText().toString();
        conpword = confirmPassword.getText().toString();
        phone = phoneNo.getText().toString();
        addr = address.getText().toString();
        generalErrorText.setText("");

        // check entries if isEmpty
        if(fname.isEmpty()){ firstName.setError("Required Field"); valid = false;}
        if((lname.isEmpty() && currentTypeOfUser == 0) || (lname.isEmpty() && currentTypeOfUser == 1)){ lastName.setError("Required Field"); valid = false;}
        if(uname.isEmpty()){ username.setError("Required Field"); valid = false;}
        if(pword.isEmpty()){ password.setError("Required Field"); valid = false;}
        if(conpword.isEmpty()){ confirmPassword.setError("Required Field"); valid = false;}
        if(phone.isEmpty()){ phoneNo.setError("Required Field"); valid = false;}
        if(addr.isEmpty() && currentTypeOfUser == 0){ address.setError("Required Field"); valid = false;}

        //check pword == conpword
        if(!pword.equals(conpword))
        {
            valid = false;
            generalErrorText.setText("Password must match the confirmed password!");
            password.setError("Must match Confirmed Password!");
        }

        // entry checker
        if(valid)
        {
            int isEntryExist = registerController.typeOfUser(uname,this) == -1 ? -1 : 0;
            // if -1 then no entry then safe to add
            if(isEntryExist == -1)
            {
                switch (currentTypeOfUser) {
                    // 0 - Client , 1 - Courier , 2 - Washer
                    case 0: {
                        boolean success = registerController.registerClient(uname, pword, fname + " " + lname, phone, addr, -1, this);
                        break;
                    }
                    case 1: {
                        boolean success = registerController.registerCourier(uname, pword, fname + " " + lname, phone, addr, 0, this);
                        break;
                    }
                    case 2: {
                        boolean success = registerController.registerWasher(uname, pword, fname, addr, phone, 0, this);
                        break;
                    }
                }
            }
            else
            {
                generalErrorText.setText("Username is already taken! Try others.");
                username.setError("Already in use!");
                valid = false;
            }
        }

        return valid;
    }
}