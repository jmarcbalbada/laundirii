package com.example.laundirii.model;
import android.content.Context;

import com.example.laundirii.database.Connect;
public class Washer {
    private int washerID;
    private String username;
    private String password;
    private String shopName;
    private String shopLocation;
    private String contactNo;
    private boolean status;
    Connect dbHelper;

    //constructors
    public Washer()
    {
        username = "";
        password = "";
        shopName  = "";
        shopLocation  = "";
        contactNo = "";
        status = false;
    }

    public Washer(String user,String pass,String name,String loc,String contact, boolean stat)
    {
        this.username = user;
        this.password = pass;
        this.shopName = name;
        this.shopLocation = loc;
        this.contactNo = contact;
        this.status = stat;
    }

    public boolean loginWasher(String username, String password, Context context)
    {
        dbHelper = new Connect(context);
        return dbHelper.loginWasher(username,password);
    }

    public boolean checkWasherByUsername(String username, Context context) {
        // Implement logic to check if a courier with the given username exists in the database
        // Example: Assuming you have a Connect class for database operations
        dbHelper = new Connect(context);
        return dbHelper.checkWasherByUsername(username);
    }

    // REGISTER

    public boolean insertWasher(String username, String password, String shopName, String shopLocation, String contactNo, int washerStatus, Context context)
    {
        dbHelper = new Connect(context);
        return dbHelper.insertWasher(username,password,shopName,shopLocation,contactNo,washerStatus);
    }

    @Override
    public String toString()
    {
        return "Shop name: " + shopName + "\n"
                + "Contact No: " + contactNo + "\n"
                + "Shop Location: " + shopLocation + "\n";
    }

}
