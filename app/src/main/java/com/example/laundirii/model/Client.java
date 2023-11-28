package com.example.laundirii.model;

import android.content.Context;

import com.example.laundirii.database.Connect;
import com.example.laundirii.view.LoginActivity;

public class Client {
    private String customerID;
    private String username;
    private String password;
    private String name;
    private String contactNo;
    private String address;
    private int paymentInfo;
    private Connect dbHelper;


    //constructors
    public Client()
    {
        customerID = "";
        username = "";
        password = "";
        name  = "";
        contactNo = "";
        address = "";
        paymentInfo = 0;
    }

    public Client(String cusID,String user,String pass,String name,String contact,String address, int payment)
    {
        this.customerID = cusID;
        this.username = user;
        this.password = pass;
        this.name = name;
        this.contactNo = contact;
        this.address = address;
        this.paymentInfo = payment;
    }

    public boolean loginClient(String username, String password, Context context)
    {
        dbHelper = new Connect(context);
        return dbHelper.loginClient(username,password);
    }

    public boolean checkClientByUsername(String username, Context context) {
        // Implement logic to check if a client with the given username exists in the database
        // Example: Assuming you have a Connect class for database operations
        dbHelper = new Connect(context);
        return dbHelper.checkClientByUsername(username);
    }

    // REGISTER
    public boolean insertClient(String username, String password, String name, String contactNo, String address, int paymentInfo, Context context)
    {
        dbHelper = new Connect(context);
        return dbHelper.insertClient(username,password,name,contactNo,address,paymentInfo);
    }

}
